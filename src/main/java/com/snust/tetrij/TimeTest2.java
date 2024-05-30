//https://velog.io/@suhongkim98/Intro-To-AspectJ

package com.snust.tetrij;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTest2 {
    //작고 소중한 디버그용
//    @Before("execution(* com.snust.tetrij..*(..))")
//    public void beforeMethod() {
//        System.out.println("Before method execution");
//    }

    @Around("execution(* com.snust.tetrij..*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // 메소드 실행

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 클래스, 메소드 이름 가져오기
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        if (duration < 1000) {
//            System.out.println(className + "." + methodName + " passed! (" + duration + "ms)");
        } else {
            System.out.println(className + "." + methodName + " not pass! (" + duration + "ms)");
        }

        return result;
    }
}