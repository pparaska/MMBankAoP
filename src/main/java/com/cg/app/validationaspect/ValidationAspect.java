package com.cg.app.validationaspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.cg.app.account.SavingsAccount;
import com.cg.app.exception.InvalidInputException;

@Aspect
@Component
public class ValidationAspect {
	private Logger logger = Logger.getLogger(ValidationAspect.class.getName());

	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.withdraw(..))")
	public void log1(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("In Withdraw method");
		Object[] params = pjp.getArgs();
		Double amount = (Double) params[1];
		SavingsAccount account = (SavingsAccount) params[0];
		Double currentBalance = account.getBankAccount().getAccountBalance();
		if (amount > 0 && currentBalance >= amount) {
			logger.info("Successful");
			pjp.proceed();
		} else {
			logger.info("Invalid Input Amount!");
		}
	}

	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.deposit(..))")
	public void log2(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("In deposit method");
		Object[] params = pjp.getArgs();
		Double amount = (Double) params[1];
		if (amount > 0) {
			logger.info("Successful");
			pjp.proceed();
		}

		else {
			logger.info("Invalid Input Amount!");
		}
	}
}
