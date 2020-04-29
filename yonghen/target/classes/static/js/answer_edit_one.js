(function() {
	var answer_id=getUrlParam("answer_id");
	var aid = getUrlParam("aid");
	var etype = getUrlParam("etype");
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
	getUserAnswerDetail();//获取
	//getDetail();
	
	
	$("#answerType").val(etype);
	if(etype==1){
		$("#answerType1").val("上级评论");
	}else if(etype==2){
		$("#answerType1").val("平级评论");
	}else if(etype==3){
		$("#answerType1").val("下级评论");
	}
	
	function getUserAnswerDetail(){
    	$.ajax({ 
    		url: "/e/user/answer/getUserAnswerDetail",
    		data:{a_id:aid},
    		async:false,
    		dataType:'json',
    		success: function(result){
				var html = '';
    			if(result.code==200){
    				var initdata = result.data;
    				$("#answerTitle").val(initdata.aTitle);
    				$("#answerUserid").val(initdata.aUserid);
    				$("#answerUsername").val(initdata.aUsername);
    				$("#answerDate").val(initdata.aTitle);
    				$("#useranswerid").val(initdata.aId);
    				
//    				$("#answerTitle").val(initdata.answerTitle);
//    				$("#answerTitle").val(initdata.answerTitle);
//    				$("#answerTitle").val(initdata.answerTitle);
    			}else{
    				layer.msg("出错，请刷新重试！", {icon: 2});
    			}
    		},
    		complete: function(result){
    			getDetail();
    		}
    	});
    }
	
	var scores = 0;
	function getQuestion(){
    	$.ajax({ 
    		url: "/e/question/getQuestions",
    		data:{
    			questionTypes:etype
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			var html = '';
    			if(result.code==200){
    				qData = result.data;
    				$.each(result.data,function(index,item){
    					var html ='<tr>'+
    				         '<td>'+item.questionTypename+'</td>'+
    				         '<td><input type="hidden" id="weighted'+item.questionId+'" name="weighted'+item.questionId+'" value="'+item.questionWeighted+'" >'+item.questionWeighted+'%</td>'+
    				           '<td>'+item.questionTitle+'</td>'+
    				           '<td>'+'5分<input type="radio" name="optionsRadios'+item.questionId+'" value="5" >完全符合；'+
    				             '4分<input type="radio" name="optionsRadios'+item.questionId+'" value="4" >比较符合；'+
    				             '3分<input type="radio" name="optionsRadios'+item.questionId+'" value="3" >符合；'+
    				             '2分<input type="radio" name="optionsRadios'+item.questionId+'" value="2" >有些不符合；'+
    				             '1分<input type="radio" name="optionsRadios'+item.questionId+'" value="1" >完全不符合； '+
    				             '<input type="radio" name="optionsRadios'+item.questionId+'" value="0" >不清楚</td>'+
    				            '<td><input type="text" name="score" id="score'+item.questionId+'" size="3" readonly="readonly"></td>'+
    				      '</tr>';
    					 $("#t_question").append(html);
    					 $('input[type=radio][name=optionsRadios'+item.questionId+']').change(function() {
        			         $('#score'+item.questionId).val(this.value);
        			         var s = 0;
        			     	 $.each(qData,function(index,item){
//        			 			$("#aaa").append(parseFloat($('#score'+item.questionId).val()));;
//        			 			$("#aaa").append(">>>>>>"+parseFloat($('#weighted'+item.questionId).val()));
        			 			if($('#score'+item.questionId).val()==null||$('#score'+item.questionId).val()==''){
        			 				
        			 			}else{
        			 				s += parseFloat($('#score'+item.questionId).val())*parseFloat($('#weighted'+item.questionId).val());
        			 				s = parseFloat(s);
        			 			}
        			 		 });
        			     	 s = parseFloat(s/5).toFixed(2);
        			     	 $("#answerTotalScore").val(s);
        			     	 $("#dscore").html('<label class="col-sm-1 control-label no-padding-right" for="answerUserid" style="width:25%;">总分:'+s+'</label>');
        			     });
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
//    	var username = $("#answerUsername").val();
//    	$("#answerTitle").val("对"+username+"的考核");
    	
    	var status = true;
    	if($("#answerDate").val()==null||$("#answerDate").val()==''){
    		layer.msg("月份不能为空", {icon: 2});
			status =false;
			return;
    	}
    	
    	var formData=new FormData($("#advertisementForm")[0]);
    	$.each(qData,function(index,item){
			if($('#score'+item.questionId).val()==null||$('#score'+item.questionId).val()==''){
				layer.msg("有内容未评分，请继续评分", {icon: 2});
				status =false;
				return;
			};
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
							msg = "提交成功";
	    				}else if(type == '2'){
							msg = "修改成功";
	    				}
	    				layer.msg(msg, {icon: 1,time:1000},function(){
	    					location.href="index?url=euseranswer_one&urlName=考核管理";
	    				});
	    			}else{
	    				layer.msg(result.message, {icon: 2,time:1000});
	    			}
	    		}
	    	});
		}
    	
    	
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=euseranswer_one&urlName=考核管理";
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
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}