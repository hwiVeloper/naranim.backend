package dev.hwiveloper.naranim.controller;

import java.util.HashMap;
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
import dev.hwiveloper.naranim.service.EvaluationService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/electionResult")
@Slf4j
public class ElectionResultController {
	
	@Autowired
	ElectionResultService electionResultService;
	
	@Autowired
	EvaluationService evaluationService;
	
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
	
	@PostMapping("/getCandidateDetail")
	public ResponseEntity<Map<String, Object>> getCandidateDetail(@RequestBody Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> candidateDetailMap = electionResultService.getCandidateDetail(param);
		List<Map<String, Object>> candidateHistoryList = electionResultService.getCandidateHistory(param);
		List<Map<String, Object>> candidateEvaluationList = evaluationService.getEvaluationByCandidate(param);
		List<Map<String, Object>> candidateElectionList = evaluationService.getElectionByCandidate(candidateDetailMap);
		
		resultMap.put("baseInfo", candidateDetailMap);
		resultMap.put("history", candidateHistoryList);
		resultMap.put("evaluation", candidateEvaluationList);
		resultMap.put("electionList", candidateElectionList);
		
		log.info("=========================");
		log.info(candidateElectionList.size() + "");
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
