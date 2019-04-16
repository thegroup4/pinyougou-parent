//首页控制器
app.controller('testController',function($scope,$interval){

	$scope.order = Storage.get("order");//order为后台传来的订单信息，里面包含订单创建时间
    var createTime;//订单创建时间
    var curTime;//当前时间
    var totalSecond;//设置计时总时间（分钟）
    if($scope.order.createtime !=null){
        //为了支持safari浏览器
        createTime=new Date($scope.order.createtime.replace(/\-/g, "/")).getTime();
        curTime=new Date().getTime();
        totalSecond=Math.round((createTime+15*60*1000-curTime)/1000);
    }else{
        totalSecond = 15 * 60;
    }

    /**
     * 支付倒计时
     */
    timePromise = $interval(function(){
        if (totalSecond >= 0) {
            var t1 = Math.floor(totalSecond / 60);
            var m = t1 < 10 ? "0" + t1 : t1;
            var t2 = totalSecond - t1 * 60;
            var s = t2 < 10 ? "0" + t2 : t2;
            totalSecond = totalSecond - 1;
            $scope.payClass=true;//添加class
            $scope.payCountDown="支付剩余时间："+m+"分钟"+s+"秒"
        } else {
            $scope.confirmPay=true;
            $scope.payClass=true;//添加class
            $scope.payCountDown= "支付超时，请重新下单！";
            $interval.cancel(timePromise);//终止倒计时
        }
    },1000)
});