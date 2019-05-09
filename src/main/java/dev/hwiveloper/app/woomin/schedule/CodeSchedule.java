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

import dev.hwiveloper.app.woomin.domain.Orig;
import dev.hwiveloper.app.woomin.domain.Poly;
import dev.hwiveloper.app.woomin.repository.MemberRepository;
import dev.hwiveloper.app.woomin.repository.OrigRepository;
import dev.hwiveloper.app.woomin.repository.PolyRepository;
import dev.hwiveloper.app.woomin.repository.ReeleGbnRepository;

/*
 * CodeSchedule
 * 각종 코드성 정보에 관한 API 호출 후 DB에 저장한다.
 * 
 * 매일 00:00:00 => getPolySearch (정당 검색)
 * 매일 00:05:00 => getLocalSearch (지역 검색)
 */
@Component
public class CodeSchedule {
	@Value("${keys.national-assembly-info-service}")
	String NATIONAL_ASSEMBLY_INFO_SERVICE;
	
	@Autowired
	PolyRepository polyRepo;
	
	@Autowired
	OrigRepository origRepo;
	
	@Autowired
	ReeleGbnRepository reeleGbnRepo;
		
	/**
	 * getPolySearch
	 * 정당 검색
	 */
	@Scheduled(cron="0 0 0 * * ?")
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
				poly.setPolyCd(item.getString("polyCd"));
				poly.setPolyNm(item.getString("polyNm"));
				listPoly.add(poly);
			}
			
			polyRepo.saveAll(listPoly);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getLocalSearch
	 * 지역 검색
	 */
	@Scheduled(cron="0 5 0 * * ?")
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
