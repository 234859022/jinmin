var progrid;
(function() {
var grid_selector = "#grid-table-pro";
var pager_selector = "#grid-pager-pro";
var getProductType=[];
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
    
    function showFormat( cellvalue, options, rowObject ){
    	var htmltext="";
        if(cellvalue == 0){
        	htmltext = "不显示";
        }else if(cellvalue = 1){
        	htmltext = "显示";
        }
        return htmltext;
    }
    
    function typeFormat( cellvalue, options, rowObject ){
    	var htmltext="";
    	if(getProductType.length>0){
    		$.each(getProductType,function(index,item){
    			if(cellvalue==item.typeId){
    				htmltext=item.typeName;
    				return false;
    			}
    		});
    	}
    	return htmltext;
    }
    
    function operateFormat(val, options, rowObject){
    	var rStr = '';
    	rStr += '<a name="edit" onclick="modifyPro(\''+val+'\')">编辑</a>';
	    rStr +='&nbsp;|&nbsp;<a name="del" onclick="deletePro(\''+val+'\')">删除</a>';
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
    	progrid=$(grid_selector).jqGrid({
            url:'/yhsj/product/getProduct',
            datatype: 'json',
            mtype: 'POST',
            height: 400,
            altRows:true,//隔行变色
            altclass:'',//用于交替变色的class属性
            colNames:['产品名称', '产品价格', '产品会员价', '产品库存', '产品简介', '产品分类', '显示情况', '创建时间', '操作'],//表栏目
            colModel:[
                        {name:'proName',index:'proName', width:60, align:"center"},
                        {name:'proPrice',index:'proPrice', width:60, align:"center", sorttype:"number"},
                        {name:'proSalePrice',index:'proSalePrice', width:60, align:"center", sorttype:"number"},
                        {name:'proNum',index:'proNum', width:60, align:"center", sorttype:"number"},
                        {name:'proSummary',index:'proSummary', width:60, align:"center"},
                        {name:'proType',index:'proType', width:100,formatter:typeFormat},
                        {name:'proIsShow',index:'proIsShow', width:90,formatter:showFormat},
                        {name:'createDate',index:'createDate', width:125, formatter:timeFormat},
                        {name:'proId',index:'proId', width:125,sorttype:"string",formatter:operateFormat}
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
            caption: "产品列表"//表头标题
        });

        //$(pager_selector).css("height","40px");
    }
    
    function func_getProductType(){
    	$.ajax({ 
    		url: "/yhsj/product/getProductType",
    		data:{},
    		async:false,
    		dataType:'json',
    		success: function(result){
				var html = '';
    			if(result.code==200){
    				getProductType=result.data;
    				$.each(getProductType,function(index,item){
    	    				html+='<option value="'+item.typeId+'">'+item.typeName+'</option>';
    	    		});
    			}
    			$("#pro_type").append(html);
    		}
    	});
    }

    /**
     * 查询
     */
    function  search(parms) {
        $(grid_selector).jqGrid('clearGridData');
        var pro_name = $("#pro_name").val();
        var pro_type = $("#pro_type").val();

        var postData = {
        	pro_name:pro_name,
        	pro_type:pro_type
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
    	func_getProductType();
        $("#searchbtn").on("click", function(){
        	 search('');
        });
    }

    var productObj = {
        initPage: initPage //初始页面信息
    }


    productPage = productObj;

})();

/**
 * 新增
 */
function  addPro() {
	location.href = 'index?url=product_edit&urlName=产品管理&type=1';
}

/**
 * 修改
 */
function modifyPro(id) {
	location.href = 'index?url=product_edit&urlName=产品管理&type=2&pro_id='+id;
}

/**
 * 删除
 */
function deletePro(id) {
	layer.confirm('确认删除所选信息?', {
		btn : [ '确定', '关闭' ]
	}, function() {
		$.ajax({ 
    		url: "/yhsj/product/deleteProduct",
    		data:{
    			pro_id:id
    		},
    		async:false,
    		dataType:'json',
    		success: function(result){
    			if(result.code=='200'){
    				layer.msg(result.data, {icon: 1,time:1000},function(){
    					progrid.trigger("reloadGrid");
    				});
    			}
    		}
    	});
		
	});
}