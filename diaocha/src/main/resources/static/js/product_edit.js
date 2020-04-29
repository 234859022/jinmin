var deleteArray=new Array();
var deleteIds = [];
(function() {
	var lastrow;  //最后修改行号先定义一个全局变量
	var lastcell; //最后修改列号
	var grid_selector = "#grid-table-editpro";
	var pro_id=getUrlParam("pro_id");
	var type =getUrlParam("type");
	var initdata={};
	var tpdata;
	if(pro_id){
		$("#header").html("修改产品信息");
	}else{
		$("#header").html("新增产品信息");
	}
	$('#minImg').on('mouseenter','.Img_outer',function () {
		$(this).children('.Img_delete').show();
	});
	$('#minImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});
	$('#detailImg').on('mouseenter','.Img_outer',function () {
			$(this).children('.Img_delete').show();
	});
	$('#detailImg').on('mouseleave','.Img_outer',function () {
		$(this).children('.Img_delete').hide();
	});
	getProductType();
	
	function operateFormat(val, options, rowObject){
    	var rStr = '';
	    rStr +='<a name="del" onclick="delRow(\''+options.rowId+'\')">删除</a>';
    	return rStr;	
    }
	
	 /**
     * 显示表格
     * @param params
     */
    function showTable() {
    	$(grid_selector).jqGrid({
            height: 400,
            altRows:true,//隔行变色
            multiselect:true,
            autowidth: true,
            datatype:"local",
            altclass:'',//用于交替变色的class属性
            colNames:['规格id','规格名称', '价格','会员价格', '库存' , '编码', '重量'],//表栏目
            colModel:[
            	 		{name:'tpId',index:'tpId', width:100, align:"left",hidden:true},
                        {name:'tpName',index:'tpName', width:100, align:"left",editable:true},
                        {name:'tpPrice',index:'tpPrice', width:60, align:"left",editable:true, sorttype:"number"},
                        {name:'tpSalePrice',index:'tpSalePrice', width:60, align:"left",editable:true, sorttype:"number"},
                        {name:'tpNum',index:'tpNum', width:60, align:"left",editable:true, sorttype:"number"},
                        {name:'tpNo',index:'tpNo', width:60, align:"left",editable:true},
                        {name:'tpWeight',index:'tpWeight', width:60,align:"left",editable:true}
            ],
            cellsubmit: "clientArray",
            cellEdit:true,
			beforeEditCell: function (rowid, cellname, v, iRow, iCol) {
			      lastrow = iRow;  //给全局变量赋值
			      lastcell = iCol;
			},
			loadComplete: function () {
				var mygrid = jQuery(grid_selector).jqGrid('clearGridData')[0];
				mygrid.addJSONData(tpdata);
            },
            caption: "规格列表"//表头标题
        });

        //$(pager_selector).css("height","40px");
    }

    
	function getProductType(){
    	$.ajax({ 
    		url: "/yhsj/product/getProductType",
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
    			$("#proType").append(html);
    		},
    		complete: function(result){
    			if(type=="2"){
        			getProductDetail();
    			}else{
    				showTable();
    			}
    		}
    	});
    }
	
	function getProductDetail(){
    	$.ajax({ 
    		url: "/yhsj/product/getProductDetail",
    		data:{
    			pro_id:pro_id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code==200){
    				if(result.data){
    					initdata=result.data;
    					initLoadData(initdata);
    					tpdata = initdata.productTp;
    				}
    			}
    		},
    		complete: function(result){
    			showTable();
    		}
    	});
    }
	
	$('#fileActivityImg').on('change',function () {
        let files = $(this).prop('files');
        if(files.length!=0){
            let reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onload = function(evt){
            	$('#minImg').html("");
            	var html="";
            	html+='<div class="Img_outer">';
				html+='<img src="'+evt.target.result+'" alt="" class="img_above">';
				html+='<span class="Img_delete" onclick="deleteImg(this,1)">×</span>';
				html+='</div>';
                $('#minImg').append(html);
                $('#fileActivityImg').val('');
            }
        }
    });
	
	$('#fileActivityImg2').on('change',function () {
		if($('#detailImg').find(".Img_outer").length>=6){
			layer.msg("详情图最多只能6张", {icon: 1});
			$('#fileActivityImg2').val('');
			return;
		}
        let files = $(this).prop('files');
        if(files.length!=0){
            let reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onload = function(evt){
            	var html="";
            	html+='<div class="Img_outer">';
				html+='<img src="'+evt.target.result+'" alt="" class="img_above">';
				html+='<span class="Img_delete" onclick="deleteImg(this,2)">×</span>';
				html+='</div>';
                $('#detailImg').append(html);
                $('#fileActivityImg2').val('');
            }
        }
    });
	
	$("#addBtn").click(function(){
		var ids = jQuery(grid_selector).jqGrid("getDataIDs");//得到jqgrid当前行数        
		var rowid1;
		if(ids.length>0){
			rowid1 = Math.max.apply(Math,ids);
		}else{
			rowid1 = 0;
		}
		//获得当前最大行号
		var netporfit = rowid1+1;//
		var dataRow = {};

		//将新添加的行插入到最后列
		$(grid_selector).jqGrid("addRowData", netporfit, dataRow,"last");
    });
	
	$("#delBtn").click(function(){
		var rowIds = $(grid_selector).jqGrid("getGridParam", "selarrrow");
		if(rowIds.length < 1) {
			alert('请先选择行记录!');
		}
		for(var i=0;i<rowIds.length;i++){
			var rowData = $(grid_selector).jqGrid('getRowData',rowIds[i]);
			if(rowData.tpId){
				deleteIds.push(rowData.tpId);
			}
			
		}
		for (var i = 0, l = rowIds.length; i < l; i++) {
			$(grid_selector).jqGrid("delRowData", rowIds[0]);
		}
	});
    
    $("#submitbtn").click(function(){
    	$(grid_selector).jqGrid("saveCell", lastrow, lastcell);
    	
    	if($("#proName").val()==null||$("#proName").val()==""){
    		layer.msg("请填写产品名称", {icon: 2});
    		return;
    	}
    	
    	if($("#proSummary").val()==null||$("#proSummary").val()==""){
    		layer.msg("请填写产品简介", {icon: 2});
    		return;
    	}
    	
    	if($("#proType").val()==null||$("#proType").val()==""){
    		layer.msg("请选择产品分类", {icon: 2});
    		return;
    	}
    	if($("#proPrice").val()==null||$("#proPrice").val()==""){
    		layer.msg("请填写产品价格", {icon: 2});
    		return;
    	}
    	if($("#proSalePrice").val()==null||$("#proSalePrice").val()==""){
    		layer.msg("请填写会员价格", {icon: 2});
    		return;
    	}
    	if($("#proNum").val()==null||$("#proNum").val()==""){
    		layer.msg("请填写产品库存", {icon: 2});
    		return;
    	}
    	
    	if($("#proWeight").val()==null||$("#proWeight").val()==""){
    		layer.msg("请填写产品重量", {icon: 2});
    		return;
    	}
    	if($("#minImg").find("img").length==0){
    		layer.msg("请上传产品logo图片", {icon: 2});
    		return;
    	}
    	var formData=new FormData($("#productForm")[0]);
    	formData.append("dtoStr",JSON.stringify(jQuery(grid_selector).jqGrid('getRowData')));
    	formData.append("deleteImgStr",deleteArray.join(","));
    	var sortArray=[];
    	if($("#minImg").find("img").length>0){		
    		$("#minImg").find("img").each(function(index,item){
    			if($(item).attr("src").indexOf(",")>=0){
    				sortArray.push("minImgFiles");
    				formData.append("minImgFiles",convertBase64UrlToBlob($(item).attr("src").substring($(item).attr("src").indexOf(",")+1)));			
    			}
    		});
    	}else{
    		formData.append("pro_min_img_url","");
    	}
    	
    	if($("#detailImg").find("img").length>0){
    		$("#detailImg").find("img").each(function(index,item){
    			if($(item).attr("src").indexOf(",")>=0){
    				sortArray.push("detailImgFiles_"+index);
    				formData.append("detailImgFiles_"+index,convertBase64UrlToBlob($(item).attr("src").substring($(item).attr("src").indexOf(",")+1)));
    			}			
    		});
    	}
    	
    	formData.append("control",sortArray.join(","));
    	formData.append("idList",deleteIds);
    	
    	$.ajax({ 
    		url: "/yhsj/product/saveProduct",
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
    					location.href="index?url=product&urlName=产品管理";
    				});
    			}else{
    				layer.msg(result.message, {icon: 2,time:1000});
    			}
    		}
    	});
    	
    });
    
    $("#cancelbtn").click(function(){
    	location.href="index?url=product&urlName=产品管理";
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
    	var product = initdata.product;
    	var productTp = initdata.productTp;
    	var encolsures = initdata.encolsures;
    	$("#proId").val(product.proId);
    	$("#proName").val(product.proName);
		$("#proSummary").val(product.proSummary);
		$("#proPrice").val(product.proPrice);
		$("#proSalePrice").val(product.proSalePrice);
		$("#proNum").val(product.proNum);
		$("#proWeight").val(product.proWeight);
		$("#proType").val(product.proType);
		$("#proIsShow").val(product.proIsShow);
		$("#proDetailDetailRemark").val(product.proDetailDetailRemark);
		$("#proIsShow").val(product.proIsShow);
		if(product.proMinImgUrl){
			var html="";
        	html+='<div class="Img_outer">';
			html+='<img src="'+product.proMinImgUrl+'" alt="" class="img_above">';
			html+='<span class="Img_delete" onclick="deleteImg(this,1)">×</span>';
			html+='</div>';
			$("#minImg").html(html);	
		}
		if(encolsures!=null&&encolsures.length>0){
			$.each(encolsures,function(index,item){
				var htmltext="";
				htmltext+='<div class="Img_outer">';
				htmltext+='<img src="'+item.enclosureUrl+'" alt="" class="img_above" dialog-data-id="'+item.enclosureId+'">';
				htmltext+='<span class="Img_delete" onclick="deleteImg(this,2)">×</span>';
				htmltext+='</div>';
				$("#detailImg").append(htmltext);
			});
		}
    }
})();

function deleteImg(that,type){
	if(type==1){
		$(that).parent().remove();
	}else{
		var img=$(that).parent().find("img");
		if(img.attr("src").indexOf(",")<0){
			if(img.attr("dialog-data-id")!=null&&img.attr("dialog-data-id")!=""){
				deleteArray.push(img.attr("dialog-data-id"));
			}
		}
		$(that).parent().remove();
	}
}