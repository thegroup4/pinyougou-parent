package cn.itcast.core.controller;

import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/search")
    public PageResult search(Integer page , Integer rows, @RequestBody User user){

      PageResult pageResult=  userService.search(page,rows,user);



        return pageResult;
    }
    @RequestMapping("/updateStatus")
    public Result updateStatus(String status,Long[] ids){

        try {
            userService.updateStatus(ids,status);
           return new  Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new  Result(false,"修改是失败");
        }

    }
}
