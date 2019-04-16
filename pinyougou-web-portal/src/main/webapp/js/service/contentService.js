app.service("contentService",function($http){
	this.findByCategoryId = function(categoryId){
		return $http.get("content/findByCategoryId.do?categoryId="+categoryId);
		// ../ / 不写都行
	}
//查询全部分类的
    this.findAll = function(){
        return $http.get("content/findAllItemCatList.do");
    }
});