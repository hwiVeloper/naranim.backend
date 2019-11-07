package dev.hwiveloper.naranim.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.naranim.service.ElectionResultService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/electionResult")
@Slf4j
public class ElectionResultController {
	
	@Autowired
	ElectionResultService electionResultService;
	
	@PostMapping("/getElectionResult")
	public ResponseEntity<List<Map<String, Object>>> getElectionResult(@RequestBody Map<String, Object> param) {
		List<Map<String, Object>> result = electionResultService.getElectionResult(param);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/getCandidateSearch")
	public ResponseEntity<List<Map<String, Object>>> getCandidateSearch(@RequestBody Map<String, Object> param) {
		List<Map<String, Object>> result = electionResultService.getCandidateSearch(param);
		log.info("size : " + result.size());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
