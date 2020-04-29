package com.sanposs.project.web;
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
import com.sanposs.project.common.MD5;
import com.sanposs.project.constants.Constants;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.SysAdmin;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.redis.RedisManager;
import com.sanposs.project.service.SysAdminService;
import com.sanposs.project.utils.SessinoManager;

/**
* Created by CodeGenerator on 2019/05/07.
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
			SessinoManager.saveSession(loginUser);

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
		HttpSession session = request.getSession(false);
		Result result = new Result();
		if (!SessinoManager.isWebLogin(request)) {
			status = Constants.Fail;
		} else {
			String token = RedisManager.getInstance().get(Constants.getSessionCacheKey(session.getId()));

			try {
				LoginUser loginUser = SessinoManager.getLoginUser(token);
				result.setData(loginUser);
				logger.info("======取session sid:" + session.getId() + "==========");
			} catch (Exception ex) {
				logger.info("======取session ex:" + ex.getMessage() + "==========");
			}
		}
		result.setMessage(status);
		return result;
	}

}
