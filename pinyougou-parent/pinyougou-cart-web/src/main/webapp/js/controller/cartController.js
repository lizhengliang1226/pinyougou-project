//控制层
app.controller('cartController', function ($scope, $location, cartService) {


    $scope.init = function () {
        // let cartList = [
        //     {
        //         "sellerId": "lizhengliang",
        //         "sellerName": "小米旗舰店",
        //         "orderItemList": [{
        //             "title": "小米10",
        //             "price": "3999",
        //             "picPath": "http://192.168.182.134/group1/M00/00/00/wKi2hmCDGSyATN6HAAA_i-KdHss477.PNG",
        //             "num": "10",
        //             "totalFee": "120009"
        //         }, {
        //             "title": "小米10",
        //             "price": "3999",
        //             "picPath": "http://192.168.182.134/group1/M00/00/00/wKi2hmCDGSyATN6HAAA_i-KdHss477.PNG",
        //             "num": "10",
        //             "totalFee": "120009"
        //         }, {
        //             "title": "小米10",
        //             "price": "3999",
        //             "picPath": "http://192.168.182.134/group1/M00/00/00/wKi2hmCDGSyATN6HAAA_i-KdHss477.PNG",
        //             "num": "10",
        //             "totalFee": "120009"
        //         }, {
        //             "title": "小米10",
        //             "price": "3999",
        //             "picPath": "http://192.168.182.134/group1/M00/00/00/wKi2hmCDGSyATN6HAAA_i-KdHss477.PNG",
        //             "num": "10",
        //             "totalFee": "120009"
        //         }]
        //     }
        //     ,
        //     {
        //         "sellerId": "lizhengliang",
        //         "sellerName": "小米旗舰店",
        //         "orderItemList": [{
        //             "title": "小米10",
        //             "price": "3999",
        //             "picPath": "http://192.168.182.134/group1/M00/00/00/wKi2hmCDGSyATN6HAAA_i-KdHss477.PNG",
        //             "num": "10",
        //             "totalFee": "120009"
        //         }]
        //     }]

        // $scope.cartList=cartList
        let itemId = $location.search()["itemId"];
        let num = $location.search()["num"];
        if (itemId !== null && num !== null) {
            $scope.addGoodsToCartList(itemId, num);
        } else {
            // $scope.cartList = cartService.getCartList()
            findCartList();
        }
    }
    //查询购物车
    let findCartList=function () {
        cartService.findCartList(cartService.getCartList()).success(function (res) {
            if (res) {
                $scope.cartList = res.data
                if (res.loginName!==""){
                    cartService.removeCartList()
                }
            }
        })
    }
    $scope.addGoodsToCartList = function (itemId, num) {
        cartService.addGoodsToCartList(itemId, num, cartService.getCartList()).success(function (res) {
            if (res.success) {
                $scope.cartList = res.data
                if (res.loginName === "") {
                    console.log("用户名未空的")
                    cartService.saveCartList($scope.cartList)
                }else{
                    console.log("用户名不为空")
                    findCartList();
                }
            } else {
                alert(res.data)
            }
        })
    }
    $scope.$watch("cartList", function (newValue, oldValue) {
        $scope.totalValue = cartService.sum(newValue)
    })

});
