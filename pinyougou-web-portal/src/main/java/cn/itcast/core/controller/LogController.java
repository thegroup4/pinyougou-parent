package cn.itcast.core.controller;

import cn.itcast.common.utils.IdWorker;
import cn.itcast.core.service.LogService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.SysLog;
import org.aspectj.lang.JoinPoint;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
public class LogController {

    @Reference
    private LogService logService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IdWorker idWorker;

    @Before("execution(* cn.itcast.core.controller.*.*(..))")
    public void before(JoinPoint joinPoint){
        System.out.println(111);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(name!=null){
            SysLog sysLog = new SysLog();
            sysLog.setId(idWorker.nextId());
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
