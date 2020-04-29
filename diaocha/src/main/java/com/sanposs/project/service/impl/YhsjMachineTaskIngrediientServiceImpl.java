package com.sanposs.project.service.impl;

import java.util.List;
import java.util.Map;

import com.sanposs.project.dao.YhsjMachineTaskIngrediientMapper;
import com.sanposs.project.model.YhsjMachineTaskIngrediient;
import com.sanposs.project.service.YhsjMachineTaskIngrediientService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/10.
 */
@Service
@Transactional
public class YhsjMachineTaskIngrediientServiceImpl extends AbstractService<YhsjMachineTaskIngrediient> implements YhsjMachineTaskIngrediientService {
    @Resource
    private YhsjMachineTaskIngrediientMapper yhsjMachineTaskIngrediientMapper;

	@Override
	public List<Map<String, Object>> dynamicSqlQuery(String sql) {
		// TODO Auto-generated method stub
		return yhsjMachineTaskIngrediientMapper.dynamicSqlQuery(sql);
	}

	@Override
	public int dynamicSqlByCount(String sql) {
		// TODO Auto-generated method stub
		return yhsjMachineTaskIngrediientMapper.dynamicSqlByCount(sql);
	}

	@Override
	public int deleteYhsjMachineTaskIngrediient(String id) {
		// TODO Auto-generated method stub
		return yhsjMachineTaskIngrediientMapper.deleteYhsjMachineTaskIngrediient(id);
	}

	@Override
	public int deleteYhsjMachineTaskIngrediientBatch(List<String> ids) {
		// TODO Auto-generated method stub
		return yhsjMachineTaskIngrediientMapper.deleteYhsjMachineTaskIngrediientBatch(ids);
	}

}
