package com.sanposs.project.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjPurchaseOrder;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.service.YhsjPurchaseOrderDetailService;
import com.sanposs.project.service.YhsjPurchaseOrderService;
import com.sanposs.project.utils.JqgridResponse;
import com.sanposs.project.utils.SessinoManager;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
* Created by CodeGenerator on 2019/05/14.
*/
@RestController
@RequestMapping("/yhsj/purchase/order")
public class YhsjPurchaseOrderController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjMachineController.class);
    @Resource
    private YhsjPurchaseOrderService yhsjPurchaseOrderService;
    @Resource
    private YhsjPurchaseOrderDetailService purchaseOrderDetailService;

    @RequestMapping("/add")
    public Result add(YhsjPurchaseOrder yhsjPurchaseOrder) {
        yhsjPurchaseOrderService.save(yhsjPurchaseOrder);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjPurchaseOrderService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjPurchaseOrder yhsjPurchaseOrder) {
        yhsjPurchaseOrderService.update(yhsjPurchaseOrder);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjPurchaseOrder yhsjPurchaseOrder = yhsjPurchaseOrderService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjPurchaseOrder);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjPurchaseOrder> list = yhsjPurchaseOrderService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
    @RequestMapping("/getPurchaseOrder")
	public JqgridResponse<YhsjPurchaseOrder> getPurchaseOrder(@RequestParam String page, @RequestParam String rows,
			@RequestParam(defaultValue = "") String btime,@RequestParam(defaultValue = "") String etime,YhsjPurchaseOrder order,
			HttpServletRequest request) {
		JqgridResponse<YhsjPurchaseOrder> response = new JqgridResponse<YhsjPurchaseOrder>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(YhsjPurchaseOrder.class);
			Example.Criteria criteria = condition.createCriteria();

			if (order.getPurchaseNo() != null && !order.getPurchaseNo().equals("")) {
				criteria.andCondition(" purchase_no like '%" +order.getPurchaseNo() + "%'");
			}
			
			if (order.getPurchaseStatus() != null) {
				criteria.andCondition(" purchase_status = " +order.getPurchaseStatus());
			}
			
			String b;
			String e;
			if(!btime.equals("")&&!etime.equals("")){
				b = btime+" 00:00:00";
				e =  etime+" 23:59:59";
				criteria.andCondition("purchase_create_time between '"+b+"' and '"+e+"'");
			}else if(btime.equals("")&&!etime.equals("")){
				e =  etime+" 23:59:59";
				criteria.andCondition("purchase_create_time <='"+e+"'");
			}else if(!btime.equals("")&&etime.equals("")){
				b = btime+" 00:00:00";
				criteria.andCondition("purchase_create_time >='"+b+"'");
			}
			
			criteria.andCondition("purchase_is_delete = 0");
			condition.setOrderByClause("purchase_create_time desc");
			PageHelper.startPage(Integer.parseInt(page), size);
			
			List<YhsjPurchaseOrder> list = yhsjPurchaseOrderService.findByCondition(condition);
			PageInfo<YhsjPurchaseOrder> pageInfo = new PageInfo<YhsjPurchaseOrder>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}

		return response;
	}
	
	@RequestMapping("/getPurchaseOrderDetail")
    @ResponseBody
    public Result getPurchaseOrderDetail(HttpServletRequest request,String purchase_id) {
// 	    Long comid = (Long)request.getSession().getAttribute("comid");
	
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("main", new YhsjPurchaseOrder());
	    	map.put("detail", new ArrayList<YhsjPurchaseOrderDetail>());
	    	if (purchase_id!=null && !purchase_id.equals("")) {
				Condition condition = new Condition(YhsjPurchaseOrder.class);
				Example.Criteria criteria = condition.createCriteria();
				criteria.andCondition(" purchase_id='"+purchase_id+"'");
				List<YhsjPurchaseOrder> purchaseOrders = yhsjPurchaseOrderService.findByCondition(condition);
				if (purchaseOrders.size()>0) {
					map.put("main",purchaseOrders.get(0));
				}
				
				Condition detailcondition = new Condition(YhsjPurchaseOrderDetail.class);
				Example.Criteria detailcriteria = detailcondition.createCriteria();
				detailcriteria.andCondition(" purchase_id='"+purchase_id+"'");
				List<YhsjPurchaseOrderDetail> details = purchaseOrderDetailService.findByCondition(detailcondition);
				if (details.size()>0) {
					map.put("detail",details);
				}
			}
	        return ResultGenerator.genSuccessResult(map);
	    }
    }
	
	@RequestMapping("/deletePurchaseOrder")
   	public Result deletePurchaseOrder(HttpServletRequest request, String purchase_id) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (purchase_id == null) {
   				return ResultGenerator.genFailResult("purchase_id为空");
   			}
   			Condition condition = new Condition(YhsjPurchaseOrder.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" purchase_id='"+purchase_id+"'");
   			List<YhsjPurchaseOrder> purchaseOrders = yhsjPurchaseOrderService.findByCondition(condition);
   			if (purchaseOrders.size()>0) {
   				purchaseOrders.get(0).setPurchaseIsDelete(1);
			}
   			int r = yhsjPurchaseOrderService.update(purchaseOrders.get(0));
   			return ResultGenerator.genSuccessResult("删除成功");
   		}
   	}
	
	@RequestMapping("/updatePurchaseOrder")
   	public Result updatePurchaseOrder(HttpServletRequest request, String purchase_id,Integer status) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (purchase_id == null) {
   				return ResultGenerator.genFailResult("purchase_id为空");
   			}
   			if (status == null) {
   				return ResultGenerator.genFailResult("status为空");
   			}
   			Condition condition = new Condition(YhsjPurchaseOrder.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" purchase_id='"+purchase_id+"'");
   			List<YhsjPurchaseOrder> purchaseOrders = yhsjPurchaseOrderService.findByCondition(condition);
   			if (purchaseOrders.size()>0) {
   				purchaseOrders.get(0).setPurchaseStatus(status);
   	   			int r = yhsjPurchaseOrderService.update(purchaseOrders.get(0));
			}
   			return ResultGenerator.genSuccessResult("修改状态成功");
   		}
   	}
}
