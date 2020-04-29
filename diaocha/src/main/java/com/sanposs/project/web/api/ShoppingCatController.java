package com.sanposs.project.web.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanposs.project.common.PlatformException;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjShoppingCat;
import com.sanposs.project.pojo.BaseDto;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.pojo.QueryPage;
import com.sanposs.project.pojo.YhsjShoppingCatExpand;
import com.sanposs.project.service.YhsjShoppingCatService;
import com.sanposs.project.utils.ApiSessinoManager;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("shoppingCatApi")
public class ShoppingCatController {
	private static final Logger logger = LoggerFactory.getLogger(ShoppingCatController.class);
    @Resource
    private YhsjShoppingCatService yhsjShoppingCatService;
	
    /**
     * 方法描述:获取购物车商品
     * @throws Exception 
    */
    @RequestMapping("/getShoppingCatList")
    public Result getShoppingCatList(YhsjShoppingCat info,BaseDto baseDto,String token,HttpServletRequest request, HttpServletResponse response){
    	logger.info("开始查询购物车列表");
		Result result= ResultGenerator.genSuccessResult();
    	LoginUser user = null;
      	if(token!=null&&!"".equals(token)){
			user = ApiSessinoManager.getLoginUser(token);
		}
      	if(user==null){
			return ResultGenerator.genFailResult("token出错");
		}
    	QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());
        try{
        	StringBuffer sbf_count=new StringBuffer();
        	sbf_count.append("SELECT count(yhsj_shopping_cat.shopping_cat_id) FROM yhsj_shopping_cat  ");
        	sbf_count.append("LEFT JOIN yhsj_product ON yhsj_shopping_cat.pro_id=yhsj_product.pro_id ");
        	sbf_count.append("LEFT JOIN yhsj_product_tp ON yhsj_shopping_cat.scale_id=yhsj_product_tp.tp_id "); 
        	sbf_count.append("LEFT JOIN yhsj_produce_detail ON yhsj_produce_detail.produce_detail_id=yhsj_shopping_cat.produce_detail_id ");  
        	sbf_count.append(" where shopping_cat_create_user_id='"+user.getUserId()+"'");
    		
        	
        	StringBuffer sbf_select=new StringBuffer();
        	sbf_select.append("SELECT yhsj_shopping_cat.*,yhsj_produce_detail.produce_detail_user_name,yhsj_produce_detail.produce_detail_color_type,yhsj_produce_detail.produce_detail_machine_name,yhsj_produce_detail.produce_detail_spec_name,yhsj_product.pro_name,yhsj_product.pro_min_img_url,yhsj_product.pro_summary,yhsj_product.pro_price pro_init_price,yhsj_product.pro_sale_price pro_sale_price, IFNULL((SELECT yhsj_product_tp.tp_num FROM yhsj_product_tp WHERE yhsj_product_tp.tp_id=yhsj_shopping_cat.scale_id),IFNULL(yhsj_product.pro_num,0)) spec_num, IFNULL((SELECT yhsj_product_tp.tp_weight FROM yhsj_product_tp WHERE yhsj_product_tp.tp_id=yhsj_shopping_cat.scale_id),IFNULL(yhsj_product.pro_weight,0)) pro_weight FROM yhsj_shopping_cat  ");
        	sbf_select.append("LEFT JOIN yhsj_product ON yhsj_shopping_cat.pro_id=yhsj_product.pro_id ");
        	sbf_select.append("LEFT JOIN yhsj_product_tp ON yhsj_shopping_cat.scale_id=yhsj_product_tp.tp_id ");
        	sbf_select.append("LEFT JOIN yhsj_produce_detail ON yhsj_produce_detail.produce_detail_id=yhsj_shopping_cat.produce_detail_id ");  

        	if(info!=null){
    			if(info.getShoppingCatState()!=null){
    	        	sbf_count.append(" and shopping_cat_state="+info.getShoppingCatState());
    	        	sbf_select.append(" and shopping_cat_state="+info.getShoppingCatState());
    			}
    		}
        	
        	sbf_select.append(" order by shopping_cat_create_time desc limit "+page.getOffset()+","+page.getPageSize());

        	int iTotal = yhsjShoppingCatService.dynamicSqlByCount(sbf_count.toString());
        	if(iTotal>0){
        		List<Map<String,Object>> mapList = yhsjShoppingCatService.dynamicSqlQuery(sbf_select.toString());
            	result.setItems(mapList);
            	result.setTotalCount(iTotal);
        	}else{
        		result.setItems(new ArrayList<Map<String,Object>>());
            	result.setTotalCount(iTotal);
        	}
        	result.setMessage("操作成功");
        	result.setCode(ResultCode.SUCCESS);
        } catch(Exception ex) {
        	logger.error("获取数据列表出现异常:"+ ex.getMessage(), ex);
        	result.setMessage("操作失败");
        	result.setCode(ResultCode.FAIL);
        	return result;
        }
		logger.info("结束------------购物查询----------------------");
		
