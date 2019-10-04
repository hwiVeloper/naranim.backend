package dev.hwiveloper.woomin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hwiveloper.woomin.mapper.ElectionResultMapper;

@Service
public class ElectionResultService {
	@Autowired
	ElectionResultMapper electionResultMapper;
	
	public List<Map<String, Object>> getElectionResult(Map<String, Object> param) {
		if (param.containsKey("WIW_NAME")) {
			return electionResultMapper.getElectionResultByWiwName(param);
		} else
		if (param.containsKey("SGG_NAME")) {
			return electionResultMapper.getElectionResultBySggName(param);
		} else {
			return null;
		}
	}
}
