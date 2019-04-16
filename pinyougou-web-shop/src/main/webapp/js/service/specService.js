app.service('specService', function ($http) {

    //分页
    this.search=function(page,rows,status){
        return $http.get('../spec/search.do?page='+page+'&rows='+rows+"&status="+status);
    }
    //增加
    this.add=function(entity){
        return  $http.post('../spec/add.do',entity );
    }
    //修改
    this.update=function(entity){
        return  $http.post('../spec/update.do',entity );
    }
    //删除
    this.dele=function(ids){
        return $http.get('../spec/delete.do?ids='+ids);
    }

    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../spec/search.do?page='+page+"&rows="+rows, searchEntity);
    }

    this.selectOptionList=function(){
        return $http.get("../spec/selectOptionList.do");
    }

    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../spec/findAll.do');
    }
    //分页
    this.findPage=function(page,rows){
        return $http.get('../spec/findPage.do?page='+page+'&rows='+rows);
    }
    //查询实体
    this.findOne=function(id){
        return $http.get('../spec/findOne.do?id='+id);
    }
});