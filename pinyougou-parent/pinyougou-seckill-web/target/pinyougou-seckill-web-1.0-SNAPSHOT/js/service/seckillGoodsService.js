//服务层
app.service('seckillGoodsService', function ($http) {
    this.findList=function () {
        return $http.get("tb_seckill_goods/findList")
    }
    this.findOneFromRedis=function (id) {
        return $http.get("tb_seckill_goods/findOneFromRedis?id="+id)
    }
    this.submitOrder=function (id) {
        return $http.get("tb_seckill_order/submitOrder?seckillId="+id)
    }
});