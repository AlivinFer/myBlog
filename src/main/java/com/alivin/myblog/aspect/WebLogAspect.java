package com.alivin.myblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 请求的日志处理
 *
 * @author Fer
 * date 2021/8/22
 */
@Component
@Aspect
public class WebLogAspect {

    private static Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.alivin.myblog.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 记录以下内容
            LOGGER.info("URL: " + request.getRequestURI().toString());
            LOGGER.info("HTTP_METHOD: " + request.getMethod());
            LOGGER.info("IP: " + request.getRemoteAddr());
            LOGGER.info("CLASS_METHOD: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            LOGGER.info("ARGS: " + Arrays.toString(joinPoint.getArgs()));
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        LOGGER.info("RESPONSE: " + ret);
        LOGGER.info("SPEND_TIME: " + (System.currentTimeMillis()-startTime.get()));
        // 用完之后要清除，不然可能导致内存泄露;
        startTime.remove();
    }
}
