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
import org.springframework.stereotype.Component;

import dev.hwiveloper.app.woomin.common.LogUtil;
import dev.hwiveloper.app.woomin.domain.common.Election;
import dev.hwiveloper.app.woomin.domain.election.Winner;
import dev.hwiveloper.app.woomin.domain.election.WinnerPK;
import dev.hwiveloper.app.woomin.repository.ElectionRepository;
import dev.hwiveloper.app.woomin.repository.WinnerRepository;

/**
 * WinnerSchedule
 * 당선인 정보를 조회하여 DB에 저장한다.
 * 
 * 매일 03:00:00 => getWinnerInfoInqire (당선인 조회)
 */
@Component
public class WinnerSchedule {
	Logger logger = LoggerFactory.getLogger(WinnerSchedule.class);
	
	@Value("${keys.winner-service}")
	String WINNER_SERVICE;
	
	@Autowired
	WinnerRepository winnerRepo;
	
	@Autowired
	ElectionRepository electionRepo;
	
	/**
	 * getWinnerInfoInqire
	 * 당선인 정보 조회
	 */
	public void getWinnerInfoInqire() {
		try {
			List<Election> electionList = (List<Election>) electionRepo.findAll();

			for (Election election : electionList) {
				// URL 생성
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/WinnerInfoInqireService2/getWinnerInfoInqire"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + WINNER_SERVICE); /*Service Key*/
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
				JSONObject resultBody = XML.toJSONObject(sb.toString())
						.getJSONObject("response")
						.getJSONObject("body");
				if (resultBody.getInt("totalCount") == 0) {
					continue;
				}
				JSONObject tmpJson = resultBody.getJSONObject("items");

				List<Winner> listWinner = new ArrayList<Winner>();

				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);
						
						Winner itemWinner = new Winner();
						WinnerPK keyWinner = new WinnerPK();

						keyWinner.setNum(item.getInt("num"));
						keyWinner.setSgId(item.get("sgId").toString());
						keyWinner.setSgTypeCode(item.get("sgTypecode").toString());
						keyWinner.setHuboId(item.get("huboid").toString());
						itemWinner.setKey(keyWinner);
						itemWinner.setSggName(item.getString("sggName"));
						itemWinner.setSdName(item.getString("sdName"));
						itemWinner.setWiwName(item.getString("wiwName"));
						itemWinner.setGiho(item.getInt("giho"));
						itemWinner.setGihoSangse(item.has("gihoSange") ? item.getString("gihoSange") : "");
						itemWinner.setJdName(item.getString("jdName"));
						itemWinner.setName(item.getString("name"));
						itemWinner.setHanjaName(item.getString("hanjaName"));
						itemWinner.setGender(item.getString("gender"));
						itemWinner.setBirthday(item.get("birthday").toString());
						itemWinner.setAge(item.getInt("age"));
						itemWinner.setAddr(item.getString("addr"));
						itemWinner.setJobId(item.get("jobId").toString());
						itemWinner.setJob(item.getString("job"));
						itemWinner.setEduId(item.get("eduId").toString());
						itemWinner.setEdu(item.getString("edu"));
						itemWinner.setCareer1(item.getString("career1"));
						itemWinner.setCareer2(item.getString("career2"));
						itemWinner.setDugsu(item.getBigDecimal("dugsu"));
						itemWinner.setDugyul(item.getBigDecimal("dugyul"));
								
						listWinner.add(itemWinner);
					}
				} else
				if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject itemJson = tmpJson.getJSONObject("item");
					Winner itemWinner = new Winner();
					WinnerPK keyWinner = new WinnerPK();

					keyWinner.setNum(itemJson.getInt("num"));
					keyWinner.setSgId(itemJson.get("sgId").toString());
					keyWinner.setSgTypeCode(itemJson.get("sgTypecode").toString());
					keyWinner.setHuboId(itemJson.get("huboid").toString());
					itemWinner.setKey(keyWinner);
					itemWinner.setSggName(itemJson.getString("sggName"));
					itemWinner.setSdName(itemJson.getString("sdName"));
					itemWinner.setWiwName(itemJson.getString("wiwName"));
					itemWinner.setGiho(itemJson.getInt("giho"));
					itemWinner.setGihoSangse(itemJson.has("gihoSange") ? itemJson.getString("gihoSange") : "");
					itemWinner.setJdName(itemJson.getString("jdName"));
					itemWinner.setName(itemJson.getString("name"));
					itemWinner.setHanjaName(itemJson.getString("hanjaName"));
					itemWinner.setGender(itemJson.getString("gender"));
					itemWinner.setBirthday(itemJson.get("birthday").toString());
					itemWinner.setAge(itemJson.getInt("age"));
					itemWinner.setAddr(itemJson.getString("addr"));
					itemWinner.setJobId(itemJson.get("jobId").toString());
					itemWinner.setJob(itemJson.getString("job"));
					itemWinner.setEduId(itemJson.get("eduId").toString());
					itemWinner.setEdu(itemJson.getString("edu"));
					itemWinner.setCareer1(itemJson.getString("career1"));
					itemWinner.setCareer2(itemJson.getString("career2"));
					itemWinner.setDugsu(itemJson.getBigDecimal("dugsu"));
					itemWinner.setDugyul(itemJson.getBigDecimal("dugyul"));
							
					listWinner.add(itemWinner);
				}

				winnerRepo.saveAll(listWinner);
			}
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}
}
