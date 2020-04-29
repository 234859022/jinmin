package com.nanwang.project.service.impl;

import com.nanwang.project.dao.EUserAnswerMapper;
import com.nanwang.project.model.EUserAnswer;
import com.nanwang.project.service.EUserAnswerService;
import com.nanwang.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/04/23.
 */
@Service
@Transactional
public class EUserAnswerServiceImpl extends AbstractService<EUserAnswer> implements EUserAnswerService {
    @Resource
    private EUserAnswerMapper eUserAnswerMapper;

}
