package com.sanposs.project.web.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjEnclosure;
import com.sanposs.project.model.YhsjProceductType;
import com.sanposs.project.model.YhsjProduceDetail;
import com.sanposs.project.model.YhsjProduct;
import com.sanposs.project.model.YhsjProductTp;
import com.sanposs.project.pojo.BaseDto;
import com.sanposs.project.pojo.QueryPage;
import com.sanposs.project.service.YhsjEnclosureService;
import com.sanposs.project.service.YhsjProceductTypeService;
import com.sanposs.project.service.YhsjProduceDetailService;
import com.sanposs.project.service.YhsjProductService;
import com.sanposs.project.service.YhsjProductTpService;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
 * 
* 类名称: productController.java   
* 功能描述: 产品相关接口
* 创建者: chenjinmin 
* 创建时间:  2019年5月9日 下午3:21:23    
* 版本信息: V1.0
* CopyRight(C)：sanposs 2019 泉州市尚宝网络科技有限公司版权所有
 */
@RestController
@RequestMapping("proApi")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Resource
	private YhsjProductService yhsjProductService;
	@Resource
	private YhsjProceductTypeService proceductTypeService;
    @Resource
    private YhsjProductTpService yhsjProductTpService;
    @Resource
    private YhsjEnclosureService yhsjEnclosureService;
    
    @Resource
    private YhsjProduceDetailService produceDetailService;
    
    /**
    * 方法描述:获取产品列表
    * 创建人:chenjinmin 
    * 创建时间:2019年5月9日 下午3:54:57  
    * @param baseDto
    * @param pro
    * @return      
    * Result
     */
    @RequestMapping("/getProList")
    public Result getProList(BaseDto baseDto,YhsjProduct pro) {
    	logger.info("开始查询产品列表");
    	QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());
		
        Condition condition = new Condition(YhsjProduct.class);
		Example.Criteria criteria = condition.createCriteria();

		if (pro.getProType() != null&& !pro.getProType().equals("")) {
			criteria.andCondition(" pro_type = '" + pro.getProType() + "'");
		}
		if (pro.getProName() != null && !pro.getProName().equals("")) {
			criteria.andCondition(" pro_name like '%" + pro.getProName() + "%'");
		}
		if (pro.getProIsShow() != null) {
			criteria.andCondition(" pro_is_show =" + pro.getProIsShow() + "");
		}
		criteria.andCondition("pro_is_delete = 1");
		
		int totalCount = yhsjProductService.countByCondition(condition);
		List<YhsjProduct> list = new ArrayList();
		if(totalCount>0){
			String orderStr = " create_date desc ";
			if(baseDto.getSortName()!=null&&!"".equals(baseDto.getSortName())){
				orderStr = baseDto.getSortName();
				if(baseDto.getOrderName()!=null&&!"".equals(baseDto.getOrderName())){
					orderStr = orderStr + " "+ baseDto.getOrderName();
				}else{
					orderStr = orderStr + " asc ";//默认
				}
			}
			condition.setOrderByClause(orderStr + " limit "+page.getOffset()+","+page.getPageSize());
			list = yhsjProductService.findByCondition(condition);
		}
        Result result = ResultGenerator.genSuccessResult();
        result.setTotalCount(totalCount);
        result.setItems(list);
        logger.info("结束查询产品列表");
        return result;
    }
    
    /**
    * 方法描述:查看产品分类
    * 创建人:chenjinmin 
    * 创建时间:2019年5月9日 下午4:49:08  
    * @param baseDto
    * @param proType
    * @return      
    * Result
     */
    @RequestMapping("/getProTypeList")
    public Result getProTypeList(BaseDto baseDto,YhsjProceductType proType) {
    	logger.info("开始查看产品分类");
    	QueryPage page = new QueryPage();
		page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());
		
        Condition condition = new Condition(YhsjProceductType.class);
		Example.Criteria criteria = condition.createCriteria();

		if (proType.getTypeName() != null&& !proType.getTypeName().equals("")) {
			criteria.andCondition(" type_name like '%" + proType.getTypeName() + "%'");
		}
		criteria.andCondition("type_delete_flag = 1");
		
		int totalCount = proceductTypeService.countByCondition(condition);
		List<YhsjProceductType> list = new ArrayList();
		if(totalCount>0){
			String orderStr = " type_show_sort asc ";
			if(baseDto.getSortName()!=null&&!"".equals(baseDto.getSortName())){
				orderStr = baseDto.getSortName();
				if(baseDto.getOrderName()!=null&&!"".equals(baseDto.getOrderName())){
					orderStr = orderStr + " "+ baseDto.getOrderName();
				}else{
					orderStr = orderStr + " asc ";//默认
				}
			}
			condition.setOrderByClause(orderStr + " limit "+page.getOffset()+","+page.getPageSize());
			list = proceductTypeService.findByCondition(condition);
		}
        Result result = ResultGenerator.genSuccessResult();
        result.setTotalCount(totalCount);
        result.setItems(list);
        logger.info("结束查看产品分类");
        return result;
    }
    
    
    /**
    * 方法描述:获取单个产品接口
    * 创建人:chenjinmin 
    * 创建时间:2019年5月9日 下午4:51:26  
    * @param baseDto
    * @param proId
    * @return      
    * Result
     */
    @RequestMapping("/getProDetail")
    public Result getProDetail(String proId) {
    	logger.info("开始单个产品接口");
    	if(proId==null||"".equals(proId)){
    		return ResultGenerator.genFailResult("proId不能为空");
    	}
		YhsjProduct product = yhsjProductService.findById(proId);
		if(product==null){
			return ResultGenerator.genFailResult("找不到proId为"+proId+"的记录");
		}
		
		Result result = ResultGenerator.genSuccessResult();
		result.setData(product);
		
		//规格
        Condition condition = new Condition(YhsjProductTp.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andCondition(" pro_id = '" + proId + "'");
		List<YhsjProductTp> list = yhsjProductTpService.findByCondition(condition);
		
		//详情图
		Condition condition1 = new Condition(YhsjEnclosure.class);
		Example.Criteria criteria1 = condition1.createCriteria();
		criteria1.andCondition(" enclosure_parent_id = '" + proId + "'");
		List<YhsjEnclosure> list1= yhsjEnclosureService.findByCondition(condition1);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("proTp", list);
		map.put("enclosure", list1);
		result.setAttributes(map);
        logger.info("结束单个产品接口");
        return result;
    }
    
    
    /**
     * 方法描述:获取生产明细列表
     * 创建人:xuwf 
     * 创建时间:2019年5月9日 下午4:49:08  
     * @param baseDto
     * @param proType
     * @return      
     * Result
      */
     @RequestMapping("/getProductDetailList")
     public Result getProductDetailList(BaseDto baseDto,YhsjProduceDetail produceDetail) {
     	logger.info("开始获取生产明细列表");
     	QueryPage page = new QueryPage();
 		page.setPageNo(baseDto.getPageNo());
 		page.setPageSize(baseDto.getPageSize());
 		
        Condition condition = new Condition(YhsjProduceDetail.class);
        Example.Criteria criteria = condition.createCriteria();
        if(produceDetail!=null){
        	if(produceDetail.getProduceDetailSpecName()!=null&&produceDetail.getProduceDetailSpecName().compareTo("")>0){
        		criteria.andCondition(" produce_detail_spec_name like '%" + produceDetail.getProduceDetailSpecName() + "%'");
        	}
        }
 		int totalCount = produceDetailService.countByCondition(condition);
 		List<YhsjProduceDetail> list = new ArrayList();
 		if(totalCount>0){
 			String orderStr = " produce_detail_create_time asc ";
 			if(baseDto.getSortName()!=null&&!"".equals(baseDto.getSortName())){
 				orderStr = baseDto.getSortName();
 				if(baseDto.getOrderName()!=null&&!"".equals(baseDto.getOrderName())){
 					orderStr = orderStr + " "+ baseDto.getOrderName();
 				}else{
 					orderStr = orderStr + " asc ";//默认
 				}
 			}
 			condition.setOrderByClause(orderStr + " limit "+page.getOffset()+","+page.getPageSize());
 			list = produceDetailService.findByCondition(condition);
 		}
         Result result = ResultGenerator.genSuccessResult();
         result.setTotalCount(totalCount);
         result.setItems(list);
         logger.info("结束获取生产明细");
         return result;
     }

}
