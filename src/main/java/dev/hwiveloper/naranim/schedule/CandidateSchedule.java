package dev.hwiveloper.naranim.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.hwiveloper.naranim.common.LogUtil;
import dev.hwiveloper.naranim.domain.common.Election;
import dev.hwiveloper.naranim.domain.election.Candidate;
import dev.hwiveloper.naranim.domain.election.CandidatePK;
import dev.hwiveloper.naranim.domain.election.PreCandidate;
import dev.hwiveloper.naranim.domain.election.PreCandidatePK;
import dev.hwiveloper.naranim.repository.CandidateRepository;
import dev.hwiveloper.naranim.repository.ElectionRepository;
import dev.hwiveloper.naranim.repository.PreCandidateRepository;
import lombok.extern.slf4j.Slf4j;

/*
 * CandidateSchedule
 * 후보자 정보 조회 후 DB에 저장한다.
 * 
 * 매일 02:15:00 => getPoelpcddRegistSttusInfoInqire (예비후보자 정보 조회)
 * 매일 02:00:00 => getPofelcddRegistSttusInfoInqire (후보자 정보 조회)
 */
@Component
@Slf4j
public class CandidateSchedule {

	@Value("${keys.poll-place-service}")
	String POLL_PLACE_SERVICE;

	@Autowired
	CandidateRepository candidateRepo;
		
	@Autowired
	PreCandidateRepository preCandidateRepo;

	@Autowired
	ElectionRepository electionRepo;

	/**
	 * getPofelcddRegistSttusInfoInqire
	 * 후보자 정보
	 */
	@Scheduled(cron="0 0 2 * * *")
	public void getPofelcddRegistSttusInfoInqire() {
		try {
			// sgId, sgTypeCode 가져오기 (선거리스트)
			List<Election> electionList = (List<Election>) electionRepo.findAll();

			// 본서비스 호출 (loop)
			for (Election election : electionList) {
				if (!election.getKey().getSgId().toString().equals("20200415")) {
					continue;
				}
				if (election.getKey().getSgTypeCode().toString().equals("0")) {
					continue;
				}

				// URL 생성
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/PofelcddInfoInqireService/getPofelcddRegistSttusInfoInqire"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + POLL_PLACE_SERVICE); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("9999", "UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
				urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(election.getKey().getSgId().toString(), "UTF-8"));
				urlBuilder.append("&" + URLEncoder.encode("sgTypecode","UTF-8") + "=" + URLEncoder.encode(election.getKey().getSgTypeCode().toString(), "UTF-8"));
				URL url = new URL(urlBuilder.toString());

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");

				BufferedReader rd;
				if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}

				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}

				rd.close();
				conn.disconnect();

				// 후처리
				JSONObject tmpRes = XML.toJSONObject(sb.toString());
				
				if (!tmpRes.has("response")) {
					continue;
				}
				
				JSONObject tmpJson = tmpRes.getJSONObject("response")
						.getJSONObject("body")
						.getJSONObject("items");
						
