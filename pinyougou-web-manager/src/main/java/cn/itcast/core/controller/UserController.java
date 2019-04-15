package cn.itcast.core.controller;


import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/findCount")
    public int findAll(){
        return userService.findCount();
    }

    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows , @RequestBody User user){
        //运营商后台管理商品的时候 对所有商家进行统一处理

        return  userService.search(page,rows,user);
    }
    @RequestMapping("/findOne")
    public User findOne(Long id){
        return userService.findOne(id);
    }

    /**
     * 统计当前活跃用户个数
     */
    @RequestMapping("/findActiveCount")
    public int findActiveUser(){
        return userService.findActiveUser();
    }
}
