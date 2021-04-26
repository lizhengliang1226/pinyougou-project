 //控制层 
app.controller('tb_contentController' ,function($scope,$controller,tb_content_categoryService  , uploadService,tb_contentService){
	
	$controller('baseController',{$scope:$scope});//继承
	$scope.status=["无效","有效"]
	$scope.uploadImg = function () {
		uploadService.upload().success(function (res) {
			if (res.error === 0) {
				$scope.entity.pic = res.url
			}else {
				alert(res.msg)
			}
		})
	}
	$scope.findContentCategoryIdList=function () {
		tb_content_categoryService.findAll().success(function (res) {
			$scope.contentCategoryIdList=res
		})
	}
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		tb_contentService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		tb_contentService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		tb_contentService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=tb_contentService.update( $scope.entity );
		}else{
			serviceObject=tb_contentService.add( $scope.entity  );//增加 
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
		tb_contentService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
    
});	