				List<Candidate> listCandidate = new ArrayList<Candidate>();

				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);

						Candidate itemCndd = new Candidate();
						CandidatePK keyCndd = new CandidatePK();

						keyCndd.setGiho(item.get("giho").toString().equals("") ? 99 : item.getInt("giho"));
						keyCndd.setSgId(item.get("sgId").toString());
						keyCndd.setSgTypeCode(item.get("sgTypecode").toString());
						keyCndd.setHuboId(item.get("huboid").toString());

						itemCndd.setKey(keyCndd);
						itemCndd.setSggName(item.getString("sggName"));
						itemCndd.setSdName(item.getString("sdName"));
						itemCndd.setWiwName(item.getString("wiwName"));
						itemCndd.setGihoSangse(item.isNull("gihoSangse") ? "" : item.getString("gihoSangse"));
						itemCndd.setJdName(item.getString("jdName"));
						itemCndd.setName(item.getString("name"));
						itemCndd.setHanjaName(item.getString("hanjaName"));
						itemCndd.setGender(item.getString("gender"));
						itemCndd.setBirthday(item.get("birthday").toString());
						itemCndd.setAge(item.getInt("age"));
						itemCndd.setAddr(item.getString("addr"));
						itemCndd.setJobId(item.get("jobId").toString());
						itemCndd.setJob(item.getString("job"));
						itemCndd.setEduId(item.get("eduId").toString());
						itemCndd.setEdu(item.getString("edu"));
						itemCndd.setCareer1(item.isNull("career1") ? "" : item.getString("career1"));
						itemCndd.setCareer2(item.isNull("career2") ? "" : item.getString("career2"));
						itemCndd.setStatus(item.getString("status"));
						
						if (item.get("sgId").toString().equals("20200415")) {
							itemCndd.setImage(getPreHuboImageUrl(item.get("sgId").toString(), item.get("huboid").toString()));
						}

						listCandidate.add(itemCndd);
					}
				} else
					if (tmpJson.get("item") instanceof JSONObject) {
						JSONObject item = tmpJson.getJSONObject("item");
						Candidate itemCndd = new Candidate();
						CandidatePK keyCndd = new CandidatePK();

						keyCndd.setGiho(item.get("giho").toString().equals("") ? 99 : item.getInt("giho"));
						keyCndd.setSgId(item.get("sgId").toString());
						keyCndd.setSgTypeCode(item.get("sgTypecode").toString());
						keyCndd.setHuboId(item.get("huboid").toString());

						itemCndd.setKey(keyCndd);
						itemCndd.setSdName(item.getString("sdName"));
						itemCndd.setWiwName(item.getString("wiwName"));
						itemCndd.setGihoSangse(item.isNull("gihoSangse") ? "" : item.getString("gihoSangse"));
						itemCndd.setJdName(item.getString("jdName"));
						itemCndd.setName(item.getString("name"));
						itemCndd.setHanjaName(item.getString("hanjaName"));
						itemCndd.setGender(item.getString("gender"));
						itemCndd.setBirthday(item.get("birthday").toString());
						itemCndd.setAge(item.getInt("age"));
						itemCndd.setAddr(item.getString("addr"));
						itemCndd.setJobId(item.get("jobId").toString());
						itemCndd.setJob(item.getString("job"));
						itemCndd.setEduId(item.get("eduId").toString());
						itemCndd.setEdu(item.getString("edu"));
						itemCndd.setCareer1(item.isNull("career1") ? "" : item.getString("career1"));
						itemCndd.setCareer2(item.isNull("career2") ? "" : item.getString("career2"));
						itemCndd.setStatus(item.getString("status"));
						
						if (item.get("sgId").toString().equals("20200415")) {
							itemCndd.setImage(getPreHuboImageUrl(item.get("sgId").toString(), item.get("huboid").toString()));
						}

						listCandidate.add(itemCndd);
					}

				candidateRepo.saveAll(listCandidate);
			}

			LogUtil.scheduleSccssLog(log, new Object() {});
		} catch (IOException e) {
			LogUtil.scheduleErrorLog(log, new Object() {}, e.getMessage());
		}
	}
	/**
	 * getPoelpcddRegistSttusInfoInqire
	 * 예비후보자 정보
	 */
