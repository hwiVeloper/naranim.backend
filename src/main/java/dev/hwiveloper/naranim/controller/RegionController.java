package dev.hwiveloper.naranim.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.naranim.domain.common.Region;
import dev.hwiveloper.naranim.domain.common.RegionPK;
import dev.hwiveloper.naranim.domain.common.UserRegion;
import dev.hwiveloper.naranim.mapper.RegionMapper;
import dev.hwiveloper.naranim.repository.RegionRepository;
import dev.hwiveloper.naranim.repository.UserRegionRepository;
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
}
