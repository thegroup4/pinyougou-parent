//服务层
app.service('orderService',function($http){

    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../order/findAll.do');
    }

    //读取列表数据绑定到表单中
    this.findSendAll=function(){
        return $http.get('../order/findSendAll.do');
    }

    //分页
    this.findPage=function(page,rows){
        return $http.get('../order/findPage.do?page='+page+'&rows='+rows);
    }
    //日,周,月
    this.searchByDateType=function(num){
        return $http.get('../order/searchByDateType.do?type='+num);
    }
    //增加
    this.add=function(entity){
        return  $http.post('../order/add.do',entity );
    }
    //修改
    this.update=function(id){
        return  $http.get('../order/update.do?id='+id);
    }
    //图形
    this.sellNum=function(){
        return $http.get('../order/sellNum.do');
    }
    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../order/search.do?page='+page+"&rows="+rows, searchEntity);
    }
    this.updateStatus = function(ids,status){
        return $http.get('../order/updateStatus.do?ids='+ids+"&status="+status);
    }

});
