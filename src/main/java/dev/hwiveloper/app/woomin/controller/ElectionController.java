package dev.hwiveloper.app.woomin.controller;

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

import dev.hwiveloper.app.woomin.domain.common.Election;
import dev.hwiveloper.app.woomin.domain.common.ElectionType;
import dev.hwiveloper.app.woomin.repository.ElectionRepository;
import dev.hwiveloper.app.woomin.repository.ElectionTypeRepository;
import dev.hwiveloper.app.woomin.service.ElectionService;

@RestController
@RequestMapping("/elections")
public class ElectionController {
	@Autowired
	ElectionRepository electionRepo;
	
	@Autowired
	ElectionTypeRepository electionTypeRepo;
	
	@Autowired
	ElectionService electionService;
	
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
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("sgId", request.get("sg_id"));
		
		List<String> result = electionService.getElections(param);
		
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/selectTest")
	public ResponseEntity<List<Map<String, Object>>> selectTest() {
		return new ResponseEntity<List<Map<String, Object>>>(electionService.selectTest(), HttpStatus.OK);
	}
}
