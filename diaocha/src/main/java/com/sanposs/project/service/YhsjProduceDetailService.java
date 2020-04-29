package com.sanposs.project.service;
import java.util.List;
import java.util.Map;

import com.sanposs.project.model.YhsjProduceDetail;
import com.sanposs.project.core.Service;


/**
 * Created by CodeGenerator on 2019/06/17.
 */
public interface YhsjProduceDetailService extends Service<YhsjProduceDetail> {

	public List<Map<String,Object>> dynamicSqlQuery(String sql);
	
	public int dynamicSqlByCount(String sql);
	
	public int deleteProduceDetail(String id);
	
	public int deleteProduceDetailBatch(List<String> ids);
}
