package com.sanposs.project.service;
import java.util.List;
import java.util.Map;

import com.sanposs.project.model.YhsjIngredient;
import com.sanposs.project.core.Service;


/**
 * Created by CodeGenerator on 2019/05/09.
 */
public interface YhsjIngredientService extends Service<YhsjIngredient> {
	public List<Map<String,Object>> dynamicSqlQuery(String sql);
	
	public int dynamicSqlByCount(String sql);
	
	public int deleteIngredient(String id);
	
	public int deleteIngredientBatch(List<String> ids);
}
