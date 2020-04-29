package com.sanposs.project.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sanposs.project.dao.YhsjMachineTaskIngrediientMapper;
import com.sanposs.project.dao.YhsjMachineTaskMapper;
import com.sanposs.project.dao.YhsjPurchaseOrderDetailMapper;
import com.sanposs.project.dao.YhsjPurchaseOrderMapper;
import com.sanposs.project.model.YhsjMachineTask;
import com.sanposs.project.model.YhsjMachineTaskIngrediient;
import com.sanposs.project.model.YhsjProduct;
import com.sanposs.project.model.YhsjProductTp;
import com.sanposs.project.model.YhsjPurchaseOrder;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.model.YhsjUserAddress;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.pojo.YhsjShoppingCatExpand;
import com.sanposs.project.service.YhsjMachineTaskService;
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
 * Created by CodeGenerator on 2019/05/10.
 */
@Service
@Transactional
public class YhsjMachineTaskServiceImpl extends AbstractService<YhsjMachineTask> implements YhsjMachineTaskService {
    @Resource
    private YhsjMachineTaskMapper yhsjMachineTaskMapper;
    
    @Resource
    private YhsjMachineTaskIngrediientMapper taskIngrediientMapper;
    
    @Resource
    private YhsjPurchaseOrderMapper purchaseOrderMapper;
    @Resource
    private YhsjPurchaseOrderDetailMapper purchaseOrderDetailMapper;

	@Override
	public List<Map<String, Object>> dynamicSqlQuery(String sql) {
		// TODO Auto-generated method stub
		return yhsjMachineTaskMapper.dynamicSqlQuery(sql);
	}

	@Override
	public int dynamicSqlByCount(String sql) {
		// TODO Auto-generated method stub
		return yhsjMachineTaskMapper.dynamicSqlByCount(sql);
	}

	@Override
	public int deleteYhsjMachineTask(String id) {
		// TODO Auto-generated method stub
		return yhsjMachineTaskMapper.deleteYhsjMachineTask(id);
	}

	@Override
	public int deleteYhsjMachineTaskBatch(List<String> ids) {
		// TODO Auto-generated method stub
		return yhsjMachineTaskMapper.deleteYhsjMachineTaskBatch(ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result createMachineTask(YhsjMachineTask machineTask,
			List<YhsjMachineTaskIngrediient> ingrediients,LoginUser loginUser,Integer detailState) throws PlatformException {
		// TODO Auto-generated method stub
		Result result= ResultGenerator.genSuccessResult();
		try{
			//创建任务
			machineTask.setTaskId(UUIDUtil.createUUID());
			machineTask.setTaskStatus(1);
			machineTask.setTaskIsDelete(0);
			machineTask.setTaskCreateTime(new Date());
			machineTask.setTaskCreateUser(loginUser.getNickname());
			machineTask.setTaskCreateUserid(loginUser.getUserId());
			int n=yhsjMachineTaskMapper.insert(machineTask);
			if(n==0){
				throw new PlatformException("任务添加异常", 1);
			}
			for(YhsjMachineTaskIngrediient ingrediient:ingrediients){
				ingrediient.setTaskIngredientId(UUIDUtil.createUUID());
				ingrediient.setTaskId(machineTask.getTaskId());
				ingrediient.setIngredientCreateTime(new Date());
				ingrediient.setIngredientCreateUser(loginUser.getNickname());
				ingrediient.setIngredientCreateUserid(loginUser.getUserId());
				int j=taskIngrediientMapper.insert(ingrediient);
				if(j==0){
					throw new PlatformException("任务材料添加异常", 1);
				}
			}
			YhsjPurchaseOrderDetail purchaseOrderDetail=purchaseOrderDetailMapper.selectByPrimaryKey(machineTask.getPurchaseDetailId());
			if(purchaseOrderDetail==null){
				throw new PlatformException("采购单明细不存在", 1);
			}
			if (machineTask.getMachineType()==1) {
				purchaseOrderDetail.setBlownMachineId(machineTask.getMachineId());
				purchaseOrderDetail.setBlownMachineName(machineTask.getMachineName());
			}else if(machineTask.getMachineType()==2){
				purchaseOrderDetail.setCutMachineId(machineTask.getMachineId());
				purchaseOrderDetail.setCutMachineName(machineTask.getMachineName());
			}
			purchaseOrderDetail.setDetailStatue(detailState);
			int u=purchaseOrderDetailMapper.updateByPrimaryKey(purchaseOrderDetail);
			if(u==0){
				throw new PlatformException("采购单明细状态修改失败", 1);
			}
			YhsjPurchaseOrder yhsjPurchaseOrder=purchaseOrderMapper.selectByPrimaryKey(purchaseOrderDetail.getPurchaseId());
			if(yhsjPurchaseOrder.getPurchaseStatus()==1){
				yhsjPurchaseOrder.setPurchaseStatus(2);
				int yy=purchaseOrderMapper.updateByPrimaryKey(yhsjPurchaseOrder);
				if(yy==0){
					throw new PlatformException("采购单状态修改失败", 1);
				}
			}
			result.setCode(ResultCode.SUCCESS);
			result.setMessage("创建成功");
		}catch(Exception e){
    		throw new PlatformException(e.getMessage(), 1);
		}
		return result;
	}

}
