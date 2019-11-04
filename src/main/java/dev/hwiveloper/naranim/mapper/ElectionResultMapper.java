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
}
