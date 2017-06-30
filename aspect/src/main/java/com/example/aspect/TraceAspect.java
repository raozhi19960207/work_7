package com.example.aspect;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by 康伟 on 2017/5/19.
 */

@Aspect
public class TraceAspect {
    private static Object currentObject = null;

    /*
    * 截获com.lincanbin.carbonforum包内和其子包所有类的所有方法。
    * */
    private static final String POINTCUT_ALLMETHOD =
            "execution(* com.lincanbin.carbonforum..*.*(..))&& target(Object) && this(Object)";

    @Pointcut(POINTCUT_ALLMETHOD)
    public void methodAnnotated() {}

    /*
    * 打出日志信息。
    * */
    @Around("methodAnnotated()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws  Throwable
    {
        if (currentObject == null)
        {
            currentObject = joinPoint.getTarget();
        }
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        String className = joinPoint.getThis().getClass().getName();
        String methodName = methodSignature.getName();

        String msg =  buildLogMessage(methodName, stopWatch.getTotalTime(1));

        Log.e(className,msg);
        return result;
    }

    private static String buildLogMessage(String methodName, double methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        if (StopWatch.Accuracy == 1){
            message.append("ms");
        }else {
            message.append("mic");
        }
        message.append("]      \n");

        return message.toString();
    }
}