		return result;
    }
    
    /**
     * 方法描述:新增购物车商品
     * @throws Exception 
    */
    @RequestMapping("/addShoppingCat")
    public Result addShoppingCat(String catDtoStr,String token,HttpServletRequest request, HttpServletResponse response) throws Exception{
    	logger.info("开始新增购物车商品");
		Result result= ResultGenerator.genSuccessResult();
    	LoginUser user = null;
		if(token!=null&&!"".equals(token)){
			user = ApiSessinoManager.getLoginUser(token);
		}
      	if(user==null){
			return ResultGenerator.genFailResult("token出错");
		}
		List<YhsjShoppingCat> shoppingCatList=new ArrayList<YhsjShoppingCat>();
		if(catDtoStr!=null&&catDtoStr.compareTo("")>0){
			ObjectMapper mapper = new ObjectMapper();
			shoppingCatList=mapper.readValue(catDtoStr,new TypeReference<List<YhsjShoppingCat>>() { });	
		}
        try{
        	yhsjShoppingCatService.addShoppingCat(shoppingCatList,user);
        } catch(Exception ex) {
        	logger.error("获取数据列表出现异常:"+ ex.getMessage(), ex);
        	result.setMessage("操作失败");
        	result.setCode(ResultCode.FAIL);
        	return result;
        }
        logger.info("结束新增购物车商品");
		
		return result;
	}
    
    
    /**
     * 方法描述:结算购物车商品
     * @throws Exception 
    */
    @RequestMapping("/balancePurchase")
    public Result balancePurchase(String catDtoStr,String token,String remark,HttpServletRequest request, HttpServletResponse response) throws Exception{
    	logger.info("开始新增购物车商品");
		Result result= ResultGenerator.genSuccessResult();
		if(token==null||"".equals(token)){
			result.setCode(ResultCode.VERIFY_ERROR);
			result.setMessage("token不能为空");
			return result;
		}
		LoginUser user =ApiSessinoManager.getLoginUser(token);
      	if(user==null){
      		result.setCode(ResultCode.VERIFY_ERROR);
			result.setMessage("token不存在");
			return result;
		}
      	
		List<YhsjShoppingCatExpand> shoppingCatList=new ArrayList<YhsjShoppingCatExpand>();
		if(catDtoStr!=null&&catDtoStr.compareTo("")>0){
			ObjectMapper mapper = new ObjectMapper();
			shoppingCatList=mapper.readValue(catDtoStr,new TypeReference<List<YhsjShoppingCatExpand>>() { });	
		}
		if(shoppingCatList==null||shoppingCatList.size()==0){
			result.setCode(ResultCode.VERIFY_ERROR);
			result.setMessage("没有结算的商品");
			return result;
		}
        try{
        	result=yhsjShoppingCatService.balancePurchase(shoppingCatList, user, remark);
        } catch(Exception ex) {
        	logger.error("结算出现异常:"+ ex.getMessage(), ex);
        	if(ex instanceof PlatformException){
        		PlatformException pl=(PlatformException)ex;
        		result.setCode(pl.getExceptionCode());
        		result.setMessage(ex.getLocalizedMessage());
        	}else{
            	result.setCode(ResultCode.FAIL);
            	result.setMessage(ex.getLocalizedMessage());
        	}
        	return result;
        }
        logger.info("结束结算");
		return result;
	}
    
    /**
     * 方法描述:获取购物车商品
     * @throws Exception 
    */
    @RequestMapping("/deleteShoppingCat")
    public Result deleteShoppingCat(String token,String cat_shop_ids,HttpServletRequest request, HttpServletResponse response) throws Exception{
    	logger.info("开始新增购物车商品");
    	Result result= ResultGenerator.genSuccessResult();
		LoginUser user = null;
		if(token!=null&&!"".equals(token)){
			user = ApiSessinoManager.getLoginUser(token);
		}
      	if(user==null){
			return ResultGenerator.genFailResult("token出错");
		}
		
		if(cat_shop_ids==null||cat_shop_ids.compareTo("")==0){
			result.setCode(ResultCode.FAIL);
			result.setMessage("商品id不存在");
		}
		
        try{
        	int n=yhsjShoppingCatService.deleteShopCatBatch(Arrays.asList(cat_shop_ids.split(",")));
        	if(n>0){
            	result.setCode(ResultCode.SUCCESS);
            	result.setMessage("删除成功");
        	}else{
            	result.setCode(ResultCode.FAIL);
            	result.setMessage("删除失败");
        	}
        } catch(Exception ex) {
        	logger.error("删除购物车商品异常:"+ ex.getMessage(), ex);
        	result.setMessage("删除失败");
        	result.setCode(ResultCode.FAIL);
        	return result;
        }
        logger.info("结束删除购物车商品");
		return result;
	}
}
