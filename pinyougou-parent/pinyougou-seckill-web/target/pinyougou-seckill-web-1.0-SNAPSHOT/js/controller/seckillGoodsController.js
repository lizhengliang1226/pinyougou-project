//控制层
app.controller('seckillGoodsController', function ($scope, seckillGoodsService) {
$scope.findList=function () {
    seckillGoodsService.findList().success(function (res) {
        $scope.list=res
    })
}
});
