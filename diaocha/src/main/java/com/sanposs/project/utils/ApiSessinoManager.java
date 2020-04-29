package com.sanposs.project.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import com.sanposs.project.constants.Constants;
import com.sanposs.project.model.SysConfig;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.redis.RedisManager;
import com.sanposs.project.service.SysConfigService;
import com.sanposs.project.utils.rsa.RsaUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/12/20/020.
 */
@Component
public class ApiSessinoManager {

	private static final Logger logger = LoggerFactory.getLogger(ApiSessinoManager.class);
	
	@Autowired
	private static SysConfigService configService;
	
	@Autowired
    public ApiSessinoManager(SysConfigService configService) {
		ApiSessinoManager.configService = configService;
    }
	
    /**
     * 判断是否已经登录
     * @param params 参数对象
     * @return       是否已经登录 true:是否 false 否
     */
    public static boolean isLogined(BaseParams params){
        if(null == params || StringUtils.isEmpty(params.getToken())){
            return false;
        }
        boolean authed = hasAuthed(params.getToken());
        if(!authed){
            System.out.println("有可能不存在的key的验证：" + GsonUtils.getJsonStr(params));
        }
        return authed;
    }

    public   static boolean isWebLogin(HttpServletRequest request){
        boolean authed = false;
        HttpSession session = request.getSession(false);
        if( null == session || !request.isRequestedSessionIdValid()){
            authed = false;
        }
        else {
            authed = true;
        }
        return authed;
    }
    /**
     * 通过Token获取到用户登录对象
     * @param token token
     * @return      用户标识
     */
    public static LoginUser getLoginUser(String token){
        if(StringUtils.isEmpty(token) || token.equals("null")||token == null){
            return null;
        }
        String userStr = RedisManager.getInstance().get(token);
        if(userStr==null){
        	return null;
        }
        try{
            String userDTOJson = RsaUtils.rsaDecrypt(userStr,
                    Constants.RSA_PRIVATE_KEY, Constants.DEFAULT_CHARTSET);
            LoginUser loginUser = GsonUtils.jsonToBean(userDTOJson, LoginUser.class);
            if(null == loginUser){
                return null;
            }
            return loginUser;
        }catch(Exception e){
            logger.debug("-------------------tokenError:"+token);
        	e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否已经验证通过
     * @param token     token对象
     * @return          是否已经登录
     */
    public static boolean hasAuthed(String token){
        try {
            LoginUser loginUser = getLoginUser(token);
            if(null == loginUser){
                return false;
            }

            // 没有用户id，表示没有登录，即没有绑定
            if(loginUser.getUserId()==null){
                return false;
            }

            String cacheKey = buildCacheKey(loginUser);

            if(null != RedisManager.getInstance().get(cacheKey)){
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String buildCacheKey(LoginUser loginUser){
        String key = "";
        if(StringUtils.isNotEmpty(loginUser.getOpenid())){
            key += loginUser.getOpenid();
        }

        String cacheKey =  Constants.getSessionCacheKey(key);
        return cacheKey;
    }

    /**
     * 保存Session对象
     */
    public static LoginUser saveSession(LoginUser loginUser){
        try {

            // 设置登录的时间，确保每次产生的加密的串是不一样的
            loginUser.setLoginTime(System.currentTimeMillis());
            String userStr = RsaUtils.rsaEncrypt(GsonUtils.getJsonStr(loginUser),
                    Constants.RSA_PUBLIC_KEY, Constants.DEFAULT_CHARTSET);

            String token = buildCacheKey(loginUser);
            System.out.println("API登录 "+DateUtils.parseToDateStr(new Date()) + "保存的缓存对象(" + loginUser.getOpenid() + ")token：" + token +",userStr:\n" + userStr);
            logger.debug("开始进行token缓存  token：" + token);
            RedisManager.getInstance().set(token, userStr);
            //RedisManager.getInstance().expire(cacheKey,Constants.expireSeconds);
            logger.debug("token缓存成功");
            loginUser.setToken(token);
            loginUser.setPassword(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginUser;
    }


    /**
     * 删除session
     * @param token 登录的token
     * @return      是否删除成功 true:是 false：否
     */
    public static boolean removeSession(String token){
        try {

            LoginUser loginUserDTO = getLoginUser(token);

            String cacheKey = buildCacheKey(loginUserDTO);

            // cacheKey有存在，这直接删除session
            if(StringUtils.isNotEmpty(cacheKey)){
                logger.info("---------removeSession:" +token+ "---------");
                RedisManager.getInstance().delObject(cacheKey);
            }


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static String[] IPs ;
	public  static void setOriginHeader(HttpServletRequest request,HttpServletResponse response){
		String originHeader = request.getHeader("Origin");
//		String[] IPs = {"http://192.168.3.137:8080","http://192.168.3.119:8080"};
		if (IPs==null||IPs.length < 0) {
			Condition condition=new Condition(SysConfig.class);
			Example.Criteria criteria=condition.createCriteria();
			criteria.andCondition("config_name ='Origin'");
			List<SysConfig> list = configService.findByCondition(condition);
			if (list != null && list.size() > 0) {
				IPs = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					SysConfig systemmgConfig =list.get(i);
					IPs[i] = systemmgConfig.getConfigValue();
				}
			}
		}
		
	    if (Arrays.asList(IPs).contains(originHeader))
	    {
	        response.setHeader("Access-Control-Allow-Origin", originHeader);
	    }
	}
}
