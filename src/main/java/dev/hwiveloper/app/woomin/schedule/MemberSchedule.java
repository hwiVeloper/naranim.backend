package dev.hwiveloper.app.woomin.schedule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
import dev.hwiveloper.app.woomin.domain.assembly.Member;
import dev.hwiveloper.app.woomin.repository.MemberRepository;
import dev.hwiveloper.app.woomin.repository.OrigRepository;
import dev.hwiveloper.app.woomin.repository.PolyRepository;
import dev.hwiveloper.app.woomin.repository.ReeleGbnRepository;

/*
 * MemberSchedule
 * 각종 의원 정보에 관한 API 호출 후 DB에 저장한다.
 * 
 * 매일 00:05:00 => getMemberCurrStateList (국회의원 현황 조회)
 * 매일 00:10:00 => getMemberDetailInfoList (국회의원 현황 상세 조회)
 */
@Component
public class MemberSchedule {
	Logger logger = LoggerFactory.getLogger(MemberSchedule.class);
	
	@Value("${keys.national-assembly-info-service}")
	String NATIONAL_ASSEMBLY_INFO_SERVICE;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	OrigRepository origRepo;
	
	@Autowired
	PolyRepository polyRepo;
	
	@Autowired
	ReeleGbnRepository reeleRepo;
	
	/**
	 * getMemberCurrStateList
	 * 국회의원 현황 조회
	 */
	@Scheduled(cron="0 5 0 * * ?")
	public void getMemberCurrStateList() {
		try {
			// URL 생성
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9710000/NationalAssemblyInfoService/getMemberCurrStateList"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + NATIONAL_ASSEMBLY_INFO_SERVICE); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("300", "UTF-8")); /*한 페이지 결과 수*/
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
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
			JSONObject jsonObj = XML.toJSONObject(sb.toString());
			List<Member> memberList = (List<Member>) memberRepo.findAll();
			
			JSONArray itemJson = jsonObj.getJSONObject("response")
										.getJSONObject("body")
										.getJSONObject("items")
										.getJSONArray("item");
			
			for (int i = 0; i < itemJson.length(); i++) {
				JSONObject tmpJson = itemJson.getJSONObject(i);
				Member itemMember = new Member();
				itemMember.setDeptCd(tmpJson.get("deptCd").toString());
				itemMember.setEmpNm(tmpJson.getString("empNm"));
				itemMember.setEngNm(tmpJson.getString("engNm"));
				itemMember.setHjNm(tmpJson.getString("hjNm"));
				itemMember.setJpgLink(tmpJson.getString("jpgLink"));
				itemMember.setNum(tmpJson.getInt("num"));
				itemMember.setOrigNm(tmpJson.getString("origNm"));
				itemMember.setReeleGbnNm(tmpJson.getString("reeleGbnNm"));
				
				// 지역코드
				itemMember.setOrigCd(origRepo.findByOrigNm(itemMember.getOrigNm()).getOrigCd());
				
				// 당선구분코드
				itemMember.setReeleGbnCd(reeleRepo.findByReeleGbnNm(itemMember.getReeleGbnNm()).getReeleGbnCd());
				
				memberList.add(itemMember);
			}
			
			memberRepo.saveAll(memberList);
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}
	
	/**
	 * getMemberDetailInfoList
	 * 국회의원 현황 상세 조회
	 */
//	@Scheduled(cron="0 10 0 * * ?")
	@Scheduled(initialDelay = 5000, fixedDelay = 6000000)
	public void getMemberDetailInfoList() {
		try {
			// 현재 국회의원 현황 조회
			List<Member> memberList = (List<Member>) memberRepo.findAll();
			
			StringBuilder urlBuilder = null;
			URL url;
			HttpURLConnection conn = null;
			BufferedReader rd = null;
			
			for (Member member : memberList) {
				urlBuilder = new StringBuilder("http://apis.data.go.kr/9710000/NationalAssemblyInfoService/getMemberDetailInfoList");
				urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + NATIONAL_ASSEMBLY_INFO_SERVICE);
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
				urlBuilder.append("&" + URLEncoder.encode("dept_cd", "UTF-8") + "=" + URLEncoder.encode(member.getDeptCd(), "UTF-8"));
				url = new URL(urlBuilder.toString());
				
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				
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
				
				if (XML.toJSONObject(sb.toString())
										.getJSONObject("response")
										.get("body").equals("")) {
					memberRepo.deleteById(member.getDeptCd());
					continue;
				}
				
				JSONObject itemJson = XML.toJSONObject(sb.toString())
										.getJSONObject("response")
										.getJSONObject("body")
										.getJSONObject("item");

				member.setBthDate(itemJson.has("bthDate") ? itemJson.get("bthDate").toString() : "");
				member.setPolyNm(itemJson.has("polyNm") ? itemJson.getString("polyNm") : "");
				member.setShrtNm(itemJson.has("shrtNm") ? itemJson.getString("shrtNm") : "");
				member.setElectionNum(itemJson.has("electionNum") ? itemJson.getString("electionNum") : "");
				member.setAssemTel(itemJson.has("assemTel") ? itemJson.getString("assemTel") : "");
				member.setAssemHomep(itemJson.has("assemHomep") ? itemJson.getString("assemHomep") : "");
				member.setAssemEmail(itemJson.has("assemEmail") ?itemJson.getString("assemEmail") : "");
				member.setStaff(itemJson.has("staff") ? itemJson.getString("staff") : "");
				member.setSecretary(itemJson.has("secretary") ? itemJson.getString("secretary") : "");
				member.setSecretary2(itemJson.has("secretary2") ? itemJson.getString("secretary2") : "");
				member.setHbbyCd(itemJson.has("hbbyCd") ? itemJson.getString("hbbyCd") : "");
				member.setExamCd(itemJson.has("examCd") ? itemJson.getString("examCd") : "");
				member.setMemTitle(itemJson.has("memTitle") ? itemJson.getString("memTitle") : "");
				
				// 정당코드
				if (!member.getPolyNm().equals("")) {
					member.setPolyCd(polyRepo.findByPolyNm(member.getPolyNm()).getPolyCd());
				}
				
			}
			
			memberRepo.saveAll(memberList);
			
			LogUtil.scheduleSccssLog(logger, new Object() {});
		} catch (Exception e) {
			LogUtil.scheduleErrorLog(logger, new Object() {}, e.getMessage());
		}
	}
}
