package dev.hwiveloper.app.woomin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.app.woomin.domain.election.VoteResult;
import dev.hwiveloper.app.woomin.repository.VoteResultRepository;

@RestController
@RequestMapping("/voteResult")
public class VoteResultController {
	@Autowired
	VoteResultRepository voteResultRepo;
	
	/**
	 * 시도명 리스트 : 선거아이디와 선거종류를 기준으로
	 * @param sgId
	 * @param sgTypeCode
	 * @return
	 */
	@GetMapping("/{sg_id}/{sg_type_code}")
	public ResponseEntity<Map<String, Object>> getUniqueSdNames(
		@PathVariable("sg_id") String sgId,
		@PathVariable("sg_type_code") String sgTypeCode
	) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<String> listSdSggNames = voteResultRepo.findDistinctSdNameSggNameByKey(sgId, sgTypeCode);
		List<Map<String, Object>> listMapSdNames = new ArrayList<Map<String, Object>>(); 
		
		int size = listSdSggNames.size();
		
		for (int i = 0; i < size; i++) {
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			String[] names = listSdSggNames.get(i).split(",");
			tmpMap.put("sdName", names[0]);
			tmpMap.put("sggName", names[1]);
			listMapSdNames.add(tmpMap);
		}
		
		List<String> listSdNames = voteResultRepo.findDistinctSdNameByKey(sgId, sgTypeCode);
		
		result.put("sdSggNames", listMapSdNames);
		result.put("sdNames", listSdNames);
		
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/{sg_id}/{sg_type_code}/{sgg_name}")
	public ResponseEntity<List<VoteResult>> getVoteResults(
		@PathVariable("sg_id") String sgId,
		@PathVariable("sg_type_code") String sgTypeCode,
		@PathVariable("sgg_name") String sggName
	) {
		List<VoteResult> result = voteResultRepo.findAllByKey(sgId, sgTypeCode, sggName);
		return new ResponseEntity<List<VoteResult>>(result, HttpStatus.OK);
	}
}
