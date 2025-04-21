package com.learning.aop.aspect;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author vickyaa
 * @date 4/17/25
 * @file StudentServiceAspect
 */

@Aspect
@Component
public class StudentServiceAspect {

    private static final Logger logger = Logger.getLogger(StudentServiceAspect.class.getName());

    @Pointcut("execution(* com.learning.aop.service.StudentService.*(..)) ")
    private void anyStudentService() {}

    @Before("anyStudentService() && args(firstName, lastName)")
    public void beforeAdvice(JoinPoint joinPoint, String firstName, String lastName) {
        logger.info("Before method: " + joinPoint.getSignature() +
            " - Adding Student with first name: " + firstName +
            ", second name: " + lastName);
    }

    @After("anyStudentService() && args(firstName, lastName)")
    public void adviceAfter(JoinPoint joinPoint, String firstName, String lastName) {
        logger.info("After method: " + joinPoint.getSignature() +
            " - Adding Student with first name: " + firstName +
            ", second name: " + lastName);
    }

    @AfterReturning(pointcut = "anyStudentService()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result){
        logger.info("Method returned: " + result);
    }

    @AfterThrowing(pointcut = "anyStudentService()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.log(Level.SEVERE, "Method " + joinPoint.getSignature().toShortString() +
            " threw exception: ", error);
    }

    @Around("anyStudentService()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before and after method: " + joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }
}