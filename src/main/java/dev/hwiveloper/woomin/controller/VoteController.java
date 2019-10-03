package dev.hwiveloper.woomin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.woomin.service.VoteService;

@RestController
@RequestMapping("/votes")
public class VoteController {
	@Autowired
	VoteService voteService;
	
	@PostMapping("/getVoteInfo")
	public ResponseEntity<Map<String, Object>> getVoteInfo(@RequestBody Map<String, Object> param) {
		Map<String, Object> result = voteService.getVoteInfo(param);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/getVoteTotalInfo")
	public ResponseEntity<List<Map<String, Object>>> getVoteTotalInfo(@RequestBody Map<String, Object> param) {
		List<Map<String, Object>> result = voteService.getVoteTotalInfo(param);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
