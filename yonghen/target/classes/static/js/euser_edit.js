(function() {
	var user_id=getUrlParam("user_id");
	var type =getUrlParam("type");
	var initdata={};
	if(user_id){
		$("#adheader").html("修改被评价人信息");
	}else{
		$("#adheader").html("新增被评价人信息");
	}
	$('#adImg').on('mouseenter','.Img_outer',function () {
		$(this).children('.Img_delete').show();
	});
	$('#adImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});
	getUserDetail();
	
	function getUserDetail(){
		
    	$.ajax({ 
    		url: "/e/user/getUserDetail",
    		data:{
    			user_id:user_id
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
    	if($("#userName").val()==null||$("#userName").val()==""){
    		layer.msg("请填写名称", {icon: 2});
    		return;
    	}
    	var formData=new FormData($("#advertisementForm")[0]);
    	
    	$.ajax({ 
    		url: "/e/user/saveUser",
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
    					location.href="index?url=euser&urlName=被评价人管理";
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=euser&urlName=被评价人管理";
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function initLoadData(initdata){
    	$("#userId").val(initdata.userId);
    	$("#userName").val(initdata.userName);
    	$("#userPost").val(initdata.userPost);
		$("#userPhone").val(initdata.userPhone);
		$("#userEntryTime").val(initdata.userEntryTime);
		$("#userRemark").val(initdata.userRemark);
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}