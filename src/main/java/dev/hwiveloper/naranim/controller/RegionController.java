package dev.hwiveloper.naranim.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.naranim.domain.common.UserRegion;
import dev.hwiveloper.naranim.mapper.RegionMapper;
import dev.hwiveloper.naranim.repository.RegionRepository;
import dev.hwiveloper.naranim.repository.UserRegionRepository;
import dev.hwiveloper.naranim.service.KakaoRestService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/region")
@Slf4j
public class RegionController {
	@Autowired
	UserRegionRepository userRegionRepo;
	
	@Autowired
	RegionRepository regionRepo;
	
	@Autowired
	KakaoRestService kakaoRestService;
	
	@Autowired
	RegionMapper regionMapper;
	
	@GetMapping("")
	public ResponseEntity<UserRegion> index() {
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> getRegion(@RequestBody HashMap<String, Object> reqParam) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		param.put("sdName", reqParam.get("sdName"));
		param.put("gusigunName", reqParam.get("gusigunName"));
		param.put("emdName", reqParam.get("emdName"));
		
		return new ResponseEntity<>(regionMapper.getRegions(param), HttpStatus.OK);
	}
	
	@PostMapping("/registerUserRegion")
	public ResponseEntity<?> registerUserRegion(@RequestBody UserRegion userRegion) {
		userRegionRepo.save(userRegion);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/getMyRegionCandidates")
	public ResponseEntity<?> getMyRegionCandidates(@RequestBody HashMap<String, Object> reqParam) {
		log.info(reqParam.toString());
		return new ResponseEntity<>(regionMapper.getMyRegionCandidates(reqParam), HttpStatus.OK);
	}
	
	@PostMapping("/getPollPlaces")
	public ResponseEntity<?> getPollPlaces(@RequestBody HashMap<String, Object> reqParam) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 선거구 조회
		String sungeogu = regionMapper.getSggByRegion(reqParam);
		
		// 중심점 좌표
		Map<String, Object> coords = kakaoRestService.getCenterPoint(reqParam);
		
		// 해당 선거구 투표소 조회
		List<Map<String, Object>> pollPlaces = kakaoRestService.getPollPlacesPoint(reqParam);
		
		result.put("sungeogu", sungeogu);
		result.put("center", coords);
		result.put("pollPlaces", pollPlaces);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/getPollPlacesByLocation")
	public ResponseEntity<?> getPollPlacesByLocation(@RequestBody HashMap<String, Object> reqParam) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 위치에 따른 행정구역 & 중심점 좌표 조회
		Map<String, Object> areaAndCoords = kakaoRestService.getAreaAndCenterByCoords(reqParam);
		
		// 선거구 조회
		String sungeogu = regionMapper.getSggByRegion((HashMap<String, Object>) areaAndCoords);
		
		// 해당 선거구 투표소 조회
		List<Map<String, Object>> pollPlaces = kakaoRestService.getPollPlacesPoint(areaAndCoords);
		
		// 중심점 좌표
		Map<String, Object> coords = new HashMap<String, Object>();
		coords.put("x", reqParam.get("longitude"));
		coords.put("y", reqParam.get("latitude"));
		
		
		result.put("sungeogu", sungeogu);
		result.put("center", coords);
		result.put("pollPlaces", pollPlaces);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
