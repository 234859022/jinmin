var etypegrid;
(function() {
var grid_selector = "#grid-table-advertisement";
var pager_selector = "#grid-pager-advertisement";
var getAdvertisement=[];
var getEType=[];
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
        if(cellvalue == 0){
        	htmltext = "不可用";
        }else if(cellvalue = 1){
        	htmltext = "可用";
        }
        return htmltext;
    }
    
    function operateFormat(cellvalue, options, rowObject){
    	var rStr = '';
    	rStr += '<a name="edit" onclick="modifyAnswer(\''+cellvalue+'\')">查看</a>';
	    //rStr +='&nbsp;|&nbsp;<a name="del" onclick="deleteAnswer(\''+cellvalue+'\')">删除</a>';
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
    	eAnswergrid=$(grid_selector).jqGrid({
            url:'/e/answer/getAnswerList',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['考核标题', '被评价人', '考核人','考核月份',  '审核总分','操作'],//表栏目
            colModel:[
                        {name:'answerTitle',index:'answerTitle', width:200, align:"center"},
                        {name:'answerUsername',index:'answerUsername', width:90},
                        {name:'answerCreateUser',index:'answerCreateUser', width:90},
                        {name:'answerDate',index:'answerDate', width:90},
                        {name:'answerTotalScore',index:'answerTotalScore', width:90},
                        {name:'answerId',index:'answerId', width:125,sorttype:"string",formatter:operateFormat}
                  
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
            caption: "问题列表"//表头标题
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
        var answer_username = $("#answer_username").val();
        var postData = {
        		answerUsername:answer_username
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
        $("#searchbtn").on("click", function(){
        	 search('');
        });
    }

    var eAnswerObj = {
        initPage: initPage //初始页面信息
    }


    eAnswerPage = eAnswerObj;

})();

/**
 * 新增
 */
function  addAnswer() {
	location.href = 'index?url=answer_edit&urlName=考核管理&type=1';
}

/**
 * 修改
 */
function modifyAnswer(id) {
	location.href = 'index?url=answer_show&urlName=考核查询&type=2&answer_id='+id;
}

/**
 * 删除
 */
function deleteAnswer(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/e/answer/deleteAnswer",
    		data:{
    			question_id:id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					eAnswergrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
		
	});
}