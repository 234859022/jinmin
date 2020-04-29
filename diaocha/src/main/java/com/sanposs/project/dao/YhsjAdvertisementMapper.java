package com.sanposs.project.dao;

import org.apache.ibatis.annotations.Param;

import com.sanposs.project.core.Mapper;
import com.sanposs.project.model.YhsjAdvertisement;

public interface YhsjAdvertisementMapper extends Mapper<YhsjAdvertisement> {
	void dynamicSqlDelete(@Param(value="sql") String sql);
}