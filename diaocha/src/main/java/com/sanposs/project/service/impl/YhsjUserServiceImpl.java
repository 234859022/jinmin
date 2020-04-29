package com.sanposs.project.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanposs.project.constants.Constants;
import com.sanposs.project.core.AbstractService;
import com.sanposs.project.dao.YhsjUserMapper;
import com.sanposs.project.model.YhsjUser;
import com.sanposs.project.service.YhsjUserService;
import com.sanposs.project.utils.UUIDGenerator;
import com.sanposs.project.utils.UUIDUtil;


/**
 * Created by CodeGenerator on 2019/05/07.
 */
@Service
@Transactional
public class YhsjUserServiceImpl extends AbstractService<YhsjUser> implements YhsjUserService {
    @Resource
    private YhsjUserMapper yhsjUserMapper;

	@Override
	public String loginByUnionid(YhsjUser user) {
		YhsjUser yUser = yhsjUserMapper.findByUnionid(user);
		if (yUser != null) {
			return Constants.UserBinded;
		}

		String userId = UUIDUtil.createUUID();
		user.setUserId(userId);
		user.setUserType(1);//第一次进来，。默认普通用户，可以下单
		user.setUserCreateTime(new Date());
		int result = yhsjUserMapper.insertSelective(user);
		return Constants.Success;
	}

	@Override
	public YhsjUser findByUnionid(YhsjUser user) {
		// TODO Auto-generated method stub
		return yhsjUserMapper.findByUnionid(user);
	}

}
