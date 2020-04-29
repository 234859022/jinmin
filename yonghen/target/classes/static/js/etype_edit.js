(function() {
	var type_id=getUrlParam("type_id");
	var type =getUrlParam("type");
	var initdata={};
	if(type_id){
		$("#adheader").html("修改分类信息");
	}else{
		$("#adheader").html("新增分类信息");
	}
	$('#adImg').on('mouseenter','.Img_outer',function () {
		$(this).children('.Img_delete').show();
	});
	$('#adImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});
	getTypeDetail();
	
	function getTypeDetail(){
    	$.ajax({ 
    		url: "/e/type/getTypeDetail",
    		data:{
    			type_id:type_id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				if(result.data){
    					initLoadData(result.data);
    				}
    			}
    		},
    	});
    }
	
	
    $("#submitbtn").click(function(){    	
    	if($("#typeName").val()==null||$("#typeName").val()==""){
    		layer.msg("请填写名称", {icon: 2});
    		return;
    	}
    	var formData=new FormData($("#advertisementForm")[0]);
    	
    	$.ajax({ 
    		url: "/e/type/saveType",
    		data:formData,
    		dataType:'json',
    		type:"POST" ,
    		processData:false,
    		contentType:false,
    		success: function(result){
    			if(result.code==200){
    				var msg = "";
    				if(type == '1'){
						msg = "新增成功";
    				}else if(type == '2'){
						msg = "修改成功";
    				}
    				layer.msg(msg, {icon: 1,time:1000},function(){
    					location.href="index?url=etype&urlName=分类管理";
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=etype&urlName=分类管理";
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function initLoadData(initdata){
    	$("#typeId").val(initdata.typeId);
    	$("#typeName").val(initdata.typeName);
    	$("#typeDetail").val(initdata.typeDetail);
		$("#typeSort").val(initdata.typeSort);
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}