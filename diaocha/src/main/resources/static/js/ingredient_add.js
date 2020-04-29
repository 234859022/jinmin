

(function() {
	var id=getUrlParam("id");
	var initdata={};
    
    if(id){
    	loadData();
    }else{
    	initdata.ingredientId=$("#ingredientId").val();
    	initdata.ingredientName=$("#ingredientName").val();
    	initdata.ingredientRemark=$("#ingredientRemark").val();
    	initdata.machineIsDelete=$("#machineIsDelete").val();
    }
    
    $("#submitbtn").click(function(){
    	var request_url="/yhsj/ingredient/add";
    	if(id){
    		request_url="/yhsj/ingredient/update";
    	}
    	var ingredientName = $("#ingredientName").val();
    	var ingredientRemark = $("#ingredientRemark").val();
    	var ingredientIsDelete = $("#ingredientIsDelete").val();
    	if(ingredientName==null||ingredientName==''){
			layer.msg("配料名称为必填内容", {icon: 2,time:1000});
			return;
    	}
    	
    	if(ingredientIsDelete==null||ingredientIsDelete==''){
			layer.msg("删除状态必选", {icon: 2,time:1000});
			return;
    	}
    	$.ajax({ 
    		url: request_url,
    		data:$('#ingredientinfo').serialize(),
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				parent.$("#grid-table-ingredient").jqGrid('setGridParam',{
    		               datatype:'json',
    		                postData:{}, //发送数据
    		                page:1
    		            }).trigger("reloadGrid");
    				parent.layer.closeAll();
    			}else{
    				layer.alert(result.message);
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	parent.layer.closeAll();
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function loadData(){
    	$.ajax({ 
    		url: "/yhsj/ingredient/detail",
    		data:{
    			id:id
    		},
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				if(result.data){
    					initdata=result.data;
    					initLoadData(initdata);
    				}
    			}
    		}
    	});
    }
    
    function initLoadData(initdata){
    	$("#ingredientId").val(initdata.ingredientId);
		$("#ingredientName").val(initdata.ingredientName);
		$("#ingredientRemark").val(initdata.ingredientRemark);
		$("#ingredientIsDelete").val(initdata.ingredientIsDelete);
    }

})();

