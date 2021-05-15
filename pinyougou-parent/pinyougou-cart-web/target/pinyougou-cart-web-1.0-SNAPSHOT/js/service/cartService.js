//服务层
app.service('cartService', function ($http) {

    this.getCartList = function () {
        let cartList = localStorage.getItem("cartList")
        return cartList === null || cartList === ''?[]:JSON.parse(cartList);
    }
    this.saveCartList=function (cartList) {
        localStorage.setItem("cartList",JSON.stringify(cartList))
    }
    this.removeCartList=function () {
        localStorage.removeItem("cartList")
    }
    this.addGoodsToCartList=function (itemId,num,cartList) {
        return $http.post("/cart/addGoodsToCartList?itemId="+itemId+"&num="+num,cartList);
    }
    this.sum=function (cartList) {
        let totalValue={"totalNum":0,"totalPrice":0.0}
        for (let i = 0; i < cartList.length; i++) {
            let cart=cartList[i]
            for (let j = 0; j < cart.orderItemList.length; j++) {
                let orderItem=cart.orderItemList[j]
                totalValue.totalNum+=orderItem.num
                totalValue.totalPrice+=orderItem.totalFee
            }
        }
        return totalValue;
    }
    this.findCartList=function (cartList) {
        return $http.post("cart/findCartList",cartList)
    }
    this.submitOrder=function (order) {
        return $http.post("tb_order/add",order)
    }
});
