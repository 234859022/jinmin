package com.sanposs.project.web;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sanposs.project.constants.Constants;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjMachine;
import com.sanposs.project.model.YhsjMachineTask;
import com.sanposs.project.model.YhsjMachineTaskIngrediient;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.redis.RedisManager;
import com.sanposs.project.service.YhsjMachineTaskIngrediientService;
import com.sanposs.project.service.YhsjMachineTaskService;
import com.sanposs.project.utils.GsonUtils;
import com.sanposs.project.utils.PageResult;
import com.sanposs.project.utils.QueryPage;
import com.sanposs.project.utils.SessinoManager;
import com.sanposs.project.utils.UUIDUtil;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
* Created by CodeGenerator on 2019/05/10.
*/
@RestController
@RequestMapping("/yhsj/machine/task")
public class YhsjMachineTaskController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjMachineTaskController.class);

    @Resource
    private YhsjMachineTaskService yhsjMachineTaskService;
    @Resource
    private YhsjMachineTaskIngrediientService yhsjMachineTaskIngrediientService;
    
    @Resource
    private YhsjMachineTaskIngrediientService taskIngrediientService;

    @RequestMapping("/add")
    public Result add(HttpServletRequest request,YhsjMachineTask yhsjMachineTask) {
    	Result result=ResultGenerator.genSuccessResult();
    	HttpSession session = request.getSession();
    	String token = RedisManager.getInstance().get(Constants.getSessionCacheKey(session.getId()));
		LoginUser loginUser=SessinoManager.getLoginUser(token);
    	yhsjMachineTask.setTaskId(UUIDUtil.createUUID());
    	yhsjMachineTask.setTaskCreateTime(new Date());
    	yhsjMachineTask.setTaskCreateUserid(loginUser.getUserId());
    	yhsjMachineTask.setTaskCreateUser(loginUser.getUserName());    	
        int n=yhsjMachineTaskService.save(yhsjMachineTask);
        if(n>0){
        	result.setData("添加成功");
        }else{
        	result.setData("添加失败");
        }
        return result;
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
    	Result result=ResultGenerator.genSuccessResult();
        int n=yhsjMachineTaskService.deleteYhsjMachineTask(id);
        if(n>0){
        	result.setData("删除成功");
        }else{
        	result.setData("删除失败");
        }
        return result;
    }

    @RequestMapping("/update")
    public Result update(YhsjMachineTask yhsjMachineTask) {
    	Result result=ResultGenerator.genSuccessResult();
    	int n=yhsjMachineTaskService.update(yhsjMachineTask);
    	if(n>0){
        	result.setData("更改成功");
        }else{
        	result.setData("更改失败");
        }
        return result;
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        YhsjMachineTask yhsjMachineTask = yhsjMachineTaskService.findBy("taskId", id);
        return ResultGenerator.genSuccessResult(yhsjMachineTask);
    }

    @RequestMapping("/list")
    public PageResult list(HttpServletRequest request,YhsjMachine machine, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer rows,String condition_str,String sidx,String sord) {
    	PageResult<Map<String,Object>> response = new PageResult<Map<String,Object>>();
    	if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	    }else {
	    	QueryPage queryPage=new QueryPage(page,rows);
	    	
	    	StringBuffer query_sql=new StringBuffer("select * from yhsj_machine_task where 1=1");
	    	StringBuffer count_sql=new StringBuffer("select count(task_id) from yhsj_machine_task where 1=1");
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
	        	query_sql.append(" order by task_create_time desc limit "+queryPage.getOffset()+","+queryPage.getPageSize());
	        }
	        
	        List<Map<String,Object>> list = yhsjMachineTaskService.dynamicSqlQuery(query_sql.toString());
	        
	        int count=yhsjMachineTaskService.dynamicSqlByCount(count_sql.toString());
	        
	        response=new PageResult<Map<String,Object>>(page, rows, count, list);
	    }
    	return response;
    }
    
    @RequestMapping("/getMachineTaskDetail")
    @ResponseBody
    public Result getMachineTaskDetail(HttpServletRequest request,String task_id) {
// 	    Long comid = (Long)request.getSession().getAttribute("comid");
	
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("main", new YhsjMachineTask());
	    	map.put("detail", new ArrayList<YhsjMachineTaskIngrediient>());
	    	if (task_id!=null && !task_id.equals("")) {
				Condition condition = new Condition(YhsjMachineTask.class);
				Example.Criteria criteria = condition.createCriteria();
				criteria.andCondition(" task_id='"+task_id+"'");
				List<YhsjMachineTask> machineTasks = yhsjMachineTaskService.findByCondition(condition);
				if (machineTasks.size()>0) {
					map.put("main",machineTasks.get(0));
				}
				
				Condition detailcondition = new Condition(YhsjMachineTaskIngrediient.class);
				Example.Criteria detailcriteria = detailcondition.createCriteria();
				detailcriteria.andCondition(" task_id='"+task_id+"'");
				List<YhsjMachineTaskIngrediient> details = yhsjMachineTaskIngrediientService.findByCondition(detailcondition);
				if (details.size()>0) {
					map.put("detail",details);
				}
			}
	        return ResultGenerator.genSuccessResult(map);
	    }
    }
    
    @RequestMapping("/getDetailByPurchaseDetailId")
    public Result getDetailByPurchaseDetailId(HttpServletRequest request,String purchase_detail_id,String task_id) {
		List<Map<String,Object>> list=yhsjMachineTaskService
				.dynamicSqlQuery("SELECT * FROM yhsj_produce_detail LEFT JOIN  yhsj_purchase_order_detail ON yhsj_produce_detail.produce_detail_id=yhsj_purchase_order_detail.produce_detail_id WHERE yhsj_purchase_order_detail.detail_id='"
						+ purchase_detail_id + "'");
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(list!=null&&list.size()>0){
			resultMap.put("produce_detail", list.get(0));
		}
		
		
		YhsjMachineTask  machineTask=yhsjMachineTaskService.findById(task_id);
		if(machineTask!=null){
			resultMap.put("machineTask", machineTask);
		}
		
		Condition condition=new Condition(YhsjMachineTaskIngrediient.class);
		Example.Criteria criteria=condition.createCriteria();
		criteria.andCondition(" task_id='"+task_id+"'");
		List<YhsjMachineTaskIngrediient> taskIngrediients=taskIngrediientService.findByCondition(condition);
		
		if(taskIngrediients!=null&&taskIngrediients.size()>0){
			resultMap.put("taskIngrediients", taskIngrediients);
		}
		
		return ResultGenerator.genSuccessResult(resultMap);
    }
}
