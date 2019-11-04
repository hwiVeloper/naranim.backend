package dev.hwiveloper.naranim.common;

import org.slf4j.Logger;

public class LogUtil {
	final static String SCHEDULE_SUCCESS = "[SCHEDULE SUCCESS] ";
	final static String SCHEDULE_ERROR   = "[SCHEDULE ERROR] ";
	
	/**
	 * scheduleSccssLog
	 * 스케쥴러에서 발생한 성공 로그를 기록한다.
	 * @param logger
	 * @param obj
	 */
	public static void scheduleSccssLog(Logger logger, Object obj) {
		logger.info(SCHEDULE_SUCCESS.concat(getScheduleMethodName(obj)));
	}
	/**
	 * scheduleErrorLog
	 * 스케쥴러에서 발생한 에러 로그를 기록한다.
	 * @param logger
	 * @param message
	 */
	public static void scheduleErrorLog(Logger logger, Object obj, String message) {
		String printMsg = SCHEDULE_ERROR.concat(getScheduleMethodName(obj)).concat(message); 
		logger.error(printMsg);
	}
	
	private static String getScheduleMethodName(Object obj) {
		return "<" + obj.getClass().getEnclosingMethod().getName() + "> ";
	}
}
