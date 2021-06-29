//控制层
app.controller('seckillGoodsController', function ($scope, $location, $interval, seckillGoodsService) {
    $scope.findList = function () {
        seckillGoodsService.findList().success(function (res) {
            $scope.list = res
        })
    }
    $scope.findOneFromRedis = function () {
        let id = $location.search()["id"];
        seckillGoodsService.findOneFromRedis(id).success(function (res) {
            $scope.entity = res;
            $scope.allSecond = Math.floor((new Date($scope.entity.endTime).getTime() - new Date().getTime()) / 1000)
            let time = $interval( function () {
                $scope.allSecond--;
               $scope.timeString=convertTimeString($scope.allSecond)
                if ($scope.allSecond === 0) {
                    $interval.cancel(time)
                }
            }, 1000)
        })
    }
    let convertTimeString = function (allSecond) {
        //计算出相差天数
        let days = Math.floor(allSecond / (60*60*24))
        //计算出小时数
        let hours = Math.floor((allSecond-days*60*60*24)/(60*60))
        //计算相差分钟数  
        let minutes = Math.floor((allSecond-days*60*60*24-hours*60*60)/60)
        //计算相差秒数  
        let seconds = allSecond-days*60*60*24-hours*60*60-minutes*60
        let time = ""
        if (days > 0) {
            time += days + "天 "
        }
        time += hours + ':' + minutes + ':' + seconds
        return time
    }
    $scope.submitOrder=function () {
        seckillGoodsService.submitOrder($scope.entity.id).success(function (res){
            if (res.success){
                alert("下单成功，请在一分钟内完成支付！")
                location.href="pay.html"
            }else{
                alert(res.message)
            }
        })
    }
});
