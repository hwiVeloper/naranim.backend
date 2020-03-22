package dev.hwiveloper.naranim.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import dev.hwiveloper.naranim.domain.common.Region;

@Mapper
public interface RegionMapper {
	List<HashMap<String, Object>> getRegions(HashMap<String, Object> param);
}
