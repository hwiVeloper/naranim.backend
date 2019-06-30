package dev.hwiveloper.app.woomin.schedule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.hwiveloper.app.woomin.common.LogUtil;
import dev.hwiveloper.app.woomin.domain.assembly.Orig;
import dev.hwiveloper.app.woomin.domain.assembly.Poly;
import dev.hwiveloper.app.woomin.domain.common.Edu;
import dev.hwiveloper.app.woomin.domain.common.EduPK;
import dev.hwiveloper.app.woomin.domain.common.Election;
import dev.hwiveloper.app.woomin.domain.common.ElectionPK;
import dev.hwiveloper.app.woomin.domain.common.Gusigun;
import dev.hwiveloper.app.woomin.domain.common.GusigunPK;
import dev.hwiveloper.app.woomin.domain.common.Job;
import dev.hwiveloper.app.woomin.domain.common.JobPK;
import dev.hwiveloper.app.woomin.domain.common.Party;
import dev.hwiveloper.app.woomin.domain.common.PartyPK;
import dev.hwiveloper.app.woomin.domain.common.Sungeogu;
import dev.hwiveloper.app.woomin.domain.common.SungeoguPK;
import dev.hwiveloper.app.woomin.repository.EduRepository;
import dev.hwiveloper.app.woomin.repository.ElectionRepository;
import dev.hwiveloper.app.woomin.repository.GusigunRepository;
import dev.hwiveloper.app.woomin.repository.JobRepository;
import dev.hwiveloper.app.woomin.repository.OrigRepository;
import dev.hwiveloper.app.woomin.repository.PartyRepository;
import dev.hwiveloper.app.woomin.repository.PolyRepository;
import dev.hwiveloper.app.woomin.repository.ReeleGbnRepository;
import dev.hwiveloper.app.woomin.repository.SungeoguRepository;

/*
 * CodeSchedule
 * 각종 코드성 정보에 관한 API 호출 후 DB에 저장한다.
 * 
 * 매일 00:30:00 => getPolySearch (정당 검색)
 * 매일 00:31:00 => getLocalSearch (지역 검색)
 * 매일 00:35:00 => getCommonSgCodeList (선거코드)
 * 매일 00:40:00 => getCommonGusigunCodeList (구시군코드)
 * 매일 00:50:00 => getCommonSggCodeList (선거구코드)
 * 매일 01:00:00 => getCommonPartyCodeList (정당코드)
 * 매일 01:05:00 => getCommonJobCodeList (직업코드)
 * 매일 01:10:00 => getCommonEduBckgrdCodeList (학력코드)
 */
@Component
public class CodeSchedule {
	Logger logger = LoggerFactory.getLogger(CodeSchedule.class);
	
	@Value("${keys.national-assembly-info-service}")
	String NATIONAL_ASSEMBLY_INFO_SERVICE;

	@Value("${keys.common-code-service}")
	String COMMON_CODE_SERVICE;

	@Autowired
	PolyRepository polyRepo;

	@Autowired
	OrigRepository origRepo;

	@Autowired
	ReeleGbnRepository reeleGbnRepo;
	
	@Autowired
	ElectionRepository electionRepo;
	
	@Autowired
	GusigunRepository gusigunRepo;
	
	@Autowired
	SungeoguRepository sungeoguRepo;
	
	@Autowired
	PartyRepository partyRepo;
	
	@Autowired
	JobRepository jobRepo;

	@Autowired
	EduRepository eduRepo;

