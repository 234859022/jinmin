package com.sanposs.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sanposs.project.core.Mapper;
import com.sanposs.project.model.YhsjProduceDetail;

public interface YhsjProduceDetailMapper extends Mapper<YhsjProduceDetail> {

	List<Map<String,Object>> dynamicSqlQuery(@Param(value="sql") String sql);
	
	int dynamicSqlByCount(@Param(value="sql") String sql);
	
	int deleteProduceDetailBatch(List<String> ids);
	
	int deleteProduceDetail(String id);
}