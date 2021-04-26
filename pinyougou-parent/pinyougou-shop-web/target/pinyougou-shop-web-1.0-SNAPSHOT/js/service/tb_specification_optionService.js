//服务层
app.service('tb_specification_optionService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../tb_specification_option/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../tb_specification_option/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../tb_specification_option/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../tb_specification_option/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../tb_specification_option/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../tb_specification_option/delete.do?ids='+ids);
	}
	    	
});
