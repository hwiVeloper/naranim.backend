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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.hwiveloper.naranim.common.LogUtil;
import dev.hwiveloper.naranim.domain.common.Election;
import dev.hwiveloper.naranim.domain.election.Vote;
import dev.hwiveloper.naranim.domain.election.VotePK;
import dev.hwiveloper.naranim.domain.election.VoteResult;
import dev.hwiveloper.naranim.domain.election.VoteResultPK;
import dev.hwiveloper.naranim.repository.ElectionRepository;
import dev.hwiveloper.naranim.repository.SungeoguRepository;
import dev.hwiveloper.naranim.repository.VoteRepository;
import dev.hwiveloper.naranim.repository.VoteResultRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * VoteSchedule
 * 투표와 개표 정보 조회 후 DB에 저장한다.
 * 
 * 매일 02:30:00 => getVoteSttusInfoInqire (투표 결과 조회)
 * 매일 02:45:00 => getXmntckSttusInfoInqire (개표 결과 조회)
 */
@Component
@Slf4j
public class VoteSchedule {
	
	@Value("${keys.vote-service}")
	String VOTE_SERVICE;

	@Autowired
	VoteRepository voteRepo;
	
	@Autowired
	VoteResultRepository voteResultRepo;

	@Autowired
	ElectionRepository electionRepo;
	
	@Autowired
	SungeoguRepository sggRepo;

	/**
	 * getVoteSttusInfoInqire
	 * 투표 결과 조회
	 */
	@Scheduled(cron="0 30 2 * * *")
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
				JSONObject resultBody = XML.toJSONObject(sb.toString())
						.getJSONObject("response")
						.getJSONObject("body");
				if (resultBody.getInt("totalCount") == 0) {
					continue;
				}
				JSONObject tmpJson = resultBody.getJSONObject("items");

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
						itemVote.setTurnout(item.getBigDecimal("turnout"));
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
					itemVote.setTurnout(item.getBigDecimal("turnout"));
					itemVote.setVrOrder(item.getInt("vrOrder"));

					listVote.add(itemVote);
				}

				voteRepo.saveAll(listVote);
			}
			
			LogUtil.scheduleSccssLog(log, new Object() {});
		} catch (IOException e) {
			LogUtil.scheduleErrorLog(log, new Object() {}, e.getMessage());
		}
	}

	/**
	 * getXmntckSttusInfoInqire
	 * 개표 결과 조회
	 */
	@Scheduled(cron="0 45 2 * * *")
	public void getXmntckSttusInfoInqire() {
		try {
			List<Election> electionList = (List<Election>) electionRepo.findAll();

			for (Election election : electionList) {
				// URL 생성
				StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9760000/VoteXmntckInfoInqireService2/getXmntckSttusInfoInqire"); /*URL*/
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
				JSONObject resultBody = XML.toJSONObject(sb.toString())
						.getJSONObject("response")
						.getJSONObject("body");
				if (resultBody.getInt("totalCount") == 0) {
					continue;
				}
				JSONObject tmpJson = resultBody.getJSONObject("items");

				List<VoteResult> listVoteResult = new ArrayList<VoteResult>();

				// JSONArray | JSONObject 케이스에 따라 분기처리
				if (tmpJson.get("item") instanceof JSONArray) {
					JSONArray itemJson = tmpJson.getJSONArray("item");
					for (int i = 0; i < itemJson.length(); i++) {
						JSONObject item = itemJson.getJSONObject(i);
						
						// 데이터 정합성 체크
						if (item.getString("sdName").equals("합계")
						||  item.getString("wiwName").equals("합계")) {
							continue;
						}
						
						// 후보자 seq 추출
						for (int seq = 1; seq <= 30; seq++) {
							String key = "jd".concat(padLeft(seq, 2));
							if (item.getString(key).isEmpty()) {
								break;
							}
							VoteResult itemVoteResult = new VoteResult();
							VoteResultPK keyVoteResult = new VoteResultPK();

							keyVoteResult.setSgId(item.get("sgId").toString());
							keyVoteResult.setSgTypeCode(item.get("sgTypecode").toString());
							keyVoteResult.setSggName(item.getString("sggName"));
							keyVoteResult.setHuboOrder(seq);
							itemVoteResult.setKey(keyVoteResult);
							itemVoteResult.setSdName(item.getString("sdName"));
							itemVoteResult.setWiwName(item.getString("wiwName"));
							itemVoteResult.setSunsu(item.getBigDecimal("sunsu"));
							itemVoteResult.setTusu(item.getBigDecimal("tusu"));
							itemVoteResult.setYutusu(item.getBigDecimal("yutusu"));
							itemVoteResult.setMutusu(item.getBigDecimal("mutusu"));
							itemVoteResult.setGigwonsu(item.getBigDecimal("gigwonsu"));
							itemVoteResult.setJd(item.getString("jd".concat(padLeft(seq, 2))));
							itemVoteResult.setHbj(item.getString("hbj".concat(padLeft(seq, 2))));
							itemVoteResult.setDugsu(item.getBigDecimal("dugsu".concat(padLeft(seq, 2))));
							itemVoteResult.setCrOrder(item.getInt("crOrder"));
							
							listVoteResult.add(itemVoteResult);
						}

					}
				} else
				if (tmpJson.get("item") instanceof JSONObject) {
					JSONObject item = tmpJson.getJSONObject("item");
					VoteResult itemVoteResult = new VoteResult();
					VoteResultPK keyVoteResult = new VoteResultPK();

					keyVoteResult.setSgId(item.get("sgId").toString());
					keyVoteResult.setSgTypeCode(item.get("sgTypecode").toString());
					keyVoteResult.setSggName(item.getString("sggName"));
					
					// 후보자 seq 추출
					for (int seq = 1; seq <= 30; seq++) {
						String key = "jd".concat(padLeft(seq, 2));
						if (!item.has(key)) {
							break;
						}
						keyVoteResult.setHuboOrder(seq);
						
						itemVoteResult.setKey(keyVoteResult);
						itemVoteResult.setSdName(item.getString("sdName"));
						itemVoteResult.setWiwName(item.getString("wiwName"));
						itemVoteResult.setSunsu(item.getBigDecimal("sunsu"));
						itemVoteResult.setTusu(item.getBigDecimal("tusu"));
						itemVoteResult.setMutusu(item.getBigDecimal("mutusu"));
						itemVoteResult.setGigwonsu(item.getBigDecimal("gigwonsu"));
						itemVoteResult.setJd(item.getString("jd".concat(padLeft(seq, 2))));
						itemVoteResult.setHbj(item.getString("hbj".concat(padLeft(seq, 2))));
						itemVoteResult.setDugsu(item.getBigDecimal("dugsu".concat(padLeft(seq, 2))));
						itemVoteResult.setCrOrder(item.getInt("crOrder"));
						
						listVoteResult.add(itemVoteResult);
					}
				}

				voteResultRepo.saveAll(listVoteResult);
			}
			LogUtil.scheduleSccssLog(log, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(log, new Object() {}, e.getMessage());
		}
	}
	
	public String padLeft(int seq, int digit) {
		return String.format("%" + digit + "s", seq).replace(' ', '0');
	}
}
