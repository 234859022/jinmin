package com.nanwang.project.service.impl;

import com.nanwang.project.dao.EAnswerMapper;
import com.nanwang.project.model.EAnswer;
import com.nanwang.project.service.EAnswerService;
import com.nanwang.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020/04/22.
 */
@Service
@Transactional
public class EAnswerServiceImpl extends AbstractService<EAnswer> implements EAnswerService {
    @Resource
    private EAnswerMapper eAnswerMapper;
    @Override
	public List<Map<String, Object>> dynamicSqlQuery(String sql) {
		// TODO Auto-generated method stub
		return eAnswerMapper.dynamicSqlQuery(sql);
	}

	@Override
	public int dynamicSqlByCount(String sql) {
		// TODO Auto-generated method stub
		return eAnswerMapper.dynamicSqlByCount(sql);
	}
}
