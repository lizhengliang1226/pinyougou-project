//服务层
app.service('seckillGoodsService', function ($http) {
    this.findList=function () {
        return $http.get("tb_seckill_goods/findList")
    }
});