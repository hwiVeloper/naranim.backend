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

import dev.hwiveloper.app.woomin.domain.election.Vote;
import dev.hwiveloper.app.woomin.repository.VoteRepository;

@RestController
@RequestMapping("/votes")
public class VoteController {
	@Autowired
	VoteRepository voteRepo;
	
	/**
	 * 시도명 리스트 선거아이디와 선거종류를 기준으로 조회
	 * @param request
	 * @return
	 */
	@PostMapping("/getSdNames")
	public ResponseEntity<List<?>> getSdNames(@RequestBody Map<String, Object> request) {
		String sgId = (String) request.get("sg_id");
		String sgTypeCode = (String) request.get("sg_type_code");
		
		List<?> result = (List<String>) voteRepo.findDistinctSdNameByKey(sgId, sgTypeCode);
		
		return new ResponseEntity<List<?>>(result, HttpStatus.OK);
	}
	
	/**
	 * 투표 현황 리스트 조회
	 * @param request
	 * @return
	 */
	@PostMapping("/getVotes")
	public ResponseEntity<List<Vote>> getVotes(@RequestBody Map<String, Object> request) {
		String sgId = (String) request.get("sg_id");
		String sgTypeCode = (String) request.get("sg_type_code");
		String sdName = (String) request.get("sd_name");
		
		List<Vote> result = null;
		
		if (request.get("sd_name") == null
		||  request.get("sd_name").toString().equals("")) {
			result = voteRepo.findAllByKey(sgId, sgTypeCode);
		} else {
			result = voteRepo.findAllByKeyAndSdName(sgId, sgTypeCode, sdName);
		}
		
		return new ResponseEntity<List<Vote>>(result, HttpStatus.OK);
	}
	
	/**
	 * 투표 현황 합계 통계 조회
	 * @param request
	 * @return
	 */
	@PostMapping("/getTotalVotes")
	public ResponseEntity<List<Vote>> getTotalElections(@RequestBody Map<String, Object> request) {
		String sgId = (String) request.get("sg_id");
		String sgTypeCode = (String) request.get("sg_type_code");
		
		List<Vote> result = voteRepo.findAllTotal(sgId, sgTypeCode);
		
		return new ResponseEntity<List<Vote>>(result, HttpStatus.OK);
	}
}
