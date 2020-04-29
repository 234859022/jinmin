package com.sanposs.project.web.api;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjPurchaseOrder;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.model.YhsjPurchaseOrderExtend;
import com.sanposs.project.model.YhsjShoppingCat;
import com.sanposs.project.model.YhsjUserAddress;
import com.sanposs.project.pojo.BaseDto;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.pojo.QueryPage;
import com.sanposs.project.service.YhsjPurchaseOrderDetailService;
import com.sanposs.project.service.YhsjPurchaseOrderService;
import com.sanposs.project.service.YhsjShoppingCatService;
import com.sanposs.project.service.YhsjUserAddressService;
import com.sanposs.project.utils.ApiSessinoManager;
import com.sanposs.project.utils.CommonUtil;
import com.sanposs.project.utils.MyBeanUtils;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
* 类名称: PurchaseController.java   
* 功能描述: 采购单相关接口
* 创建者: chenjinmin 
* 创建时间:  2019年5月9日 下午5:17:46    
* 版本信息: V1.0
* CopyRight(C)：sanposs 2019 泉州市尚宝网络科技有限公司版权所有
 */
@RestController
@RequestMapping("purchaseApi")
public class PurchaseController {
	private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);
    @Resource
    private YhsjPurchaseOrderService yhsjPurchaseOrderService;
    @Resource
    private YhsjPurchaseOrderDetailService yhsjPurchaseOrderDetailService;
    @Resource
    private YhsjShoppingCatService shoppingCatService;
    @Resource
    private YhsjUserAddressService yhsjUserAddressService;
    
    /**
    * 方法描述:查看采购单订单列表
    * 创建人:chenjinmin 
    * 创建时间:2019年5月9日 下午5:32:50  
    * @param baseDto
    * @param order
    * @param token 有传表示查看我的采购订单，没有则表示老板查看的列表
    * @return      
    * Result
     */
    @RequestMapping("/getPurchaseOrderList")
    public Result getPurchaseOrderList(BaseDto baseDto,YhsjPurchaseOrder order,String token) {
    	logger.info("开始采购单订单列表");
    	QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());
		
        Condition condition = new Condition(YhsjPurchaseOrder.class);
		Example.Criteria criteria = condition.createCriteria();

		if (order.getPurchaseCreateUserName() != null&& !order.getPurchaseCreateUserName().equals("")) {
			criteria.andCondition(" purchase_create_user_name like '%" + order.getPurchaseCreateUserName() + "%'");
		}
		if (order.getPurchaseStatus() != null) {
			criteria.andCondition(" purchase_status = " + order.getPurchaseStatus());
		}
		if(CommonUtil.isNotEmpty(order.getPurchaseNo())){
			criteria.andCondition(" purchase_no like '%"+order.getPurchaseNo()+"%'");
		}
		if(token!=null&&!"".equals(token)){
			LoginUser user = ApiSessinoManager.getLoginUser(token);
			if(user!=null){
				criteria.andCondition(" purchase_create_user_id = '" + user.getUserId() + "'");
			}else{
				return ResultGenerator.genFailResult("token出错");
			}
		}
		
		int totalCount = yhsjPurchaseOrderService.countByCondition(condition);
		List<YhsjPurchaseOrderExtend> returnlist = new ArrayList<YhsjPurchaseOrderExtend>();
		if(totalCount>0){
			String orderStr = " purchase_create_time desc ";
			if(baseDto.getSortName()!=null&&!"".equals(baseDto.getSortName())){
				orderStr = baseDto.getSortName();
				if(baseDto.getOrderName()!=null&&!"".equals(baseDto.getOrderName())){
					orderStr = orderStr + " "+ baseDto.getOrderName();
				}else{
					orderStr = orderStr + " asc ";//默认
				}
			}
			condition.setOrderByClause(orderStr + " limit "+page.getOffset()+","+page.getPageSize());
			List<YhsjPurchaseOrder> list = yhsjPurchaseOrderService.findByCondition(condition);
			for(int i=0;i<list.size();i++){
				YhsjPurchaseOrder purchaseOrder = (YhsjPurchaseOrder)list.get(i);
				
				List<Map<String,Object>> detailList=shoppingCatService.dynamicSqlQuery("select yhsj_purchase_order_detail.*," +
						"yhsj_produce_detail.produce_detail_spec_name,yhsj_produce_detail.produce_detail_user_name,yhsj_produce_detail.produce_detail_color_type,yhsj_produce_detail.produce_detail_machine_name from yhsj_purchase_order_detail " +
						"left join yhsj_produce_detail on yhsj_produce_detail.produce_detail_id=yhsj_purchase_order_detail.produce_detail_id" +
						" where yhsj_purchase_order_detail.purchase_id= '" + purchaseOrder.getPurchaseId() + "'");
				
				YhsjPurchaseOrderExtend  extend = new YhsjPurchaseOrderExtend();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(purchaseOrder, extend);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				extend.setDetailList(detailList);
				returnlist.add(extend);
			}
		}
        Result result = ResultGenerator.genSuccessResult();
        result.setTotalCount(totalCount);
        result.setItems(returnlist);
        logger.info("结束采购单订单列表");
        return result;
    }
    
    /**
    * 方法描述:获取采购单详情
    * 创建人:chenjinmin 
    * 创建时间:2019年5月9日 下午5:38:19  
    * @param baseDto
    * @param purchaseId
    * @return      
    * Result
     */
    @RequestMapping("/getPurchaseOrderDetail")
    public Result getPurchaseOrderDetail(BaseDto baseDto,String purchaseId) {
    	logger.info("开始获取采购单详情");
    	if(purchaseId==null||"".equals(purchaseId)){
    		return ResultGenerator.genFailResult("purchaseId不能为空");
    	}
    	QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());
		YhsjPurchaseOrder order = yhsjPurchaseOrderService.findById(purchaseId);
		if(order==null){
			return ResultGenerator.genFailResult("找不到proId为"+purchaseId+"的记录");
		}
		
		Result result = ResultGenerator.genSuccessResult();
		result.setData(order);
		
		//订单详情列表
		List<Map<String,Object>> list=shoppingCatService.dynamicSqlQuery("select yhsj_purchase_order_detail.*," +
				"yhsj_produce_detail.produce_detail_spec_name,yhsj_produce_detail.produce_detail_user_name,yhsj_produce_detail.produce_detail_color_type,yhsj_produce_detail.produce_detail_machine_name from yhsj_purchase_order_detail " +
				"left join yhsj_produce_detail on yhsj_produce_detail.produce_detail_id=yhsj_purchase_order_detail.produce_detail_id" +
				" where yhsj_purchase_order_detail.purchase_id= '" + order.getPurchaseId() + "'");
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("detailList", list);
		result.setAttributes(map);
        logger.info("结束获取采购单详情");
        return result;
    }
    
    /**
    * 方法描述:根据no获取采购单信息
    * 创建人:chenjinmin 
    * 创建时间:2019年5月15日 下午3:50:51  
    * @param no
    * @return      
    * Result
     */
     @RequestMapping("/getPurchaseOrderByNo")
     public Result getPurchaseOrderByNo(String no) {
     	logger.info("开始根据no获取采购单信息");
     	if(no==null||"".equals(no)){
     		return ResultGenerator.genFailResult("no不能为空");
     	}
 		YhsjPurchaseOrder order = yhsjPurchaseOrderService.findBy("purchaseNo", no);
 		if(order==null){
 			return ResultGenerator.genFailResult("找不到no为"+no+"的记录");
 		}
 		Result result = ResultGenerator.genSuccessResult();
 		result.setData(order);
        logger.info("结束根据no获取采购单信息");
        return result;
     }
    
    /**
     * 方法描述:新增采购单
      */
     @RequestMapping("/addPurchaseOrder")
     public Result addPurchaseOrder(HttpServletRequest request,HttpServletResponse response,String catIdStr,String token,String address_id,YhsjPurchaseOrder po) {
  		Result result = ResultGenerator.genSuccessResult();
     	logger.info("开始新增采购单");
     	LoginUser user = null;
     	if(token!=null&&!"".equals(token)){
			user = ApiSessinoManager.getLoginUser(token);
		}
     	if(user==null){
			return ResultGenerator.genFailResult("token出错");
		}
     	
     	if (catIdStr == null || "".equals(catIdStr)) {
			result.setMessage("购物车商品不能为空！");
			result.setCode(ResultCode.FAIL);
			return result;
		}
     	if (address_id == null || "".equals(address_id)) {
			result.setMessage("address_id不能为空！");
			result.setCode(ResultCode.FAIL);
			return result;
		}
     	YhsjUserAddress address=yhsjUserAddressService.findById(address_id);
		if(address==null){
			result.setCode(ResultCode.FAIL);
			result.setMessage("收货地址不存在");
			return result;
		}
 		try {
 	     	List<String> catIds = Arrays.asList(catIdStr.split(","));
 	 		
 	 		BigDecimal totalprice = new BigDecimal(0);
 	 		YhsjPurchaseOrder purchaseOrder = new YhsjPurchaseOrder();
 	 		List<YhsjPurchaseOrderDetail> purchaseOrderDetails = new ArrayList<YhsjPurchaseOrderDetail>();
 	 		
 			//采购单主表
 	 		purchaseOrder.setPurchaseId(CommonUtil.getUUID());
 	 		purchaseOrder.setPurchaseNo(CommonUtil.createNo("BN"));
 	 		purchaseOrder.setPurchaseStatus(1);
 	 		purchaseOrder.setPurchaseCreateUserId(purchaseOrder.getPurchaseCreateUserId());
 	 		purchaseOrder.setPurchaseCreateUserName(address.getUserName());
 	 		purchaseOrder.setPurchaseCreateTime(new Date());
 	 		purchaseOrder.setPurchasePhone(address.getUserPhone());
 	 		purchaseOrder.setPurchaseAddress(address.getAddressPrivince()+address.getAddressCity()+address.getAddressTown()+address.getAddressDetail());
 	 		purchaseOrder.setPurchaseIsDelete(0);
 	 		
 	 		//采购单明细
 	 		BigDecimal count = new BigDecimal(0);
 	 		BigDecimal price = new BigDecimal(0);
 	 		for (String catid : catIds) {
 	 			YhsjShoppingCat shoppingCat = shoppingCatService.findById(catid);
 	 			YhsjPurchaseOrderDetail detail = new YhsjPurchaseOrderDetail();
 	 			count = new BigDecimal(shoppingCat.getProNum());
 	 			price = shoppingCat.getProPrice();
 	 			
 	 			totalprice = count.multiply(price);
 	 			
 	 			detail.setDetailId(CommonUtil.getUUID());
 	 			detail.setPurchaseId(purchaseOrder.getPurchaseId());
 	 			detail.setProductId(shoppingCat.getProId());
 	 			detail.setProductName(shoppingCat.getProName());
 	 			detail.setProductPrice(shoppingCat.getProPrice());
 	 			detail.setProductWeight(shoppingCat.getProWeight());
 	 			detail.setProductCount(shoppingCat.getProNum());
 	 			detail.setProductTotalPrice(totalprice);
 	 			detail.setTpId(shoppingCat.getScaleId());
 	 			detail.setTpName(shoppingCat.getScaleLabel());
 	 			detail.setDetailStatue(1);
 	 			detail.setDetailIsDelete(0);
 	 			
 	 			purchaseOrderDetails.add(detail);
 			}
 	 		
 	 		int r = yhsjPurchaseOrderService.save(purchaseOrder);
 	 		if (r>0) {
 	 			yhsjPurchaseOrderDetailService.save(purchaseOrderDetails);
 			}else {
 				result = ResultGenerator.genFailResult("新增失败");
 			}
		} catch (Exception e) {
			logger.info("订单提交失败" + e.getMessage(), e);
			result.setMessage("订单提交失败");
			result.setCode(ResultCode.FAIL);
		} 		
 		logger.info("结束新增采购单");
 		return result;
     }
    
    /**
     * 方法描述:修改采购单状态
     * @throws Exception 
    */
    @RequestMapping("/updateDeliveryOrder")
    public Result updateDeliveryOrder(HttpServletRequest request,HttpServletResponse response,YhsjPurchaseOrder order,String token){
  	Result result = ResultGenerator.genSuccessResult();
  	logger.info("开始修改采购单状态");
  	try {
  		LoginUser user = null;
        	if(token!=null&&!"".equals(token)){
  			user = ApiSessinoManager.getLoginUser(token);
  		}
		if(user==null){
			return ResultGenerator.genFailResult("token出错");
		}
  		
  		if(order.getPurchaseId()==null||"".equals(order.getPurchaseId())){
     		return ResultGenerator.genFailResult("purchase_id不能为空");
     	}
  		YhsjPurchaseOrder purchaseOrder = yhsjPurchaseOrderService.findById(order.getPurchaseId());
     	if(purchaseOrder==null){
     		return ResultGenerator.genFailResult("找不到purchase_id为"+order.getPurchaseId()+"的记录");
     	}
 		MyBeanUtils.copyBeanNotNull2Bean(order, purchaseOrder);
 		purchaseOrder.setPurchaseUpdateTime(new Date());
 		int r = yhsjPurchaseOrderService.update(purchaseOrder);
 		if (r<1) {
 			result = ResultGenerator.genFailResult("修改失败");
  		}
 		logger.info("结束修改采购单状态");
 		return result;
  	} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultCode.FAIL);
			result.setMessage("处理异常");
		}
		return result;
    }
    
    /**
     * 方法描述:修改采购单状态
     * @throws Exception 
    */
    @RequestMapping("/updateDeliveryOrderDetail")
    public Result updateDeliveryOrderDetail(HttpServletRequest request,HttpServletResponse response,YhsjPurchaseOrderDetail detail,String token){
  	Result result = ResultGenerator.genSuccessResult();
  	logger.info("开始修改采购单详情状态");
  	try {
  		LoginUser user = null;
        	if(token!=null&&!"".equals(token)){
  			user = ApiSessinoManager.getLoginUser(token);
  		}
		if(user==null){
			return ResultGenerator.genFailResult("token出错");
		}
  		
  		if(detail.getDetailId()==null||"".equals(detail.getDetailId())){
     		return ResultGenerator.genFailResult("detail_id不能为空");
     	}
  		YhsjPurchaseOrderDetail purchaseOrderDetail = yhsjPurchaseOrderDetailService.findById(detail.getDetailId());
     	if(purchaseOrderDetail==null){
     		return ResultGenerator.genFailResult("找不到detail_id为"+detail.getDetailId()+"的记录");
     	}
 		MyBeanUtils.copyBeanNotNull2Bean(detail, purchaseOrderDetail);
 		int r = yhsjPurchaseOrderDetailService.update(purchaseOrderDetail);
 		if (r<1) {
 			result = ResultGenerator.genFailResult("修改失败");
  		}
 		Condition condition=new Condition(YhsjPurchaseOrderDetail.class);
 		Example.Criteria criteria=condition.createCriteria();
 		criteria.andCondition(" detail_statue!=7");
 		criteria.andCondition(" detail_statue!=8");
 		criteria.andCondition(" purchase_id='"+purchaseOrderDetail.getPurchaseId()+"'");
 		int count=yhsjPurchaseOrderDetailService.countByCondition(condition);
 		if(count==0){
 			YhsjPurchaseOrder purchaseOrder=yhsjPurchaseOrderService.findBy("purchaseId", purchaseOrderDetail.getPurchaseId());
 			purchaseOrder.setPurchaseStatus(3);
 			yhsjPurchaseOrderService.update(purchaseOrder);
 		}
 		
 		logger.info("结束修改采购单详情状态");
 		return result;
  	} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultCode.FAIL);
			result.setMessage("处理异常");
		}
		return result;
    }
    
    
    /**
     * 方法描述:修改采购单状态
     * @throws Exception 
    */
    @RequestMapping("/getPurchaseOrderDetailById")
    public Result getPurchaseOrderDetailById(HttpServletRequest request,HttpServletResponse response,String token,String detail_id){
		Result result = ResultGenerator.genSuccessResult();
		logger.info("开始获取采购单详情状态");
		try {
			LoginUser user = null;
		    if(token==null||"".equals(token)){
		    	return ResultGenerator.genFailResult("缺少token");
			}
		    user = ApiSessinoManager.getLoginUser(token);
			if(user==null){
				return ResultGenerator.genFailResult("token出错");
			}
			
			if(detail_id==null||"".equals(detail_id)){
		 		return ResultGenerator.genFailResult("detail_id不能为空");
		 	}
			
			YhsjPurchaseOrderDetail purchaseOrderDetail = yhsjPurchaseOrderDetailService.findById(detail_id);
		 	if(purchaseOrderDetail==null){
		 		return ResultGenerator.genFailResult("找不到detail_id为"+detail_id+"的记录");
		 	}
		 	
		 	YhsjPurchaseOrder yhsjPurchaseOrder=yhsjPurchaseOrderService.findBy("purchaseId", purchaseOrderDetail.getPurchaseId());
		 	
		 	Map<String,Object> map=new HashMap<String,Object>();
		 	map.put("purcharseObj", yhsjPurchaseOrder);
		 	map.put("purcharseDetailObj", purchaseOrderDetail);
		 	
		 	result.setCode(ResultCode.SUCCESS);
		 	result.setData(map);
		 	result.setMessage("获取成功");
		 	
			logger.info("结束获取采购单详情状态");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultCode.FAIL);
			result.setMessage("获取异常");
		}
		return result;
    }

}
