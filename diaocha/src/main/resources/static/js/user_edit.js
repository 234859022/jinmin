(function() {
	var user_id=getUrlParam("user_id");
	var type =getUrlParam("type");
	var initdata={};
	if(user_id){
		$("#header").html("修改用户信息");
	}else{
		$("#header").html("新增用户信息");
	}
	getUserDetail();
	
	function getUserDetail(){
    	$.ajax({ 
    		url: "/yhsj/user/getUserDetail",
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
    		layer.msg("请填写用户名", {icon: 2});
    		return;
    	}
    	var formData=new FormData($("#productTypeForm")[0]);
    	
    	$.ajax({ 
    		url: "/yhsj/user/saveUser",
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
    					location.href="index?url=user&urlName=用户管理";
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=user&urlName=用户管理";
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function convertBase64UrlToBlob(urlData,contentype){
        var bytes=window.atob(urlData);        //去掉url的头，并转换为byte
        
        //处理异常,将ascii码小于0的转换为大于0
        var ab = new ArrayBuffer(bytes.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < bytes.length; i++) {
            ia[i] = bytes.charCodeAt(i);
        }
        return new Blob( [ab] , {type : contentype});
    }
    
    function initLoadData(initdata){
    	$("#userId").val(initdata.userId);
    	$("#userName").val(initdata.userName);
		$("#userPhone").val(initdata.userPhone);
		$("#userRealName").val(initdata.userRealName);
		$("#userPassword").val(initdata.userPassword);
		$("#userType").val(initdata.userType);
		$("#userContent").val(initdata.userContent);
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}