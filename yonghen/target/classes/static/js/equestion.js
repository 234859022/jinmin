var eQuestiongrid;
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
    
    function typesFormat( cellvalue, options, rowObject ){
    	var htmltext="";
    	if(cellvalue == 1){
        	htmltext = "上级";
        }else if(cellvalue == 2){
        	htmltext = "同级";
        }else if(cellvalue == 3){
        	htmltext = "下级";
        }else if(cellvalue == 4){
        	htmltext = "自评";
        }
        return htmltext;
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
    	eQuestiongrid=$(grid_selector).jqGrid({
            url:'/e/question/getQuestionList',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['问题', '权重', '分类','等级', '排序','操作'],//表栏目
            colModel:[
                        {name:'questionTitle',index:'questionTitle', width:200, align:"center"},
                        {name:'questionWeighted',index:'questionWeighted', width:90},
                        {name:'questionTypeid',index:'questionTypeid', width:100,formatter:typeFormat},
                        {name:'questionTypes',index:'questionTypes', width:100,formatter:typesFormat},
                        {name:'questionSort',index:'questionSort', width:90},
                        {name:'questionId',index:'questionId', width:125,sorttype:"string",formatter:operateFormat}
                  
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
        var question_title = $("#question_title").val();
        var question_type = $("#question_type").val();
        var questionTypes =  $("#questionTypes").val();
        var postData = {
        		question_title:question_title,
        		question_type:question_type,
        		questionTypes:questionTypes
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

    var eQuestionObj = {
        initPage: initPage //初始页面信息
    }


    eQuestionPage = eQuestionObj;

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
    					eQuestiongrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
		
	});
}