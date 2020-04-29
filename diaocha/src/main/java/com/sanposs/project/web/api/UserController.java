package com.sanposs.project.web.api;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjUser;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.service.YhsjUserService;
import com.sanposs.project.utils.ApiSessinoManager;
import com.sanposs.project.utils.MyBeanUtils;

/**
* 类名称: UserController.java   
* 功能描述:用户接口相关
* 创建者: chenjinmin 
* 创建时间:  2019年5月11日 下午2:01:32    
* 版本信息: V1.0
* CopyRight(C)：sanposs 2019 泉州市尚宝网络科技有限公司版权所有
 */
@RestController
@RequestMapping("userApi")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private YhsjUserService yhsjUserService;

   
    /**
    * 方法描述:根据TOKEN获取用户信息
    * 创建人:chenjinmin 
    * 创建时间:2019年5月11日 下午2:01:53  
    * @param token
    * @return      
    * Result
     */
    @RequestMapping("/getUser")
    public Result getUser(String token) {
    	logger.info("开始根据TOKEN获取用户信息");
    	LoginUser user = null;
      	if(token!=null&&!"".equals(token)){
			user = ApiSessinoManager.getLoginUser(token);
		}
      	if(user==null){
			return ResultGenerator.genFailResult("token出错");
		}
      	YhsjUser yhsjUser = yhsjUserService.findById(user.getUserId());
      	if(yhsjUser==null){
      		return ResultGenerator.genFailResult("找不到token为"+token+"的记录");
      	}
		Result result = ResultGenerator.genSuccessResult();
		result.setData(yhsjUser);
        logger.info("结束根据TOKEN获取用户信息");
        return result;
    }
    
    /**
    * 方法描述:修改用户信息
    * 创建人:chenjinmin 
    * 创建时间:2019年5月11日 下午2:07:01  
    * @param token
    * @param info
    * @return
    * @throws Exception      
    * Result
     */
    @RequestMapping("/saveUser")
    public Result saveUser(String token,YhsjUser info) throws Exception {
    	logger.info("开始修改用户信息");
    	LoginUser user = null;
      	if(token!=null&&!"".equals(token)){
			user = ApiSessinoManager.getLoginUser(token);
		}
      	if(user==null){
			return ResultGenerator.genFailResult("token出错");
		}
      	YhsjUser yhsjUser = yhsjUserService.findById(user.getUserId());
      	if(yhsjUser==null){
      		return ResultGenerator.genFailResult("找不到token为"+token+"的记录");
      	}
      	MyBeanUtils.copyBeanNotNull2Bean(info, yhsjUser);
      	int r = yhsjUserService.update(yhsjUser);
      	Result result = ResultGenerator.genSuccessResult();
      	if(r>0){
    		result.setData(yhsjUser);	
		}else{
			return ResultGenerator.genFailResult("修改失败");
		}
        logger.info("结束修改用户信息");
        return result;
    }

}