	/**
	 * getPolySearch
	 * 정당 검색
	 */
	@Scheduled(cron="0 30 0 * * *")
	public void getPolySearch() {
		try {
			// URL 생성
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9710000/NationalAssemblyInfoService/getPolySearch"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + NATIONAL_ASSEMBLY_INFO_SERVICE); /*Service Key*/
			URL url = new URL(urlBuilder.toString());

			// API 호출
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			BufferedReader rd;
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
			JSONArray itemJson = XML.toJSONObject(sb.toString())
					.getJSONObject("response")
					.getJSONObject("body")
					.getJSONObject("items")
					.getJSONArray("item");
			List<Poly> listPoly = new ArrayList<Poly>();

			for (int i = 0; i < itemJson.length(); i++) {
				JSONObject item = itemJson.getJSONObject(i);

				Poly poly = new Poly();
				poly.setPolyCd(item.get("polyCd").toString());
				poly.setPolyNm(item.getString("polyNm"));
				listPoly.add(poly);
			}

			polyRepo.saveAll(listPoly);
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}

	/**
	 * getLocalSearch
	 * 지역 검색
	 */
	@Scheduled(cron="0 1 0 * * *")
	public void getLocalSearch() {
		try {
			// 상위지역코드 조회
			List<Orig> listUpOrig = origRepo.findByUpOrigCd(null);

			for (Orig orig : listUpOrig) {
				// URL 생성
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9710000/NationalAssemblyInfoService/getLocalSearch"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + NATIONAL_ASSEMBLY_INFO_SERVICE); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
				urlBuilder.append("&" + URLEncoder.encode("up_orig_cd","UTF-8") + "=" + URLEncoder.encode(orig.getOrigCd(), "UTF-8")); /*시도코드*/
				URL url = new URL(urlBuilder.toString());

				// API 호출
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");

				BufferedReader rd;
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
				List<Orig> listOrig = new ArrayList<Orig>();

				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);
						Orig itemOrig = new Orig();
						itemOrig.setOrigCd(item.get("origCd").toString());
						itemOrig.setOrigNm(item.getString("origNm"));
						itemOrig.setUpOrigCd(orig.getOrigCd());
						listOrig.add(itemOrig);
					}
				} else
				if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject itemJson = tmpJson.getJSONObject("item");
					Orig itemOrig = new Orig();
					itemOrig.setOrigCd(itemJson.get("origCd").toString());
					itemOrig.setOrigNm(itemJson.getString("origNm"));
					itemOrig.setUpOrigCd(orig.getOrigCd());
					listOrig.add(itemOrig);
				}

