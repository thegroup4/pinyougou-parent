//控制层
app.controller('userController', function ($scope, $controller, $http, $interval, userService) {
    $scope.entity = {};
    //注册用户
    $scope.reg = function () {

        //比较两次输入的密码是否一致
        if ($scope.password != $scope.entity.password) {
            alert("两次输入密码不一致，请重新输入");
            $scope.entity.password = "";
            $scope.password = "";
            return;
        }
        //新增
        userService.add($scope.entity, $scope.smscode).success(
            function (response) {
                alert(response.message);
            }
        );
    }

    //发送验证码
    $scope.sendCode = function () {
        if ($scope.entity.phone == null || $scope.entity.phone == "") {
            alert("请填写手机号码");
            return;
        }//
        //商家后台
        //运营商后台 能前端就前端判断 后端不判断  员工用
        //网站前台  消费者 前端 美  后端 安全

        userService.sendCode($scope.entity.phone).success(
            function (response) {
                alert(response.message);
            }
        );
    }

    $scope.findSeckillOrderByUserId = function () {

        userService.findSeckillOrderByUserId().success(
            function (response) {
                $scope.seckillOrderList = response;
            }
        );
    }





    $scope.seckillOrderintervalStr = seckillOrderintervalStr;

    $scope.findSeckillOrderByUserId = function () {
        $http.get('http://localhost:9105/seckillOrder/findSeckillOrderByUserId.do', {'withCredentials': true}).success(function (response) {
                $scope.seckillOrderList = response;

                for (var i = 0; i < $scope.seckillOrderList.length; i++) {
                    var a1 = $scope.seckillOrderList[i].createTime

                    seckillOrderinterval.push(totalSeconds);
                }
            }
        );


    }

    var seckillOrderinterval = new Array();
    var seckillOrderintervalStr = new Array();
    var totalSeconds = null;

    $scope.findOrderById=function(id){
        userService.findOrderById(id).success(
            function (response) {
                $scope.date= response;
                // alert(1);
                // var a = new Date( $scope.date).getTime();
                // alert(a);
                // //alert(date);
                // totalSeconds = Math.floor((new Date().getTime()-new Date($scope.date).getTime())/1000);
                // alert(totalSeconds)
            }
        );
    }

    // var totalSeconds =
    // // .getTime是获取到的毫秒值，需要通过计算来进行真实时间的显示，但是由于在计算的时候会产生小数


    $interval(function () {

        $scope.timeStr = "";
        var days = Math.floor(totalSeconds / 60 / 60 / 24);
        //13.5233---13天
        var hours = Math.floor((totalSeconds - days * 24 * 60 * 60) / 60 / 60);
        //12.234545-->12
        var minuts = Math.floor((totalSeconds - days * 24 * 60 * 60 - hours * 60 * 60) / 60);
        //20.1322-->20
        var seconds = totalSeconds - days * 24 * 60 * 60 - hours * 60 * 60 - minuts * 60;
        if (days < 10) {
            days = "0" + days;
        }
        if (hours < 10) {
            hours = "0" + hours;
        }
        if (minuts < 10) {
            minuts = "0" + minuts;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        if (days == 0) {
            $scope.timeStr+=hours+":"+minuts+":"+seconds;
        } else {
            $scope.timeStr += days + "天 " + hours + ":" + minuts + ":" + seconds;
        }
        totalSeconds--;
    }, 1000, totalSeconds);


});
