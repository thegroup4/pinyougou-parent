package cn.itcast.core.service;

import cn.itcast.core.pojo.user.User;
import entity.PageResult;
import org.apache.ibatis.annotations.Select;

public interface UserService {
    void sendCode(String phone);

    void add(String smscode, User user);

    int findCount();

    PageResult search(Integer page, Integer rows, User user);

    User findOne(Long id);

    int findActiveUser();
}
