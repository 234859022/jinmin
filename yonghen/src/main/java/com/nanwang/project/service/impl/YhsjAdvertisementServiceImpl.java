package com.nanwang.project.service.impl;

import com.nanwang.project.core.AbstractService;
import com.nanwang.project.dao.YhsjAdvertisementMapper;
import com.nanwang.project.model.YhsjAdvertisement;
import com.nanwang.project.service.YhsjAdvertisementService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/28.
 */
@Service
@Transactional
public class YhsjAdvertisementServiceImpl extends AbstractService<YhsjAdvertisement> implements YhsjAdvertisementService {
    @Resource
    private YhsjAdvertisementMapper yhsjAdvertisementMapper;

	@Override
	public void dynamicSqlDelete(String sql) {
		yhsjAdvertisementMapper.dynamicSqlDelete(sql);
	}

}
