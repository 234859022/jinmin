(function() {
	var answer_id=getUrlParam("answer_id");
	var type =getUrlParam("type");
	var initdata={};
	var qData;
	if(answer_id){
		$("#adheader").html("修改考核");
	}else{
		$("#adheader").html("新增考核");
	}
	$('#adImg').on('mouseenter','.Img_outer',function () {
		$(this).children('.Img_delete').show();
	});
	$('#adImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});
	getQuestion();//获取问题
	getUsers();//获取被评论人
	getDetail();
	
	function getUsers(){
    	$.ajax({ 
    		url: "/e/user/getUser",
    		data:{},
    		async:false,
    		dataType:'json',
    		success: function(result){
				var html = '';
    			if(result.code==200){
    				$.each(result.data,function(index,item){
    	    				html += '<option value="'+item.userId+'">'+item.userName+'</option>';
    	    		});
    			}
    			$("#answerUserid").append(html);
    		},
    		complete: function(result){
    			getDetail();
    		}
    	});
    }
	
	function getQuestion(){
    	$.ajax({ 
    		url: "/e/answer/detail/getAnswerDetails",
    		data:{
    			answerid:answer_id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			var html = '';
    			if(result.code==200){
    				qData = result.data;
    				$.each(result.data,function(index,item){
    					var ss = '';
    					if(item.detailScore==0){
    						ss = '不清楚';
    					}else if(item.detailScore==1){
    						ss = '完全不符合';
    					}else if(item.detailScore==2){
    						ss = '有些不符合';
    					}else if(item.detailScore==3){
    						ss = '基本符合';
    					}else if(item.detailScore==4){
    						ss = '比较优秀';
    					}else if(item.detailScore==5){
    						ss = '非常优秀';
    					}
    					var html ='<tr>'+
    				         '<td>'+item.detailQuestiontype+'</td>'+
    				         '<td>'+item.detailQuestionweightd+'%</td>'+
    				           '<td>'+item.detailQuestiontitle+'</td>'+
    				           '<td>'+ss+'</td>'+
    				            '<td>'+item.detailScore+'</td>'+
    				      '</tr>';
    					 $("#t_question").append(html);
    	    		});
    			}
    			
    		},
    	});
    }
	
	function getDetail(){
    	$.ajax({ 
    		url: "/e/answer/getAnswerDetail",
    		data:{
    			answer_id:answer_id
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
    	var username = $("#answerUserid").find("option:selected").text();
    	$("#answerTitle").val("对"+username+"的考核");
    	$("#answerUsername").val(username);
    	
    	var status = true;
    	if($("#answerDate").val()==null||$("#answerDate").val()==''){
    		layer.msg("月份不能为空", {icon: 2});
			status =false;
			return;
    	}
    	
    	var formData=new FormData($("#advertisementForm")[0]);
    	var s = 0;
    	$.each(qData,function(index,item){
			if($('#score'+item.questionId).val()==null||$('#score'+item.questionId).val()==''){
				layer.msg("有内容未评分，请继续评分", {icon: 2});
				status =false;
				return;
			};
			s += parseInt($('#score'+item.questionId).val());
		});
		if(status){
			$.ajax({ 
	    		url: "/e/answer/saveAnswer",
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
	    					location.href="index?url=answer&urlName=考核管理";
	    				});
	    			}else{
	    				layer.msg(result.message, {icon: 2,time:1000});
	    			}
	    		}
	    	});
		}
    	
    	
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=answer&urlName=考核查询";
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function initLoadData(initdata){
    	$("#answerTitle").val(initdata.answerTitle);
    	if(initdata.answerType==1){
    		$("#answerType1").val("上级评价");
    	}else if(initdata.answerType==2){
    		$("#answerType1").val("平级评价");
    	}else if(initdata.answerType==3){
    		$("#answerType1").val("下级评价");
    	}
    	$("#answerId").val(initdata.answerId);
    	$("#answerUsername").val(initdata.answerUsername);
    	$("#answerTotalScore").val(initdata.answerTotalScore);
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}