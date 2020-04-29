package com.nanwang.project.web;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanwang.project.common.MD5;
import com.nanwang.project.constants.Constants;
import com.nanwang.project.core.Result;
import com.nanwang.project.core.ResultCode;
import com.nanwang.project.core.ResultGenerator;
import com.nanwang.project.model.EUser;
import com.nanwang.project.model.SysAdmin;
import com.nanwang.project.pojo.LoginUser;
import com.nanwang.project.service.SysAdminService;
import com.nanwang.project.utils.JqgridResponse;
import com.nanwang.project.utils.MyBeanUtils;
import com.nanwang.project.utils.SessinoManager;
import com.nanwang.project.utils.UUIDGenerator;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: SysAdminController
 * @Description: TODO(描述)
 * @author chenjinmin
 * @date 2020-04-26 08:39:50
 */
@RestController
@RequestMapping("/sys/admin")
public class SysAdminController {
	private static final Logger logger = LoggerFactory.getLogger(SysAdminController.class);
    @Resource
    private SysAdminService sysAdminService;

    @RequestMapping("/add")
    public Result add(SysAdmin sysAdmin) {
        sysAdminService.save(sysAdmin);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        sysAdminService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(SysAdmin sysAdmin) {
        sysAdminService.update(sysAdmin);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        SysAdmin sysAdmin = sysAdminService.findById(id);
        return ResultGenerator.genSuccessResult(sysAdmin);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<SysAdmin> list = sysAdminService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
    @ResponseBody
    @RequestMapping(value="/changepwd")
    public Result changepwd(String oldPwd,String newPwd,HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LOGIN..");
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
		String oldpassword = MD5.MD5Encode(oldPwd);
		SysAdmin admin = sysAdminService.findBy("username", loginUser.getNickname());
		if (admin == null) {
			return ResultGenerator.genFailResult("用户已过期");
		} else if (admin.getIsDelete()==1) {
			return ResultGenerator.genFailResult("用户已删除");
		} else if (!admin.getPassword().equals(oldpassword)) {
			return ResultGenerator.genFailResult("旧密码填写错误");
		} else {
			String password = MD5.MD5Encode(newPwd);
			admin.setPassword(password);
			int r = sysAdminService.update(admin);
			if(r>0) {
				return ResultGenerator.genSuccessResult("修改成功");
			}else {
				return ResultGenerator.genSuccessResult("修改失败");
			}
		}
    }
    
    
    
    /**
    * 方法描述:登录
    * 创建人:chenjinmin 
    * 创建时间:2019年5月7日 上午9:45:23  
    * @param sysAdmin
    * @param request
    * @return      
    * Result
     */
    @ResponseBody
    @RequestMapping(value="/login")
    public Result login(SysAdmin sysAdmin, @RequestParam(defaultValue = "false") String remember,HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LOGIN..");
		logger.info("LOGIN sid:" + sysAdmin.getUsername() + "==========");
		LoginUser loginUser = new LoginUser();
		if (sysAdmin.getUsername() == null || "".equals(sysAdmin.getUsername()) || sysAdmin.getPassword() == null
				|| "".equals(sysAdmin.getPassword())) {
			return ResultGenerator.genFailResult(Constants.LoginFailErr);
		}
		String password = MD5.MD5Encode(sysAdmin.getPassword());
		SysAdmin admin = sysAdminService.findBy("username", sysAdmin.getUsername());
		if (admin == null) {
			return ResultGenerator.genFailResult(Constants.LoginFailPhoneErr);
		} else if (admin.getIsDelete()==1) {
			return ResultGenerator.genFailResult(Constants.ChangeFailUserErr2);
		} else if (!admin.getPassword().equals(password)) {
			return ResultGenerator.genFailResult(Constants.LoginFailPasswordErr);
		} else {
			loginUser.setPhone(admin.getMobilePhone());
			loginUser.setNickname(admin.getUsername());
			loginUser.setUserId(admin.getId());
			loginUser.setUserName(admin.getRealName());
			loginUser.setLoginTime(System.currentTimeMillis());
			HttpSession session = request.getSession();
			loginUser.setOpenid(session.getId());
			session.setAttribute("loginUser", loginUser);
			//SessinoManager.saveSession(loginUser);

			// 创建一个新的Cookie
			Cookie cookie = new Cookie("username", admin.getUsername() + "");
			cookie.setPath(request.getContextPath());
			if (remember.equals("true")) {
				Cookie cookieRem = new Cookie("rember", "true");
				cookieRem.setPath(request.getContextPath());
				// 如果选择了记住用户名，则设置Cookie的有效期为一个不为0的整数
				cookie.setMaxAge(Integer.MAX_VALUE);
				cookieRem.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(cookie);
				response.addCookie(cookieRem);
			} else {
				// 否则则把有效期设置为0，即命令浏览器删除该cookie
				cookie.setMaxAge(0);
			}
			return ResultGenerator.genSuccessResult(loginUser);
		}
    }
    

	@RequestMapping(value = "/sessions", method = RequestMethod.GET)
	public Result sessions(HttpServletRequest request) {
		String status = Constants.Success;
		HttpSession session = request.getSession();
		Result result = new Result();
		try {
			LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
			//LoginUser loginUser = SessinoManager.getLoginUser(token);
			result.setData(loginUser);
			logger.info("======取session sid:" + session.getId() + "==========");
		} catch (Exception ex) {
			logger.info("======取session ex:" + ex.getMessage() + "==========");
		}
		
//		if (!SessinoManager.isWebLogin(request)) {
//			status = Constants.Fail;
//		} else {
//			//String token = RedisManager.getInstance().get(Constants.getSessionCacheKey(session.getId()));
//
//			
//		}
		result.setMessage(status);
		return result;
	}
	
    
    @RequestMapping("/getSysAdminList")
	public JqgridResponse<SysAdmin> getUserList(@RequestParam String page, @RequestParam String rows,
			SysAdmin admin,HttpServletRequest request) {
		JqgridResponse<SysAdmin> response = new JqgridResponse<SysAdmin>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(SysAdmin.class);
			Example.Criteria criteria = condition.createCriteria();

			if (admin.getUsername() != null && !admin.getUsername().equals("")) {
				criteria.andCondition(" username like '%" + admin.getUsername() + "%'");
			}
			criteria.andCondition(" is_delete = 0 ");
			condition.setOrderByClause(" id desc");
			PageHelper.startPage(Integer.parseInt(page), size);
			
			List<SysAdmin> list = sysAdminService.findByCondition(condition);
			PageInfo<SysAdmin> pageInfo = new PageInfo<SysAdmin>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}
		return response;
	}
    

	
	@RequestMapping("/getSysAdminDetail")
    @ResponseBody
    public Result getSysAdminDetail(HttpServletRequest request,String id) {
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	if (id == null || id == "") {
   				return ResultGenerator.genFailResult("id为空");
   			}
			Condition condition = new Condition(SysAdmin.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" id='"+id+"'");
			List<SysAdmin> eUsers = sysAdminService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(eUsers.get(0));
		}
    }
	
