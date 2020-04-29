package com.sanposs.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sanposs.project.core.Mapper;
import com.sanposs.project.model.YhsjMachine;

public interface YhsjMachineMapper extends Mapper<YhsjMachine> {
	List<Map<String,Object>> dynamicSqlQuery(@Param(value="sql") String sql);
	
	int dynamicSqlByCount(@Param(value="sql") String sql);
	
	int deleteMachineBatch(List<String> ids);
	
	int deleteMachine(String id);
}