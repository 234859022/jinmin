package com.sanposs.project.service.impl;

import com.sanposs.project.dao.YhsjProceductTypeMapper;
import com.sanposs.project.model.YhsjProceductType;
import com.sanposs.project.service.YhsjProceductTypeService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/07.
 */
@Service
@Transactional
public class YhsjProceductTypeServiceImpl extends AbstractService<YhsjProceductType> implements YhsjProceductTypeService {
    @Resource
    private YhsjProceductTypeMapper yhsjProceductTypeMapper;

}
