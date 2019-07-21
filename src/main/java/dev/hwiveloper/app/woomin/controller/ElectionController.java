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
import dev.hwiveloper.app.woomin.domain.common.ElectionPK;
import dev.hwiveloper.app.woomin.repository.ElectionRepository;

@RestController
@RequestMapping("/elections")
public class ElectionController {
	@Autowired
	ElectionRepository electionRepo;
	
	@GetMapping("/")
	public ResponseEntity<List<Election>> getElections() {
		return new ResponseEntity<List<Election>>((List<Election>) electionRepo.findAll(), HttpStatus.OK);			
	}
	
	@GetMapping("/{sg_id}")
	public ResponseEntity<List<Election>> getElectionDetail(
				@PathVariable("sg_id") String sgId
			) {
		List<Election> electionList = electionRepo.findAllBySgId(sgId);
		return new ResponseEntity<List<Election>>((List<Election>) electionList, HttpStatus.OK);
	}
	
	@GetMapping("/getDates")
	public ResponseEntity<List<String>> getDates() {
		return new ResponseEntity<List<String>>((List<String>) electionRepo.findDistinctKeySgId(), HttpStatus.OK);
	}
}
