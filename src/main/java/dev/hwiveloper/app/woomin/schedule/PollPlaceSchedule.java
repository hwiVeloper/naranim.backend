package dev.hwiveloper.app.woomin.schedule;

import dev.hwiveloper.app.woomin.domain.common.PolPlace;
import dev.hwiveloper.app.woomin.domain.common.PolPlacePK;
import dev.hwiveloper.app.woomin.domain.common.Sungeogu;
import dev.hwiveloper.app.woomin.repository.PolPlaceRepository;
import dev.hwiveloper.app.woomin.repository.SungeoguRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * PollPlaceSchedule
 * 투표소 정보 관련 스케쥴러
 *
 * 매일 01:00:00 => getPrePolplcOtlnmapTrnsportInfoInqire (사전투표소 정보 조회)
 * 매일 01:30:00 => getPolplcOtlnmapTrnsportInfoInqire (선거일 투표소 정보 조회)
 */
@Component
public class PollPlaceSchedule {

    @Value("${keys.poll-place-service}")
    String POLL_PLACE_SERVICE;

    @Autowired
    PolPlaceRepository ppRepo;

    @Autowired
    SungeoguRepository sggRepo;

    /**
     * getPrePolplcOtlnmapTrnsportInfoInqire
     * 사전투표소 정보
     */
    @Scheduled(cron="0 0 1 * * *")
    public void getPrePolplcOtlnmapTrnsportInfoInqire() {
        try {
            // sgId, sdName 추출
            List<Sungeogu> sggList = sggRepo.findDistinctSgIdSdNameBySdNameOrderBySgIdSOrder();

            // 본서비스 호출 (loop)
            for (Sungeogu sgg : sggList) {
                // URL 생성
                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/9710000/NationalAssemblyInfoService/getLocalSearch"); /*URL*/
                urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + POLL_PLACE_SERVICE); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("9999", "UTF-8")); /*한 페이지 결과 수*/
                urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
                urlBuilder.append("&" + URLEncoder.encode("sgId","UTF-8") + "=" + URLEncoder.encode(sgg.getKey().getSgId().toString(), "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("sdName","UTF-8") + "=" + URLEncoder.encode(sgg.getSdName(), "UTF-8"));
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
                List<PolPlace> listPolPlace = new ArrayList<PolPlace>();

                // JSONArray | JSONObject 케이스에 따라 분기처리
                if (tmpJson.get("item") instanceof JSONArray) {
                    JSONArray itemJson = tmpJson.getJSONArray("item");
                    for (int i = 0; i < itemJson.length(); i++) {
                        JSONObject item = itemJson.getJSONObject(i);
                        PolPlace itemPp = new PolPlace();
                        PolPlacePK keyPp = new PolPlacePK();

                        keyPp.setPreYn(true); // Y, N
                        keyPp.setNum(item.getInt("num"));
                        keyPp.setSgId(item.get("sgId").toString());
                        keyPp.setSdName(item.getString("sdName"));

                        itemPp.setKey(keyPp);
                        itemPp.setEvPsName(item.getString("evPsName"));
                        itemPp.setWiwName(item.getString("wiwName"));
                        itemPp.setEmdName(item.getString("emdName"));
                        itemPp.setEvOrder(item.getInt("evOrder"));
                        itemPp.setPlaceName(item.getString("placeName"));
                        itemPp.setAddr(item.getString("addr"));
                        itemPp.setFloor(item.getString("floor"));

                        listPolPlace.add(itemPp);
                    }
                } else
                if (tmpJson.get("item") instanceof JSONObject) {
                    JSONObject itemJson = tmpJson.getJSONObject("item");
                    PolPlace itemPp = new PolPlace();
                    PolPlacePK keyPp = new PolPlacePK();

                    keyPp.setPreYn(true); // Y, N
                    keyPp.setNum(itemJson.getInt("num"));
                    keyPp.setSgId(itemJson.get("sgId").toString());
                    keyPp.setSdName(itemJson.getString("sdName"));

                    itemPp.setKey(keyPp);
                    itemPp.setEvPsName(itemJson.getString("evPsName"));
                    itemPp.setWiwName(itemJson.getString("wiwName"));
                    itemPp.setEmdName(itemJson.getString("emdName"));
                    itemPp.setEvOrder(itemJson.getInt("evOrder"));
                    itemPp.setPlaceName(itemJson.getString("placeName"));
                    itemPp.setAddr(itemJson.getString("addr"));
                    itemPp.setFloor(itemJson.getString("floor"));

                    listPolPlace.add(itemPp);
                }

                ppRepo.saveAll(listPolPlace);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * getPolplcOtlnmapTrnsportInfoInqire
     * 선거일 투표소 정보 조회
     */@Scheduled(cron="0 30 1 * * *")
    public void getPolplcOtlnmapTrnsportInfoInqire() {

    }
}
