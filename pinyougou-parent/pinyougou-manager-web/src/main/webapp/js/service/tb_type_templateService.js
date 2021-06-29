//服务层
app.service('tb_type_templateService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../tb_type_template/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows,searchEntity){
		return $http.post('../tb_type_template/findPage.do?page='+page+'&rows='+rows,searchEntity);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../tb_type_template/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../tb_type_template/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../tb_type_template/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../tb_type_template/delete.do?ids='+ids);
	}


});
