package dev.hwiveloper.app.woomin.common;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.hwiveloper.app.woomin.domain.common.ScheduleLog;
import dev.hwiveloper.app.woomin.repository.ScheduleLogRepository;

@Component
public class ScheduleUtil {
	@Autowired
	static ScheduleLogRepository repo;
	
	/**
	 * writeScheduleSccssLog
	 * @param group
	 * @param job
	 * @param status
	 * @param startTime
	 */
	public static void writeScheduleSccssLog(String group, String job, Date startTime) {
		ScheduleLog sLog = new ScheduleLog();
		sLog.setGroupName(group);
		sLog.setJobName(job);
		sLog.setResultType("SUCCESS");
		sLog.setResultDesc("SUCCESS");
		sLog.setStartTime(startTime);
		sLog.setEndTime(new Date());
		
		repo.save(sLog);
	}
	
	/**
	 * writeErrorLog
	 * @param group
	 * @param job
	 * @param status
	 * @param result
	 * @param startTime
	 */
	public static void writeErrorScheduleLog(String group, Object job, String result, Date startTime) {
		ScheduleLog sLog = new ScheduleLog();
		sLog.setGroupName(group);
		sLog.setJobName(job.getClass().getEnclosingMethod().getName());
		sLog.setResultType("ERROR");
		sLog.setResultDesc(result);
		sLog.setStartTime(startTime);
		sLog.setEndTime(new Date());
		
		repo.save(sLog);
	}
}