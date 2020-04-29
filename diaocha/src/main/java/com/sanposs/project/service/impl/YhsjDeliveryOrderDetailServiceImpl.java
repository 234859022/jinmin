package com.sanposs.project.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.sanposs.project.dao.YhsjDeliveryOrderDetailMapper;
import com.sanposs.project.dao.YhsjDeliveryOrderMapper;
import com.sanposs.project.dao.YhsjPurchaseOrderMapper;
import com.sanposs.project.model.YhsjDeliveryOrder;
import com.sanposs.project.model.YhsjDeliveryOrderDetail;
import com.sanposs.project.model.YhsjPurchaseOrder;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.model.YhsjShoppingCat;
import com.sanposs.project.service.YhsjDeliveryOrderDetailService;
import com.sanposs.project.utils.CommonUtil;
import com.sanposs.project.utils.UUIDUtil;
import com.sanposs.project.common.PlatformException;
import com.sanposs.project.core.AbstractService;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/09.
 */
@Service
@Transactional
public class YhsjDeliveryOrderDetailServiceImpl extends AbstractService<YhsjDeliveryOrderDetail> implements YhsjDeliveryOrderDetailService {
    @Resource
    private YhsjDeliveryOrderMapper deliveryOrderMapper;
    
    @Resource
    private YhsjDeliveryOrderDetailMapper yhsjDeliveryOrderDetailMapper;
    
    @Resource
    private YhsjPurchaseOrderMapper yhsjPurchaseOrderMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result addDeliveryOrder(YhsjDeliveryOrder deliveryOrder,
			List<YhsjDeliveryOrderDetail> deliveryOrderDetails,String purchaseId)
			throws PlatformException {
		// TODO Auto-generated method stub
		Result result = ResultGenerator.genSuccessResult();	
		try{
			YhsjPurchaseOrder purchaseOrder=yhsjPurchaseOrderMapper.selectByPrimaryKey(purchaseId);

			if(purchaseOrder==null){
				result.setCode(ResultCode.VERIFY_ERROR);
				result.setMessage("采购单不存在");
				return result;
			}
			if(purchaseOrder.getPurchaseStatus()!=3){
				result.setCode(ResultCode.VERIFY_ERROR);
				result.setMessage("请先处理完成");
				return result;
			}
			int n=deliveryOrderMapper.insert(deliveryOrder);
			if(n==0){
				throw new PlatformException("发货单添加异常", 1);
			}
			
			for(YhsjDeliveryOrderDetail deliveryOrderDetail:deliveryOrderDetails){
				int y=yhsjDeliveryOrderDetailMapper.insert(deliveryOrderDetail);
				if(y==0){
					throw new PlatformException("发货单详情添加异常", 1);
				}
			}
			
			purchaseOrder.setPurchaseStatus(4);
			int ss=yhsjPurchaseOrderMapper.updateByPrimaryKey(purchaseOrder);
			if(ss==0){
				throw new PlatformException("采购单状态修改异常", 1);
			}
			
			result.setCode(ResultCode.SUCCESS);
			result.setMessage("生成成功");
		}catch(Exception e){
			e.printStackTrace();
			throw new PlatformException("添加操作异常", 1);
		}
		return result;
	}

}
