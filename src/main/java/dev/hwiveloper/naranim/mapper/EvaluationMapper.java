package dev.hwiveloper.naranim.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EvaluationMapper {
	// 해당 후보의 평가 조회
	List<Map<String, Object>> getEvaluationByCandidate(Map<String, Object> param);
	
	// 후보의 선거이력 조회
	List<Map<String, Object>> getElectionByCandidate(Map<String, Object> param);
}
