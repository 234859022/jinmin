package com.sanposs.project.service;
import java.util.List;
import java.util.Map;

import com.sanposs.project.model.YhsjMachineTask;
import com.sanposs.project.model.YhsjMachineTaskIngrediient;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.common.PlatformException;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.Service;


/**
 * Created by CodeGenerator on 2019/05/10.
 */
public interface YhsjMachineTaskService extends Service<YhsjMachineTask> {

	public List<Map<String,Object>> dynamicSqlQuery(String sql);
	
	public int dynamicSqlByCount(String sql);
	
	public int deleteYhsjMachineTask(String id);
	
	public int deleteYhsjMachineTaskBatch(List<String> ids);
	
	public Result createMachineTask(YhsjMachineTask machineTask,List<YhsjMachineTaskIngrediient> ingrediients,LoginUser loginUser,Integer detailState) throws PlatformException;
}
