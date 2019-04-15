package cn.itcast.core.controller;

import cn.itcast.core.service.LogService;
import entity.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
public class LogController {

    @Autowired
    private LogService logService;
    @Autowired
    private HttpServletRequest request;

    @Before("execution(* cn.itcast.core.controller.*.*(..))")
    public void before(JoinPoint joinPoint){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(name!=null){
            SysLog sysLog = new SysLog();
            //保存日志
            //        private Long id;
            //        private String visitTime;
            sysLog.setVisitTime(new SimpleDateFormat().format(new Date()));
            //        private String username;
            sysLog.setUsername(name);
            //        private String ip;
            sysLog.setIp(request.getRemoteAddr());
            //        private String method;
            String packageAndClassAndMethod = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
            sysLog.setMethod(packageAndClassAndMethod);
            //保存
            logService.save(sysLog);

        }


    }
}
