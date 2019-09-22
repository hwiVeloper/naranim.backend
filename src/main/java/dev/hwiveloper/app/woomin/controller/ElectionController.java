package dev.hwiveloper.app.woomin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.app.woomin.domain.common.Election;
import dev.hwiveloper.app.woomin.domain.common.ElectionType;
import dev.hwiveloper.app.woomin.repository.ElectionRepository;
import dev.hwiveloper.app.woomin.repository.ElectionTypeRepository;

@RestController
@RequestMapping("/elections")
public class ElectionController {
	@Autowired
	ElectionRepository electionRepo;
	
	@Autowired
	ElectionTypeRepository electionTypeRepo;
	
	/**
	 * 선거정보리스트 조회
	 * @return
	 */
	@PostMapping("/getElections")
	public ResponseEntity<List<Election>> getElections(@RequestBody Map<String, Object> request) {
		return new ResponseEntity<List<Election>>((List<Election>) electionRepo.findAll(), HttpStatus.OK);			
	}
	
	/**
	 * 선거정보리스트 투표일자 기준으로 조회
	 * @param sgId
	 * @return
	 */
	@PostMapping("/getElectionBySgId")
	public ResponseEntity<List<Election>> getElectionBySgId(@RequestBody Map<String, Object> request) {
		String sgId = (String) request.get("sg_id");
		
		List<Election> electionList = electionRepo.findAllBySgId(sgId);
		return new ResponseEntity<List<Election>>((List<Election>) electionList, HttpStatus.OK);
	}
	
	/**
	 * 선거종류 리스트 조회
	 * @return
	 */
	@PostMapping("/getTypes")
	public ResponseEntity<List<ElectionType>> getTypes(@RequestBody Map<String, Object> request) {
		return new ResponseEntity<List<ElectionType>>((List<ElectionType>) electionTypeRepo.findAll(), HttpStatus.OK);
	}
	
	/**
	 * 선거일자 리스트 Unique 조회
	 * @return
	 */
	@PostMapping("/getDates")
	public ResponseEntity<List<String>> getDates(@RequestBody Map<String, Object> request) {
		return new ResponseEntity<List<String>>((List<String>) electionRepo.findDistinctKeySgId(), HttpStatus.OK);
	}
}
