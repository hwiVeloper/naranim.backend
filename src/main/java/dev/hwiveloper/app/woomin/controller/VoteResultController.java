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
	public ResponseEntity<List<Map<String, Object>>> getUniqueSdNames(
		@PathVariable("sg_id") String sgId,
		@PathVariable("sg_type_code") String sgTypeCode
	) {
		List<String> listSdNames = voteResultRepo.findDistinctSdNameByKey(sgId, sgTypeCode);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		int size = listSdNames.size();
		
		for (int i = 0; i < size; i++) {
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			String[] names = listSdNames.get(i).split(",");
			tmpMap.put("sdName", names[0]);
			tmpMap.put("sggName", names[1]);
			result.add(tmpMap);
		}
		
		return new ResponseEntity<List<Map<String, Object>>>(result, HttpStatus.OK);
	}
}