//	@Scheduled(cron="0 15 2 * * *")
	public void getPoelpcddRegistSttusInfoInqire() {
		try {
			// sgId, sgTypeCode 가져오기 (선거리스트)
			List<Election> electionList = (List<Election>) electionRepo.findAll();

			// 본서비스 호출 (loop)
			for (Election election : electionList) {
				// 2020년 총선만 호출한다.
				if (! election.getKey().getSgId().toString().equals("20200415")) {
					continue;
				}
				
				if (election.getKey().getSgTypeCode().toString().equals("0")) {
					continue;
				}

				// URL 생성
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/PofelcddInfoInqireService/getPoelpcddRegistSttusInfoInqire"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + POLL_PLACE_SERVICE); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("9999", "UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
				urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(election.getKey().getSgId().toString(), "UTF-8"));
				urlBuilder.append("&" + URLEncoder.encode("sgTypecode","UTF-8") + "=" + URLEncoder.encode(election.getKey().getSgTypeCode().toString(), "UTF-8"));
				URL url = new URL(urlBuilder.toString());

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");

				BufferedReader rd;
				if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}

				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}

				rd.close();
				conn.disconnect();
				
				log.info("DEBUG 키값 :: " + election.getKey().getSgId() + " :: " + election.getKey().getSgTypeCode());
				
				// 후처리
				if ( XML.toJSONObject(sb.toString())
						.getJSONObject("response")
						.getJSONObject("body")
						.get("items").equals("")
				||  ! XML.toJSONObject(sb.toString())
						.getJSONObject("response")
						.getJSONObject("body")
						.has("items")
						) {
					log.info("데이터 없음 :: " + election.getKey().getSgId() + " :: " + election.getKey().getSgTypeCode());
					continue;
				}
				JSONObject tmpJson = XML.toJSONObject(sb.toString())
						.getJSONObject("response")
						.getJSONObject("body")
						.getJSONObject("items");
				List<PreCandidate> listCandidate = new ArrayList<PreCandidate>();

				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);

						PreCandidate itemCndd = new PreCandidate();
						PreCandidatePK keyCndd = new PreCandidatePK();

						keyCndd.setSgId(item.get("sgId").toString());
						keyCndd.setSgTypeCode(item.get("sgTypecode").toString());
						keyCndd.setHuboId(item.get("huboid").toString());

						itemCndd.setKey(keyCndd);
						itemCndd.setSggName(item.getString("sggName"));
						itemCndd.setSdName(item.getString("sdName"));
						itemCndd.setWiwName(item.getString("wiwName"));
						itemCndd.setJdName(item.getString("jdName"));
						itemCndd.setName(item.getString("name"));
						itemCndd.setHanjaName(item.getString("hanjaName"));
						itemCndd.setGender(item.getString("gender"));
						itemCndd.setBirthday(item.get("birthday").toString());
						itemCndd.setAge(item.getInt("age"));
						itemCndd.setAddr(item.getString("addr"));
						itemCndd.setJobId(item.get("jobId").toString());
						itemCndd.setJob(item.getString("job"));
						itemCndd.setEduId(item.get("eduId").toString());
						itemCndd.setEdu(item.getString("edu"));
						itemCndd.setCareer1(item.isNull("career1") ? "" : item.getString("career1"));
						itemCndd.setCareer2(item.isNull("career2") ? "" : item.getString("career2"));
						itemCndd.setStatus(item.getString("status"));
						itemCndd.setImage(getPreHuboImageUrl(item.get("sgId").toString(), item.get("huboid").toString()));

						listCandidate.add(itemCndd);
					}
				} else if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject item = tmpJson.getJSONObject("item");
					PreCandidate itemCndd = new PreCandidate();
					PreCandidatePK keyCndd = new PreCandidatePK();

					keyCndd.setSgId(item.get("sgId").toString());
					keyCndd.setSgTypeCode(item.get("sgTypecode").toString());
					keyCndd.setHuboId(item.get("huboid").toString());

					itemCndd.setKey(keyCndd);
					itemCndd.setSdName(item.getString("sdName"));
					itemCndd.setWiwName(item.getString("wiwName"));
					itemCndd.setJdName(item.getString("jdName"));
					itemCndd.setName(item.getString("name"));
					itemCndd.setHanjaName(item.getString("hanjaName"));
					itemCndd.setGender(item.getString("gender"));
					itemCndd.setBirthday(item.get("birthday").toString());
					itemCndd.setAge(item.getInt("age"));
					itemCndd.setAddr(item.getString("addr"));
					itemCndd.setJobId(item.get("jobId").toString());
					itemCndd.setJob(item.getString("job"));
					itemCndd.setEduId(item.get("eduId").toString());
					itemCndd.setEdu(item.getString("edu"));
					itemCndd.setCareer1(item.isNull("career1") ? "" : item.getString("career1"));
					itemCndd.setCareer2(item.isNull("career2") ? "" : item.getString("career2"));
					itemCndd.setStatus(item.getString("status"));
					itemCndd.setImage(getPreHuboImageUrl(item.get("sgId").toString(), item.get("huboid").toString()));

					listCandidate.add(itemCndd);
				}

				preCandidateRepo.saveAll(listCandidate);
			}
			
			log.info("[SCHEDULE SUCCESS] getPoelpcddRegistSttusInfoInqire");
		} catch (IOException e) {
			log.error("[SCHEDULE FAILED] getPoelpcddRegistSttusInfoInqire");
			log.error(e.getMessage());
		}
	}
	
	public String getPreHuboImageUrl(String sgId, String huboId) {
		try {
			Connection.Response response = Jsoup.connect("http://info.nec.go.kr/electioninfo/candidate_detail_info.xhtml?electionId=00"+sgId+"&huboId="+huboId)
												.method(Connection.Method.GET)
												.execute();
			Document huboDocument = response.parse();
			Element imgEl = huboDocument.select(".pic img").first();
			String imgUrl = imgEl.attr("src");
//			String gsgCode = imgUrl.split("/")[2];
//			String sungeoguCode = gsgCode.substring(3);
//			http://info.nec.go.kr/photo_20200415/Gsg1113/Hb100137164/gicho/100137164.JPG
			if (imgUrl.equals("") || imgUrl == null) {
				return "";
			}
			return imgUrl;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
