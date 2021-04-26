 //控制层 
app.controller('tb_item_catController' ,function($scope,$controller   ,tb_item_catService,tb_type_templateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		tb_item_catService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		tb_item_catService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		tb_item_catService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=tb_item_catService.update( $scope.entity );
		}else{
			serviceObject=tb_item_catService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	// $scope.reloadList();//重新加载
					$scope.findByParentId($scope.entity.parentId);
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){
		if ($scope.selectIds.length===0){
			alert("请选择要删除的编号！")
			return ;
		}
		let b = confirm("确定删除吗？");
		if (b){
			//获取选中的复选框
			tb_item_catService.dele( $scope.selectIds ).success(
				function(response){
					if(response.success){
						$scope.findByParentId($scope.entity.parentId);//刷新列表
					}else{
						alert(response.message)
						$scope.deselect(".item-cat-list td:nth-child(2)")
					}
					$scope.selectIds=[];
				}
			);
			return
		}
		$scope.deselect(".item-cat-list td:nth-child(2)")
		$scope.selectIds=[];
	}
	$scope.breadcrumb=[{id:0,name:"顶级分类"}]
	$scope.entity={parentId:0};
	$scope.findByParentId=function (id ) {
		$scope.entity={parentId:id};
		tb_item_catService.findByParentId(id).success(function (res) {
			$scope.list=res;
		})
	}
	$scope.search=function (id,name){
		$scope.breadcrumb.push({id:id,name:name})
		$scope.findByParentId(id);
	}
	$scope.showList=function (index,id){
		$scope.breadcrumb.splice(index+1,$scope.breadcrumb.length-index-1);
		$scope.findByParentId(id)
	}
	$scope.typeTemplateMap=[];
	$scope.findTypeTemplateList=function () {
		tb_type_templateService.findAll().success(function (res) {
			$scope.typeTemplateList=res
			$scope.typeTemplateMap=[];
			$scope.typeTemplateList.forEach(s=>{
				$scope.typeTemplateMap[s.id]=s.name;
			})
		})
	}
});	
