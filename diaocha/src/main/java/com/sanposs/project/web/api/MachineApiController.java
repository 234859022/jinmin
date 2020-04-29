package com.sanposs.project.web.api;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjMachine;
import com.sanposs.project.model.YhsjMachineTask;
import com.sanposs.project.model.YhsjMachineTaskExpand;
import com.sanposs.project.model.YhsjMachineTaskIngrediient;
import com.sanposs.project.model.YhsjPurchaseOrder;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.pojo.BaseDto;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.pojo.QueryPage;
import com.sanposs.project.service.YhsjMachineService;
import com.sanposs.project.service.YhsjMachineTaskIngrediientService;
import com.sanposs.project.service.YhsjMachineTaskService;
import com.sanposs.project.service.YhsjPurchaseOrderDetailService;
import com.sanposs.project.service.YhsjPurchaseOrderService;
import com.sanposs.project.utils.ApiSessinoManager;
import com.sanposs.project.utils.CommonUtil;
import com.sanposs.project.utils.JacksonUtil;
import com.sanposs.project.utils.MyBeanUtils;
import com.sanposs.project.utils.UUIDUtil;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
 * 
* 类名称: MachineApiController.java   
* 功能描述: 机器相关接口
* 创建者: xuwf 
* 创建时间:  2019年5月9日 下午3:21:23    
* 版本信息: V1.0
* CopyRight(C)：sanposs 2019 泉州市尚宝网络科技有限公司版权所有
 */
@RestController
@RequestMapping("machineApi")
public class MachineApiController {
	private static final Logger logger = LoggerFactory
			.getLogger(MachineApiController.class);

	@Autowired
	private YhsjMachineService machineService;
	
	@Autowired
	private YhsjMachineTaskService machineTaskService;
	
	@Autowired
	private YhsjMachineTaskIngrediientService machineTaskIngrediientService;
	
	@Autowired
	private YhsjPurchaseOrderDetailService purchaseOrderDetailService;
	
	@Autowired
	private YhsjPurchaseOrderService purchaseOrderService;

	/**
	 * 方法描述:获取机器列表 
	 * 创建人:xuwf 
	 * 创建时间:2019年5月9日 下午3:54:57
	 * 
	 * @param baseDto
	 * @param pro
	 * @return Result
	 */
	@RequestMapping("/getMachineList")
	public Result getMachineList(HttpServletResponse response,HttpServletRequest request,BaseDto baseDto, YhsjMachine machine,boolean isload) {
		logger.info("开始查询机器列表");
		ApiSessinoManager.setOriginHeader(request, response);
		Result result = ResultGenerator.genSuccessResult();
		QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());

		Condition condition = new Condition(YhsjMachine.class);
		Example.Criteria criteria = condition.createCriteria();

		if (machine != null) {
			if (machine.getMachineName() != null
					&& !machine.getMachineName().equals("")) {
				criteria.andCondition(" machine_name like '%"
						+ machine.getMachineName() + "%'");
			}
			if (machine.getMachineType() != null) {
				criteria.andCondition(" machine_type = "
						+ machine.getMachineType());
			}
		}
		criteria.andCondition("machine_is_delete = 0");
		criteria.andCondition("machine_is_fault = 0");

