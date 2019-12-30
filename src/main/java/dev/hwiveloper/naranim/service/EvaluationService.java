package dev.hwiveloper.naranim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hwiveloper.naranim.mapper.EvaluationMapper;

@Service
public class EvaluationService {
	@Autowired
	EvaluationMapper evaluationMapper;
	
	public List<Map<String, Object>> getEvaluationByCandidate(Map<String, Object> param) {
		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("huboId", param.get("huboId"));
		
		return evaluationMapper.getEvaluationByCandidate(sqlParam);
	}
	
	public List<Map<String, Object>> getElectionByCandidate(Map<String, Object> param) {
		return evaluationMapper.getElectionByCandidate(param);
	}
}
