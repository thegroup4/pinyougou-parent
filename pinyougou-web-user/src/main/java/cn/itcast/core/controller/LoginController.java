package cn.itcast.core.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/login")
public class LoginController {

    //获取当前登陆人
    @RequestMapping("/name")
    public Map<String,Object> name(){
        //1:当前线程中的SecurityContext
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(111111);
        System.out.println(111111);
        System.out.println(1111111112);
        Map<String,Object> map = new HashMap<>();
        map.put("loginName",name);
       // map.put("curTime",new Date());
        return map;


    }
}
