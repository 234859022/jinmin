package com.nanwang.project.service.impl;

import com.nanwang.project.dao.EQuestionMapper;
import com.nanwang.project.model.EQuestion;
import com.nanwang.project.service.EQuestionService;
import com.nanwang.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/04/21.
 */
@Service
@Transactional
public class EQuestionServiceImpl extends AbstractService<EQuestion> implements EQuestionService {
    @Resource
    private EQuestionMapper eQuestionMapper;

}
