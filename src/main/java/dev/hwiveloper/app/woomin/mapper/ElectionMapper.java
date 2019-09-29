package dev.hwiveloper.app.woomin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ElectionMapper {
	List<Map<String, Object>> selectTest();
	List<Map<String, Object>> getElectionTypes();
	List<String> getElections(Map<String, Object> param);
}
