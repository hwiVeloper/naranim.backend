package dev.hwiveloper.app.woomin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.app.woomin.domain.election.Vote;
import dev.hwiveloper.app.woomin.repository.VoteRepository;

@RestController
@RequestMapping("/votes")
public class VoteController {
	@Autowired
	VoteRepository voteRepo;
	
	/**
	 * 시도명 리스트 선거아이디와 선거종류를 기준으로 조회
	 * @param sgId
	 * @param sgTypeCode
	 * @return
	 */
	@GetMapping("/{sg_id}/{sg_type_code}")
	public ResponseEntity<List<String>> getUniqueSdNames(
		@PathVariable("sg_id") String sgId,
		@PathVariable("sg_type_code") String sgTypeCode
	) {
		List<String> result = null;
		
		result = (List<String>) voteRepo.findDistinctSdNameByKey(sgId, sgTypeCode);
		
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}
	
	/**
	 * 투표 현황 리스트 조회
	 * @param sgId
	 * @param sgTypeCode
	 * @param sdName
	 * @return
	 */
	@GetMapping("/{sg_id}/{sg_type_code}/{sd_name}")
	public ResponseEntity<List<Vote>> getElections(
		@PathVariable("sg_id") String sgId,
		@PathVariable("sg_type_code") String sgTypeCode,
		@PathVariable("sd_name") String sdName
	) {
		List<Vote> result = null;
		
		result = (List<Vote>) voteRepo.findAllByKeyAndSdName(sgId, sgTypeCode, sdName);
		
		return new ResponseEntity<List<Vote>>(result, HttpStatus.OK);
	}
	
	/**
	 * 투표 현황 합계 통계 조회
	 * @param sgId
	 * @param sgTypeCode
	 * @return
	 */
	@GetMapping("/total/{sg_id}/{sg_type_code}")
	public ResponseEntity<List<Vote>> getTotalElections(
		@PathVariable("sg_id") String sgId,
		@PathVariable("sg_type_code") String sgTypeCode
	) {
		List<Vote> result = voteRepo.findAllTotal(sgId, sgTypeCode);
		
		return new ResponseEntity<List<Vote>>(result, HttpStatus.OK);
	}
}
