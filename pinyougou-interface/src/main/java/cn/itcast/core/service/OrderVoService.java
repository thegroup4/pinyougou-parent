package cn.itcast.core.service;

import entity.PageResult;

public interface OrderVoService {
    PageResult findPage(Integer page, Integer rows, String orderItem);

}
