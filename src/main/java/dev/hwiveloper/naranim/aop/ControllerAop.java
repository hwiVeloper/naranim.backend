package dev.hwiveloper.naranim.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class ControllerAop {

	ObjectMapper objectMapper = new ObjectMapper();

	@Before("execution(* dev.hwiveloper.naranim.controller.*.*(..))")
	public void onBeforeHandler(JoinPoint joinPoint) {
		// 메서드 실행 전에 수행
		log.info("=============== onBeforeThing");
	}

	@After("execution(* dev.hwiveloper.naranim.controller.*.*(..))")
	public void onAfterHandler(JoinPoint joinPoint) {
		// 메서드 실행 후에 수행
		log.info("=============== onAfterHandler");
	}

//	@AfterReturning(pointcut = "execution(* dev.hwiveloper.naranim.controller.*.*(..))", returning = "ret")
//	public void onAfterReturningHandler(JoinPoint joinPoint, Object ret) {
//		log.info("@AfterReturning : " + ret);
//		log.info("=============== onAfterReturningHandler");
//	}

	@Around("execution(* dev.hwiveloper.naranim.controller.*.*(..))")
	public ResponseEntity<?> around(ProceedingJoinPoint pjp) throws Throwable {
		log.info("==========================> " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName() + " START");
		log.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
		
		ResponseEntity<?> result;
		result = (ResponseEntity<?>) pjp.proceed();

		log.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
		
		log.info("==========================> " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName() + " END");

		return result;
	}
}
