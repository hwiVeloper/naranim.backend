package dev.hwiveloper.naranim.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegionMapper {
	List<HashMap<String, Object>> getRegions(HashMap<String, Object> param);
	
	List<HashMap<String, Object>> getMyRegionCandidates(HashMap<String, Object> param);
	
	String getSggByRegion(HashMap<String, Object> param);
	
	List<Map<String, Object>> getPollPlaces(HashMap<String, Object> param);
}
