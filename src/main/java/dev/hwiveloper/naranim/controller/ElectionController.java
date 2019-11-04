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

import dev.hwiveloper.naranim.service.ElectionService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/election")
@Slf4j
public class ElectionController {
	
	@Autowired
	ElectionService electionService;
	
	@PostMapping("/getCandidates")
	public ResponseEntity<List<Map<String, Object>>> getCandidates(@RequestBody Map<String, Object> param) {
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PostMapping("/getElectionTypes")
	public ResponseEntity<List<Map<String, Object>>> getElectionTypes(@RequestBody Map<String, Object> param) {
		log.info("==========================> {}.{} START", getClass().getSimpleName(), new Object() {}.getClass().getEnclosingMethod().getName());
		
		List<Map<String, Object>> result = electionService.getElectionTypes();
		
		log.info("==========================> {}.{} END", getClass().getSimpleName(), new Object() {}.getClass().getEnclosingMethod().getName());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/getElections")
	public ResponseEntity<List<Map<String, Object>>> getElections(@RequestBody Map<String, Object> param) {
		List<Map<String, Object>> result = electionService.getElections(param);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/getSdNames")
	public ResponseEntity<List<String>> getSdNames(@RequestBody Map<String, Object> param) {
		List<String> result = electionService.getSdNames(param);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/getSggNames")
	public ResponseEntity<List<String>> getSggNames(@RequestBody Map<String, Object> param) {
		List<String> result = electionService.getSggNames(param);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/getWiwNames")
	public ResponseEntity<List<String>> getWiwNames(@RequestBody Map<String, Object> param) {
		List<String> result = electionService.getWiwNames(param);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
