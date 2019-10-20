package dev.hwiveloper.woomin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hwiveloper.woomin.mapper.ElectionMapper;

@Service
public class ElectionService {
	@Autowired
	ElectionMapper electionMapper;
	
	public List<Map<String, Object>> getElectionTypes() {
		return electionMapper.getElectionTypes();
	}
	
	public List<Map<String, Object>> getElections(Map<String, Object> param) {
		return electionMapper.getElections(param);
	}
	
	public List<String> getSdNames(Map<String, Object> param) {
		return electionMapper.getSdNames(param);
	}
	
	public List<String> getSggNames(Map<String, Object> param) {
		return electionMapper.getSggNames(param);
	}
	
	public List<String> getWiwNames(Map<String, Object> param) {
		return electionMapper.getWiwNames(param);
	}
}
