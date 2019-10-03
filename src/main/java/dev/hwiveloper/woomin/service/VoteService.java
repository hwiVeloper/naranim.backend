package dev.hwiveloper.woomin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hwiveloper.woomin.mapper.VoteMapper;

@Service
public class VoteService {
	@Autowired
	VoteMapper voteMapper;
	
	public Map<String, Object> getVoteInfo(Map<String, Object> param) {
		Map<String, Object> voteInfo = new HashMap<String, Object>();
		
		voteInfo.put("VOTE_INFO", voteMapper.getVoteInfo(param));
		voteInfo.put("VOTE_TOTAL_INFO", voteMapper.getVoteSdTotalInfo(param));
		
		return voteInfo;
	}
	
	public List<Map<String, Object>> getVoteTotalInfo(Map<String, Object> param) {
		return voteMapper.getVoteTotalInfo(param);
	}
}
