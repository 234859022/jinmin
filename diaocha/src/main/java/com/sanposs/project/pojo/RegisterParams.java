package com.sanposs.project.pojo;

/**
 * Created by Administrator on 2018/12/19/019.
 */
public class RegisterParams {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -4789933948814185016L;

    /**
     * 密码
     */
    private String password;//用户密码

    /**
     * 手机号
     */
    private String phone; // 手机号码

    /**
     * 验证码
     */
    private String verifyCode; //验证码


    /**
     * 获取验证码
     * @return
     */
    public String getVerifyCode() {
        return verifyCode;
    }

    /**
     * 设置验证码
     * @param verifyCode
     */
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }


    /**
     * 获取手机号码
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取密码
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
