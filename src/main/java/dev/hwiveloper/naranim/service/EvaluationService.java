package dev.hwiveloper.naranim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hwiveloper.naranim.mapper.EvaluationMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvaluationService {
	@Autowired
	EvaluationMapper evaluationMapper;
	
	public List<Map<String, Object>> getEvaluationByCandidate(Map<String, Object> param) {
		return evaluationMapper.getEvaluationByCandidate(param);
	}
	
	public List<Map<String, Object>> getElectionByCandidate(Map<String, Object> param) {
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		sqlMap.put("name", param.get("name"));
		sqlMap.put("birthday", ((String)param.get("birthday")).replaceAll("\\.", ""));
		sqlMap.put("hanjaName", param.get("hanjaName"));
		
		return evaluationMapper.getElectionByCandidate(sqlMap);
	}
}
