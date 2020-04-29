package com.sanposs.project.web;
import com.sanposs.project.constants.Constants;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjMachine;
import com.sanposs.project.model.YhsjProduceDetail;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.redis.RedisManager;
import com.sanposs.project.service.YhsjProduceDetailService;
import com.sanposs.project.utils.GsonUtils;
import com.sanposs.project.utils.PageResult;
import com.sanposs.project.utils.QueryPage;
import com.sanposs.project.utils.SessinoManager;
import com.sanposs.project.utils.UUIDUtil;
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
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* Created by CodeGenerator on 2019/06/17.
*/
@RestController
@RequestMapping("/yhsj/produce/detail")
public class YhsjProduceDetailController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjMachineController.class);
	
    @Resource
    private YhsjProduceDetailService yhsjProduceDetailService;

    @RequestMapping("/add")
    public Result add(HttpServletRequest request,YhsjProduceDetail yhsjProduceDetail) {
    	if(yhsjProduceDetail==null){
            return ResultGenerator.genFailResult("缺少必要参数");
    	}
    	HttpSession session = request.getSession();
    	String token = RedisManager.getInstance().get(Constants.getSessionCacheKey(session.getId()));
		LoginUser loginUser=SessinoManager.getLoginUser(token);
		yhsjProduceDetail.setProduceDetailId(UUIDUtil.createUUID());
    	yhsjProduceDetail.setProduceDetailCreateTime(new Date());
    	yhsjProduceDetail.setProduceDetailCreateUserId(loginUser.getUserId());
        int n=yhsjProduceDetailService.save(yhsjProduceDetail);
        if(n>0){
            return ResultGenerator.genSuccessResult("新增失败");	
        }else{
            return ResultGenerator.genFailResult("新增成功");
        }
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {   	
        int n=yhsjProduceDetailService.deleteProduceDetail(id);
        if(n>0){
            return ResultGenerator.genSuccessResult("删除成功");
        }else{
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @RequestMapping("/update")
    public Result update(YhsjProduceDetail yhsjProduceDetail) {
        int n=yhsjProduceDetailService.update(yhsjProduceDetail);
        if(n>0){
        	return ResultGenerator.genSuccessResult("修改成功");
        }else{
            return ResultGenerator.genFailResult("修改失败");
        }
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        YhsjProduceDetail yhsjProduceDetail = yhsjProduceDetailService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjProduceDetail);
    }

    @RequestMapping("/list")
    public PageResult<Map<String,Object>> list(HttpServletRequest request,YhsjMachine machine, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer rows,String condition_str,String sidx,String sord) {
    	PageResult<Map<String,Object>> response = new PageResult<Map<String,Object>>();
    	if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	    }else {
	    	QueryPage queryPage=new QueryPage(page,rows);
	    	
	    	StringBuffer query_sql=new StringBuffer("select * from yhsj_produce_detail where 1=1");
	    	StringBuffer count_sql=new StringBuffer("select count(produce_detail_id) from yhsj_produce_detail where 1=1");
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
	        	query_sql.append(" order by produce_detail_create_time desc limit "+queryPage.getOffset()+","+queryPage.getPageSize());
	        }
	        
	        List<Map<String,Object>> list = yhsjProduceDetailService.dynamicSqlQuery(query_sql.toString());
	        
	        int count=yhsjProduceDetailService.dynamicSqlByCount(count_sql.toString());
	        
	        response=new PageResult<Map<String,Object>>(page, rows, count, list);
	    }
    	return response;
    }
}
