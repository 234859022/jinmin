package com.sanposs.project.web.api;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjIngredient;
import com.sanposs.project.model.YhsjMachineTask;
import com.sanposs.project.model.YhsjMachineTaskIngrediient;
import com.sanposs.project.pojo.BaseDto;
import com.sanposs.project.pojo.QueryPage;
import com.sanposs.project.service.YhsjIngredientService;
import com.sanposs.project.service.YhsjMachineTaskIngrediientService;
import com.sanposs.project.service.YhsjMachineTaskService;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
* 类名称: IngredientController.java   
* 功能描述: 配料相关接口
* 创建者: chenjinmin 
* 创建时间:  2019年5月10日 下午1:41:37    
* 版本信息: V1.0
* CopyRight(C)：sanposs 2019 泉州市尚宝网络科技有限公司版权所有
 */
@RestController
@RequestMapping("ingredientApi")
public class IngredientController {
	private static final Logger logger = LoggerFactory.getLogger(IngredientController.class);
    @Resource
    private YhsjIngredientService yhsjIngredientService;
    @Resource
    private YhsjMachineTaskService yhsjMachineTaskService;
    @Resource
    private YhsjMachineTaskIngrediientService yhsjMachineTaskIngrediientService;
    
    
    /**
    * 方法描述:查看配料相关接口
    * 创建人:chenjinmin 
    * 创建时间:2019年5月10日 下午1:42:31  
    * @param baseDto
    * @param ingredient
    * @param token
    * @return      
    * Result
     */
    @RequestMapping("/getIngredientList")
    public Result getIngredientList(BaseDto baseDto,YhsjIngredient ingredient,String token) {
    	logger.info("开始查询配料列表");
    	QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());
		
        Condition condition = new Condition(YhsjIngredient.class);
		Example.Criteria criteria = condition.createCriteria();

		if (ingredient.getIngredientName() != null&& !ingredient.getIngredientName().equals("")) {
			criteria.andCondition(" ingredient_name like '%" + ingredient.getIngredientName() + "%'");
		}
		
		int totalCount = yhsjIngredientService.countByCondition(condition);
		List<YhsjIngredient> list = new ArrayList();
		if(totalCount>0){
			String orderStr = " ingredient_create_time desc ";
			if(baseDto.getSortName()!=null&&!"".equals(baseDto.getSortName())){
				orderStr = baseDto.getSortName();
				if(baseDto.getOrderName()!=null&&!"".equals(baseDto.getOrderName())){
					orderStr = orderStr + " "+ baseDto.getOrderName();
				}else{
					orderStr = orderStr + " asc ";//默认
				}
			}
			condition.setOrderByClause(orderStr + " limit "+page.getOffset()+","+page.getPageSize());
			list = yhsjIngredientService.findByCondition(condition);
		}
        Result result = ResultGenerator.genSuccessResult();
        result.setTotalCount(totalCount);
        result.setItems(list);
        logger.info("结束查询配料列表");
        return result;
    }
    
    /**
    * 方法描述:获取最近一次使用的配料信息
    * 创建人:chenjinmin 
    * 创建时间:2019年5月10日 下午1:51:14  
    * @param baseDto
    * @return      
    * Result
     */
    @RequestMapping("/getMachineTaskIngrediient")
    public Result getMachineTaskIngrediient() {
    	logger.info("开始获取最近一次使用的配料信息");
        Condition condition = new Condition(YhsjMachineTask.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andCondition(" machine_type = 1");
		condition.setOrderByClause(" task_create_time desc limit 0,1");
		List<YhsjMachineTask> list =yhsjMachineTaskService.findByCondition(condition);//找出最后保存的记录
		Result result = ResultGenerator.genSuccessResult();
		if(list!=null&&list.size()>0){
			YhsjMachineTask task= (YhsjMachineTask)list.get(0);
			result.setData(task);
			Condition condition2 = new Condition(YhsjMachineTaskIngrediient.class);
			Example.Criteria criteria2 = condition2.createCriteria();
			criteria2.andCondition(" task_id = '" + task.getTaskId() + "'");
			List list2 = yhsjMachineTaskIngrediientService.findByCondition(condition2);
			result.setItems(list2);
		}
        logger.info("结束获取最近一次使用的配料信息");
        return result;
    }
    
    

}
