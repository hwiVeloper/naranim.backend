package dev.hwiveloper.naranim.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PreCandidateMapper {
	List<Map<String, Object>> getPreCandidateList(Map<String, Object> param);
}
