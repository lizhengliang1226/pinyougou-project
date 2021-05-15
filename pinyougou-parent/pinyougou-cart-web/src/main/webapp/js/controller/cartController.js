//控制层
app.controller('cartController', function ($scope, $location,tb_addressService,cartService) {


    $scope.init = function () {
        let itemId = $location.search()["itemId"];
        let num = $location.search()["num"];
        if (itemId !== null && num !== null) {
            $scope.addGoodsToCartList(itemId, num);
        } else {
            // $scope.cartList = cartService.getCartList()
            $scope.findCartList();
        }
    }
    //查询购物车
    $scope.findCartList=function () {
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
                    $scope.findCartList();
                }
            } else {
                alert(res.data)
            }
        })
    }
    $scope.$watch("cartList", function (newValue, oldValue) {
        $scope.totalValue = cartService.sum(newValue)
    })
    $scope.findAddressList=function () {
        tb_addressService.findAddressList().success(function (res) {
            $scope.addressList=res
            $scope.address=$scope.addressList[0];
        })
    }
    $scope.setMobile=function (mobile) {
        return mobile.substring(0,3)+"****"+mobile.substring(7,11)
    }
    $scope.order={paymentType:'1'}
    $scope.selectPaymentType=function (paymentType) {
        $scope.order.paymentType=paymentType
    }
    $scope.selectAddress=function (address) {
        $scope.address=address
    }
    $scope.isSelectedAddress=function (address) {
        return $scope.address===address
    }
    //保存
    $scope.save=function(){
        var serviceObject;//服务层对象
        if($scope.entity.id!=null){//如果有ID
            serviceObject=tb_addressService.update( $scope.entity ); 修改
        }else{
            serviceObject=tb_addressService.add( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //重新查询
                    $scope.reloadList();//重新加载
                }else{
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        tb_addressService.dele( $scope.selectIds ).success(
            function(response){
                if(response.success){
                    $scope.reloadList();//刷新列表
                }
            }
        );
    }
    $scope.submitOrder=function () {
        $scope.order.receiverAreaName=$scope.address.address
        $scope.order.receiverMobile=$scope.address.mobile
        $scope.order.receiver=$scope.address.contact
        // $scope.order.payment=$scope.totalValue.totalPrice
        cartService.submitOrder($scope.order).success(function (res) {
            if (res.success){
                if ($scope.order.paymentType === '1') {
                    location.href="pay.html"
                }else{
                    location.href="paysuccess.html"
                }
            }else{
                alert(res.message)
            }
        })
    }
});
