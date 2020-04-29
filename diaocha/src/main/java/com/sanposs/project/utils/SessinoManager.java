package com.sanposs.project.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sanposs.project.constants.Constants;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.redis.RedisManager;
import com.sanposs.project.utils.rsa.RsaUtils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/12/20/020.
 */
public class SessinoManager {

	private static final Logger logger = LoggerFactory.getLogger(SessinoManager.class);
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
        try{

            String userDTOJson = RsaUtils.rsaDecrypt(token,
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
            String tokenStr = RsaUtils.rsaEncrypt(GsonUtils.getJsonStr(loginUser),
                    Constants.RSA_PUBLIC_KEY, Constants.DEFAULT_CHARTSET);

            String cacheKey = buildCacheKey(loginUser);
            System.out.println(DateUtils.parseToDateStr(new Date()) + "保存的缓存对象(" + loginUser.getOpenid() + ")：" + cacheKey +",tokenStr:\n" + tokenStr);
            logger.debug("开始进行token缓存");
            RedisManager.getInstance().set(cacheKey, tokenStr);
            //RedisManager.getInstance().expire(cacheKey,Constants.expireSeconds);
            logger.debug("token缓存成功");
            loginUser.setToken(tokenStr);
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
}