	@RequestMapping("/saveSysAdmin")
   	public Result saveSysAdmin(HttpServletRequest request, SysAdmin info,String control) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			try{
   				int i=0;
   				if(info.getPassword()!=null&&!info.getPassword().equals("")) {
   					String password = MD5.MD5Encode(info.getPassword());
   					info.setPassword(password);
   				}
   				
   				if(info.getId()==null||info.getId().compareTo("")==0){
   					info.setId(UUIDGenerator.getUUID2());
   					HttpSession session = request.getSession();
					LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
					info.setCreateadminid(loginUser.getUserId());
					info.setCreateTime(new Date());
					info.setIsDelete(0);
   					int r = sysAdminService.save(info);
   					if (r>0) {
   						
					}else{
						result = ResultGenerator.genFailResult("新增失败");
					}
   				}else{
   					Condition proCondition = new Condition(SysAdmin.class);
   			        Example.Criteria proCriteria = proCondition.createCriteria();
   			        proCriteria.andCondition(" id='"+info.getId()+"'");
   			        List<SysAdmin> eUsers = sysAdminService.findByCondition(proCondition);	
   			        SysAdmin eUser = eUsers.get(0);
   					MyBeanUtils.copyBeanNotNull2Bean(info, eUser);
   					int r = sysAdminService.update(eUser);
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
	
	@RequestMapping("/deleteAdmin")
   	public Result deleteAdmin(HttpServletRequest request, String id) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (id == null || id.equals("")) {
   				return ResultGenerator.genFailResult("id为空");
   			}
   			Condition condition = new Condition(SysAdmin.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" id='"+id+"'");
   			List<SysAdmin> eUsers = sysAdminService.findByCondition(condition);
   			SysAdmin eUser = eUsers.get(0);
   		    eUser.setIsDelete(1);
   			int r = sysAdminService.update(eUser);
   			return ResultGenerator.genSuccessResult("删除成功");
   		}
   	}
	
	   
    @RequestMapping("/getAdmin")
    @ResponseBody
    public Result getAdmin(HttpServletRequest request) {
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	        Condition condition = new Condition(SysAdmin.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" is_delete =0 ");
			condition.setOrderByClause("create_time asc");
	        
	        List<SysAdmin> list = sysAdminService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(list);
	    }
    }

}
