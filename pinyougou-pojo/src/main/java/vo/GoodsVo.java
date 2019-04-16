package vo;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsDesc;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.seckill.SeckillGoods;

import java.io.Serializable;
import java.util.List;

/**
 * 包装对象
 * 组合对象
 */
public class GoodsVo implements Serializable{

    private String title;

    private String introduction;
    //商品对象
    private Goods goods;
    //商品详情对象
    private GoodsDesc goodsDesc;
    //库存对象结果集
    private List<Item> itemList;

    public List<SeckillGoods> getSeckillGoodsList() {
        return seckillGoodsList;
    }

    public void setSeckillGoodsList(List<SeckillGoods> seckillGoodsList) {
        this.seckillGoodsList = seckillGoodsList;
    }

    private List<SeckillGoods> seckillGoodsList;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "GoodsVo{" +
                "title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", goods=" + goods +
                ", goodsDesc=" + goodsDesc +
                ", itemList=" + itemList +
                '}';
    }
}
