//控制层
app.controller('orderController' ,function($scope,$controller,$location,orderService){

    $controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        orderService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    };

    //发货改状态
    $scope.sendOrderItem = function(id){
        var message =  confirm("您确定要发货吗");
        if (message)
        {
            orderService.update(id).success(
                function (response) {
                    if(response.flag){
                        // 保存成功
                        alert(response.message);
                        $scope.reloadList();
                    }else{
                        // 保存失败
                        alert(response.message);
                    }
                }
            )
        }

    };

    $scope.findSendAll=function(){
        orderService.findSendAll().success(
            function(response){
                $scope.list=response;
            }
        );
    };

    $scope.sellNum=function(){
        orderService.sellNum().success(
            function(response){
                $scope.numList=response.goodsNum;
                $scope.nameList=response.goodsName;
                $scope.$apply();
            }
        );
    };

    //分页
    $scope.findPage=function(page,rows){
        orderService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    };


    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        orderService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    };

    // $scope.entity={order:{},orderDesc:{},itemList:[]}


    $scope.status = ["未付款","已付款","未发货","已发货"];

    $scope.sourceType=["app端","pc端","M端","微信端","手Q端"];
    $scope.updateStatus = function(status){
        orderService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.flag){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    };
});
