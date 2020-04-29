package com.nanwang.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nanwang.project.core.Mapper;
import com.nanwang.project.model.EAnswer;

public interface EAnswerMapper extends Mapper<EAnswer> {
	List<Map<String,Object>> dynamicSqlQuery(@Param(value="sql") String sql);
	int dynamicSqlByCount(@Param(value="sql") String sql);
}