				origRepo.saveAll(listOrig);
			}
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}

	/**
	 * getCommonSgCodeList
	 * 선거코드
	 */
	@Scheduled(cron="0 35 0 * * *")
	public void getCommonSgCodeList() {
		try {
			//API 호출
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/CommonCodeService/getCommonSgCodeList"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + COMMON_CODE_SERVICE); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수*/
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			
			BufferedReader rd;
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
			JSONArray tmpJson = XML.toJSONObject(sb.toString())
					.getJSONObject("response")
					.getJSONObject("body")
					.getJSONObject("items")
					.getJSONArray("item");
			List<Election> listElection = new ArrayList<Election>();
			
			for (int i = 0; i < tmpJson.length(); i++) {
				JSONObject tmpObj = tmpJson.getJSONObject(i);
				Election itemElection = new Election();
				
				ElectionPK keyElection = new ElectionPK();
				keyElection.setSgId(tmpObj.get("sgId").toString());
				keyElection.setSgTypeCode(tmpObj.get("sgTypecode").toString());
				
				itemElection.setKey(keyElection);
				itemElection.setSgName(tmpObj.getString("sgName"));
				itemElection.setSgVoteDate(tmpObj.get("sgVotedate").toString());
				
				listElection.add(itemElection);
			}
			
			electionRepo.saveAll(listElection);
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}
	
	/**
	 * getCommonGusigunCodeList
	 * 구시군코드
	 */
	@Scheduled(cron="0 40 0 * * *")
	public void getCommonGusigunCodeList () {
		try {
			List<String> electionList = (List<String>) electionRepo.findDistinctKeySgId();
			
			for (String election : electionList) {
				// API 호출
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/CommonCodeService/getCommonGusigunCodeList"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + COMMON_CODE_SERVICE); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(election.toString(), "UTF-8"));
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				
				BufferedReader rd;
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
				JSONArray tmpJson = XML.toJSONObject(sb.toString())
						.getJSONObject("response")
						.getJSONObject("body")
						.getJSONObject("items")
						.getJSONArray("item");
				List<Gusigun> listGusigun = new ArrayList<Gusigun>();
				
				for (int i = 0; i < tmpJson.length(); i++) {
					JSONObject tmpObj = tmpJson.getJSONObject(i);
					Gusigun itemGusigun = new Gusigun();
					GusigunPK keyGusigun = new GusigunPK();
					
					keyGusigun.setSgId(tmpObj.get("sgId").toString());
					keyGusigun.setWOrder(tmpObj.getInt("wOrder"));
					
					itemGusigun.setKey(keyGusigun);
					itemGusigun.setSdName(tmpObj.getString("sdName").equals(null) ? null : tmpObj.getString("sdName"));
					itemGusigun.setWiwName(tmpObj.getString("wiwName"));
					
					listGusigun.add(itemGusigun);
				}
				
				gusigunRepo.saveAll(listGusigun);
			}
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}
	
	/**
	 * getCommonSggCodeList
	 * 선거구코드
	 */
	@Scheduled(cron="0 50 0 * * *")
	public void getCommonSggCodeList() {
		try {
			List<Election> electionList = (List<Election>) electionRepo.findAll();
			
			for (Election election : electionList) {
				// 선거종류코드 : 0 일 때, continue
				if (election.getKey().getSgTypeCode().toString().equals("0")) {
					continue;
				}
				
				// API 호출
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/CommonCodeService/getCommonSggCodeList"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + COMMON_CODE_SERVICE); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(election.getKey().getSgId().toString(), "UTF-8")); // 선거ID
				urlBuilder.append("&" + URLEncoder.encode("sgTypecode","UTF-8") + "=" + URLEncoder.encode(election.getKey().getSgTypeCode().toString(), "UTF-8")); // 선거종류코드
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				
				BufferedReader rd;
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
				List<Sungeogu> listSungeogu = new ArrayList<Sungeogu>();
				
				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);
						Sungeogu itemSungeogu = new Sungeogu();
						SungeoguPK keySungeogu = new SungeoguPK();
						
						keySungeogu.setSgId(item.get("sgId").toString());
						keySungeogu.setSgTypeCode(item.get("sgTypecode").toString());
						keySungeogu.setSggName(item.getString("sggName"));
						
						itemSungeogu.setKey(keySungeogu);
						itemSungeogu.setSdName(item.getString("sdName").equals(null) ? null : item.getString("sdName"));
						itemSungeogu.setWiwName(item.getString("wiwName").equals(null) ? null : item.getString("wiwName"));
						itemSungeogu.setSggJungsu(item.getInt("sggJungsu"));
						itemSungeogu.setSOrder(item.getInt("sOrder"));
						
						listSungeogu.add(itemSungeogu);
					}
				} else
				if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject itemJson = tmpJson.getJSONObject("item");
					Sungeogu itemSungeogu = new Sungeogu();
					SungeoguPK keySungeogu = new SungeoguPK();
					
					keySungeogu.setSgId(itemJson.get("sgId").toString());
					keySungeogu.setSgTypeCode(itemJson.get("sgTypecode").toString());
					keySungeogu.setSggName(itemJson.getString("sggName"));
					
					itemSungeogu.setKey(keySungeogu);
					itemSungeogu.setSdName(itemJson.getString("sdName").equals(null) ? null : itemJson.getString("sdName"));
					itemSungeogu.setWiwName(itemJson.getString("wiwName").equals(null) ? null : itemJson.getString("wiwName"));
					itemSungeogu.setSggJungsu(itemJson.getInt("sggJungsu"));
					itemSungeogu.setSOrder(itemJson.getInt("sOrder"));
					
					listSungeogu.add(itemSungeogu);
				}
				
				sungeoguRepo.saveAll(listSungeogu);
			}
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}
	
	/**
	 * getCommonPartyCodeList
	 * 정당코드
	 */
	@Scheduled(cron="0 0 1 * * *")
	public void getCommonPartyCodeList() {
		try {
			List<String> electionList = (List<String>) electionRepo.findDistinctKeySgId();
			
			for (String election : electionList) {				
				// API 호출
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/CommonCodeService/getCommonPartyCodeList"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + COMMON_CODE_SERVICE); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(election, "UTF-8")); // 선거ID
				URL url = new URL(urlBuilder.toString());

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				
				BufferedReader rd;
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
				List<Party> listParty = new ArrayList<Party>();
				
				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);
						Party itemParty = new Party();
						PartyPK keyParty = new PartyPK();
						
						keyParty.setSgId(item.get("sgId").toString());
						keyParty.setPOrder(item.getInt("pOrder"));
						
						itemParty.setKey(keyParty);
						itemParty.setJdName(item.getString("jdName").equals(null) ? null : item.getString("jdName"));
						
						listParty.add(itemParty);
					}
				} else
				if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject itemJson = tmpJson.getJSONObject("item");
					Party itemParty = new Party();
					PartyPK keyParty = new PartyPK();
					
					keyParty.setSgId(itemJson.get("sgId").toString());
					keyParty.setPOrder(itemJson.getInt("pOrder"));
					
					itemParty.setKey(keyParty);
					itemParty.setJdName(itemJson.getString("jdName").equals(null) ? null : itemJson.getString("jdName"));
					
					listParty.add(itemParty);
				}
				
				partyRepo.saveAll(listParty);
			}
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}
	
	/**
	 * getCommonJobCodeList
	 * 직업코드
	 */
	@Scheduled(cron="0 5 1 * * *")
	public void getCommonJobCodeList() {
		try {
			List<String> electionList = (List<String>) electionRepo.findDistinctKeySgId();
			
			for (String election : electionList) {				
				// API 호출
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/CommonCodeService/getCommonJobCodeList"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + COMMON_CODE_SERVICE); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(election, "UTF-8")); // 선거ID
				URL url = new URL(urlBuilder.toString());

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				
				BufferedReader rd;
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
				List<Job> listJob = new ArrayList<Job>();
				
				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);
						Job itemJob = new Job();
						JobPK keyJob = new JobPK();
						
						keyJob.setSgId(item.get("sgId").toString());
						keyJob.setJobId(item.get("jobId").toString());
						
						itemJob.setKey(keyJob);
						itemJob.setJobName(item.getString("jobName").equals(null) ? null : item.getString("jobName"));
						itemJob.setJOrder(item.get("jOrder") == null ? null : item.getInt("jOrder"));
						
						listJob.add(itemJob);
					}
				} else
				if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject itemJson = tmpJson.getJSONObject("item");
					Job itemJob = new Job();
					JobPK keyJob = new JobPK();
					
					keyJob.setSgId(itemJson.get("sgId").toString());
					keyJob.setJobId(itemJson.get("jobId").toString());
					
					itemJob.setKey(keyJob);
					itemJob.setJobName(itemJson.getString("jobName").equals(null) ? null : itemJson.getString("jobName"));
					itemJob.setJOrder(itemJson.get("jOrder") == null ? null : itemJson.getInt("jOrder"));
					
					listJob.add(itemJob);
				}
				
				jobRepo.saveAll(listJob);
			}
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}

	/**
	 * getCommonEduBckgrdCodeList
	 * 학력코드
	 */
	@Scheduled(cron="0 10 1 * * *")
	public void getCommonEduBckgrdCodeList() {
		try {
			List<String> electionList = (List<String>) electionRepo.findDistinctKeySgId();

			for (String election : electionList) {
				// API 호출
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/CommonCodeService/getCommonEduBckgrdCodeList"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + COMMON_CODE_SERVICE); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(election, "UTF-8")); // 선거ID
				URL url = new URL(urlBuilder.toString());

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");

				BufferedReader rd;
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
				List<Edu> listEdu = new ArrayList<Edu>();

				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);
						Edu itemEdu = new Edu();
						EduPK keyEdu = new EduPK();

						keyEdu.setSgId(item.get("sgId").toString());
						keyEdu.setEduId(item.get("eduId").toString());

						itemEdu.setKey(keyEdu);
						
//						itemEdu.setSgId(item.get("sgId").toString());
//						itemEdu.setEduId(item.get("eduId").toString());
						itemEdu.setEduName(item.getString("eduName").equals(null) ? null : item.getString("eduName"));
						itemEdu.setEOrder(item.get("eOrder") == null ? null : item.getInt("eOrder"));

						listEdu.add(itemEdu);
					}
				} else
				if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject itemJson = tmpJson.getJSONObject("item");
					Edu itemEdu = new Edu();
					EduPK keyEdu = new EduPK();

					keyEdu.setSgId(itemJson.get("sgId").toString());
					keyEdu.setEduId(itemJson.get("eduId").toString());

					itemEdu.setKey(keyEdu);
					
//					itemEdu.setSgId(itemJson.get("sgId").toString());
//					itemEdu.setEduId(itemJson.get("eduId").toString());
					itemEdu.setEduName(itemJson.getString("eduName").equals(null) ? null : itemJson.getString("eduName"));
					itemEdu.setEOrder(itemJson.get("eOrder") == null ? null : itemJson.getInt("eOrder"));

					listEdu.add(itemEdu);
				}

				eduRepo.saveAll(listEdu);
			}
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}
}
