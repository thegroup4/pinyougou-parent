//服务层
app.service('seckillService',function($http){
	    	
	this.saveSeckillGoods=function(startTime,endTime,seckillgoods){
		return  $http.post('../seckill/add.do?start='+startTime+'&end='+endTime,seckillgoods );
	}

    this.findSeckillGoodsListBySellerId=function(){
        return  $http.post('../seckill/findSeckillGoodsListBySellerId.do' );
    }


    this.findSckillOrderListBySellerId=function(){
        return  $http.post('../seckillOrder/findSckillOrderListBySellerId.do' );
    }

    this.findSeckillGoodsByGoodsId=function(goodsId){
        return  $http.post('../seckill/findSeckillGoodsByGoodsId.do?goodsId='+goodsId );
    }

    this.findSeckillGoodsVo=function(goodsId){
        return  $http.post('../seckill/findSeckillGoodsVo.do?goodsId='+goodsId );
    }
});
