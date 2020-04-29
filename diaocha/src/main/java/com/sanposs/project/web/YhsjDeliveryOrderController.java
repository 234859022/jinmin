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
import com.sanposs.project.model.YhsjDeliveryOrder;
import com.sanposs.project.model.YhsjDeliveryOrderDetail;
import com.sanposs.project.service.YhsjDeliveryOrderDetailService;
import com.sanposs.project.service.YhsjDeliveryOrderService;
import com.sanposs.project.utils.JqgridResponse;
import com.sanposs.project.utils.SessinoManager;
import com.sanposs.project.utils.TimeTools;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
* Created by CodeGenerator on 2019/05/09.
*/
@RestController
@RequestMapping("/yhsj/delivery/order")
public class YhsjDeliveryOrderController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjDeliveryOrderController.class);
    @Resource
    private YhsjDeliveryOrderService yhsjDeliveryOrderService;
    @Resource
    private YhsjDeliveryOrderDetailService deliveryOrderDetailService;

    @RequestMapping("/add")
    public Result add(YhsjDeliveryOrder yhsjDeliveryOrder) {
        yhsjDeliveryOrderService.save(yhsjDeliveryOrder);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjDeliveryOrderService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjDeliveryOrder yhsjDeliveryOrder) {
        yhsjDeliveryOrderService.update(yhsjDeliveryOrder);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjDeliveryOrder yhsjDeliveryOrder = yhsjDeliveryOrderService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjDeliveryOrder);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjDeliveryOrder> list = yhsjDeliveryOrderService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
    @RequestMapping("/getDeliveryOrder")
	public JqgridResponse<YhsjDeliveryOrder> getDeliveryOrder(@RequestParam String page, @RequestParam String rows,
			@RequestParam(defaultValue = "") String delivery_no,@RequestParam(defaultValue = "") String purchase_no,
			@RequestParam(defaultValue = "")Integer delivery_status,@RequestParam(defaultValue = "") String btime,
			@RequestParam(defaultValue = "") String etime,HttpServletRequest request) {
		JqgridResponse<YhsjDeliveryOrder> response = new JqgridResponse<YhsjDeliveryOrder>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(YhsjDeliveryOrder.class);
			Example.Criteria criteria = condition.createCriteria();

			if (delivery_no != null && !delivery_no.equals("")) {
				criteria.andCondition(" delivery_no like '%" + delivery_no + "%'");
			}
			
			if (purchase_no != null && !purchase_no.equals("")) {
				criteria.andCondition(" purchase_no like '%" + purchase_no + "%'");
			}
			
			String b;
			String e;
			if(!btime.equals("")&&!etime.equals("")){
				b = btime+" 00:00:00";
				e =  etime+" 23:59:59";
				criteria.andCondition("delivery_create_time between '"+b+"' and '"+e+"'");
			}else if(btime.equals("")&&!etime.equals("")){
				e =  etime+" 23:59:59";
				criteria.andCondition("delivery_create_time <='"+e+"'");
			}else if(!btime.equals("")&&etime.equals("")){
				b = btime+" 00:00:00";
				criteria.andCondition("delivery_create_time >='"+b+"'");
			}
			criteria.andCondition("delivery_is_delete = 0");
			condition.setOrderByClause("delivery_create_time desc");
			PageHelper.startPage(Integer.parseInt(page), size);
			
			List<YhsjDeliveryOrder> list = yhsjDeliveryOrderService.findByCondition(condition);
			PageInfo<YhsjDeliveryOrder> pageInfo = new PageInfo<YhsjDeliveryOrder>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}

		return response;
	}
	
	@RequestMapping("/getDeliveryOrderDetail")
    @ResponseBody
    public Result getDeliveryOrderDetail(HttpServletRequest request,String delivery_id) {
// 	    Long comid = (Long)request.getSession().getAttribute("comid");
	
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("main", new YhsjDeliveryOrder());
	    	map.put("detail", new ArrayList<YhsjDeliveryOrderDetail>());
	    	if (delivery_id!=null && !delivery_id.equals("")) {
				Condition condition = new Condition(YhsjDeliveryOrder.class);
				Example.Criteria criteria = condition.createCriteria();
				criteria.andCondition(" delivery_id='"+delivery_id+"'");
				List<YhsjDeliveryOrder> deliveryOrders = yhsjDeliveryOrderService.findByCondition(condition);
				if (deliveryOrders.size()>0) {
					map.put("main",deliveryOrders.get(0));
				}
				
				Condition detailcondition = new Condition(YhsjDeliveryOrderDetail.class);
				Example.Criteria detailcriteria = detailcondition.createCriteria();
				detailcriteria.andCondition(" delivery_id='"+delivery_id+"'");
				List<YhsjDeliveryOrderDetail> details = deliveryOrderDetailService.findByCondition(detailcondition);
				if (details.size()>0) {
					map.put("detail",details);
				}
			}
	        return ResultGenerator.genSuccessResult(map);
	    }
    }
	
	@RequestMapping("/deleteDeliveryOrder")
   	public Result deleteDeliveryOrder(HttpServletRequest request, String delivery_id) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (delivery_id == null) {
   				return ResultGenerator.genFailResult("delivery_id为空");
   			}
   			Condition condition = new Condition(YhsjDeliveryOrder.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" delivery_id='"+delivery_id+"'");
   			List<YhsjDeliveryOrder> deliveryOrders = yhsjDeliveryOrderService.findByCondition(condition);
   			if (deliveryOrders.size()>0) {
   				deliveryOrders.get(0).setDeliveryIsDelete(1);
			}
   			int r = yhsjDeliveryOrderService.update(deliveryOrders.get(0));
   			return ResultGenerator.genSuccessResult("删除成功");
   		}
   	}
	
	@RequestMapping("/updateDeliveryOrder")
   	public Result updateDeliveryOrder(HttpServletRequest request, String delivery_id,Integer status) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (delivery_id == null || delivery_id == "") {
   				return ResultGenerator.genFailResult("delivery_id为空");
   			}
   			if (status == null) {
   				return ResultGenerator.genFailResult("status为空");
   			}
   			Condition condition = new Condition(YhsjDeliveryOrder.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" delivery_id='"+delivery_id+"'");
   			List<YhsjDeliveryOrder> deliveryOrders = yhsjDeliveryOrderService.findByCondition(condition);
   			if (deliveryOrders.size()>0) {
   				deliveryOrders.get(0).setDeliveryStatus(status);
   	   			int r = yhsjDeliveryOrderService.update(deliveryOrders.get(0));
			}
   			return ResultGenerator.genSuccessResult("修改状态成功");
   		}
   	}
}
