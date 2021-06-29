//服务层
app.service('homeService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findOrderNum=function(){
		return $http.get('../tb_order/orderNum');
	}

	    	
});
