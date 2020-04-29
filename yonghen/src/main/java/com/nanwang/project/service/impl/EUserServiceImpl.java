package com.nanwang.project.service.impl;

import com.nanwang.project.dao.EUserMapper;
import com.nanwang.project.model.EUser;
import com.nanwang.project.service.EUserService;
import com.nanwang.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/04/22.
 */
@Service
@Transactional
public class EUserServiceImpl extends AbstractService<EUser> implements EUserService {
    @Resource
    private EUserMapper eUserMapper;

}
