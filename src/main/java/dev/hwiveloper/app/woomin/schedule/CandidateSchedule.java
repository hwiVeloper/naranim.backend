package dev.hwiveloper.app.woomin.schedule;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.hwiveloper.app.woomin.domain.common.Candidate;
import dev.hwiveloper.app.woomin.domain.common.CandidatePK;
import dev.hwiveloper.app.woomin.domain.common.Election;
import dev.hwiveloper.app.woomin.repository.CandidateRepository;
import dev.hwiveloper.app.woomin.repository.ElectionRepository;

/*
 * CandidateSchedule
 * 후보자 정보 조회 후 DB에 저장한다.
 * 
 * 매일 0?:??:?? => getPoelpcddRegistSttusInfoInqire (예비후보자 정보 조회)
 * 매일 02:00:00 => getPofelcddRegistSttusInfoInqire (후보자 정보 조회) 
 */
@Component
public class CandidateSchedule {
	@Value("${keys.poll-place-service}")
	String POLL_PLACE_SERVICE;
	
	@Autowired
	CandidateRepository candidateRepo;
	
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
				JSONObject tmpJson = XML.toJSONObject(sb.toString())
						.getJSONObject("response")
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

						keyCndd.setNum(item.getInt("num"));
						keyCndd.setSgId(item.get("sgId").toString());
						keyCndd.setSgTypeCode(item.get("sgTypecode").toString());
						keyCndd.setHuboId(item.get("huboId").toString());

						itemCndd.setKey(keyCndd);
						itemCndd.setSdName(item.getString("sdName"));
						itemCndd.setWiwName(item.getString("wiwName"));
						itemCndd.setGiho(item.getInt("giho"));
						itemCndd.setGihoSangse(item.isNull("gihoSangse") ? "" : item.getString("gihoSangse"));
						itemCndd.setJdName(item.getString("jdName"));
						itemCndd.setName(item.getString("name"));
						itemCndd.setHanjaName(item.getString("hanjaName"));
						itemCndd.setGender(item.getString("gender"));
						itemCndd.setBirthday(item.getString("birthday"));
						itemCndd.setAge(item.getInt("age"));
						itemCndd.setAddr(item.getString("addr"));
						itemCndd.setJobId(item.get("jobId").toString());
						itemCndd.setJob(item.getString("job"));
						itemCndd.setEduId(item.get("eduId").toString());
						itemCndd.setEdu(item.getString("edu"));
						itemCndd.setCareer1(item.isNull("career1") ? "" : item.getString("career1"));
						itemCndd.setCareer2(item.isNull("career2") ? "" : item.getString("career2"));
						itemCndd.setStatus(item.getString("status"));
						
						listCandidate.add(itemCndd);
					}
				} else
				if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject item = tmpJson.getJSONObject("item");
					Candidate itemCndd = new Candidate();
					CandidatePK keyCndd = new CandidatePK();

					keyCndd.setNum(item.getInt("num"));
					keyCndd.setSgId(item.get("sgId").toString());
					keyCndd.setSgTypeCode(item.get("sgTypecode").toString());
					keyCndd.setHuboId(item.get("huboId").toString());

					itemCndd.setKey(keyCndd);
					itemCndd.setSdName(item.getString("sdName"));
					itemCndd.setWiwName(item.getString("wiwName"));
					itemCndd.setGiho(item.getInt("giho"));
					itemCndd.setGihoSangse(item.isNull("gihoSangse") ? "" : item.getString("gihoSangse"));
					itemCndd.setJdName(item.getString("jdName"));
					itemCndd.setName(item.getString("name"));
					itemCndd.setHanjaName(item.getString("hanjaName"));
					itemCndd.setGender(item.getString("gender"));
					itemCndd.setBirthday(item.getString("birthday"));
					itemCndd.setAge(item.getInt("age"));
					itemCndd.setAddr(item.getString("addr"));
					itemCndd.setJobId(item.get("jobId").toString());
					itemCndd.setJob(item.getString("job"));
					itemCndd.setEduId(item.get("eduId").toString());
					itemCndd.setEdu(item.getString("edu"));
					itemCndd.setCareer1(item.isNull("career1") ? "" : item.getString("career1"));
					itemCndd.setCareer2(item.isNull("career2") ? "" : item.getString("career2"));
					itemCndd.setStatus(item.getString("status"));

					listCandidate.add(itemCndd);
				}

				candidateRepo.saveAll(listCandidate);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
