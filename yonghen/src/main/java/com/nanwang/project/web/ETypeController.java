package com.nanwang.project.web;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanwang.project.core.Result;
import com.nanwang.project.core.ResultCode;
import com.nanwang.project.core.ResultGenerator;
import com.nanwang.project.model.EType;
import com.nanwang.project.pojo.LoginUser;
import com.nanwang.project.service.ETypeService;
import com.nanwang.project.utils.JqgridResponse;
import com.nanwang.project.utils.MyBeanUtils;
import com.nanwang.project.utils.SessinoManager;
import com.nanwang.project.utils.UUIDGenerator;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: ETypeController
 * @Description: TODO(描述)
 * @author chenjinmin
 * @date 2020-04-26 08:39:18
 */
@RestController
@RequestMapping("/e/type")
public class ETypeController {
	private static final Logger logger = LoggerFactory.getLogger(ETypeController.class);
    @Resource
    private ETypeService eTypeService;

    @RequestMapping("/add")
    public Result add(EType eType) {
        eTypeService.save(eType);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        eTypeService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(EType eType) {
        eTypeService.update(eType);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        EType eType = eTypeService.findById(id);
        return ResultGenerator.genSuccessResult(eType);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<EType> list = eTypeService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
    @RequestMapping("/getTypeList")
	public JqgridResponse<EType> getTypeList(@RequestParam String page, @RequestParam String rows,
			@RequestParam(defaultValue = "") String name,HttpServletRequest request) {
		JqgridResponse<EType> response = new JqgridResponse<EType>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(EType.class);
			Example.Criteria criteria = condition.createCriteria();

			if (name != null && !name.equals("")) {
				criteria.andCondition(" type_name like '%" + name + "%'");
			}
			criteria.andCondition(" type_status = 0 ");
			condition.setOrderByClause("TYPE_CREATE_TIME desc");
			PageHelper.startPage(Integer.parseInt(page), size);
			
			List<EType> list = eTypeService.findByCondition(condition);
			PageInfo<EType> pageInfo = new PageInfo<EType>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}
		return response;
	}
    
	
	@RequestMapping("/getType")
    @ResponseBody
    public Result getType(HttpServletRequest request) {
// 	    Long comid = (Long)request.getSession().getAttribute("comid");
	
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	        Condition condition = new Condition(EType.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" type_status = 0 ");
			condition.setOrderByClause("TYPE_CREATE_TIME asc");
	        
	        List<EType> list = eTypeService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(list);
	    }
    }
	
	@RequestMapping("/getTypeDetail")
    @ResponseBody
    public Result getTypeDetail(HttpServletRequest request,String type_id) {
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	if (type_id == null || type_id == "") {
   				return ResultGenerator.genFailResult("type_id为空");
   			}
			Condition condition = new Condition(EType.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" type_id='"+type_id+"'");
			List<EType> etypes = eTypeService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(etypes.get(0));
		}
    }
	
	@RequestMapping("/saveType")
   	public Result saveType(HttpServletRequest request, EType info,String control) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			try{
   				int i=0;
   				
   				if(info.getTypeId()==null||info.getTypeId().compareTo("")==0){
   					info.setTypeId(UUIDGenerator.getUUID2());
//   					info.setAdvertisementCreateTime(new Date());
   					info.setTypeCreateTime(new Date());
   					info.setTypeStatus(0);
   					HttpSession session = request.getSession();
					LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
					info.setTypeCreateUser(loginUser.getUserId());
   					int r = eTypeService.save(info);
   					if (r>0) {
   						
					}else{
						result = ResultGenerator.genFailResult("新增失败");
					}
   				}else{
   					Condition proCondition = new Condition(EType.class);
   			        Example.Criteria proCriteria = proCondition.createCriteria();
   			        proCriteria.andCondition(" type_id='"+info.getTypeId()+"'");
   			        List<EType> etypes = eTypeService.findByCondition(proCondition);	
   			        EType etype = etypes.get(0);
   					MyBeanUtils.copyBeanNotNull2Bean(info, etype);
   					int r = eTypeService.update(etype);
   					if(r>0){
   						
   					}else{
   						result = ResultGenerator.genFailResult("修改失败");
   					}
   				}
   			}catch(Exception e){
   				e.printStackTrace();
   				result.setCode(ResultCode.FAIL);
   				result.setMessage("新增异常");
   			}
   			return result;
   		
   		}
   	}
	
	@RequestMapping("/deleteType")
   	public Result deleteType(HttpServletRequest request, String type_id) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (type_id == null || type_id.equals("")) {
   				return ResultGenerator.genFailResult("type_id为空");
   			}
   			Condition condition = new Condition(EType.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" type_id='"+type_id+"'");
   			List<EType> etypes = eTypeService.findByCondition(condition);
   		    EType etype = etypes.get(0);
   		    etype.setTypeStatus(1);
   			int r = eTypeService.update(etype);
   			return ResultGenerator.genSuccessResult("删除成功");
   		}
   	}
}
