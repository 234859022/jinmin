package com.sanposs.project.service.impl;

import java.util.List;
import java.util.Map;

import com.sanposs.project.dao.YhsjIngredientMapper;
import com.sanposs.project.model.YhsjIngredient;
import com.sanposs.project.service.YhsjIngredientService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/09.
 */
@Service
@Transactional
public class YhsjIngredientServiceImpl extends AbstractService<YhsjIngredient> implements YhsjIngredientService {
    @Resource
    private YhsjIngredientMapper yhsjIngredientMapper;

	@Override
	public List<Map<String, Object>> dynamicSqlQuery(String sql) {
		// TODO Auto-generated method stub
		return yhsjIngredientMapper.dynamicSqlQuery(sql);
	}

	@Override
	public int dynamicSqlByCount(String sql) {
		// TODO Auto-generated method stub
		return yhsjIngredientMapper.dynamicSqlByCount(sql);
	}

	@Override
	public int deleteIngredient(String id) {
		// TODO Auto-generated method stub
		return yhsjIngredientMapper.deleteIngredient(id);
	}

	@Override
	public int deleteIngredientBatch(List<String> ids) {
		// TODO Auto-generated method stub
		return yhsjIngredientMapper.deleteIngredientBatch(ids);
	}
    
    

}