		int totalCount = machineService.countByCondition(condition);
		List<YhsjMachine> list = new ArrayList();
		if (totalCount > 0) {
			String orderStr = " machine_create_time asc ";
			if (baseDto.getSortName() != null
					&& !"".equals(baseDto.getSortName())) {
				orderStr = baseDto.getSortName();
				if (baseDto.getOrderName() != null
						&& !"".equals(baseDto.getOrderName())) {
					orderStr = orderStr + " " + baseDto.getOrderName();
				} else {
					orderStr = orderStr + " asc ";// 默认
				}
			}
			condition.setOrderByClause(orderStr + " limit " + page.getOffset()
					+ "," + page.getPageSize());
			list = machineService.findByCondition(condition);
			if(isload){
				List<Map<String,Object>> tmpList=new ArrayList<Map<String,Object>>();
				if(list!=null&&list.size()>0){
					for(YhsjMachine yhsjMachine:list){
						Map<String,Object> map=JacksonUtil.json2Map(JacksonUtil.bean2Json(yhsjMachine));
						Condition condition2=new Condition(YhsjMachineTask.class);
						Example.Criteria criteria2=condition2.createCriteria();
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						String dt=sdf.format(new Date());
						criteria2.andCondition(" TO_DAYS(task_start_time)<=TO_DAYS('"+dt+"')");
						criteria2.andCondition(" TO_DAYS(task_end_time)>=TO_DAYS('"+dt+"')");
						criteria2.andCondition(" machine_id='"+yhsjMachine.getMachineId()+"'");
						condition2.setOrderByClause(" task_create_time desc  limit 0,1 ");
						
						List<YhsjMachineTask> yhsjMachineTasks= machineTaskService.findByCondition(condition2);
						map.put("tasks", yhsjMachineTasks);
						tmpList.add(map);
					}
					result.setItems(tmpList);
				}
			}else{
				result.setItems(list);
			}
		}
		result.setTotalCount(totalCount);
		logger.info("结束查询机器列表");
		return result;
	}
	
	/**
	 * 方法描述:获取机器列表 
	 */
	@RequestMapping("/getMachineListByTask")
	public Result getMachineListByTask(HttpServletResponse response,HttpServletRequest request,BaseDto baseDto, YhsjMachine machine,boolean isload) {
		logger.info("开始查询机器列表");
		ApiSessinoManager.setOriginHeader(request, response);
		Result result = ResultGenerator.genSuccessResult();
		QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());

		Condition condition = new Condition(YhsjMachine.class);
		Example.Criteria criteria = condition.createCriteria();

		if (machine != null) {
			if (machine.getMachineName() != null
					&& !machine.getMachineName().equals("")) {
				criteria.andCondition(" machine_name like '%"
						+ machine.getMachineName() + "%'");
			}
			if (machine.getMachineType() != null) {
				criteria.andCondition(" machine_type = "
						+ machine.getMachineType());
			}
		}
		criteria.andCondition("machine_is_delete = 0");

		int totalCount = machineService.countByCondition(condition);
		List<YhsjMachine> list = new ArrayList();
		if (totalCount > 0) {
			String orderStr = " machine_create_time asc ";
			if (baseDto.getSortName() != null
					&& !"".equals(baseDto.getSortName())) {
				orderStr = baseDto.getSortName();
				if (baseDto.getOrderName() != null
						&& !"".equals(baseDto.getOrderName())) {
					orderStr = orderStr + " " + baseDto.getOrderName();
				} else {
					orderStr = orderStr + " asc ";// 默认
				}
			}
			condition.setOrderByClause(orderStr + " limit " + page.getOffset()
					+ "," + page.getPageSize());
			list = machineService.findByCondition(condition);
			if(isload){
				List<Map<String,Object>> tmpList=new ArrayList<Map<String,Object>>();
				if(list!=null&&list.size()>0){
					for(YhsjMachine yhsjMachine:list){
						Map<String,Object> map=JacksonUtil.json2Map(JacksonUtil.bean2Json(yhsjMachine));
						Condition condition2=new Condition(YhsjMachineTask.class);
						Example.Criteria criteria2=condition2.createCriteria();
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						String dt=sdf.format(new Date());
						criteria2.andCondition(" TO_DAYS(task_start_time)<=TO_DAYS('"+dt+"')");
						criteria2.andCondition(" TO_DAYS(task_end_time)>=TO_DAYS('"+dt+"')");
						criteria2.andCondition(" machine_id='"+yhsjMachine.getMachineId()+"'");
						condition2.setOrderByClause(" task_create_time desc  limit 0,1 ");
						
						List<YhsjMachineTask> yhsjMachineTasks= machineTaskService.findByCondition(condition2);
						map.put("tasks", yhsjMachineTasks);
						tmpList.add(map);
					}
					result.setItems(tmpList);
				}
			}else{
				result.setItems(list);
			}
		}
		result.setTotalCount(totalCount);
		logger.info("结束查询机器列表");
		return result;
	}
	
	/**
	 * 方法描述:获取机器任务列表 
	 * 创建人:xuwf 
	 * 创建时间:2019年5月9日 下午3:54:57
	 * 
	 * @param baseDto
	 * @param machineTask
	 * @return Result
	 * @throws Exception 
	 */
	@RequestMapping("/getMachineTaskList")
	public Result getMachineTaskList(HttpServletResponse response,HttpServletRequest request,BaseDto baseDto, YhsjMachineTask machineTask) throws Exception {
		ApiSessinoManager.setOriginHeader(request, response);
		logger.info("开始查询机器任务列表");
		QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());

		Condition condition = new Condition(YhsjMachine.class);
		Example.Criteria criteria = condition.createCriteria();
		
		criteria.andCondition(" task_is_delete=0");

		if (machineTask != null) {
			if (machineTask.getMachineId() != null
					&& !machineTask.getMachineId().equals("")) {
				criteria.andCondition(" machine_id = '"
						+ machineTask.getMachineId() + "'");
			}
			
			if (machineTask.getPurchaseDetailId() != null
					&& !machineTask.getPurchaseDetailId().equals("")) {
				criteria.andCondition(" purchase_detail_id like '%"
						+ machineTask.getPurchaseDetailId() + "%'");
			}
			
			if (machineTask.getPurchaseNo() != null
					&& !machineTask.getPurchaseNo().equals("")) {
				criteria.andCondition(" purchase_no like '%"
						+ machineTask.getPurchaseNo() + "%'");
			}
			
			if (machineTask.getProductName() != null
					&& !machineTask.getProductName().equals("")) {
				criteria.andCondition(" product_name like '%"
						+ machineTask.getProductName() + "%'");
			}
			
			if (machineTask.getProductId() != null
					&& !machineTask.getProductId().equals("")) {
				criteria.andCondition(" product_id = '"
						+ machineTask.getProductId() + "'");
			}
			
			if (machineTask.getTpId() != null
					&& !machineTask.getTpId().equals("")) {
				criteria.andCondition(" tp_id = '"
						+ machineTask.getTpId() + "'");
			}
			
			if (machineTask.getTpName() != null
					&& !machineTask.getTpName().equals("")) {
				criteria.andCondition(" tp_name like '%"
						+ machineTask.getTpName() + "%'");
			}
			
			if (machineTask.getTaskStatus() != null) {
				criteria.andCondition(" task_status = "+ machineTask.getTaskStatus());
			}
			
		}

		int totalCount = machineTaskService.countByCondition(condition);
		List<YhsjMachineTask> list = new ArrayList<YhsjMachineTask>();
		List<YhsjMachineTaskExpand> taskList = new ArrayList<YhsjMachineTaskExpand>();
		if (totalCount > 0) {
			String orderStr = " task_create_time desc ";
			if (baseDto.getSortName() != null
					&& !"".equals(baseDto.getSortName())) {
				orderStr = baseDto.getSortName();
				if (baseDto.getOrderName() != null
						&& !"".equals(baseDto.getOrderName())) {
					orderStr = orderStr + " " + baseDto.getOrderName();
				} else {
					orderStr = orderStr + " asc ";// 默认
				}
			}
			condition.setOrderByClause(orderStr + " limit " + page.getOffset()
					+ "," + page.getPageSize());
			list = machineTaskService.findByCondition(condition);
			for (int i = 0; i < list.size(); i++) {
				YhsjMachineTask machineTask2 = list.get(i);
				YhsjMachineTaskExpand taskExpand = new YhsjMachineTaskExpand();
				MyBeanUtils.copyBeanNotNull2Bean(machineTask2, taskExpand);
				
				Condition condition2 = new Condition(YhsjPurchaseOrder.class);
				Example.Criteria criteria2 = condition2.createCriteria();
				criteria2.andCondition(" purchase_no='"+machineTask2.getPurchaseNo()+"'");
				
				List<YhsjPurchaseOrder> orders = purchaseOrderService.findByCondition(condition2);
				
				if (orders.size()>0) {
					taskExpand.setPurchaseCreateUserName(orders.get(0).getPurchaseCreateUserName());
				}
				taskList.add(taskExpand);
			}
		}
		Result result = ResultGenerator.genSuccessResult();
		result.setTotalCount(totalCount);
		result.setItems(taskList);
		logger.info("结束查询机器任务列表");
		return result;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param machine_id
	 * @return Result
	 */
	@RequestMapping("/getMachine")
	public Result getMachine(HttpServletRequest request,HttpServletResponse response,String machine_id) {
		ApiSessinoManager.setOriginHeader(request, response);
		logger.info("开始查询机器信息");
		Result result = ResultGenerator.genSuccessResult();

		YhsjMachine machine=machineService.findBy("machineId", machine_id);
		result.setCode(ResultCode.SUCCESS);
		result.setData(machine);
		result.setMessage("获取成功");
		logger.info("结束查询机器任务列表");
		return result;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param task_id
	 * @return Result
	 */
	@RequestMapping("/getMachineTask")
	public Result getMachineTask(HttpServletRequest request,HttpServletResponse response,String task_id) {
		ApiSessinoManager.setOriginHeader(request, response);
		logger.info("开始查询机器任务信息");
		Result result = ResultGenerator.genSuccessResult();
		Map<String, Object> map = new HashMap<String, Object>();
		YhsjMachineTask machinetask=machineTaskService.findBy("taskId", task_id);
		map.put("task", machinetask);
		
		Condition condition = new Condition(YhsjMachineTaskIngrediient.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andCondition("task_id='"+task_id+"'");
		List<YhsjMachineTaskIngrediient> ingrediient=machineTaskIngrediientService.findByCondition(condition);
		map.put("ingrediient", ingrediient);
		result.setCode(ResultCode.SUCCESS);
		result.setData(map);
		result.setMessage("获取成功");
		logger.info("结束查询机器任务列表");
		return result;
	}
	
	@RequestMapping("/saveMachineTask")
	public Result saveMachineTask(HttpServletRequest request,HttpServletResponse response,YhsjMachineTask task,String startTime,String endTime,String token,
			Integer machineType) {
		ApiSessinoManager.setOriginHeader(request, response);
		logger.info("开始查询机器任务信息");
		LoginUser user =null;
		Result result = ResultGenerator.genSuccessResult();		
		if(token==null||token.compareTo("")==0){
			result.setCode(ResultCode.NOT_FOUND);
			result.setMessage("token不能为空");
			return result;
		}
		user = ApiSessinoManager.getLoginUser(token);
		
		if(user==null){
			result.setCode(ResultCode.NOT_FOUND);
			result.setMessage("用户不存在");
			return result;
		}
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			if (task.getTaskId()!=null && !task.getTaskId().equals("")) {
				YhsjMachineTask machineTask = machineTaskService.findById(task.getTaskId());
				if (task.getTaskStatus()!=null) {
					machineTask.setTaskStatus(task.getTaskStatus());
				}
				if (startTime!=null && !startTime.equals("")) {
					machineTask.setTaskStartTime(format.parse(startTime));
				}
				if (endTime!=null && !endTime.equals("")) {
					machineTask.setTaskEndTime(format.parse(endTime));
				}
				if (task.getActualWeight()!=null) {
					machineTask.setActualWeight(task.getActualWeight());
				}
				
				int r = machineTaskService.update(machineTask);
				if (r>0) {
					YhsjPurchaseOrderDetail orderDetail = purchaseOrderDetailService.findById(machineTask.getPurchaseDetailId());
					if (orderDetail!=null) {
						if(machineType==1 && task.getTaskStatus()==2){
							orderDetail.setDetailStatue(3);
							orderDetail.setBlownTime(new Date());
						}else if(machineType==1 && task.getTaskStatus()==3){
							orderDetail.setDetailStatue(4);
							//吹膜完成是设置实际重量
							if (task.getActualWeight()!=null) {
								orderDetail.setActualWeight(task.getActualWeight());
							}
						}else if(machineType==2 && task.getTaskStatus()==2){
							orderDetail.setDetailStatue(6);
							orderDetail.setCutTime(new Date());
						}else if(machineType==2 && task.getTaskStatus()==3){
							orderDetail.setDetailStatue(7);
						}
						int rlt = purchaseOrderDetailService.update(orderDetail);
						
						if (rlt>0 && machineType==2 && task.getTaskStatus()==3) {						
							Condition condition = new Condition(YhsjPurchaseOrderDetail.class);
							Example.Criteria criteria = condition.createCriteria();
							criteria.andCondition("purchase_id='"+orderDetail.getPurchaseId()+"'");
							
							boolean isUpdate = true;
							List<YhsjPurchaseOrderDetail> details = purchaseOrderDetailService.findByCondition(condition);
							for (YhsjPurchaseOrderDetail yhsjPurchaseOrderDetail : details) {
								if(yhsjPurchaseOrderDetail.getDetailStatue() != 7 && yhsjPurchaseOrderDetail.getDetailStatue() != 8){
									isUpdate = false;
								}
							}
							
							if (isUpdate) {
								YhsjPurchaseOrder order = new YhsjPurchaseOrder();
								order = purchaseOrderService.findById(orderDetail.getPurchaseId());
								order.setPurchaseStatus(3);
								order.setPurchaseUpdateTime(new Date());
								int orlt = purchaseOrderService.update(order);
							}
						}
					}
					result.setCode(ResultCode.SUCCESS);
					result.setMessage("保存成功");
				}else{
					result.setCode(ResultCode.FAIL);
					result.setMessage("保存失败");
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setCode(ResultCode.FAIL);
			result.setMessage("保存异常");
		}
		logger.info("结束查询机器任务列表");
		return result;
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @param task_ingredient_id
	 * @return Result
	 */
	@RequestMapping("/getMachineTaskIngrediient")
	public Result getMachineTaskIngrediient(HttpServletRequest request,HttpServletResponse response,String task_ingredient_id) {
		ApiSessinoManager.setOriginHeader(request, response);
		logger.info("开始查询机器任务材料信息");
		Result result = ResultGenerator.genSuccessResult();

		YhsjMachineTaskIngrediient machineTaskIngrediient=machineTaskIngrediientService.findBy("taskIngredientId", task_ingredient_id);
		result.setCode(ResultCode.SUCCESS);
		result.setData(machineTaskIngrediient);
		result.setMessage("获取成功");
		logger.info("结束查询机器任务材料列表");
		return result;
	}
	
	/**
	 * 方法描述:获取机器任务材料列表 
	 * 创建人:xuwf 
	 * 创建时间:2019年5月9日 下午3:54:57
	 * 
	 * @param baseDto
	 * @param machineTaskIngrediient
	 * @return Result
	 */
	@RequestMapping("/getMachineTaskIngrediientList")
	public Result getMachineTaskIngrediientList(HttpServletResponse response,HttpServletRequest request,BaseDto baseDto, YhsjMachineTaskIngrediient machineTaskIngrediient) {
		ApiSessinoManager.setOriginHeader(request, response);
		logger.info("开始查询机器任务材料列表");
		QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());

		Condition condition = new Condition(YhsjMachine.class);
		Example.Criteria criteria = condition.createCriteria();
		
		if (machineTaskIngrediient != null) {
			if (machineTaskIngrediient.getTaskId() != null
					&& !machineTaskIngrediient.getTaskId().equals("")) {
				criteria.andCondition(" task_id = '"
						+ machineTaskIngrediient.getTaskId() + "'");
			}
		}

		int totalCount = machineTaskIngrediientService.countByCondition(condition);
		List<YhsjMachineTaskIngrediient> list = new ArrayList<YhsjMachineTaskIngrediient>();
		if (totalCount > 0) {
			String orderStr = " ingredient_create_time desc ";
			if (baseDto.getSortName() != null
					&& !"".equals(baseDto.getSortName())) {
				orderStr = baseDto.getSortName();
				if (baseDto.getOrderName() != null
						&& !"".equals(baseDto.getOrderName())) {
					orderStr = orderStr + " " + baseDto.getOrderName();
				} else {
					orderStr = orderStr + " asc ";// 默认
				}
			}
			condition.setOrderByClause(orderStr + " limit " + page.getOffset()
					+ "," + page.getPageSize());
			list = machineTaskIngrediientService.findByCondition(condition);
		}
		Result result = ResultGenerator.genSuccessResult();
		result.setTotalCount(totalCount);
		result.setItems(list);
		logger.info("结束查询机器任务材料列表");
		return result;
	}
	
	/**
	 * 方法描述:创建机器
	 * 创建人:xuwf 
	 * 创建时间:2019年5月9日 下午3:54:57
	 * 
	 * @param token
	 * @param machine
	 * @return Result
	 */
	/**
	 * @param token
	 * @param machine
	 * @return
	 */
	@RequestMapping("/createMachine")
	public Result createMachine(HttpServletResponse response,HttpServletRequest request,String token,YhsjMachine machine) {
		ApiSessinoManager.setOriginHeader(request, response);
		logger.info("开始创建机器信息");
		LoginUser user =null;
		Result result = ResultGenerator.genSuccessResult();		
		if(token==null||token.compareTo("")==0){
			result.setCode(ResultCode.NOT_FOUND);
			result.setMessage("token不能为空");
			return result;
		}
		user = ApiSessinoManager.getLoginUser(token);
		
		if(user==null){
			result.setCode(ResultCode.NOT_FOUND);
			result.setMessage("用户不存在");
			return result;
		}
		if(machine.getMachineId()!=null&&machine.getMachineId().compareTo("")>0){
			int n=machineService.update(machine);
			if(n>0){
				result.setCode(ResultCode.SUCCESS);
				result.setMessage("更新成功");
			}else{
				result.setCode(ResultCode.FAIL);
				result.setMessage("更新失败");
			}
		}else{
			machine.setMachineId(UUIDUtil.createUUID());
			machine.setMachineIsDelete(0);
			machine.setMachineCreateTime(new Date());
			machine.setMachineCreateUser(user.getUserId());
			
			int n=machineService.save(machine);
			if(n>0){
				result.setCode(ResultCode.SUCCESS);
				result.setMessage("新增成功");
			}else{
				result.setCode(ResultCode.FAIL);
				result.setMessage("新增失败");
			}
		}
		return result;
	}
	
	
	/**
	 * 方法描述:创建生产线任务
	 * 创建人:xuwf 
	 * 创建时间:2019年5月9日 下午3:54:57
	 * @param response
	 * @param request
	 * @param token
	 * @param machineTask
	 * @return
	 */
	@RequestMapping("/createMachineTask")
	public Result createMachineTask(HttpServletResponse response,HttpServletRequest request,String token,YhsjMachineTask machineTask,String taskingrediient_str,Integer detailState,String weight) {
		ApiSessinoManager.setOriginHeader(request, response);
		logger.info("开始创建机器任务信息");
		LoginUser user =null;
		Result result = ResultGenerator.genSuccessResult();		
		if(token==null||token.compareTo("")==0){
			result.setCode(ResultCode.NOT_FOUND);
			result.setMessage("token不能为空");
			return result;
		}
		user = ApiSessinoManager.getLoginUser(token);
		
		if(user==null){
			result.setCode(ResultCode.NOT_FOUND);
			result.setMessage("用户不存在");
			return result;
		}
		List<YhsjMachineTaskIngrediient> ingrediients=new ArrayList<YhsjMachineTaskIngrediient>();
		try {
			if(taskingrediient_str!=null&&taskingrediient_str.compareTo("")>0){
				ObjectMapper mapper = new ObjectMapper();
				ingrediients=mapper.readValue(taskingrediient_str,new TypeReference<List<YhsjMachineTaskIngrediient>>() { });	
			}
			if (CommonUtil.isNotEmpty(weight)) {
				machineTask.setActualWeight(new BigDecimal(weight));
			}
			result=machineTaskService.createMachineTask(machineTask, ingrediients, user,detailState);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setCode(ResultCode.FAIL);
			result.setMessage("创建异常");
		}
		return result;
	}
}
