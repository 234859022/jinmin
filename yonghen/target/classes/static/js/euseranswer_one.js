var eAonegrid;
(function() {
var grid_selector = "#grid-table-advertisement";
var pager_selector = "#grid-pager-advertisement";
var getAdvertisement=[];
var getEType=[];
    var adminid = sessionStorage.getItem('adminid'); 
 
    function updatePagerIcons(table) {
        var replacement =
            {
                'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
                'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
                'ui-icon-seek-next' : 'icon-angle-right bigger-140',
                'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
            };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
        })
    }
    
	function statusFormat( cellvalue, options, rowObject ){
    	var htmltext="";
        if(cellvalue == 1){
        	htmltext = "已处理";
        }else{
        	htmltext = "未处理";
        }
        return htmltext;
    }
	
	function checkFormat1(cellvalue, options, rowObject){
		var rStr = cellvalue;
		if(rowObject.aYiAdminid.indexOf(adminid)>-1){
			if(rowObject.aYiStatus ==1){
				rStr = "<a onclick='showExam(1,\""+rowObject.aId+"\");'>"+" 查看评价</a>";
			}else{
				if(rowObject.aYiScores.indexOf(adminid)>-1){
					rStr = "<a onclick='showExam(1,\""+rowObject.aId+"\");'>"+" 查看评价</a>";
				}else{
					rStr = "<a onclick='goExam(1,\""+rowObject.aId+"\");'>"+" 马上评价</a>";
				}
			}
	    }else{
	    	rStr = "";
	    }
		return rStr;	
	}
	function checkFormat2(cellvalue, options, rowObject){
		var rStr = cellvalue;
		if(rowObject.aErAdminid.indexOf(adminid)>-1){
			if(rowObject.aErStatus ==1){
				rStr = "<a onclick='showExam(2,\""+rowObject.aId+"\");'>"+" 查看评价</a>";
			}else{
				if(rowObject.aErScores.indexOf(adminid)>-1){
					rStr = "<a onclick='showExam(2,\""+rowObject.aId+"\");'>"+" 查看评价</a>";
				}else{
					rStr = "<a onclick='goExam(2,\""+rowObject.aId+"\");'>"+" 马上评价</a>";
				}
			}
	    }else{
	    	rStr = "";
	    }
		return rStr;	
	}
	function checkFormat3(cellvalue, options, rowObject){
		var rStr = cellvalue;
		if(rowObject.aSanAdminid.indexOf(adminid)>-1){
			if(rowObject.aSanSatatus ==1){
				rStr = "<a onclick='showExam(3,\""+rowObject.aId+"\");'>"+" 查看评价</a>";
			}else{
				if(rowObject.aSanScores.indexOf(adminid)>-1){
					rStr = "<a onclick='showExam(3,\""+rowObject.aId+"\");'>"+" 查看评价</a>";
				}else{
					rStr = "<a onclick='goExam(3,\""+rowObject.aId+"\");'>"+" 马上评价</a>";
				}
			}
	    }else{
	    	rStr = "";
	    }
		return rStr;	
	}
    
    function operateFormat(cellvalue, options, rowObject){
    	var rStr = '';
    	rStr += '<a name="edit" onclick="modifyQuestion(\''+cellvalue+'\')">编辑</a>';
	    rStr +='&nbsp;|&nbsp;<a name="del" onclick="deleteQuestion(\''+cellvalue+'\')">删除</a>';
    	return rStr;	
    }
    
    function timeFormat( cellvalue, options, rowObject ){
		var html = "";
		if(cellvalue!=null && cellvalue!=""  && cellvalue!="null"){
			html = (new Date(cellvalue)).Format("yyyy-MM-dd HH:mm:ss");
		}
		return html;
    }
    

    /**
     * 显示表格
     * @param params
     */
    function showTable(params) {
    	eAonegrid=$(grid_selector).jqGrid({
    		url:'/e/user/answer/getUserAnswerList',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['标题', '被评价人', '上级评价人', '平均得分', '平级评价人','平均得分','下级评价人','平均得分','状态','创建时间'],//表栏目
            colModel:[
                        {name:'aTitle',index:'aTitle', width:120, align:"center"},
                        {name:'aUsername',index:'aUsername', width:90, align:"center"},
                        {name:'aYiAdmin',index:'aYiAdmin', width:90,formatter:checkFormat1},
                        {name:'aYiScore',index:'aYiScore', width:50, align:"center"},
                        {name:'aErAdmin',index:'aErAdmin', width:90,formatter:checkFormat2},
                        {name:'aErScore',index:'aErScore', width:50, align:"center"},
                        {name:'aSanAdmin',index:'aSanAdmin', width:90,formatter:checkFormat3},
                        {name:'aSanScore',index:'aSanScore', width:50, align:"center"},
                        {name:'aStatus',index:'aStatus', width:90,formatter:statusFormat},
                        {name:'aCreateTime',index:'aCreateTime', width:125, formatter:timeFormat}
                  
            ],
            postData:params,
            viewrecords:true,//是否显示所有记录的总数
            rowNum:10,//每页显示的个数
            pager:pager_selector,//默认的分页
            autowidth: true,
            rowList: [10, 20, 30],
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    updatePagerIcons(table);
                }, 0);
            },jsonReader : {
                root: "rows",
                page: "page",
                total: "total",
                records: "records",
                repeatitems: false,
                cell: "cell",
                id: "id"
            },
            caption: "考核列表"//表头标题
        });

        //$(pager_selector).css("height","40px");
    }
    
    function typeFormat( cellvalue, options, rowObject ){
    	var htmltext="";
    	if(getEType.length>0){
    		$.each(getEType,function(index,item){
    			if(cellvalue==item.typeId){
    				htmltext=item.typeName;
    				return false;
    			}
    		});
    	}
    	return htmltext;
    }
    
    function func_getType(){
    	$.ajax({ 
    		url: "/e/type/getType",
    		data:{},
    		async:false,
    		dataType:'json',
    		success: function(result){
				var html = '';
    			if(result.code==200){
    				getEType=result.data;
    				$.each(getEType,function(index,item){
    	    				html+='<option value="'+item.typeId+'">'+item.typeName+'</option>';
    	    		});
    			}
    			$("#question_type").append(html);
    		}
    	});
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var A_USERNAME = $("#A_USERNAME").val();
        var postData = {
        		aUsername:A_USERNAME,
        		isOne:1
        };
        if(parms == ''){
           $(grid_selector).jqGrid('setGridParam',{
               datatype:'json',
                postData:postData, //发送数据
                page:1
            }).trigger("reloadGrid");
        }
        else{
            showTable(postData);
        }
    }

    /**
     * 初始化函数
     * @param param
     */
    function  initPage(param) {
        search('init');
        func_getType();
        $("#searchbtn").on("click", function(){
        	 search('');
        });
    }

    var eAoneObj = {
        initPage: initPage //初始页面信息
    }


    eAonePage = eAoneObj;

})();

/**
 * 新增
 */
function  addQuestion() {
	location.href = 'index?url=equestion_edit&urlName=问题管理&type=1';
}

/**
 * 修改
 */
function modifyQuestion(id) {
	location.href = 'index?url=equestion_edit&urlName=问题管理&type=2&question_id='+id;
}

/**
 * 删除
 */
function deleteQuestion(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/e/question/deleteQuestion",
    		data:{
    			question_id:id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					eAonegrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
		
	});
}

function goExam(a,aid){
	location.href = 'index?url=answer_edit_one&urlName=考核管理&type=1&etype='+a+'&aid='+aid;
}

function showExam(a,aid){
	location.href = 'index?url=answer_show_one&urlName=考核管理&type=1&etype='+a+'&aid='+aid;
}