//服务层
app.service('tb_sellerService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../tb_seller/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows,searchEntity){
		return $http.post('../tb_seller/search?page='+page+'&rows='+rows,searchEntity);
	}

	//查询实体
	this.findOne=function(id){
		return $http.get('../tb_seller/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../tb_seller/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../tb_seller/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../tb_seller/delete.do?ids='+ids);
	}
	this.updateStatus=function (sellerId,status) {
		return $http.get("../tb_seller/updateStatus?sellerId="+sellerId+"&status="+status);
	}
});
