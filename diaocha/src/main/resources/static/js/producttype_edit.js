(function() {
	var type_id=getUrlParam("type_id");
	var type =getUrlParam("type");
	var initdata={};
	if(type_id){
		$("#header").html("修改产品分类信息");
	}else{
		$("#header").html("新增产品分类信息");
	}
	$('#typeImg').on('mouseenter','.Img_outer',function () {
		$(this).children('.Img_delete').show();
	});
	$('#typeImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});
	getProductDetail();
	
	function getProductDetail(){
    	$.ajax({ 
    		url: "/yhsj/proceduct/type/getProductTypeDetail",
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
	
	$('#fileActivityImg').on('change',function () {
        let files = $(this).prop('files');
        if(files.length!=0){
            let reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onload = function(evt){
            	$('#typeImg').html("");
            	var html="";
            	html+='<div class="Img_outer">';
				html+='<img src="'+evt.target.result+'" alt="" class="img_above">';
				html+='<span class="Img_delete" onclick="deleteImg(this)">×</span>';
				html+='</div>';
                $('#typeImg').append(html);
                $('#fileActivityImg').val('');
            }
        }
    });
    
    $("#submitbtn").click(function(){    	
    	if($("#typeName").val()==null||$("#typeName").val()==""){
    		layer.msg("请填写分类名", {icon: 2});
    		return;
    	}
    	var formData=new FormData($("#productTypeForm")[0]);
    	var sortArray=[];
    	if($("#typeImg").find("img").length>0){		
    		$("#typeImg").find("img").each(function(index,item){
    			if($(item).attr("src").indexOf(",")>=0){
    				sortArray.push("typeImgFiles");
    				formData.append("typeImgFiles",convertBase64UrlToBlob($(item).attr("src").substring($(item).attr("src").indexOf(",")+1)));			
    			}
    		});
    	}else{
    		formData.append("typeLogImg","");
    	}
    	
    	formData.append("control",sortArray.join(","));
    	
    	$.ajax({ 
    		url: "/yhsj/proceduct/type/saveProductType",
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
    					location.href="index?url=producttype&urlName=产品分类管理";
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=producttype&urlName=产品分类管理";
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
    	$("#typeId").val(initdata.typeId);
    	$("#typeName").val(initdata.typeName);
		$("#typeShowSort").val(initdata.typeShowSort);
		$("#remark").val(initdata.remark);
		if(initdata.typeLogImg){
			var html="";
        	html+='<div class="Img_outer">';
			html+='<img src="'+initdata.typeLogImg+'" alt="" class="img_above">';
			html+='<span class="Img_delete" onclick="deleteImg(this)">×</span>';
			html+='</div>';
			$("#typeImg").html(html);	
		}
    }
})();

function deleteImg(that){
	$(that).parent().remove();
}