package com.sanposs.project.web.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
import com.sanposs.project.model.YhsjDeliveryOrder;
import com.sanposs.project.model.YhsjDeliveryOrderDetail;
import com.sanposs.project.model.YhsjDeliveryOrderExtend;
import com.sanposs.project.model.YhsjPurchaseOrder;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.model.YhsjPurchaseOrderExtend;
import com.sanposs.project.pojo.BaseDto;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.pojo.QueryPage;
import com.sanposs.project.service.YhsjDeliveryOrderDetailService;
import com.sanposs.project.service.YhsjDeliveryOrderService;
import com.sanposs.project.service.YhsjProductService;
import com.sanposs.project.service.YhsjPurchaseOrderDetailService;
import com.sanposs.project.service.YhsjPurchaseOrderService;
import com.sanposs.project.service.YhsjShoppingCatService;
import com.sanposs.project.utils.ApiSessinoManager;
import com.sanposs.project.utils.CommonUtil;
import com.sanposs.project.utils.MyBeanUtils;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("deliveryOrderApi")
public class DeliveryOrderController {
	private static final Logger logger = LoggerFactory.getLogger(DeliveryOrderController.class);
    @Resource
    private YhsjPurchaseOrderService purchaseOrderService;
    @Resource
    private YhsjPurchaseOrderDetailService purchaseOrderDetailService;
    @Resource
    private YhsjDeliveryOrderService deliveryOrderService;
    @Resource
    private YhsjDeliveryOrderDetailService deliveryOrderDetailService;
    @Resource
    private YhsjProductService productService;
    
    @Resource 
    private YhsjShoppingCatService shoppingCatService;
    
    /**
     * 方法描述:查询发货单列表
      */
    @RequestMapping("/getDeliveryOrderList")
    public Result getDeliveryOrderList(HttpServletRequest request,HttpServletResponse response,BaseDto baseDto,YhsjDeliveryOrder order,String token) {
    	logger.info("开始查询发货单订单列表");
    	QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());
		
        Condition condition = new Condition(YhsjDeliveryOrder.class);
		Example.Criteria criteria = condition.createCriteria();

		if (order.getDeliveryNo() != null&& !order.getDeliveryNo().equals("")) {
			criteria.andCondition(" delivery_no like '%" + order.getDeliveryNo() + "%'");
		}
		if (order.getPurchaseNo() != null&& !order.getPurchaseNo().equals("")) {
			criteria.andCondition(" purchase_no like '%" + order.getPurchaseNo() + "%'");
		}
		if (order.getDeliveryStatus() != null) {
			criteria.andCondition(" delivery_status =" + order.getDeliveryStatus());
		}
		if(CommonUtil.isNotEmpty(order.getDeliveryUserName())){
			criteria.andCondition(" delivery_user_name like '%" + order.getDeliveryUserName() + "%'");
		}
		if(token!=null&&!"".equals(token)){
			LoginUser user = ApiSessinoManager.getLoginUser(token);
			if(user!=null){
				criteria.andCondition(" delivery_user_id = '" + user.getUserId() + "'");
			}else{
				return ResultGenerator.genFailResult("token出错");
			}
		}
		
