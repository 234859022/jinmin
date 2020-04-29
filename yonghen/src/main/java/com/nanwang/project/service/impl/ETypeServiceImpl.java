package com.nanwang.project.service.impl;

import com.nanwang.project.dao.ETypeMapper;
import com.nanwang.project.model.EType;
import com.nanwang.project.service.ETypeService;
import com.nanwang.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/04/21.
 */
@Service
@Transactional
public class ETypeServiceImpl extends AbstractService<EType> implements ETypeService {
    @Resource
    private ETypeMapper eTypeMapper;

}
