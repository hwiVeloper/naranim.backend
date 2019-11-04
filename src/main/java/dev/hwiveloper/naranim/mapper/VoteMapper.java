package dev.hwiveloper.naranim.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VoteMapper {
	// 투표 정보 조회 (시도별)
	List<Map<String, Object>> getVoteInfo(Map<String, Object> param);
	
	// 투표 합계정보 조회 (시도별)
	List<Map<String, Object>> getVoteSdTotalInfo(Map<String, Object> param);
	
	// 투표 합계정보 조회 (합계별)
	List<Map<String, Object>> getVoteTotalInfo(Map<String, Object> param);
}
