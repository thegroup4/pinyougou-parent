//服务层
app.service('seckillgoodsService',function($http){
	    	

	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seckillgoods/findPage.do?page='+page+'&rows='+rows);
	}
	//删除
	this.dele=function(ids){
		return $http.get('../seckillgoods/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seckillgoods/search.do?page='+page+"&rows="+rows, searchEntity);
	}    
	
	this.updateStatus = function(ids,status){
		return $http.get('../seckillgoods/updateStatus.do?ids='+ids+"&status="+status);
	}
	this.findByGoodId = function(goodsId){
		return $http.get('../seckillgoods/findByGooIds.do?goodsId='+goodsId);
	}
});
