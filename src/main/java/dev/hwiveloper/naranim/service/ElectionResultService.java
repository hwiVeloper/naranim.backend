package dev.hwiveloper.naranim.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hwiveloper.naranim.mapper.ElectionResultMapper;

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
	
	public List<Map<String, Object>> getCandidateSearch(Map<String, Object> param) {
		return electionResultMapper.getCandidateSearch(param);
	}
	
	public Map<String, Object> getCandidateDetail(Map<String, Object> param) {
		return electionResultMapper.getCandidateDetail(param);
	}
	
	public List<Map<String, Object>> getCandidateHistory(Map<String, Object> param) {
		return electionResultMapper.getCandidateHistory(param);
	}
}
