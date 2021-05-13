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
});
