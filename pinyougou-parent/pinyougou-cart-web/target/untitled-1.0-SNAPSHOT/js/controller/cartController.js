//控制层
app.controller('cartController', function ($scope, cartService) {


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
        $scope.cartList = cartService.getCartList()
        // $scope.cartList=cartList
    }

});
