package com.sanposs.project.service.impl;

import com.sanposs.project.dao.YhsjDeliveryOrderMapper;
import com.sanposs.project.model.YhsjDeliveryOrder;
import com.sanposs.project.service.YhsjDeliveryOrderService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/13.
 */
@Service
@Transactional
public class YhsjDeliveryOrderServiceImpl extends AbstractService<YhsjDeliveryOrder> implements YhsjDeliveryOrderService {
    @Resource
    private YhsjDeliveryOrderMapper yhsjDeliveryOrderMapper;
    
    @Override
	public List<Map<String, Object>> dynamicSqlQuery(String sql) {
		return yhsjDeliveryOrderMapper.dynamicSqlQuery(sql);
	}

	@Override
	public int dynamicSqlByCount(String sql) {
		return yhsjDeliveryOrderMapper.dynamicSqlByCount(sql);
	}

}
