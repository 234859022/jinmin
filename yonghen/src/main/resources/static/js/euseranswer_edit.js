var user_id='';
(function() {
	user_id=getUrlParam("user_id");
	$("#aUserid").val(user_id);
	var a_id=getUrlParam("a_id");
	var type =getUrlParam("type");
	var initdata={};
	if(a_id){
		$("#adheader").html("修改考核信息");
	}else{
		$("#adheader").html("新增考核信息");
	}
	$('#adImg').on('mouseenter','.Img_outer',function () {
		$(this).children('.Img_delete').show();
	});
	$('#adImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});

	getDetail();
	getUserDetail();//获取被考核人
	getAdmin();//获取评论人
	
	function getAdmin(){
    	$.ajax({ 
    		url: "/sys/admin/getAdmin",
    		data:{},
    		async:false,
    		dataType:'json',
    		success: function(result){
				var html = '';
    			if(result.code==200){
    				$.each(result.data,function(index,item){
    	    			html += '<option value="'+item.id+'">'+item.realName+'</option>';
    	    		});
    			}
    			$("#aYiAdminid").append(html);
    			$("#aErAdminid").append(html);
    			$("#aSanAdminid").append(html);
    			$('.multiselect').multiselect({
    			      buttonClass: 'btn',
    			      buttonWidth: 'auto',
    			      buttonText: function(options) {
    			        if (options.length == 0) {
    			          return '请选择考核人';
    			        }
    			        else {
    			          var selected = '';
    			          options.each(function() {
    			            selected += $(this).text() + ',';
    			          });
    			          return selected.substr(0, selected.length -1) ;
    			        }
    			      },
    			    });
    		},
    		complete: function(result){
    			getDetail();
    		}
    	});
    }
	
	
	function getDetail(){
    	$.ajax({ 
    		url: "/e/user/answer/getUserAnswerDetail",
    		data:{
    			a_id:a_id
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
	
	//获取被评价人信息
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
    					$("#A_USERNAME").val(result.data.userName);
    				}
    			}
    		},
    		complete: function(result){
    			showTable();
    		}
    	});
    }
	
	
    $("#submitbtn").click(function(){    	
    	if($("#aTitle").val()==null||$("#aTitle").val()==""){
    		layer.msg("请填写标题", {icon: 2});
    		return;
    	}
    	
    	$("#aYiAdmin").val($("#aYiAdminid").find("option:selected").text());
    	$("#aErAdmin").val($("#aErAdminid").find("option:selected").text());
    	$("#aSanAdmin").val($("#aSanAdminid").find("option:selected").text());
    	var formData=new FormData($("#advertisementForm")[0]);
    	
    	$.ajax({ 
    		url: "/e/user/answer/saveUserAnswer",
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
    					location.href="index?url=euseranswer&urlName=设置考核管理&user_id="+user_id;
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=euseranswer&urlName=设置考核管理&user_id="+user_id;
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function initLoadData(initdata){
    	$("#aId").val(initdata.aId);
    	$("#aUsername").val(initdata.aUsername);
    	$("#aUserid").val(initdata.aUserid);
		$("#aYiAdmin").val(initdata.aYiAdmin);
		$("#a1").html(initdata.aYiAdmin);
		$("#aYiAdminid").val(initdata.aYiAdminid);
		$("#aYiStatus").val(initdata.aYiStatus);
		$("#aErAdmin").val(initdata.aErAdmin);
		$("#a2").html(initdata.aErAdmin);
		$("#aErAdminid").val(initdata.aErAdminid);
		$("#aErStatus").val(initdata.aErStatus);
		$("#aSanAdmin").val(initdata.aSanAdmin);
		$("#a3").html(initdata.aSanAdmin);
		$("#aSanAdminid").val(initdata.aSanAdminid);
		$("#aSanSatatus").val(initdata.aSanSatatus);
		$("#aStatus").val(initdata.aStatus);
		$("#aScore").val(initdata.aScore);
		$("#aYiScore").val(initdata.aYiScore);
		$("#aErScore").val(initdata.aErScore);
		$("#aSanScore").val(initdata.aSanScore);
		$("#aTitle").val(initdata.aTitle);

    }
})();

function deleteImg(that){
	$(that).parent().remove();
}