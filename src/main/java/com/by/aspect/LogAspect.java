package com.by.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //第一个*:拦截全部类无论时public还是private,第二个*:所有类名，第三个*：所有方法，..代表任何参数
    //表示
    @Pointcut("execution(* com.by.controller.*.*(..))")
    public void log(){}

    @Before("log()")//在log方法之前执行
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." +joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}",requestLog);
    }
    @After("log()")
    public void doAfter(){
        logger.info("---doAfter----");
    }
    //拦截的方法执行完后获取返回值
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}" , result);
    }
//    private class RequestLog{
//        private String url;
//        private String ip;
//        private String classMethod;
//        private Object[] args;
//
//        public RequestLog(String url, String ip, String classMethod, Object[] args) {
//            this.url = url;
//            this.ip = ip;
//            this.classMethod = classMethod;
//            this.args = args;
//        }
//
//        @Override
//        public String toString() {
//            return "{" +
//                    "url='" + url + '\'' +
//                    ", ip='" + ip + '\'' +
//                    ", classMethod='" + classMethod + '\'' +
//                    ", args=" + Arrays.toString(args) +
//                    '}';
//        }
//    }
}
