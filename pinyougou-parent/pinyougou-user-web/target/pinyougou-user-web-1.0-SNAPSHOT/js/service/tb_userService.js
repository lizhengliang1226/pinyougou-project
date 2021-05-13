//服务层
app.service('tb_userService',function($http){

	//增加 
	this.add=function(entity,code){
		return  $http.post('../tb_user/add?code='+code,entity );
	}
	this.sendCode=function (phone){
		return $http.get("../tb_user/sendCode?phone="+phone)
	}
	this.loginName=function () {
		return $http.get("../login/name");
	}
});
