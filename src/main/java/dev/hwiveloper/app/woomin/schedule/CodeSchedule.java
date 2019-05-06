package dev.hwiveloper.app.woomin.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.hwiveloper.app.woomin.repository.MemberRepository;

@Component
public class CodeSchedule {
	@Value("${keys.national-assembly-info-service}")
	String NATIONAL_ASSEMBLY_INFO_SERVICE;
	
	@Autowired
	MemberRepository memberRepo;
	
//	@Scheduled(cron="0 0 0 */1 * ?")
}
