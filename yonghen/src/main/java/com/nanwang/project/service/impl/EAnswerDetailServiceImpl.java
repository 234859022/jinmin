package com.nanwang.project.service.impl;

import com.nanwang.project.dao.EAnswerDetailMapper;
import com.nanwang.project.model.EAnswerDetail;
import com.nanwang.project.service.EAnswerDetailService;
import com.nanwang.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/04/22.
 */
@Service
@Transactional
public class EAnswerDetailServiceImpl extends AbstractService<EAnswerDetail> implements EAnswerDetailService {
    @Resource
    private EAnswerDetailMapper eAnswerDetailMapper;

}
