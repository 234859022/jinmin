package com.sanposs.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sanposs.project.core.Mapper;
import com.sanposs.project.model.YhsjIngredient;

public interface YhsjIngredientMapper extends Mapper<YhsjIngredient> {
	List<Map<String,Object>> dynamicSqlQuery(@Param(value="sql") String sql);
	
	int dynamicSqlByCount(@Param(value="sql") String sql);
	
	int deleteIngredientBatch(List<String> ids);
	
	int deleteIngredient(String id);
	
}