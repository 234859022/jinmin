package com.sanposs.project.web.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sanposs.project.constants.Constants;
import com.sanposs.project.model.YhsjUser;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.service.YhsjUserService;
import com.sanposs.project.utils.ApiSessinoManager;
import com.vdurmont.emoji.EmojiParser;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 获取用户详细参数接口
 */
@WebServlet(name = "wechat",urlPatterns="/wechatUser")
public class WechatController  extends HttpServlet{
	
	//小程序ID
	@Value("${xcx.appID}")
	private String appID ;
	@Value("${xcx.secret}")
	private String secret;

    private static final long serialVersionUID = 1L;
    @Resource
    private YhsjUserService yhsjUserService;
	
	static {
        Security.addProvider(new BouncyCastleProvider());
    }
	public WechatController() {
        super();
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //通过code获取session
        String code = request.getParameter("code");
        String iv = request.getParameter("iv");
        String encryptedData = request.getParameter("encryptedData");
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+ appID+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code");
        //设置请求器的配置
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        JSONObject codeJson = JSON.parseObject(result);
        
        String sessionKey = codeJson.getString("session_key");
        JSONObject json = getUserInfo(encryptedData, sessionKey, iv);

        String userHeadPic = json.getString("avatarUrl");
        String nickName = EmojiParser.parseToAliases(json.getString("nickName"));//该字段为添加emoji的字段;
        //EmojiParser.parseToUnicode(nickName);//该字段为存有emoji的字段
        String openId = json.getString("openId");
        if (StringUtil.isNotEmpty(nickName) && StringUtil.isNotEmpty(openId)) {
        	YhsjUser user = new YhsjUser();
        	user.setUserWeixinUnionid(openId);//没有Unionid 用openId代替
        	user.setUserName(nickName);
        	user.setUserHeadPic(userHeadPic);
        	user.setUserAddressDetail(json.getString("province")+" "+json.getString("city"));
        	String createResult = yhsjUserService.loginByUnionid(user);
            if(Constants.UserBinded.equals(createResult)) {
                System.out.println("======已经绑定（" + openId + "）======");
            }

            LoginUser loginUser = setUserLogin(openId);
            Map<String, Object> userInfo = new HashMap<String, Object>();
            userInfo.put("userHeadPic", userHeadPic);
            userInfo.put("city", json.getString("city"));
            userInfo.put("country", json.getString("country"));
            userInfo.put("gender", json.getString("gender"));
            userInfo.put("language", json.getString("language"));
            userInfo.put("nickName",json.getString("nickName"));
            userInfo.put("province",json.getString("province"));
            userInfo.put("userType",loginUser.getUserType());

            Map<String, Object> resultInfo = new HashMap<String, Object>();
            resultInfo.put("token",loginUser.getToken());
            resultInfo.put("userInfo",userInfo);
            JSONObject jsonNew = new JSONObject(resultInfo);

            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(jsonNew.toJSONString());
        }

    }

    /**
     * 配置用户获取openid成功后保存openid，
     */
    private  LoginUser setUserLogin(String openId){
        LoginUser loginUser = new LoginUser();
        loginUser.setOpenid(openId);
        loginUser.setLoginTime(System.currentTimeMillis());

        YhsjUser user = new YhsjUser();
        user.setUserWeixinUnionid(openId);
        YhsjUser parkUser = yhsjUserService.findByUnionid(user);
        loginUser.setUserId(parkUser.getUserId());
        loginUser.setNickname(parkUser.getUserName());
        loginUser.setPhone(parkUser.getUserPhone()==null?parkUser.getUserPhone():"");
        loginUser.setHeadPic(parkUser.getUserHeadPic());
        loginUser.setUserName(parkUser.getUserRealName());
        loginUser.setPassword(parkUser.getUserPassword());
        loginUser.setUserType(parkUser.getUserType());
        loginUser = ApiSessinoManager.saveSession(loginUser);
        return  loginUser;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * 解密用户敏感数据获取用户信息
     *
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return
     * */
    public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
    	//被加密的数据
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        //加密秘钥
        byte[] keyByte = Base64.decodeBase64(sessionKey);
        //偏移量
        byte[] ivByte = Base64.decodeBase64(iv);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String StringFilter(String str) throws PatternSyntaxException {
		//去掉微信表情
		Pattern emoji = Pattern.compile(
						"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
						Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher emojiMatcher = emoji.matcher(str);
		if (emojiMatcher.find()) {
			str = "******";
		}
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}
