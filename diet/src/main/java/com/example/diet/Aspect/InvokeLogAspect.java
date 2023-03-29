package com.example.diet.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InvokeLogAspect {

    @Pointcut("@annotation(com.example.diet.Aspect.InvokeLog)")
    public void pt() {
    }

    @Around("pt()")
    public Object printInvokeLog(ProceedingJoinPoint joinPoint) {
        //目标方法调用前
        Object proceed = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        try {
            proceed = joinPoint.proceed();
            //目标方法调用后
            System.out.println(methodName+":Called out");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //目标方法出现异常了
            System.out.println(methodName+":There was an Exception!");
        }
        return proceed;
    }
}
