package cn.itcast.core.service;

import java.util.Map;

public interface PayService {
    Map<String,String> createNative(String name);

    Map<String,String> queryPayStatus(String out_trade_no);

<<<<<<< Updated upstream
    Map<String,String> closePay(String out_trade_no);
=======
    /**
     * 关闭订单
     * @param out_trade_no
     * @return
     */
    public Map closePay(String out_trade_no);
>>>>>>> Stashed changes
}

