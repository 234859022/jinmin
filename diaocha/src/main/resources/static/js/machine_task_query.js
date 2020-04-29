(function() {
    var condition=[];
    
    condition.push({
    	field_text:'机器名称',
    	field_name:'machine_name',
    	field_type:'text',
    	field_html_widget_list:[{
        	compare_value:'=',
        	compare_text:'等于'
        },{
        	compare_value:'like',
        	compare_text:'包含'
        }]
    });
    
    condition.push({
    	field_text:'采购编号',
    	field_name:'purchase_no',
    	field_type:'text',
    	field_html_widget_list:[{
        	compare_value:'=',
        	compare_text:'等于'
        },{
        	compare_value:'like',
        	compare_text:'包含'
        }]
    });
    
    condition.push({
    	field_text:'产品名称',
    	field_name:'product_name',
    	field_type:'text',
    	field_html_widget_list:[{
        	compare_value:'=',
        	compare_text:'等于'
        },{
        	compare_value:'like',
        	compare_text:'包含'
        }]
    });
    
    condition.push({
    	field_text:'数量',
    	field_name:'product_count',
    	field_type:'number',
    	field_html_widget_list:[{
        	compare_value:'=',
        	compare_text:'等于'
        },{
    		compare_value:'between',
        	compare_text:'区间'
    	},{
    		compare_value:'>=',
        	compare_text:'大于等于'
    	},{
    		compare_value:'<=',
        	compare_text:'小于等于'
    	}]
    });
    
    
    condition.push({
    	field_text:'重量',
    	field_name:'product_weight',
    	field_type:'number',
    	field_html_widget_list:[{
        	compare_value:'=',
        	compare_text:'等于'
        },{
    		compare_value:'between',
        	compare_text:'区间'
    	},{
    		compare_value:'>=',
        	compare_text:'大于等于'
    	},{
    		compare_value:'<=',
        	compare_text:'小于等于'
    	}]
    });
    
    condition.push({
    	field_text:'规格名称',
    	field_name:'tp_name',
    	field_type:'text',
    	field_html_widget_list:[{
        	compare_value:'=',
        	compare_text:'等于'
        },{
        	compare_value:'like',
        	compare_text:'包含'
        }]
    });
    
    condition.push({
    	field_text:'开始时间',
    	field_name:'task_start_time',
    	field_type:'date',
    	field_html_widget_list:[{
        	compare_value:'=',
        	compare_text:'等于'
        },{
    		compare_value:'between',
        	compare_text:'区间'
    	},{
    		compare_value:'>=',
        	compare_text:'大于等于'
    	},{
    		compare_value:'<=',
        	compare_text:'小于等于'
    	}]
    });
    
    condition.push({
    	field_text:'结束时间',
    	field_name:'task_end_time',
    	field_type:'date',
    	field_html_widget_list:[{
        	compare_value:'=',
        	compare_text:'等于'
        },{
    		compare_value:'between',
        	compare_text:'区间'
    	},{
    		compare_value:'>=',
        	compare_text:'大于等于'
    	},{
    		compare_value:'<=',
        	compare_text:'小于等于'
    	}]
    });
        
    condition.push({
    	field_text:'状态',
    	field_name:'task_status',
    	field_type:'number',
    	field_html_widget_list:[],
    	field_value_list:[{
    		compare_value:'',
    		compare_text:'请选择'
    	},{
    		compare_value:'1',
    		compare_text:'进行中'
    	},{
    		compare_value:'2',
    		compare_text:'未处理'
    	},{
    		compare_value:'3',
    		compare_text:'已完成'
    	}]
    });
    
    condition.push({
    	field_text:'是否删除',
    	field_name:'task_status',
    	field_type:'number',
    	field_html_widget_list:[],
    	field_value_list:[{
    		compare_value:'',
    		compare_text:'请选择'
    	},{
    		compare_value:'0',
    		compare_text:'否'
    	},{
    		compare_value:'1',
    		compare_text:'是'
    	}]
    });
    
    createConditionQuery('machinetaskinfo',condition);
    
    $("#submitbtn").click(function(){
    	var cons=getCondition(condition);
    	parent.$("#grid-table-machine-task").jqGrid('setGridParam',{
            datatype:'json',
             postData:{
	                rows:10,
	                sidx:"task_id",
	                sord:"desc",
	                condition_str:JSON.stringify(cons)
             }, //发送数据
             page:1,
         }).trigger("reloadGrid");
		parent.layer.closeAll();
    });
    
    $("#cancelbtn").click(function(){
    	parent.layer.closeAll();
    });
    
    $("#resetbtn").click(function(){
    	$("#machinetaskinfo").reset();
    });

})();

