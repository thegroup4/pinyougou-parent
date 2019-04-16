package cn.itcast.core.service;

import cn.itcast.common.utils.HttpClient;
import cn.itcast.core.pojo.log.PayLog;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付管理
 */
@SuppressWarnings("all")
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${appid}")
    private String appid;
    @Value("${partner}")
    private String partner;
    @Value("${partnerkey}")
    private String partnerkey;

    @Override
    public Map<String, String> createNative(String name) {
        //获取日志表
        PayLog payLog = (PayLog) redisTemplate.boundHashOps("payLog").get(name);
        //调统一下单Api
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        //发出Http请求
        HttpClient httpClient = new HttpClient(url);
        //是http https
        httpClient.setHttps(true);
        //入参：
        Map<String, String> param = new HashMap<>();
        //公众账号ID
        param.put("appid", appid);
        //商户号
        param.put("mch_id", partner);
        //随机字符串
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        //商品描述
        param.put("body", "品优购要收钱了，你给不？");
        //商户订单号
        param.put("out_trade_no", payLog.getOutTradeNo());
        // param.put("total_fee", String.valueOf(payLog.getTotalFee()));
        param.put("total_fee", "1");
        //终端IP
        param.put("spbill_create_ip", "127.0.0.1");
        //通知地址 	notify_url 	是 	String(256) 	http://www.weixin.qq.com/wxpay/pay.php 	异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        param.put("notify_url", "http://itcast.cn");
        //交易类型
        param.put("trade_type", "NATIVE");
        try {
            //签名
            String xml = WXPayUtil.generateSignedXml(param, partnerkey);
            //签名类型
            httpClient.setXmlParam(xml);
            //执行
            httpClient.post();
            //响应 xml 格式字符串
            String content = httpClient.getContent();
            //转成Map
            Map<String, String> map = WXPayUtil.xmlToMap(content);
            //二维码连接
            //金额
            map.put("total_fee", String.valueOf(payLog.getTotalFee()));
            //订单ID
            map.put("out_trade_no", payLog.getOutTradeNo());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //调用查询
    @Override
    public Map<String, String> queryPayStatus(String out_trade_no) {
        //查询订单
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";
        //发出Http请求 Apache httpClient 类
        HttpClient httpClient = new HttpClient(url);
        //是http https
        httpClient.setHttps(true);
        //入参：
        Map<String, String> param = new HashMap<>();
        //公众账号ID
        param.put("appid", appid);
        //商户号
        param.put("mch_id", partner);
        //商户订单号
        param.put("out_trade_no", out_trade_no);
       // 随机字符串
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            //签名
            String xml = WXPayUtil.generateSignedXml(param, partnerkey);
            //签名类型
            httpClient.setXmlParam(xml);
            //执行
            httpClient.post();
            //响应 xml 格式字符串
            String content = httpClient.getContent();
            //转成Map
            Map<String, String> map = WXPayUtil.xmlToMap(content);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

<<<<<<< Updated upstream

    //关闭
    @Override
    public Map<String,String> closePay(String out_trade_no) {
        //调统一下单API
        String url = "https://api.mch.weixin.qq.com/pay/closeorder";

        //发出http请求,Apache httpClient 类
        HttpClient httpClient = new HttpClient(url);
        //是 http  https
        httpClient.setHttps(true);
        //入参:
        Map<String, String> param = new HashMap<>();

//        公众账号ID 	appid 	是 	String(32) 	wxd678efh567hg6787 	微信支付分配的公众账号ID（企业号corpid即为此appId）
        param.put("appid", appid);
//        商户号 	mch_id 	是 	String(32) 	1230000109 	微信支付分配的商户号
        param.put("mch_id", partner);
        //商户订单号	out_trade_no	String(32)	20150806125346	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号
        param.put("out_trade_no", out_trade_no);

        // 随机字符串	nonce_str	是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	随机字符串，不长于32位。推荐随机数生成算法
        param.put("nonce_str", WXPayUtil.generateNonceStr());


        try {
//        签名 	sign 	是 	String(32) 	C380BEC2BFD727A4B6845133519F3AD6 	通过签名算法计算得出的签名值，详见签名生成算法
            String xml = WXPayUtil.generateSignedXml(param, partnerkey);
//        签名类型 	sign_type 	否 	String(32) 	MD5 	签名类型，默认为MD5，支持HMAC-SHA256和MD5。
            httpClient.setXmlParam(xml);
            //执行
            httpClient.post();
            //响应(xml格式)
            String content = httpClient.getContent();
            //转成map
            Map<String, String> map = WXPayUtil.xmlToMap(content);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
=======
    //关闭微信支付
    @Override
    public Map closePay(String out_trade_no) {
        //1.封装参数
        Map param=new HashMap();
        param.put("appid", appid);
        param.put("mch_id", partner);
        param.put("out_trade_no", out_trade_no);
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            //2.发送请求
            HttpClient httpClient=new HttpClient("https://api.mch.weixin.qq.com/pay/closeorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //3.获取结果
            String xmlResult = httpClient.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
            System.out.println("调动查询API返回结果："+xmlResult);
            return map;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
>>>>>>> Stashed changes
    }
}
