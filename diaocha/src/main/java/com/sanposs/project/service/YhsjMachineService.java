package com.sanposs.project.service;
import java.util.List;
import java.util.Map;

import com.sanposs.project.model.YhsjMachine;
import com.sanposs.project.core.Service;


/**
 * Created by CodeGenerator on 2019/05/09.
 */
public interface YhsjMachineService extends Service<YhsjMachine> {

	public List<Map<String,Object>> dynamicSqlQuery(String sql);
	
	public int dynamicSqlByCount(String sql);
	
	public int deleteMachine(String id);
	
	public int deleteMachineBatch(List<String> ids);
}
