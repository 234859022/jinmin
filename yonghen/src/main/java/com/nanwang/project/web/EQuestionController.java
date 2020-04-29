package com.nanwang.project.web;
import com.nanwang.project.core.Result;
import com.nanwang.project.core.ResultCode;
import com.nanwang.project.core.ResultGenerator;
import com.nanwang.project.model.EQuestion;
import com.nanwang.project.model.EType;
import com.nanwang.project.model.EUser;
import com.nanwang.project.pojo.LoginUser;
import com.nanwang.project.service.EQuestionService;
import com.nanwang.project.service.ETypeService;
import com.nanwang.project.utils.JqgridResponse;
import com.nanwang.project.utils.MyBeanUtils;
import com.nanwang.project.utils.SessinoManager;
import com.nanwang.project.utils.UUIDGenerator;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: EQuestionController
 * @Description: TODO(描述)
 * @author chenjinmin
 * @date 2020-04-26 08:39:09
 */
@RestController
@RequestMapping("/e/question")
public class EQuestionController {
	private static final Logger logger = LoggerFactory.getLogger(EQuestionController.class);
    @Resource
    private EQuestionService eQuestionService;
    @Resource
    private ETypeService eTypeService;

    @RequestMapping("/add")
    public Result add(EQuestion eQuestion) {
        eQuestionService.save(eQuestion);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        eQuestionService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(EQuestion eQuestion) {
        eQuestionService.update(eQuestion);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        EQuestion eQuestion = eQuestionService.findById(id);
        return ResultGenerator.genSuccessResult(eQuestion);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<EQuestion> list = eQuestionService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
    @RequestMapping("/getQuestionList")
	public JqgridResponse<EQuestion> getQuestionList(@RequestParam String page, @RequestParam String rows,
			@RequestParam(defaultValue = "") String question_title,@RequestParam(defaultValue = "") String question_type,
			String questionTypes,HttpServletRequest request) {
		JqgridResponse<EQuestion> response = new JqgridResponse<EQuestion>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(EQuestion.class);
			Example.Criteria criteria = condition.createCriteria();

			if (question_title != null && !question_title.equals("")) {
				criteria.andCondition(" Question_title like '%" + question_title + "%'");
			}
			if (question_type != null && !question_type.equals("")) {
				criteria.andCondition(" QUESTION_TYPEID = '" + question_type + "'");
			}
			if (questionTypes != null && !questionTypes.equals("")) {
				criteria.andCondition(" QUESTION_TYPES = '" + questionTypes + "'");
			}
			criteria.andCondition(" Question_status = 0 ");
			condition.setOrderByClause("Question_CREATE_TIME desc");
			PageHelper.startPage(Integer.parseInt(page), size);
			
			List<EQuestion> list = eQuestionService.findByCondition(condition);
			PageInfo<EQuestion> pageInfo = new PageInfo<EQuestion>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}
		return response;
	}
    
	
	@RequestMapping("/getQuestionDetail")
    @ResponseBody
    public Result getQuestionDetail(HttpServletRequest request,String question_id) {
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	if (question_id == null || question_id == "") {
   				return ResultGenerator.genFailResult("Question_id为空");
   			}
			Condition condition = new Condition(EQuestion.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" Question_id='"+question_id+"'");
			List<EQuestion> eQuestions = eQuestionService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(eQuestions.get(0));
		}
    }
	
	@RequestMapping("/saveQuestion")
   	public Result saveQuestion(HttpServletRequest request, EQuestion info,String control) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			try{
   				int i=0;
   				if(info.getQuestionTypeid()!=null&&!"".equals(info.getQuestionTypeid())) {
   					Condition condition = new Condition(EType.class);
   					Example.Criteria criteria = condition.createCriteria();
   					criteria.andCondition(" type_id='"+info.getQuestionTypeid()+"'");
   					List<EType> etypes = eTypeService.findByCondition(condition);
   					EType etype = etypes.get(0);
   					info.setQuestionTypename(etype.getTypeName());
   				}
   				
   				if(info.getQuestionId()==null||info.getQuestionId().compareTo("")==0){
   					
   					info.setQuestionId(UUIDGenerator.getUUID2());
//   					info.setAdvertisementCreateTime(new Date());
   					info.setQuestionCreateTime(new Date());
   					info.setQuestionStatus(0);
   					HttpSession session = request.getSession();
					LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
					info.setQuestionCreateUser(loginUser.getUserId());
					
   					int r = eQuestionService.save(info);
   					if (r>0) {
   						
					}else{
						result = ResultGenerator.genFailResult("新增失败");
					}
   				}else{
   					Condition proCondition = new Condition(EQuestion.class);
   			        Example.Criteria proCriteria = proCondition.createCriteria();
   			        proCriteria.andCondition(" Question_id='"+info.getQuestionId()+"'");
   			        List<EQuestion> eQuestions = eQuestionService.findByCondition(proCondition);	
   			        EQuestion eQuestion = eQuestions.get(0);
   					MyBeanUtils.copyBeanNotNull2Bean(info, eQuestion);
   					int r = eQuestionService.update(eQuestion);
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
	
	@RequestMapping("/deleteQuestion")
   	public Result deleteQuestion(HttpServletRequest request, String question_id) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (question_id == null || question_id.equals("")) {
   				return ResultGenerator.genFailResult("Question_id为空");
   			}
   			Condition condition = new Condition(EQuestion.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" question_id='"+question_id+"'");
   			List<EQuestion> eQuestions = eQuestionService.findByCondition(condition);
   		    EQuestion eQuestion = eQuestions.get(0);
   		    eQuestion.setQuestionStatus(1);
   			int r = eQuestionService.update(eQuestion);
   			return ResultGenerator.genSuccessResult("删除成功");
   		}
   	}
	
	
	@RequestMapping("/getQuestions")
	@ResponseBody
	public Result getQuestions(HttpServletRequest request,String questionTypes) {
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult("session is not vaid");
		} else {
			Condition condition = new Condition(EType.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" Question_status = 0 ");
			if(questionTypes!=null&&!"".equals(questionTypes)) {
				criteria.andCondition(" QUESTION_TYPES = '" + questionTypes + "'");
			}
			condition.setOrderByClause(" QUESTION_TYPEID desc, Question_CREATE_TIME desc");

			List<EQuestion> list = eQuestionService.findByCondition(condition);
			return ResultGenerator.genSuccessResult(list);
		}
	}
}
