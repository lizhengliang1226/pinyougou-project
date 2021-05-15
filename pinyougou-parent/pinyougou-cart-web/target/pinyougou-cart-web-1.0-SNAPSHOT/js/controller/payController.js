//控制层
app.controller('payController', function ($scope, payService) {
    $scope.createNative=function () {
        payService.createNative().success(function (res) {
            $scope.out_trade_no=res.out_trade_no//订单号
            $scope.total_fee=(res.total_fee/100).toFixed(2);//金额
            let qr=new QRious({
                element:document.getElementById("qrious"),
                size:250,
                value:res.code_url,
                level:"H"
            })
           queryPayStatus($scope.out_trade_no);
        })
    }
    $scope.queryPayStatus=function (out_trade_no) {
        payService.queryPayStatus(out_trade_no).success(function (res) {
            alert("响应胡来了")
            if (res.success){
                    location.href="paysuccess.html"
            }else{
                location.href="payfail.html"
            }
        })
    }

});
