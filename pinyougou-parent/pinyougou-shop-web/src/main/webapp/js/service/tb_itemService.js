//服务层
app.service('tb_itemService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../tb_item/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../tb_item/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../tb_item/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../tb_item/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../tb_item/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../tb_item/delete.do?ids='+ids);
	}
	    	
});
