package dev.hwiveloper.naranim.service;

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
		return evaluationMapper.getEvaluationByCandidate(param);
	}
}
