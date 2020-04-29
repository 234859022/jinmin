package com.sanposs.project.service;
import com.sanposs.project.model.YhsjDeliveryOrder;

import java.util.List;
import java.util.Map;

import com.sanposs.project.core.Service;


/**
 * Created by CodeGenerator on 2019/05/13.
 */
public interface YhsjDeliveryOrderService extends Service<YhsjDeliveryOrder> {

	List<Map<String,Object>> dynamicSqlQuery(String sql);
	
	int dynamicSqlByCount(String sql);

}
