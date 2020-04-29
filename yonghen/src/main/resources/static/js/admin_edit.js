(function() {
	var id=getUrlParam("id");
	var type =getUrlParam("type");
	var initdata={};
	if(id){
		$("#adheader").html("修改用户信息");
	}else{
		$("#adheader").html("新增用户信息");
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
    		url: "/sys/admin/getSysAdminDetail",
    		data:{
    			id:id
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
    	if($("#username").val()==null||$("#username").val()==""){
    		layer.msg("请填写登录名", {icon: 2});
    		return;
    	}
    	var formData=new FormData($("#advertisementForm")[0]);
    	
    	$.ajax({ 
    		url: "/sys/admin/saveSysAdmin",
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
    					location.href="index?url=admin&urlName=用户管理";
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=admin&urlName=用户管理";
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function initLoadData(initdata){
    	$("#username").val(initdata.username);
    	$("#mobilePhone").val(initdata.mobilePhone);
    	$("#realName").val(initdata.realName);
		$("#id").val(initdata.id);
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}