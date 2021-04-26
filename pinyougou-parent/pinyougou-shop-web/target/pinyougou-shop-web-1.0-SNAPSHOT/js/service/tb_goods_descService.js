//服务层
app.service('tb_goods_descService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../tb_goods_desc/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../tb_goods_desc/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../tb_goods_desc/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../tb_goods_desc/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../tb_goods_desc/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../tb_goods_desc/delete.do?ids='+ids);
	}
	    	
});
