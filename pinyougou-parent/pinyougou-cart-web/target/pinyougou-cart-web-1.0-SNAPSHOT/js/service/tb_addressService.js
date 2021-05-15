//服务层
app.service('tb_addressService',function($http){
	    	

	//增加 
	this.add=function(entity){
		return  $http.post('/tb_address/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('/tb_address/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('/tb_address/delete.do?ids='+ids);
	}
	this.findAddressList=function (){
		return $http.get("/tb_address/findListByLoginUser");
	}
});
