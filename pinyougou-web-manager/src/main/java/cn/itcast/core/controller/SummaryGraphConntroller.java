package cn.itcast.core.controller;

import cn.itcast.core.service.SummaryGraphService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class SummaryGraphConntroller {

    @Reference
    private SummaryGraphService summaryGraphService;

    /**
     * 折线图示例
     * @return
     */
    @RequestMapping("/line")
    public List<Map<String,Object>> line(String beginDate,String endDate){
        List<Map<String, Object>> mapList = summaryGraphService.findOrderForLine(beginDate,endDate);
        return mapList;
    }

    /**
     * 饼状图示例
     * @return
     */
    @RequestMapping("/pie")
    public List<Map<String,Object>> pie(String beginDate,String endDate){
        List<Map<String, Object>> mapList = summaryGraphService.findOrderForPie(beginDate,endDate);
        return mapList;
    }

    /**
     * 折线图示例
     * @return
     */
    @RequestMapping("/userLine")
    public List<Map<String,Object>> userLine(String beginDate,String endDate){
        List<Map<String, Object>> mapList = summaryGraphService.findUserForLine(beginDate,endDate);
        return mapList;
    }
}
