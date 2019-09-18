package dev.hwiveloper.app.woomin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@GetMapping("/")
	public ResponseEntity<List<Election>> getElections() {
		return new ResponseEntity<List<Election>>((List<Election>) electionRepo.findAll(), HttpStatus.OK);			
	}
	
	/**
	 * 선거정보리스트 투표일자 기준으로 조회
	 * @param sgId
	 * @return
	 */
	@GetMapping("/{sg_id}")
	public ResponseEntity<List<Election>> getElectionBySgId(
		@PathVariable("sg_id") String sgId
	) {
		List<Election> electionList = electionRepo.findAllBySgId(sgId);
		return new ResponseEntity<List<Election>>((List<Election>) electionList, HttpStatus.OK);
	}
	
	/**
	 * 선거종류 리스트 조회
	 * @return
	 */
	@GetMapping("/getTypes")
	public ResponseEntity<List<ElectionType>> getTypes() {
		return new ResponseEntity<List<ElectionType>>((List<ElectionType>) electionTypeRepo.findAll(), HttpStatus.OK);
	}
	
	/**
	 * 선거일자 리스트 Unique 조회
	 * @return
	 */
	@GetMapping("/getDates")
	public ResponseEntity<List<String>> getDates() {
		return new ResponseEntity<List<String>>((List<String>) electionRepo.findDistinctKeySgId(), HttpStatus.OK);
	}
}
