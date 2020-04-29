(function() {

	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	function updatePagerIcons(table) {
		var replacement = {
			'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
			'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
			'ui-icon-seek-next' : 'icon-angle-right bigger-140',
			'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
		};
		$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function() {
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

			if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
		})
	}
	function timeFormat(cellvalue, options, rowObject) {
		var html = "";
		if(cellvalue!=null && cellvalue!=""  && cellvalue!="null"){
			html = (new Date(cellvalue)).Format("yyyy-MM-dd HH:mm:ss");
		}
		return html;
    }

	function usrtTypeFormat(cellvalue, options, rowObject) {
		var htmltext = "";
		if (cellvalue == 1) {
			htmltext = "普通用户";
		} else if (cellvalue == 2) {
			htmltext = "车间主管";
		} else if (cellvalue == 3) {
			htmltext = "老板";
		}
		return htmltext;
	}

	function operateFormat(cellvalue, options, rowObject) {
		var rStr = '';
		rStr += '<a name="edit" onclick="modifyUser(\'' + cellvalue + '\')">编辑</a>';
		return rStr;
	}

	/**
	 * 显示表格
	 * @param params
	 */
	function showTable(params) {
		$(grid_selector).jqGrid({
			url : '/yhsj/user/getUser',
			datatype : 'json',
			mtype : 'POST',
			height : 400,
			altRows : true, //隔行变色
			altclass : '', //用于交替变色的class属性
			colNames : [ 'id', '用户名', '电话', '真实姓名',  '微信unionid', "用户类型", "创建时间", "操作" ], //表栏目
			colModel : [
				{
					name : 'userId',
					index : 'userId',
					width : 60,
					align : "center",
					hidden : true
				},
				{
					name : 'userName',
					index : 'userName',
					width : 100
				},
				{
					name : 'userPhone',
					index : 'userPhone',
					width : 90
				},
				{
					name : 'userRealName',
					index : 'userRealName',
					width : 125,
					sorttype : "string"
				},
				{
					name : 'userWeixinUnionid',
					index : 'userWeixinUnionid',
					width : 125
				},
				{
					name : 'userType',
					index : 'userType',
					width : 100,
					formatter : usrtTypeFormat
				},
				{
					name : 'userCreateTime',
					index : 'userCreateTime',
					width : 125,
					formatter : timeFormat
				},
				{
					name : 'userId',
					index : 'userId',
					width : 125,
					sorttype : "string",
					formatter : operateFormat
				}

			],
			postData : params,
			viewrecords : true, //是否显示所有记录的总数
			rowNum : 10, //每页显示的个数
			pager : pager_selector, //默认的分页
			autowidth : true,
			rowList : [ 10, 20, 30 ],
			loadComplete : function() {
				var table = this;
				setTimeout(function() {
					updatePagerIcons(table);
				}, 0);
			},
			jsonReader : {
				root : "rows",
				page : "page",
				total : "total",
				records : "records",
				repeatitems : false,
				cell : "cell",
				id : "id"
			},
			caption : "用户记录" //表头标题
		});

	//$(pager_selector).css("height","40px");
	}

	/**
	 * 查找
	 * @param param
	 */
	function search(parms) {
		$(grid_selector).jqGrid('clearGridData');
		var user_name = $("#inpNickName").val();
		var user_phone = $("#inpPhone").val();

		var postData = {
			user_name : user_name,
			user_phone : user_phone
		};
		if (parms == '') {
			$(grid_selector).jqGrid('setGridParam', {
				datatype : 'json',
				postData : postData, //发送数据
				page : 1
			}).trigger("reloadGrid");
		} else {
			showTable(postData);
		}
	}

	/**
	 * 初始化函数
	 * @param param
	 */
	function initPage(param) {
		search('init');
		$("#searchbtn").on("click", function() {
			search('');
		});
	}

	var userObj = {
		initPage : initPage //初始页面信息
	}


	userPage = userObj;

})();
/**
 * 修改
 */
function modifyUser(id) {
	location.href = 'index?url=user_edit&urlName=用户管理&type=2&user_id=' + id;
}

//function uploadImg(control,imgId, valId){
//	//开始上传文件时显示一个图片,文件上传完成将图片隐藏
//	$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});
//		 if($("#"+control).val()==null||$("#control").val()==""){
//			 alert('请选择图片');
//    	 return;
//	}
//	var strFilter=".jpeg|.gif|.jpg|.png|.bmp|.pic|";
//	if($("#"+control).val().indexOf(".")>-1)
//	{
//   		var p = $("#"+control).val().lastIndexOf(".");
//    	var strPostfix= $("#"+control).val().substring(p,$("#"+control).val().length) + '|';        
//    	strPostfix = strPostfix.toLowerCase();
//    	if(strFilter.indexOf(strPostfix)>-1)
//    	{
//    	}else{
//    		alert('图片类型出错，请重新选择！');
//     	    return;
//   	    }
//	} 
//	//执行上传文件操作的函数
//	$.ajaxFileUpload({
//    //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
//    	url:'../UploadController/upload?childPath=Banner&control='+control,
//		secureuri : false, //是否启用安全提交,默认为false 
//		fileElementId : control, //文件选择框的id属性
//		dataType : 'text', //服务器返回的格式,可以是json或xml等
//		success : function(res, status) { //服务器响应成功时的处理函数
//			var d = JSON.parse(res);
//		    if(d.code == "200")
//            {
//                $("#"+valId).val(d.data);
//			    $('#'+imgId).attr("src",d.data);
//            }else{
//                alert(d.data);
//            }
//		},
//		error : function(data, status, e) { //服务器响应失败时的处理函数
//			alert('图片上传失败，请重试！');
//		}
//	});	
//}