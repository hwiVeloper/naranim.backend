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
import org.springframework.stereotype.Component;

import dev.hwiveloper.app.woomin.domain.common.Election;
import dev.hwiveloper.app.woomin.domain.election.Candidate;
import dev.hwiveloper.app.woomin.domain.election.CandidatePK;
import dev.hwiveloper.app.woomin.domain.election.Vote;
import dev.hwiveloper.app.woomin.domain.election.VotePK;
import dev.hwiveloper.app.woomin.repository.ElectionRepository;
import dev.hwiveloper.app.woomin.repository.VoteRepository;

/**
 * VoteSchedule
 * 
 * 매일 ??:??:?? => getVoteSttusInfoInqire (투표 결과 조회)
 * 매일 ??:??:?? => getXmntckSttusInfoInqire (개표 결과 조회)
 */
@Component
public class VoteSchedule {
	@Value("${keys.vote-service}")
	String VOTE_SERVICE;
	
	@Autowired
	VoteRepository voteRepo;
	
	@Autowired
	ElectionRepository electionRepo;
	
	/**
	 * getVoteSttusInfoInqire
	 * 투표 결과 조회
	 */
	public void getVoteSttusInfoInqire() {
		try {
			List<Election> electionList = (List<Election>) electionRepo.findAll();
			
			for (Election election : electionList) {
				// URL 생성
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/VoteXmntckInfoInqireService2/getVoteSttusInfoInqire"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + VOTE_SERVICE); /*Service Key*/
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
				// 후처리
				JSONObject tmpJson = XML.toJSONObject(sb.toString())
						.getJSONObject("response")
						.getJSONObject("body")
						.getJSONObject("items");
				List<Vote> listVote = new ArrayList<Vote>();
				
				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);
						Vote itemVote = new Vote();
						VotePK keyVote = new VotePK();

						keyVote.setNum(item.getInt("num"));
						keyVote.setSgId(item.get("sgId").toString());
						keyVote.setSgTypeCode(item.get("sgTypecode").toString());

						itemVote.setKey(keyVote);
						itemVote.setSdName(item.getString("sdName"));
						itemVote.setWiwName(item.getString("wiwName"));
						itemVote.setTotSunsu(item.getBigDecimal("totSunsu"));
						itemVote.setPsSunsu(item.getBigDecimal("psSunsu"));
						itemVote.setPsEtcSunsu(item.getBigDecimal("psEtcSunsu"));
						itemVote.setTotTusu(item.getBigDecimal("totTusu"));
						itemVote.setPsTusu(item.getBigDecimal("psTusu"));
						itemVote.setPsEtcTusu(item.getBigDecimal("psEtcTusu"));
						itemVote.setTurnout(item.getBigDecimal("turnou"));
						itemVote.setVrOrder(item.getInt("vrOrder"));
						
						listVote.add(itemVote);
					}
				} else
				if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject item = tmpJson.getJSONObject("item");
					Vote itemVote = new Vote();
					VotePK keyVote = new VotePK();

					keyVote.setNum(item.getInt("num"));
					keyVote.setSgId(item.get("sgId").toString());
					keyVote.setSgTypeCode(item.get("sgTypecode").toString());

					itemVote.setKey(keyVote);
					itemVote.setSdName(item.getString("sdName"));
					itemVote.setWiwName(item.getString("wiwName"));
					itemVote.setTotSunsu(item.getBigDecimal("totSunsu"));
					itemVote.setPsSunsu(item.getBigDecimal("psSunsu"));
					itemVote.setPsEtcSunsu(item.getBigDecimal("psEtcSunsu"));
					itemVote.setTotTusu(item.getBigDecimal("totTusu"));
					itemVote.setPsTusu(item.getBigDecimal("psTusu"));
					itemVote.setPsEtcTusu(item.getBigDecimal("psEtcTusu"));
					itemVote.setTurnout(item.getBigDecimal("turnou"));
					itemVote.setVrOrder(item.getInt("vrOrder"));
					
					listVote.add(itemVote);
				}

				voteRepo.saveAll(listVote);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getXmntckSttusInfoInqire
	 * 개표 결과 조회
	 */
	public void getXmntckSttusInfoInqire() {
		
	}
}