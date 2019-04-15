package cn.itcast.core.service;

import cn.itcast.core.pojo.user.User;
import entity.PageResult;

public interface UserService {
    void sendCode(String phone);

    void add(String smscode, User user);

    PageResult search(Integer page, Integer rows, User user);

    void updateStatus(Long[] ids, String status);


}
