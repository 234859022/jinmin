package com.sanposs.project.service.impl;

import java.util.List;
import java.util.Map;

import com.sanposs.project.dao.YhsjMachineMapper;
import com.sanposs.project.model.YhsjMachine;
import com.sanposs.project.service.YhsjMachineService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/09.
 */
@Service
@Transactional
public class YhsjMachineServiceImpl extends AbstractService<YhsjMachine> implements YhsjMachineService {
    @Resource
    private YhsjMachineMapper yhsjMachineMapper;

	@Override
	public List<Map<String, Object>> dynamicSqlQuery(String sql) {
		// TODO Auto-generated method stub
		return yhsjMachineMapper.dynamicSqlQuery(sql);
	}

	@Override
	public int dynamicSqlByCount(String sql) {
		// TODO Auto-generated method stub
		return yhsjMachineMapper.dynamicSqlByCount(sql);
	}

	@Override
	public int deleteMachine(String id) {
		// TODO Auto-generated method stub
		return yhsjMachineMapper.deleteMachine(id);
	}

	@Override
	public int deleteMachineBatch(List<String> ids) {
		// TODO Auto-generated method stub
		return yhsjMachineMapper.deleteMachineBatch(ids);
	}

}
