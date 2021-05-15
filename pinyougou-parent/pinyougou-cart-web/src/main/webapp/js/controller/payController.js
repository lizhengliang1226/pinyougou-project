//控制层
app.controller('payController', function ($scope, payService,$location) {
    $scope.createNative=function () {
        payService.createNative().success(function (res) {
            $scope.out_trade_no=res.out_trade_no//订单号
            $scope.total_fee=(res.total_fee/100).toFixed(2);//金额
            $scope.queryPayStatus($scope.out_trade_no);
            let qr=new QRious({
                element:document.getElementById("qrious"),
                size:250,
                value:res.code_url,
                level:"H"
            })
        })
    }
    $scope.queryPayStatus=function (out_trade_no) {
        payService.queryPayStatus(out_trade_no).success(function (res) {
            if (res.success){
                    location.href="paysuccess.html#?total_fee="+$scope.total_fee
            }else{
                location.href="payfail.html"
            }
        })
    }
    $scope.getTotalFee=function () {
        $scope.total_fee = $location.search()["total_fee"];
    }

});
