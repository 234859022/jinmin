package com.sanposs.project.service.impl;

import com.sanposs.project.dao.SysConfigMapper;
import com.sanposs.project.model.SysConfig;
import com.sanposs.project.service.SysConfigService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/08.
 */
@Service
@Transactional
public class SysConfigServiceImpl extends AbstractService<SysConfig> implements SysConfigService {
    @Resource
    private SysConfigMapper sysConfigMapper;

}
