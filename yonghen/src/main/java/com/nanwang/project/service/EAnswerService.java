package com.nanwang.project.service;
import com.nanwang.project.model.EAnswer;

import java.util.List;
import java.util.Map;

import com.nanwang.project.core.Service;


/**
 * Created by CodeGenerator on 2020/04/22.
 */
public interface EAnswerService extends Service<EAnswer> {
    List<Map<String,Object>> dynamicSqlQuery(String sql);
    
	int dynamicSqlByCount(String sql);

}
