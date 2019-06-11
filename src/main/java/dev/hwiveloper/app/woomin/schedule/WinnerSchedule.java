package dev.hwiveloper.app.woomin.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.hwiveloper.app.woomin.repository.ElectionRepository;
import dev.hwiveloper.app.woomin.repository.WinnerRepository;

/**
 * WinnerSchedule
 * 당선인 정보를 조회하여 DB에 저장한다.
 * 
 * 매일 ??:??:?? => getWinnerInfoInqire (당선인 조회)
 */
@Component
public class WinnerSchedule {
	@Value("${keys.winner-service}")
	String WINNER_SERVICE;
	
	@Autowired
	WinnerRepository winnerRepo;
	
	@Autowired
	ElectionRepository electionRepo;
	
	/**
	 * getWinnerInfoInqire
	 * 당선인 정보 조회
	 */
	public void getWinnerInfoInqire() {
		
	}
}
