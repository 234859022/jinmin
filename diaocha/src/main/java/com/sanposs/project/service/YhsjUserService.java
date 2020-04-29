package com.sanposs.project.service;
import com.sanposs.project.core.Service;
import com.sanposs.project.model.YhsjUser;


/**
 * Created by CodeGenerator on 2019/05/07.
 */
public interface YhsjUserService extends Service<YhsjUser> {
    /**
     * 绑定微信后创建用户
     * @param nickName
     * @param avater
     * @return
     */
    String loginByUnionid(YhsjUser user);
    
    /**
     * 根据wechat uniodID查找用户
     * @param parkUser
     * @return
     */
    YhsjUser findByUnionid(YhsjUser user);

}
