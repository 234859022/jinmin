package com.nanwang.project.service.impl;

import com.nanwang.project.core.AbstractService;
import com.nanwang.project.dao.SysAdminMapper;
import com.nanwang.project.model.SysAdmin;
import com.nanwang.project.service.SysAdminService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/07.
 */
@Service
@Transactional
public class SysAdminServiceImpl extends AbstractService<SysAdmin> implements SysAdminService {
    @Resource
    private SysAdminMapper sysAdminMapper;

}
