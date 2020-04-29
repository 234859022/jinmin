package com.sanposs.project.service.impl;

import com.sanposs.project.dao.YhsjAdvertisementMapper;
import com.sanposs.project.model.YhsjAdvertisement;
import com.sanposs.project.service.YhsjAdvertisementService;
import com.sanposs.project.core.AbstractService;
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
