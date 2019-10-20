package dev.hwiveloper.woomin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ElectionMapper {
	// 선거 종류 조회
	List<Map<String, Object>> getElectionTypes();
	
	// 선거 일자 조회
	List<Map<String, Object>> getElections(Map<String, Object> param);
	
	// 시도명 조회
	List<String> getSdNames(Map<String, Object> param);
	
	// 선거구명 조회
	List<String> getSggNames(Map<String, Object> param);
	
	// 구시군명 조회
	List<String> getWiwNames(Map<String, Object> param);
}
