package com.sanposs.project.service;
import com.sanposs.project.model.YhsjShoppingCat;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.pojo.YhsjShoppingCatExpand;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sanposs.project.common.PlatformException;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.Service;


/**
 * Created by CodeGenerator on 2019/05/13.
 */
public interface YhsjShoppingCatService extends Service<YhsjShoppingCat> {

	Result addShoppingCat(List<YhsjShoppingCat> shoppingCatList, LoginUser user) throws PlatformException;

	List<Map<String,Object>> dynamicSqlQuery(String sql);
	
	int dynamicSqlByCount(String sql);
	
	Result balancePurchase(List<YhsjShoppingCatExpand> shoppingCatList, LoginUser user,String remark) throws PlatformException;
	
	int deleteShopCatBatch(List<String> ids);
}
