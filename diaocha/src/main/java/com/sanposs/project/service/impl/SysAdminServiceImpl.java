package com.sanposs.project.service.impl;

import com.sanposs.project.dao.SysAdminMapper;
import com.sanposs.project.model.SysAdmin;
import com.sanposs.project.service.SysAdminService;
import com.sanposs.project.core.AbstractService;
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
