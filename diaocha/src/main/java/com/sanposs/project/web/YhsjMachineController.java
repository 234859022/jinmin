package com.sanposs.project.web;
import com.sanposs.project.constants.Constants;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjMachine;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.redis.RedisManager;
import com.sanposs.project.service.YhsjMachineService;
import com.sanposs.project.utils.GsonUtils;
import com.sanposs.project.utils.PageResult;
import com.sanposs.project.utils.QueryPage;
import com.sanposs.project.utils.SessinoManager;
import com.sanposs.project.utils.UUIDUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* Created by CodeGenerator on 2019/05/09.
*/
@RestController
@RequestMapping("/yhsj/machine")
public class YhsjMachineController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjMachineController.class);
	
    @Resource
    private YhsjMachineService yhsjMachineService;

    @RequestMapping("/add")
    public Result add(HttpServletRequest request,YhsjMachine yhsjMachine) {
    	Result result=ResultGenerator.genSuccessResult();
    	HttpSession session = request.getSession();
    	String token = RedisManager.getInstance().get(Constants.getSessionCacheKey(session.getId()));
		LoginUser loginUser=SessinoManager.getLoginUser(token);
    	yhsjMachine.setMachineId(UUIDUtil.createUUID());
    	yhsjMachine.setMachineCreateTime(new Date());
    	yhsjMachine.setMachineCreateUser(loginUser.getUserId());
        int n=yhsjMachineService.save(yhsjMachine);
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
        int n=yhsjMachineService.deleteMachine(id);
        if(n>0){
        	result.setData("删除成功");
        }else{
        	result.setData("删除失败");
        }
        return result;
    }

    @RequestMapping("/update")
    public Result update(YhsjMachine yhsjMachine) {
    	Result result=ResultGenerator.genSuccessResult();
        int n=yhsjMachineService.update(yhsjMachine);
        if(n>0){
        	result.setData("更改成功");
        }else{
        	result.setData("更改失败");
        }
        return result;
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        YhsjMachine yhsjMachine = yhsjMachineService.findBy("machineId", id);
        return ResultGenerator.genSuccessResult(yhsjMachine);
    }

    @RequestMapping("/list")
    public PageResult<Map<String,Object>> list(HttpServletRequest request,YhsjMachine machine, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer rows,String condition_str,String sidx,String sord) {
    	PageResult<Map<String,Object>> response = new PageResult<Map<String,Object>>();
    	if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	    }else {
	    	QueryPage queryPage=new QueryPage(page,rows);
	    	
	    	StringBuffer query_sql=new StringBuffer("select yhsj_machine.*,sys_admin.username from yhsj_machine left join sys_admin on yhsj_machine.machine_create_user=sys_admin.id where 1=1");
	    	StringBuffer count_sql=new StringBuffer("select count(machine_id) from yhsj_machine where 1=1");
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
	        	query_sql.append(" order by machine_create_time desc limit "+queryPage.getOffset()+","+queryPage.getPageSize());
	        }
	        
	        List<Map<String,Object>> list = yhsjMachineService.dynamicSqlQuery(query_sql.toString());
	        
	        int count=yhsjMachineService.dynamicSqlByCount(count_sql.toString());
	        
	        response=new PageResult<Map<String,Object>>(page, rows, count, list);
	    }
    	return response;
    }
}
