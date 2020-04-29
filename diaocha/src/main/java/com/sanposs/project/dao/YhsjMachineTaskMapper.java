package com.sanposs.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sanposs.project.core.Mapper;
import com.sanposs.project.model.YhsjMachineTask;

public interface YhsjMachineTaskMapper extends Mapper<YhsjMachineTask> {
	
	List<Map<String,Object>> dynamicSqlQuery(@Param(value="sql") String sql);
	
	int dynamicSqlByCount(@Param(value="sql") String sql);
	
	int deleteYhsjMachineTaskBatch(List<String> ids);
	
	int deleteYhsjMachineTask(String id);
}