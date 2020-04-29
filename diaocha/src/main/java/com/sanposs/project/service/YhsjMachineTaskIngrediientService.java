package com.sanposs.project.service;
import java.util.List;
import java.util.Map;

import com.sanposs.project.model.YhsjMachineTaskIngrediient;
import com.sanposs.project.core.Service;


/**
 * Created by CodeGenerator on 2019/05/10.
 */
public interface YhsjMachineTaskIngrediientService extends Service<YhsjMachineTaskIngrediient> {

	public List<Map<String,Object>> dynamicSqlQuery(String sql);
	
	public int dynamicSqlByCount(String sql);
	
	public int deleteYhsjMachineTaskIngrediient(String id);
	
	public int deleteYhsjMachineTaskIngrediientBatch(List<String> ids);
}
