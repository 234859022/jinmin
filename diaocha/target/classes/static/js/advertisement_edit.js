(function() {
	var advertisement_id=getUrlParam("advertisement_id");
	var type =getUrlParam("type");
	var initdata={};
	if(advertisement_id){
		$("#adheader").html("修改广告信息");
	}else{
		$("#adheader").html("新增广告信息");
	}
	$('#adImg').on('mouseenter','.Img_outer',function () {
		$(this).children('.Img_delete').show();
	});
	$('#adImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});
	getAdvertisementDetail();
	
	function getAdvertisementDetail(){
    	$.ajax({ 
    		url: "/yhsj/advertisement/getAdvertisementDetail",
    		data:{
    			advertisement_id:advertisement_id
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
	
	$('#fileActivityImg').on('change',function () {
        let files = $(this).prop('files');
        if(files.length!=0){
            let reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onload = function(evt){
            	$('#adImg').html("");
            	var html="";
            	html+='<div class="Img_outer">';
				html+='<img src="'+evt.target.result+'" alt="" class="img_above">';
				html+='<span class="Img_delete" onclick="deleteImg(this)">×</span>';
				html+='</div>';
                $('#adImg').append(html);
                $('#fileActivityImg').val('');
            }
        }
    });
    
    $("#submitbtn").click(function(){    	
    	if($("#advertisementName").val()==null||$("#advertisementName").val()==""){
    		layer.msg("请填写名称", {icon: 2});
    		return;
    	}
    	var formData=new FormData($("#advertisementForm")[0]);
    	var sortArray=[];
    	if($("#adImg").find("img").length>0){		
    		$("#adImg").find("img").each(function(index,item){
    			if($(item).attr("src").indexOf(",")>=0){
    				sortArray.push("adImgFiles");
    				formData.append("adImgFiles",convertBase64UrlToBlob($(item).attr("src").substring($(item).attr("src").indexOf(",")+1)));			
    			}
    		});
    	}else{
    		formData.append("advertisementImageUrl","");
    	}
    	
    	formData.append("control",sortArray.join(","));
    	
    	$.ajax({ 
    		url: "/yhsj/advertisement/saveAdvertisement",
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
    					location.href="index?url=advertisement&urlName=广告管理";
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=advertisement&urlName=广告管理";
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
    	$("#advertisementId").val(initdata.advertisementId);
    	$("#advertisementName").val(initdata.advertisementName);
    	$("#advertisementStatus").val(initdata.advertisementStatus);
		$("#advertisementSort").val(initdata.advertisementSort);
		$("#advertisementDesc").val(initdata.advertisementDesc);
		if(initdata.advertisementImageUrl){
			var html="";
        	html+='<div class="Img_outer">';
			html+='<img src="'+initdata.advertisementImageUrl+'" alt="" class="img_above">';
			html+='<span class="Img_delete" onclick="deleteImg(this)">×</span>';
			html+='</div>';
			$("#adImg").html(html);	
		}
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}