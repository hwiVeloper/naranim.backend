package dev.hwiveloper.naranim.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ElectionResultMapper {
	
	// 시군구명 단위 선거결과 조회
	List<Map<String, Object>> getElectionResultByWiwName(Map<String, Object> param);
	
	// 선거구명 단위 선거결과 조회
	List<Map<String, Object>> getElectionResultBySggName(Map<String, Object> param);
	
	// 정치인 이름찾기
	List<Map<String, Object>> getCandidateSearch(Map<String, Object> param);
	
	// 정치인 상세 기본정보
	Map<String, Object> getCandidateDetail(Map<String, Object> param);
	
	// 정치인 이력정보
	List<Map<String, Object>> getCandidateHistory(Map<String, Object> param);
}
