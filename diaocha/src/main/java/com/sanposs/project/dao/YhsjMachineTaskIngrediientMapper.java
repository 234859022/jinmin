package com.sanposs.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sanposs.project.core.Mapper;
import com.sanposs.project.model.YhsjMachineTaskIngrediient;

public interface YhsjMachineTaskIngrediientMapper extends Mapper<YhsjMachineTaskIngrediient> {
	List<Map<String,Object>> dynamicSqlQuery(@Param(value="sql") String sql);
	
	int dynamicSqlByCount(@Param(value="sql") String sql);
	
	int deleteYhsjMachineTaskIngrediientBatch(List<String> ids);
	
	int deleteYhsjMachineTaskIngrediient(String id);
}