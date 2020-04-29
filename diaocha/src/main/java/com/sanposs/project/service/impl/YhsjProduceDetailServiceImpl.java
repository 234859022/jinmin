package com.sanposs.project.service.impl;

import java.util.List;
import java.util.Map;

import com.sanposs.project.dao.YhsjProduceDetailMapper;
import com.sanposs.project.model.YhsjProduceDetail;
import com.sanposs.project.service.YhsjProduceDetailService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/06/17.
 */
@Service
@Transactional
public class YhsjProduceDetailServiceImpl extends AbstractService<YhsjProduceDetail> implements YhsjProduceDetailService {
    @Resource
    private YhsjProduceDetailMapper yhsjProduceDetailMapper;

	@Override
	public List<Map<String, Object>> dynamicSqlQuery(String sql) {
		// TODO Auto-generated method stub
		return yhsjProduceDetailMapper.dynamicSqlQuery(sql);
	}

	@Override
	public int dynamicSqlByCount(String sql) {
		// TODO Auto-generated method stub
		return yhsjProduceDetailMapper.dynamicSqlByCount(sql);
	}

	@Override
	public int deleteProduceDetail(String id) {
		// TODO Auto-generated method stub
		return yhsjProduceDetailMapper.deleteProduceDetail(id);
	}

	@Override
	public int deleteProduceDetailBatch(List<String> ids) {
		// TODO Auto-generated method stub
		return yhsjProduceDetailMapper.deleteProduceDetailBatch(ids);
	}

}
