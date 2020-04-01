package dev.hwiveloper.naranim.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import dev.hwiveloper.naranim.mapper.RegionMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@PropertySource("classpath:application.yml")
@Slf4j
public class KakaoRestService {

	String KAKAO_REST_KEY = "a137689844fdc0dd06194c0550d1e7de";

	private String kakaoLocalEndpoint = "https://dapi.kakao.com/v2/local";
	
	@Autowired
	RegionMapper regionMapper;

	/**
	 * 시도, 시군구, 읍면동으로 지도의 중심점을 도출
	 * @param param
	 * @return
	 */
	public Map<String, Object> getCenterPoint(Map<String, Object> param) {
		String sdName      = (String) param.get("sdName");
		String gusigunName = (String) param.get("gusigunName");
		String emdName     = (String) param.get("emdName");
		String address     = sdName + " " + gusigunName + " " + emdName;

		log.info("query address value >>>>> " + address);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// URL 생성
			StringBuilder urlBuilder = new StringBuilder(kakaoLocalEndpoint.concat("/search/address.json")); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("AddressSize", "UTF-8") + "=" + 1);
			URL url = new URL(urlBuilder.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "KakaoAK ".concat(KAKAO_REST_KEY));

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
			
			JSONObject resultJson = new JSONObject(sb.toString());
			
			JSONArray documents = resultJson.getJSONArray("documents");
			JSONObject document = new JSONObject();
			
			if (documents.length() > 0) {
				document = documents.getJSONObject(0);
				resultMap.put("x", document.get("x").toString());
				resultMap.put("y", document.get("y").toString());
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultMap;
	}
	
	public List<Map<String, Object>> getPollPlacesPoint(Map<String, Object> param) {
		
		List<Map<String, Object>> pollPlaces = regionMapper.getPollPlaces((HashMap<String, Object>) param);
		
		for (Map<String, Object> pollPlace : pollPlaces) {
			Map<String, Object> coords = getCoordsByAddr(pollPlace);
			pollPlace.put("x", coords.get("x").toString());
			pollPlace.put("y", coords.get("y").toString());
		}
		
		return pollPlaces;
	}
	
	public Map<String, Object> getCoordsByAddr(Map<String, Object> pollPlace) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			// URL 생성
			StringBuilder urlBuilder = new StringBuilder(kakaoLocalEndpoint.concat("/search/address.json")); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode((String) pollPlace.get("addr"), "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("AddressSize", "UTF-8") + "=" + 1);
			URL url = new URL(urlBuilder.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "KakaoAK ".concat(KAKAO_REST_KEY));

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
			
			JSONObject resultJson = new JSONObject(sb.toString());
			
			JSONArray documents = resultJson.getJSONArray("documents");
			JSONObject document = new JSONObject();
			
			if (documents.length() > 0) {
				document = documents.getJSONObject(0);
				resultMap.put("x", document.get("x").toString());
				resultMap.put("y", document.get("y").toString());
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultMap;
	}
}
