package dev.hwiveloper.app.woomin.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.hwiveloper.app.woomin.repository.MemberRepository;
import dev.hwiveloper.app.woomin.repository.OrigRepository;
import dev.hwiveloper.app.woomin.repository.PolyRepository;
import dev.hwiveloper.app.woomin.repository.ReeleGbnRepository;

/*
 * CodeSchedule
 * 각종 코드성 정보에 관한 API 호출 후 DB에 저장한다.
 */
@Component
public class CodeSchedule {
	@Value("${keys.national-assembly-info-service}")
	String NATIONAL_ASSEMBLY_INFO_SERVICE;
	
	@Autowired
	PolyRepository polyRepo;
	
	@Autowired
	OrigRepository origRepo;
	
	@Autowired
	ReeleGbnRepository reeleGbnRepo;
	
//	@Scheduled(cron="0 0 0 */1 * ?")
	
}
