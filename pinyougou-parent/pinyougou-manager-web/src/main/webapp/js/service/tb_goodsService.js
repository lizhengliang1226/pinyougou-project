//服务层
app.service('tb_goodsService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../tb_goods/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows,searchEntity){
		return $http.post('../tb_goods/findPage.do?page='+page+'&rows='+rows,searchEntity);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../tb_goods/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../tb_goods/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../tb_goods/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../tb_goods/delete.do?ids='+ids);
	}
	this.search=function (page,rows,entity){
		return $http.post("../tb_goods/search?page="+page+"&rows="+rows,entity);
	}
	this.updateStatus=function (ids,status) {
		return $http.get("../tb_goods/updateStatus?ids="+ids+"&status="+status)
	}
});
