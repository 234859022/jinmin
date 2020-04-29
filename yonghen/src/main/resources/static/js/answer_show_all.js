(function() {
	var aid=getUrlParam("a_id");
	var etype=getUrlParam("etype");
	var type =getUrlParam("type");
	var user_id = getUrlParam("user_id");
	var initdata={};
	var qData;
	$("#adheader").html("查看考核");
	$('#adImg').on('mouseenter','.Img_outer',function () {
		$(this).children('.Img_delete').show();
	});
	$('#adImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});
	getQuestion();//获取问题
	getDetail();
	
	function getQuestion(){
    	$.ajax({ 
    		url: "/e/answer/detail/getAnswerDetailsAllByAid",
    		data:{
    			aid:aid
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			var html = '';
    			if(result.code==200){
    				qData = result.data;
    				if(qData.a1!=null){
    					$.each(qData.a1,function(index,item){
    						var score = "0";
    						if(item.score==null||item.score=='null'||item.score==''){
    							
    						}else{
    							score = item.score;
    						}
        					var html ='<tr>'+
        				         '<td>'+item.QUESTION_TYPENAME+'</td>'+
        				         '<td>'+item.QUESTION_WEIGHTED+'%</td>'+
        				           '<td>'+item.QUESTION_TITLE+'</td>'+
        				           '<td>'+score+'</td>'+
        				      '</tr>';
        					 $("#t_1").append(html);
        	    		});
    				}
    				
    				if(qData.a2!=null){
    					$.each(qData.a2,function(index,item){
    						var score = "0";
    						if(item.score==null||item.score=='null'||item.score==''){
    							
    						}else{
    							score = item.score;
    						}
        					var html ='<tr>'+
        				         '<td>'+item.QUESTION_TYPENAME+'</td>'+
        				         '<td>'+item.QUESTION_WEIGHTED+'%</td>'+
        				           '<td>'+item.QUESTION_TITLE+'</td>'+
        				           '<td>'+score+'</td>'+
        				      '</tr>';
        					 $("#t_2").append(html);
        	    		});
    				}
    				
    				if(qData.a3!=null){
    					$.each(qData.a3,function(index,item){
    						var score = "0";
    						if(item.score==null||item.score=='null'||item.score==''){
    							
    						}else{
    							score = item.score;
    						}
        					var html ='<tr>'+
        				         '<td>'+item.QUESTION_TYPENAME+'</td>'+
        				         '<td>'+item.QUESTION_WEIGHTED+'%</td>'+
        				           '<td>'+item.QUESTION_TITLE+'</td>'+
        				           '<td>'+score+'</td>'+
        				      '</tr>';
        					 $("#t_3").append(html);
        	    		});
    				}
    				
    			}
    			
    		},
    	});
    }
	
	function getDetail(){
    	$.ajax({ 
    		url: "/e/user/answer/getUserAnswerDetail",
    		data:{
    			a_id:aid
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
    	location.href="index?url=euseranswer&urlName=设置考核管理&type=2&user_id="+user_id;
    });
    
    $("#resetbtn").click(function(){
    	initLoadData(initdata);
    });
    
    function initLoadData(initdata){
    	$("#aId").val(initdata.aId);
    	$("#aUsername").val(initdata.aUsername);
    	$("#aUserid").val(initdata.aUserid);
		$("#aYiAdmin").val(initdata.aYiAdmin);
		$("#aYiAdminid").val(initdata.aYiAdminid);
		$("#aYiStatus").val(initdata.aYiStatus);
		$("#aErAdmin").val(initdata.aErAdmin);
		$("#aErAdminid").val(initdata.aErAdminid);
		$("#aErStatus").val(initdata.aErStatus);
		$("#aSanAdmin").val(initdata.aSanAdmin);
		$("#aSanAdminid").val(initdata.aSanAdminid);
		$("#aSanSatatus").val(initdata.aSanSatatus);
		$("#aStatus").val(initdata.aStatus);
		$("#aScore").val(initdata.aScore);
		$("#aYiScore").val(initdata.aYiScore);
		$("#aErScore").val(initdata.aErScore);
		$("#aSanScore").val(initdata.aSanScore);
		$("#aTitle").val(initdata.aTitle);
		var ayi = '';
		if(initdata.aYiScores!=null&&initdata.aYiScores!=''){
			if(initdata.aYiScores.indexOf(';')>-1){
				var ascores = initdata.aYiScores.split(';');
				for(let index in ascores) {  
					var s = ascores[index];
					if(s.length>35){
						if(ayi ==''){
							ayi = s.substring(35).replace(',',':');
						}else{
							ayi = ayi + ";"+ s.substring(35).replace(',',':');
						}
					}
			    };  
			}else{
				var s = initdata.aYiScores
				if(s.length>35){
					ayi = s.substring(35).replace(',',':');
				}
			}
		}
		$("#t1").html(ayi);
		
		var aer = '';
		if(initdata.aErScores!=null&&initdata.aErScores!=''){
			if(initdata.aErScores.indexOf(';')>-1){
				var ascores = initdata.aErScores.split(';');
				for(let index in ascores) {  
					var s = ascores[index];
					if(s.length>35){
						if(aer ==''){
							aer = s.substring(35).replace(',',':');
						}else{
							aer = aer + ";"+ s.substring(35).replace(',',':');
						}
					}
			    };  
			}else{
				var s = initdata.aErScores
				if(s.length>35){
					aer = s.substring(35).replace(',',':');
				}
			}
		}
		$("#t2").html(aer);
		
		var asan = '';
		if(initdata.aSanScores!=null&&initdata.aSanScores!=''){
			if(initdata.aSanScores.indexOf(';')>-1){
				var ascores = initdata.aSanScores.split(';');
				for(let index in ascores) {  
					var s = ascores[index];
					if(s.length>35){
						if(asan ==''){
							asan = s.substring(35).replace(',',':');
						}else{
							asan = asan + ";"+ s.substring(35).replace(',',':');
						}
					}
			    };  
			}else{
				var s = initdata.aSanScores
				if(s.length>35){
					asan = s.substring(35).replace(',',':');
				}
			}
		}
		$("#t3").html(asan);
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}