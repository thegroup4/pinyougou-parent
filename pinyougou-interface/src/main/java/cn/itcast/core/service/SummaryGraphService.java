package cn.itcast.core.service;

import java.util.List;
import java.util.Map;

public interface SummaryGraphService {
    /**
     * 折线图销售订单按年月日显示
     * @return
     */
    List<Map<String,Object>> findOrderForLine(String beginDate, String endDate);

    /**
     * 饼状销售订单按年月显示
     * @return
     */
    List<Map<String,Object>> findOrderForPie(String beginDate, String endDate);

    /**
     * 折线图销售订单按年月日显示
     * @return
     */
    List<Map<String,Object>> findUserForLine(String beginDate, String endDate);
}
