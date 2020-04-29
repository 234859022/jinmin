package com.sanposs.project.dao;

import com.sanposs.project.core.Mapper;
import com.sanposs.project.model.YhsjUser;

public interface YhsjUserMapper extends Mapper<YhsjUser> {
    
    YhsjUser findByUserid(YhsjUser user);

    YhsjUser findByPhone(YhsjUser user);

    YhsjUser findByUnionid(YhsjUser user);

    int changeNickName(YhsjUser user);

    int changeMobile(YhsjUser user);

}