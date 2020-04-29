(function() {
	var question_id=getUrlParam("question_id");
	var type =getUrlParam("type");
	var initdata={};
	if(question_id){
		$("#adheader").html("修改问题");
	}else{
		$("#adheader").html("新增问题");
	}
	$('#adImg').on('mouseenter','.Img_outer',function () {
		$(this).children('.Img_delete').show();
	});
	$('#adImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});
	getEType();
	 
    function getEType(){
    	$.ajax({ 
    		url: "/e/type/getType",
    		data:{},
    		async:false,
    		dataType:'json',
    		success: function(result){
				var html = '';
    			if(result.code==200){
    				$.each(result.data,function(index,item){
    	    				html += '<option value="'+item.typeId+'">'+item.typeName+'</option>';
    	    		});
    			}
    			$("#questionTypeid").append(html);
    		},
    		complete: function(result){
    			getDetail();
    		}
    	});
    }
	function getDetail(){
    	$.ajax({ 
    		url: "/e/question/getQuestionDetail",
    		data:{
    			question_id:question_id
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
    	if($("#questionTitle").val()==null||$("#questionTitle").val()==""){
    		layer.msg("请填写问题", {icon: 2});
    		return;
    	}
    	var formData=new FormData($("#advertisementForm")[0]);
    	
    	$.ajax({ 
    		url: "/e/question/saveQuestion",
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
    					location.href="index?url=equestion&urlName=问题管理";
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=equestion&urlName=问题管理";
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function initLoadData(initdata){
    	$("#questionId").val(initdata.questionId);
    	$("#questionTitle").val(initdata.questionTitle);
    	$("#questionTypename").val(initdata.questionTypename);
    	$("#questionTypeid").val(initdata.questionTypeid);
		$("#questionSort").val(initdata.questionSort);
		$("#questionWeighted").val(initdata.questionWeighted);
		$("#questionTypes").val(initdata.questionTypes);
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}