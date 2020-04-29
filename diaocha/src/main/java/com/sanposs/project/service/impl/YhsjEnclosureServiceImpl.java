package com.sanposs.project.service.impl;

import com.sanposs.project.dao.YhsjEnclosureMapper;
import com.sanposs.project.model.YhsjEnclosure;
import com.sanposs.project.service.YhsjEnclosureService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/08.
 */
@Service
@Transactional
public class YhsjEnclosureServiceImpl extends AbstractService<YhsjEnclosure> implements YhsjEnclosureService {
    @Resource
    private YhsjEnclosureMapper yhsjEnclosureMapper;

}
