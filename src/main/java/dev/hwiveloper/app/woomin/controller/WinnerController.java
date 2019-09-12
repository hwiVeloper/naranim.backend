package dev.hwiveloper.app.woomin.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hwiveloper.app.woomin.domain.election.Winner;
import dev.hwiveloper.app.woomin.repository.WinnerRepository;

@RestController
@RequestMapping("/winners")
public class WinnerController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WinnerRepository winnerRepo;
	
	@PostMapping("")
	public ResponseEntity<List<Winner>> getWinners(@RequestBody Map<String, Object> request) {
		String sgId = (String) request.get("sgId");
		String sgTypeCode = (String) request.get("sgTypeCode");
		String wiwName = (String) request.get("wiwName");
		String sggName = (String) request.get("sggName");
		
		List<Winner> result = null;
		if (wiwName != null)
			result = winnerRepo.findWinnerByWiwName(sgId, sgTypeCode, wiwName);
		else if (sggName != null)
			result = winnerRepo.findWinnerBySggName(sgId, sgTypeCode, sggName);
		
		return new ResponseEntity<List<Winner>>(result, HttpStatus.OK);
	}
}
