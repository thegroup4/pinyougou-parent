package entity;

public class StatusCode {
    /**
     * 秒杀未开始
     */
    public static final String SECKILL_NOTBEGIN = "0";
    /**
     *  秒杀中
     */
    public static final String SECKILL_ON = "1";
    /**
     * 秒杀结束
     */
    public static final String SECKILL_OFF = "2";
    /**
     * 非秒杀商品
     */
    public static final String NOT_SECKILL = "0";
    /**
     * 秒杀商品
     */
    public static final String IS_SECKILL = "1";
    /**
     * 审核不通过
     */
    public static final String NOT_PASS = "0";
    /**
     * 审核通过
     */
    public static final String IS_PASS = "1";
}
