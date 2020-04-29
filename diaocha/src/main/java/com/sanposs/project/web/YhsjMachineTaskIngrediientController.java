package com.sanposs.project.web;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjMachineTaskIngrediient;
import com.sanposs.project.service.YhsjMachineTaskIngrediientService;
import com.sanposs.project.utils.GsonUtils;
import com.sanposs.project.utils.PageResult;
import com.sanposs.project.utils.QueryPage;
import com.sanposs.project.utils.SessinoManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
* Created by CodeGenerator on 2019/05/10.
*/
@RestController
@RequestMapping("/yhsj/machine/task/ingrediient")
public class YhsjMachineTaskIngrediientController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjMachineTaskIngrediientController.class);
	
    @Resource
    private YhsjMachineTaskIngrediientService yhsjMachineTaskIngrediientService;

    @RequestMapping("/add")
    public Result add(YhsjMachineTaskIngrediient yhsjMachineTaskIngrediient) {
        yhsjMachineTaskIngrediientService.save(yhsjMachineTaskIngrediient);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjMachineTaskIngrediientService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjMachineTaskIngrediient yhsjMachineTaskIngrediient) {
        yhsjMachineTaskIngrediientService.update(yhsjMachineTaskIngrediient);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjMachineTaskIngrediient yhsjMachineTaskIngrediient = yhsjMachineTaskIngrediientService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjMachineTaskIngrediient);
    }

    @RequestMapping("/list")
    public PageResult list(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer rows,String condition_str,String sidx,String sord) {
    	PageResult<Map<String,Object>> response = new PageResult<Map<String,Object>>();
    	if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	    }else {
	    	QueryPage queryPage=new QueryPage(page,rows);
	    	
	    	StringBuffer query_sql=new StringBuffer("select * from yhsj_machine_task_ingrediient where 1=1");
	    	StringBuffer count_sql=new StringBuffer("select count(task_ingredient_id) from yhsj_machine_task_ingrediient where 1=1");
	        if(condition_str!=null&&condition_str.compareTo("")>0){
		        List<Map<String,Object>> paramMaps=GsonUtils.jsonToArray(condition_str,new TypeReference<List<Map<String,Object>>>(){});
		        if(paramMaps.size()>0){
		        	for(Map<String,Object> param:paramMaps){
		        		if(param==null){
		        			continue;
		        		}
		        		query_sql.append(" and "+param.get("field_name")+" "+param.get("field_compare")+" "+param.get("field_value"));
		        		count_sql.append(" and "+param.get("field_name")+" "+param.get("field_compare")+" "+param.get("field_value"));
		        	}
		        }
	        }
	        if(sidx!=null&&sidx.compareTo("")>0){
	        	String order_str=" order by "+sidx;
	        	if(sord!=null&&sord.compareTo("")>0){
	        		order_str+=" "+sord;
	        	}else{
	        		order_str+=" desc";
	        	}
	        	query_sql.append(order_str+" limit "+queryPage.getOffset()+","+queryPage.getPageSize());
	        }else{
	        	query_sql.append(" order by ingredient_create_time desc limit "+queryPage.getOffset()+","+queryPage.getPageSize());
	        }
	        
	        List<Map<String,Object>> list = yhsjMachineTaskIngrediientService.dynamicSqlQuery(query_sql.toString());
	        
	        int count=yhsjMachineTaskIngrediientService.dynamicSqlByCount(count_sql.toString());
	        
	        response=new PageResult<Map<String,Object>>(page, rows, count, list);
	    }
    	return response;
    }
}
