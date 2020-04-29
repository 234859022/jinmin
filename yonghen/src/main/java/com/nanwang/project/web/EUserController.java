package com.nanwang.project.web;
import com.nanwang.project.core.Result;
import com.nanwang.project.core.ResultCode;
import com.nanwang.project.core.ResultGenerator;
import com.nanwang.project.model.EUser;
import com.nanwang.project.pojo.LoginUser;
import com.nanwang.project.service.EUserService;
import com.nanwang.project.utils.JqgridResponse;
import com.nanwang.project.utils.MyBeanUtils;
import com.nanwang.project.utils.SessinoManager;
import com.nanwang.project.utils.UUIDGenerator;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: EUserController
 * @Description: TODO(描述)
 * @author chenjinmin
 * @date 2020-04-26 08:39:38
 */
@RestController
@RequestMapping("/e/user")
public class EUserController {
	private static final Logger logger = LoggerFactory.getLogger(EUserController.class);
    @Resource
    private EUserService eUserService;

    @RequestMapping("/add")
    public Result add(EUser eUser) {
        eUserService.save(eUser);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        eUserService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(EUser eUser) {
        eUserService.update(eUser);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        EUser eUser = eUserService.findById(id);
        return ResultGenerator.genSuccessResult(eUser);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<EUser> list = eUserService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
    @RequestMapping("/getUser")
    @ResponseBody
    public Result getUser(HttpServletRequest request) {
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	        Condition condition = new Condition(EUser.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" user_status =0 ");
			condition.setOrderByClause("user_create_time asc");
	        
	        List<EUser> list = eUserService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(list);
	    }
    }
    
    @RequestMapping("/getUserList")
	public JqgridResponse<EUser> getUserList(@RequestParam String page, @RequestParam String rows,
			EUser user,HttpServletRequest request) {
		JqgridResponse<EUser> response = new JqgridResponse<EUser>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(EUser.class);
			Example.Criteria criteria = condition.createCriteria();

			if (user.getUserName() != null && !user.getUserName().equals("")) {
				criteria.andCondition(" user_name like '%" + user.getUserName() + "%'");
			}
			criteria.andCondition(" user_status =0 ");
			condition.setOrderByClause(" user_create_time desc");
			PageHelper.startPage(Integer.parseInt(page), size);
			
			List<EUser> list = eUserService.findByCondition(condition);
			PageInfo<EUser> pageInfo = new PageInfo<EUser>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}
		return response;
	}
    

	
	@RequestMapping("/getUserDetail")
    @ResponseBody
    public Result getUserDetail(HttpServletRequest request,String user_id) {
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	if (user_id == null || user_id == "") {
   				return ResultGenerator.genFailResult("user_id为空");
   			}
			Condition condition = new Condition(EUser.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" user_id='"+user_id+"'");
			List<EUser> eUsers = eUserService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(eUsers.get(0));
		}
    }
	
	@RequestMapping("/saveUser")
   	public Result saveUser(HttpServletRequest request, EUser info,String control) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			try{
   				int i=0;
   				
   				if(info.getUserId()==null||info.getUserId().compareTo("")==0){
   					info.setUserId(UUIDGenerator.getUUID2());
   					info.setUserCreateTime(new Date());
   					info.setUserStatus(0);
   					HttpSession session = request.getSession();
					LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
					info.setUserCreateUser(loginUser.getUserId());
   					int r = eUserService.save(info);
   					if (r>0) {
   						
					}else{
						result = ResultGenerator.genFailResult("新增失败");
					}
   				}else{
   					Condition proCondition = new Condition(EUser.class);
   			        Example.Criteria proCriteria = proCondition.createCriteria();
   			        proCriteria.andCondition(" user_id='"+info.getUserId()+"'");
   			        List<EUser> eUsers = eUserService.findByCondition(proCondition);	
   			        EUser eUser = eUsers.get(0);
   					MyBeanUtils.copyBeanNotNull2Bean(info, eUser);
   					int r = eUserService.update(eUser);
   					if(r>0){
   						
   					}else{
   						result = ResultGenerator.genFailResult("修改失败");
   					}
   				}
   			}catch(Exception e){
   				e.printStackTrace();
   				result.setCode(ResultCode.FAIL);
   				result.setMessage("新增异常");
   			}
   			return result;
   		
   		}
   	}
	
	@RequestMapping("/deleteUser")
   	public Result deleteUser(HttpServletRequest request, String user_id) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (user_id == null || user_id.equals("")) {
   				return ResultGenerator.genFailResult("type_id为空");
   			}
   			Condition condition = new Condition(EUser.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" user_id='"+user_id+"'");
   			List<EUser> eUsers = eUserService.findByCondition(condition);
   		    EUser eUser = eUsers.get(0);
   		    eUser.setUserStatus(1);
   			int r = eUserService.update(eUser);
   			return ResultGenerator.genSuccessResult("删除成功");
   		}
   	}
}
