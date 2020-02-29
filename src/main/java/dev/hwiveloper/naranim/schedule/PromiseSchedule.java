package dev.hwiveloper.naranim.schedule;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.hwiveloper.naranim.common.LogUtil;
import dev.hwiveloper.naranim.domain.common.Election;
import dev.hwiveloper.naranim.domain.election.Candidate;
import dev.hwiveloper.naranim.domain.election.Promise;
import dev.hwiveloper.naranim.repository.CandidateRepository;
import dev.hwiveloper.naranim.repository.ElectionRepository;
import dev.hwiveloper.naranim.repository.PromiseRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PromiseSchedule {

	@Value("${keys.common-code-service}")
	String GOV_API_CODE;

	@Autowired
	PromiseRepository promiseRepo;

	@Autowired
	ElectionRepository electionRepo;

	@Autowired
	CandidateRepository candidateRepository;

	@Scheduled(cron="0 15 3 * * *")
	public void elecPrmsInfoInqireService() {
		try {
			List<Election> electionList = (List<Election>) electionRepo.findAll();

			for (Election election : electionList) {
				
				log.info("=====================================================================");
				log.info(election.getKey().getSgId() + " :: " + election.getKey().getSgTypeCode());

				List<Candidate> candidateList = candidateRepository.findByElection(election.getKey().getSgId(), election.getKey().getSgTypeCode());

				for (Candidate candidate : candidateList) {
					
					log.info(" > " + candidate.getKey().getHuboId());
					
					// URL 생성
					StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/ElecPrmsInfoInqireService/getCnddtElecPrmsInfoInqire"); /*URL*/
					urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + GOV_API_CODE); /*Service Key*/
					urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
					urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("9999", "UTF-8")); /*목록 건수*/
					urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(candidate.getKey().getSgId(), "UTF-8")); /*선거ID*/
					urlBuilder.append("&" + URLEncoder.encode("sgTypecode","UTF-8") + "=" + URLEncoder.encode(candidate.getKey().getSgTypeCode(), "UTF-8")); /*선거종류코드*/
					urlBuilder.append("&" + URLEncoder.encode("cnddtId","UTF-8") + "=" + URLEncoder.encode(candidate.getKey().getHuboId(), "UTF-8")); /*후보자ID*/
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
					JSONObject res = XML.toJSONObject(sb.toString());
					if (!res.has("response")) {
						continue;
					}
					
					JSONObject response = res.getJSONObject("response");
					
					if (!response.has("body")) {
						continue;
					}
					
					JSONObject body = response.getJSONObject("body");
					
					if (body.getInt("totalCount") == 0) {
						continue;
					}
					
					JSONObject tmpItems = body.getJSONObject("items");
					
					if (tmpItems.isEmpty()) {
						continue;
					}
					
					List<Promise> listPromise = new ArrayList<Promise>();

					// JSONArray | JSONObject 케이스에 따라 분기처리
					if (tmpItems.get("item") instanceof JSONArray) {
						JSONArray itemJson = tmpItems.getJSONArray("item");
						for (int i = 0; i < itemJson.length(); i++) {
							JSONObject item = itemJson.getJSONObject(i);
							Promise promise = new Promise();
							
							promise.setHuboId(item.getString("cnddtId"));
							promise.setSgId(item.getString("sgId"));
							promise.setSgTypeCode(item.getString("sgTypecode"));
							promise.setSggName(item.getString("sggName"));
							promise.setSidoName(item.getString("sidoName"));
							promise.setWiwName(item.getString("wiwName"));
							promise.setPartyName(item.getString("partyName"));
							promise.setKrName(item.getString("krName"));
							promise.setCnName(item.getString("cnName"));
							
							for (int p = 1; p <= item.getInt("prmsCnt"); p++) {
								promise.setPrmsOrd(p);
								promise.setPrmsRealmName(item.getString("prmsRealmName"+p));
								promise.setPrmsTitle(item.getString("prmsTitle"+p));
								promise.setPrmsContent(item.getString("prmsCont"+p));
								
								listPromise.add(promise);
							}

						}
					} else if (tmpItems.get("item") instanceof JSONObject) {
						JSONObject item = tmpItems.getJSONObject("item");
						Promise promise = new Promise();
						
						promise.setHuboId(item.getString("cnddtId"));
						promise.setSgId(item.getString("sgId"));
						promise.setSgTypeCode(item.getString("sgTypecode"));
						promise.setSggName(item.getString("sggName"));
						promise.setSidoName(item.getString("sidoName"));
						promise.setWiwName(item.getString("wiwName"));
						promise.setPartyName(item.getString("partyName"));
						promise.setKrName(item.getString("krName"));
						promise.setCnName(item.getString("cnName"));
						
						for (int p = 1; p <= item.getInt("prmsCnt"); p++) {
							promise.setPrmsOrd(p);
							promise.setPrmsRealmName(item.getString("prmsRealmName"+p));
							promise.setPrmsTitle(item.getString("prmsTitle"+p));
							promise.setPrmsContent(item.getString("prmsCont"+p));
							
							listPromise.add(promise);
						}
					}

					promiseRepo.saveAll(listPromise);

				}
				log.info("=====================================================================");
			}

			LogUtil.scheduleSccssLog(log, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(log, new Object() {}, e.getMessage());
		}
	}
}
