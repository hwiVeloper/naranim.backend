package dev.hwiveloper.app.woomin.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Aspect
public class ControllerAop {

	Logger logger = LoggerFactory.getLogger(getClass());

	ObjectMapper objectMapper = new ObjectMapper();

	@Before("execution(* dev.hwiveloper.app.woomin.controller.*.*(..))")
	public void onBeforeHandler(JoinPoint joinPoint) {
		// 메서드 실행 전에 수행
		logger.info("=============== onBeforeThing");
	}

	@After("execution(* dev.hwiveloper.app.woomin.controller.*.*(..))")
	public void onAfterHandler(JoinPoint joinPoint) {
		// 메서드 실행 후에 수행
		logger.info("=============== onAfterHandler");
	}

	@AfterReturning(pointcut = "execution(* dev.hwiveloper.app.woomin.controller.*.*(..))", returning = "ret")
	public void onAfterReturningHandler(JoinPoint joinPoint, Object ret) {
		logger.info("@AfterReturning : " + ret);
		logger.info("=============== onAfterReturningHandler");
	}

	@Around("execution(* dev.hwiveloper.app.woomin.controller.*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
		
		Object result;
		result = pjp.proceed();

		logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());

		return result;
	}
}
