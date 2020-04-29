package com.sanposs.project.service.impl;

import com.sanposs.project.dao.YhsjProductTpMapper;
import com.sanposs.project.model.YhsjProductTp;
import com.sanposs.project.service.YhsjProductTpService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/08.
 */
@Service
@Transactional
public class YhsjProductTpServiceImpl extends AbstractService<YhsjProductTp> implements YhsjProductTpService {
    @Resource
    private YhsjProductTpMapper yhsjProductTpMapper;

}
