package com.sanposs.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sanposs.project.core.Mapper;
import com.sanposs.project.model.YhsjShoppingCat;

public interface YhsjShoppingCatMapper extends Mapper<YhsjShoppingCat> {
	List<Map<String,Object>> dynamicSqlQuery(@Param(value="sql") String sql);
	
	int dynamicSqlByCount(@Param(value="sql") String sql);
	
	int deleteShopCatBatch(List<String> ids);
}