//服务层
app.service('typeTempService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll1=function(){
		return $http.get('../typeTemp/findAll.do');
	}
	//分页 
	this.findPage1=function(page,rows){
		return $http.get('../typeTemp/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne1=function(id){
		return $http.get('../typeTemp/findOne.do?id='+id);
	}
	//增加 
	this.add1=function(entity){
		return  $http.post('../typeTemp/add.do',entity );
	}
	//修改 
	this.update1=function(entity){
		return  $http.post('../typeTemp/update.do',entity );
	}
	//删除
	this.dele1=function(ids){
		return $http.get('../typeTemp/delete.do?ids='+ids);
	}
	//搜索
	this.search1=function(page,rows,searchEntity){
		return $http.post('../typeTemp/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
	
	this.findBySpecList1=function(id){
		return $http.get('../typeTemp/findBySpecList.do?id='+id);
	}
});
