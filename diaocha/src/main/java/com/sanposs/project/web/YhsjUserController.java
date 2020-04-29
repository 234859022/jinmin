package com.sanposs.project.web;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.SysConfig;
import com.sanposs.project.model.YhsjProceductType;
import com.sanposs.project.model.YhsjProduct;
import com.sanposs.project.model.YhsjUser;
import com.sanposs.project.service.YhsjUserService;
import com.sanposs.project.utils.CommonUtil;
import com.sanposs.project.utils.JqgridResponse;
import com.sanposs.project.utils.MyBeanUtils;
import com.sanposs.project.utils.OSSUnit;
import com.sanposs.project.utils.SessinoManager;
import com.sanposs.project.utils.UUIDGenerator;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
* Created by CodeGenerator on 2019/05/07.
*/
@RestController
@RequestMapping("/yhsj/user")
public class YhsjUserController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjUserController.class);
    @Resource
    private YhsjUserService yhsjUserService;

    @RequestMapping("/add")
    public Result add(YhsjUser yhsjUser) {
        yhsjUserService.save(yhsjUser);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjUserService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjUser yhsjUser) {
        yhsjUserService.update(yhsjUser);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjUser yhsjUser = yhsjUserService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjUser);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjUser> list = yhsjUserService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
    /**
	 * 方法描述:测试 用户列表信息 创建人:chenjinmin 创建时间:2019年4月12日 下午2:29:32
	 * 
	 * @param page
	 * @param rows
	 * @param startDate
	 * @param endDate
	 * @param parkName
	 * @param lockCode
	 * @param errType
	 * @param request
	 * @return JqgridResponse<UserInfoTb>
	 */
	@RequestMapping("/getUser")
	public JqgridResponse<YhsjUser> getUser(@RequestParam String page, @RequestParam String rows,
			@RequestParam(defaultValue = "") String user_name, @RequestParam(defaultValue = "") String user_phone,
			HttpServletRequest request) {
		JqgridResponse<YhsjUser> response = new JqgridResponse<YhsjUser>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		String moneyLine = "";

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(YhsjUser.class);
			Example.Criteria criteria = condition.createCriteria();

			if (!user_name.equals("")) {
				criteria.andCondition(" user_name like'%" + user_name + "%'");
			}
			if (!user_phone.equals("")) {
				criteria.andCondition(" user_phone like'%" + user_phone + "%'");
			}
			PageHelper.startPage(Integer.parseInt(page), size);

			condition.setOrderByClause(" user_create_time desc");
			List<YhsjUser> list = yhsjUserService.findByCondition(condition);
			PageInfo<YhsjUser> pageInfo = new PageInfo<YhsjUser>(list);

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
			Condition condition = new Condition(YhsjProduct.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" user_id='"+user_id+"'");
			List<YhsjUser> user = yhsjUserService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(user.get(0));
		}
    }
	
	@RequestMapping("/saveUser")
   	public Result saveUser(HttpServletRequest request, YhsjUser info) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			try{
   				if(info.getUserId()==null||info.getUserId().compareTo("")==0){
   					info.setUserId(UUIDGenerator.getUUID2());
   					info.setUserType(1);
   					info.setUserCreateTime(new Date());
   					int r = yhsjUserService.save(info);
   					if (r>0) {
   						
					}else{
						result = ResultGenerator.genFailResult("新增失败");
					}
   				}else{
   					Condition proCondition = new Condition(YhsjProceductType.class);
   			        Example.Criteria proCriteria = proCondition.createCriteria();
   			        proCriteria.andCondition(" user_id='"+info.getUserId()+"'");
   			        List<YhsjUser> userList = yhsjUserService.findByCondition(proCondition);	
   			        YhsjUser user = userList.get(0);
   					MyBeanUtils.copyBeanNotNull2Bean(info, user);
   					int r = yhsjUserService.update(user);
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

}
