package com.sanposs.project.pojo;

/**
 * Created by Administrator on 2018/12/24/024.
 */
public class TokenParam {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -7789933948814185013L;
    private  String session_key;

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    private  String openid;


}
