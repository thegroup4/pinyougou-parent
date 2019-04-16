package cn.itcast.core.controller;

import cn.itcast.core.service.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.GoodsVo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Reference
    private SeckillService seckillService;

    @RequestMapping("/add")
    public Result add(@DateTimeFormat String start, @DateTimeFormat String end,@RequestBody GoodsVo goodsVo){

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date startTime1 = simpleDateFormat.parse(start);
            Date endTime1 = simpleDateFormat.parse(end);
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            seckillService.add(sellerId,startTime1,endTime1,goodsVo);

            return new Result(true,"添加成功,请耐心等待审核通过");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败,请重新");
        }
    }

    @RequestMapping("/findSeckillGoodsListBySellerId")
    public List<Map<String, Object>> findSeckillGoodsListBySellerId(){
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            return seckillService.findSeckillGoodsListBySellerId(sellerId);
    }


    @RequestMapping("/findSeckillGoodsVo")
    public Map<String, Object> findSeckillGoodsVo(Long goodsId){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        return seckillService.findSeckillGoodsVo(sellerId,goodsId);
    }

}
