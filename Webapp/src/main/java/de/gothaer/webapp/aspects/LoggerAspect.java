package de.gothaer.webapp.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
//@Profile("dev")
public class LoggerAspect {



    @Before("de.gothaer.webapp.aspects.Pointcuts.personenQueryControllerMethods()")
    public void logAdvice(final JoinPoint joinPoint) {
        log.warn(String.format(
                "##################### Methode  %s wurde aufgerufen ########################"
                , joinPoint.getSignature().getName()));
    }

    @AfterReturning(value="de.gothaer.webapp.aspects.Pointcuts.personenQueryControllerMethods()", returning = "result")
    public void logAfterReturning(final JoinPoint joinPoint, Object result) {
        log.warn(String.format("############################# Afterreturning: %s ######################", joinPoint.getSignature().getName()));
        log.warn(String.format("############################# Result: %s ######################", result.toString()));
    }

    @After(value="execution(public * de.gothaer.webapp.presentation.v1.PersonQueryController.*(..)) ")
    public void logAfterReturning(final JoinPoint joinPoint) {
        log.warn(String.format("############################# After: %s ######################", joinPoint.getSignature().getName()));

    }

    @AfterThrowing(value="execution(public * de.gothaer.webapp.presentation.v1.PersonQueryController.*(..))", throwing = "ex")
    public void afterThrowing(final JoinPoint joinPoint, Throwable ex) {

        log.warn(String.format("############################# Afterreturning: %s ######################", joinPoint.getSignature().getName()));
        log.warn(String.format("############################# Result: %s ######################", ex.toString()));
    }

    @Around(value="execution(public * de.gothaer.webapp.presentation.v1.PersonQueryController.*(..)) ")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    @Before("de.gothaer.webapp.aspects.Pointcuts.serviceMethods()")
    public void logAdviceServices(final JoinPoint joinPoint) {
        log.warn(String.format(
                "##################### Methode  %s wurde aufgerufen ########################"
                , joinPoint.getSignature().getName()));
    }

    @Before("de.gothaer.webapp.aspects.Pointcuts.dozentMethods()")
    public void logDozentServices(final JoinPoint joinPoint) {
        log.warn(String.format(
                "##################### Methode  %s wurde aufgerufen ########################"
                , joinPoint.getSignature().getName()));
    }

}
