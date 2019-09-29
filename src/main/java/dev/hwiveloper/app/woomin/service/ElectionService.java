package dev.hwiveloper.app.woomin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hwiveloper.app.woomin.mapper.ElectionMapper;

@Service
public class ElectionService {
	@Autowired
	ElectionMapper electionMapper;
	
	public List<Map<String, Object>> selectTest() {
		return electionMapper.selectTest();
	}
	
	public List<String> getElections(Map<String, Object> param) {
		return electionMapper.getElections(param);
	}
}
