package com.learning.connector.aop;

import com.learning.connector.exception.AccountNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author vickyaa
 * @date 5/5/25
 * @file AccountExceptionLoggingAspect
 */

@Aspect
@Component
public class AccountExceptionLoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(AccountExceptionLoggingAspect.class);

  @AfterThrowing(
      pointcut = "execution(* com.learning.connector.service.AccountService.getAccountByNumber(..))",
      throwing = "exception"
  )
  public void logAccountNotFoundException(JoinPoint joinPoint, AccountNotFoundException exception) {
    String accountNumber = (String) joinPoint.getArgs()[0];

    logger.error("Account lookup failed: Account with number '{}' was not found. Error message: {}",
        accountNumber, exception.getMessage());
  }

  @AfterThrowing(
      pointcut = "execution(* com.learning.connector.exception.GlobalExceptionHandler.handleAccountNotFoundException(..))",
      throwing = "exception"
  )
  public void logExceptionHandling(JoinPoint joinPoint, Exception exception) {
    AccountNotFoundException originalException = (AccountNotFoundException) joinPoint.getArgs()[0];

    logger.info(
        "AccountNotFoundException was handled by GlobalExceptionHandler. Original exception: {}",
        originalException.getMessage());
  }
}

