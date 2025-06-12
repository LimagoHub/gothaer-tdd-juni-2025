package de.gothaer.webapp.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {


    @Pointcut(value="execution(public * de.gothaer.webapp.presentation.v1.PersonQueryController.*(..))")
    public void personenQueryControllerMethods(){}

    @Pointcut(value="@within(org.springframework.stereotype.Service)")
    public void serviceMethods(){}

    @Pointcut("@within(de.gothaer.webapp.aspects.Dozent)")
    public void dozentMethods(){}

}
