package cn.itcast.core.controller;

import cn.itcast.core.pojo.address.*;
import cn.itcast.core.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.org.apache.regexp.internal.RE;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 收货地址
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    //查询当前登陆人所有收货地址
    @RequestMapping("/findListByLoginUser")
    public List<Address> findListByLoginUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressService.findListByLoginUser(name);
    }

    //删除用户地址
    @RequestMapping("/deleAddr")
    public Result deleAddr(Long id){
        try {
            addressService.deleAddr(id);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    //省
    @RequestMapping("/findAllAddress")
    public List<Provinces> findAllAddress(){
        List<Provinces> allAddress = addressService.findAllAddress();
        return allAddress;
    }
    //市
    @RequestMapping("/findCityById")
    public List<Cities> findCityById(String provinceid){
        return addressService.findCityById(provinceid);
    }
    //县
    @RequestMapping("/findAreaById")
    public List<Areas> findAreaById(String cityid){
        return addressService.findAreaById(cityid);
    }

    @RequestMapping("/add")
    public Result save(@RequestBody Address entity){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            addressService.save(entity,name);
            return new Result(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"保存失败");
        }
    }
}
