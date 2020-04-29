package com.nanwang.project.dao;

import org.apache.ibatis.annotations.Param;

import com.nanwang.project.core.Mapper;
import com.nanwang.project.model.YhsjAdvertisement;

public interface YhsjAdvertisementMapper extends Mapper<YhsjAdvertisement> {
	int dynamicSqlDelete(@Param(value="sql") String sql);
}