package dev.hwiveloper.app.woomin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.app.woomin.domain.election.Candidate;
import dev.hwiveloper.app.woomin.repository.CandidateRepository;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	CandidateRepository candidateRepo;
	
	/**
	 * 후보자 정보 조회
	 * 선거구명 또는 지역명으로 조회가 가능하다.
	 * @param request
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<List<Candidate>> getCandidates(@RequestBody Map<String, Object> request) {
		String sgId = (String) request.get("sgId");
		String sgTypeCode = (String) request.get("sgTypeCode");
		String wiwName = (String) request.get("wiwName");
		String sggName = (String) request.get("sggName");
		
		List<Candidate> result = null;
		if (wiwName != null)
			result = candidateRepo.findCandidatesByWiwName(sgId, sgTypeCode, wiwName);
		else if (sggName != null)
			result = candidateRepo.findCandidatesBySggName(sgId, sgTypeCode, sggName);
		
		return new ResponseEntity<List<Candidate>>(result, HttpStatus.OK);
	}
	
	/**
	 * 후보자 상세 정보 조회
	 * @param huboId
	 * @return
	 */
	@GetMapping("/{hubo_id}")
	public ResponseEntity<Candidate> getCandidate(
		@PathVariable("hubo_id") String huboId
	) {
		Candidate result = candidateRepo.findByKeyHuboId(huboId);
		return new ResponseEntity<Candidate>(result, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public ResponseEntity<Map<String, Object>> test() {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("test 메서드 실행");
		result.put("test1", "테스트1");
		result.put("test2", "테스트2");
		result.put("test3", "test value3");
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
}
