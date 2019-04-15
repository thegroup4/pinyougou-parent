//服务层
app.service('seckillordersService',function($http){
	    	

	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seckillorders/findPage.do?page='+page+'&rows='+rows);
	}



	//删除
	this.dele=function(ids){
		return $http.get('../seckillorders/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seckillorders/search.do?page='+page+"&rows="+rows, searchEntity);
	}    
	
	this.updateStatus = function(ids,status){
		return $http.get('../seckillorders/updateStatus.do?ids='+ids+"&status="+status);
	}
});