		int totalCount = deliveryOrderService.countByCondition(condition);
		List<YhsjDeliveryOrderExtend> returnlist = new ArrayList();
		if(totalCount>0){
			String orderStr = " delivery_create_time desc ";
			if(baseDto.getSortName()!=null&&!"".equals(baseDto.getSortName())){
				orderStr = baseDto.getSortName();
				if(baseDto.getOrderName()!=null&&!"".equals(baseDto.getOrderName())){
					orderStr = orderStr + " "+ baseDto.getOrderName();
				}else{
					orderStr = orderStr + " asc ";//默认
				}
			}
			condition.setOrderByClause(orderStr + " limit "+page.getOffset()+","+page.getPageSize());
			List<YhsjDeliveryOrder> list = deliveryOrderService.findByCondition(condition);
			for(int i=0;i<list.size();i++){
				YhsjDeliveryOrder deliveryOrder = (YhsjDeliveryOrder)list.get(i);
				
				List<Map<String, Object>> detailList = shoppingCatService
						.dynamicSqlQuery("select yhsj_delivery_order_detail.*,"
								+ "yhsj_produce_detail.produce_detail_spec_name,yhsj_produce_detail.produce_detail_user_name,yhsj_produce_detail.produce_detail_color_type,yhsj_produce_detail.produce_detail_machine_name from yhsj_delivery_order_detail "
								+ "left join yhsj_produce_detail on yhsj_produce_detail.produce_detail_id=yhsj_delivery_order_detail.produce_detail_id"
								+ " where yhsj_delivery_order_detail.delivery_id= '"
								+ deliveryOrder.getDeliveryId() + "'");
				
				YhsjDeliveryOrderExtend  extend = new YhsjDeliveryOrderExtend();
				try {
					MyBeanUtils.copyBeanNotNull2Bean(deliveryOrder, extend);
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
        logger.info("结束查询发货单订单列表");
        return result;
    }
    
    /**
     * 方法描述:获取发货单详情
      */
     @RequestMapping("/getDeliveryOrderDetail")
     public Result getDeliveryOrderDetail(HttpServletRequest request,HttpServletResponse response,String delivery_id) {
     	logger.info("开始获取发货单详情");
     	if(delivery_id==null||"".equals(delivery_id)){
     		return ResultGenerator.genFailResult("delivery_id不能为空");
     	}
     	YhsjDeliveryOrder order = deliveryOrderService.findById(delivery_id);
 		if(order==null){
 			return ResultGenerator.genFailResult("找不到delivery_id为"+delivery_id+"的记录");
 		}
 		
 		Result result = ResultGenerator.genSuccessResult();
 		result.setData(order);
 		
 		//订单详情列表
 		List<Map<String, Object>> list = shoppingCatService
				.dynamicSqlQuery("select yhsj_delivery_order_detail.*,"
						+ "yhsj_produce_detail.produce_detail_spec_name,yhsj_produce_detail.produce_detail_user_name,yhsj_produce_detail.produce_detail_color_type,yhsj_produce_detail.produce_detail_machine_name from yhsj_delivery_order_detail "
						+ "left join yhsj_produce_detail on yhsj_produce_detail.produce_detail_id=yhsj_delivery_order_detail.produce_detail_id"
						+ " where yhsj_delivery_order_detail.delivery_id= '"
						+ order.getDeliveryId() + "'");
 		
 		
 		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("detailList", list);
 		result.setAttributes(map);
 		logger.info("结束获取发货单详情");
 		return result;
     }
     
     /**
      * 方法描述:新增发货单
       */
      @RequestMapping("/addDeliveryOrder")
      public Result addDeliveryOrder(HttpServletRequest request,HttpServletResponse response,String purchase_id,String token) {
   		Result result = ResultGenerator.genSuccessResult();
      	logger.info("开始新增发货单");
      	if(purchase_id==null||"".equals(purchase_id)){
      		return ResultGenerator.genFailResult("purchase_id不能为空");
      	}
      	LoginUser user = null;
      	if(token!=null&&!"".equals(token)){
			user = ApiSessinoManager.getLoginUser(token);
		}
      	if(user==null){
			return ResultGenerator.genFailResult("token出错");
		}
      	//查询采购单主表
      	YhsjPurchaseOrder purchaseOrder = purchaseOrderService.findById(purchase_id);
      	
  		if(purchaseOrder==null){
  			return ResultGenerator.genFailResult("找不到purchase_id为"+purchase_id+"的记录");
  		}
  		
  		// 查询采购单明细表
  		Condition condition = new Condition(YhsjPurchaseOrderDetail.class);
  		Example.Criteria criteria = condition.createCriteria();
  		criteria.andCondition(" purchase_id = '" + purchase_id + "'");
  		criteria.andCondition(" detail_is_delete = 0 ");
  		List<YhsjPurchaseOrderDetail> purchaseOrderDetails = purchaseOrderDetailService.findByCondition(condition);
  		if(purchaseOrderDetails.size()<=0){
  			return ResultGenerator.genFailResult("找不到purchase_id为"+purchase_id+"的产品记录");
  		}
  		
  		BigDecimal totalprice = new BigDecimal(0);
  		BigDecimal totalweight = new BigDecimal(0);
  		YhsjDeliveryOrder deliveryOrder = new YhsjDeliveryOrder();
  		List<YhsjDeliveryOrderDetail> deliveryOrderDetails = new ArrayList<YhsjDeliveryOrderDetail>();
  		
  		//发货单主表
  		deliveryOrder.setDeliveryId(CommonUtil.getUUID());
  		deliveryOrder.setDeliveryNo(CommonUtil.createNo("FH"));
  		deliveryOrder.setPurchaseNo(purchaseOrder.getPurchaseNo());
  		deliveryOrder.setDeliveryStatus(1);
  		deliveryOrder.setDeliveryUserId(purchaseOrder.getPurchaseCreateUserId());
  		deliveryOrder.setDeliveryUserName(purchaseOrder.getPurchaseCreateUserName());
  		deliveryOrder.setDeliveryCreateTime(new Date());
  		deliveryOrder.setDeliveryPhone(purchaseOrder.getPurchasePhone());
  		deliveryOrder.setDeliveryAddress(purchaseOrder.getPurchaseAddress());
  		deliveryOrder.setDeliveryIsDelete(0);
  		deliveryOrder.setDeliveryOperationId(user.getUserId());
  		deliveryOrder.setDeliveryOperationName(user.getUserName());
  		deliveryOrder.setDeliveryRemark(purchaseOrder.getPurchaseRemark());
  		
  		//发货单明细
  		BigDecimal count = new BigDecimal(0);
  		BigDecimal price = new BigDecimal(0);
  		BigDecimal weight = new BigDecimal(0);
  		for (YhsjPurchaseOrderDetail orderDetail : purchaseOrderDetails) {
  			YhsjDeliveryOrderDetail deliveryOrderDetail = new YhsjDeliveryOrderDetail();
  			count = new BigDecimal(orderDetail.getProductCount());
  			if(orderDetail.getProductPrice()!=null){
  				price = orderDetail.getProductPrice();
  			}
  			if(orderDetail.getProductWeight()!=null){
  				weight = orderDetail.getProductWeight();
  			}
  			
  			totalprice = totalprice.add(count.multiply(price));
  			totalweight = totalweight.add(weight);
  			
  			deliveryOrderDetail.setDetailId(CommonUtil.getUUID());
  			deliveryOrderDetail.setDeliveryId(deliveryOrder.getDeliveryId());
  			deliveryOrderDetail.setProductId(orderDetail.getProductId());
  			deliveryOrderDetail.setProductName(orderDetail.getProductName());
  			deliveryOrderDetail.setProductPrice(orderDetail.getProductPrice());
  			deliveryOrderDetail.setProductCount(orderDetail.getProductCount());
  			deliveryOrderDetail.setProductWeight(orderDetail.getProductWeight());
  			deliveryOrderDetail.setProductTotalPrice(orderDetail.getProductTotalPrice());
  			deliveryOrderDetail.setTpId(orderDetail.getTpId());
  			deliveryOrderDetail.setTpName(orderDetail.getTpName());
  			deliveryOrderDetail.setDetailStatue(1);
  			deliveryOrderDetail.setDetailIsDelete(0);
  			deliveryOrderDetail.setProduceDetailId(orderDetail.getProduceDetailId());
  			deliveryOrderDetails.add(deliveryOrderDetail);
		}
  		
  		deliveryOrder.setDeliveryTotalPrice(totalprice);
  		deliveryOrder.setDeliveryTotalWeight(totalweight);
  		
  		try {
  	  		result=deliveryOrderDetailService.addDeliveryOrder(deliveryOrder, deliveryOrderDetails,purchase_id);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(ResultCode.FAIL);
			result.setMessage("生成异常");
		}
  		logger.info("结束新增发货单");
  		return result;
      }
      
      /**
       * 方法描述:修改发货单
     * @throws Exception 
        */
       @RequestMapping("/updateDeliveryOrder")
       public Result updateDeliveryOrder(HttpServletRequest request,HttpServletResponse response,YhsjDeliveryOrder order,String token){
    	Result result = ResultGenerator.genSuccessResult();
    	logger.info("开始修改发货单");
    	try {
    		LoginUser user = null;
          	if(token!=null&&!"".equals(token)){
    			user = ApiSessinoManager.getLoginUser(token);
    		}
			if(user==null){
				return ResultGenerator.genFailResult("token出错");
			}
    		
    		if(order.getDeliveryId()==null||"".equals(order.getDeliveryId())){
           		return ResultGenerator.genFailResult("delivery_id不能为空");
           	}
           	YhsjDeliveryOrder deliveryOrder = deliveryOrderService.findById(order.getDeliveryId());
           	if(deliveryOrder==null){
           		return ResultGenerator.genFailResult("找不到deliveryId为"+order.getDeliveryId()+"的记录");
           	}
       		MyBeanUtils.copyBeanNotNull2Bean(order, deliveryOrder);
       		deliveryOrder.setDeliveryUpdateTime(new Date());
       		int r = deliveryOrderService.update(deliveryOrder);
       		if (r<1) {
       			result = ResultGenerator.genFailResult("修改失败");
    		}
       		logger.info("结束修改发货单");
       		return result;
    	} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultCode.FAIL);
			result.setMessage("处理异常");
		}
		return result;
       }
}
