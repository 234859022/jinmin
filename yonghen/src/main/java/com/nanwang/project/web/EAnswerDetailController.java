package com.nanwang.project.web;
import com.nanwang.project.core.Result;
import com.nanwang.project.core.ResultGenerator;
import com.nanwang.project.model.EAnswer;
import com.nanwang.project.model.EAnswerDetail;
import com.nanwang.project.model.EQuestion;
import com.nanwang.project.model.EType;
import com.nanwang.project.pojo.LoginUser;
import com.nanwang.project.service.EAnswerDetailService;
import com.nanwang.project.service.EAnswerService;
import com.nanwang.project.service.EQuestionService;
import com.nanwang.project.utils.SessinoManager;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EAnswerDetailController
 * @Description: TODO(描述)
 * @author chenjinmin
 * @date 2020-04-26 08:38:59
 */
@RestController
@RequestMapping("/e/answer/detail")
public class EAnswerDetailController {
	private static final Logger logger = LoggerFactory.getLogger(EAnswerController.class);
    @Resource
    private EQuestionService eQuestionService;
    @Resource
    private EAnswerDetailService eAnswerDetailService;
	@Resource
	private EAnswerService eAnswerService;

    @RequestMapping("/add")
    public Result add(EAnswerDetail eAnswerDetail) {
        eAnswerDetailService.save(eAnswerDetail);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        eAnswerDetailService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(EAnswerDetail eAnswerDetail) {
        eAnswerDetailService.update(eAnswerDetail);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        EAnswerDetail eAnswerDetail = eAnswerDetailService.findById(id);
        return ResultGenerator.genSuccessResult(eAnswerDetail);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<EAnswerDetail> list = eAnswerDetailService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    

	
	@RequestMapping("/getAnswerDetails")
	@ResponseBody
	public Result getAnswers(HttpServletRequest request,String answerid) {
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult("session is not vaid");
		} else {
			Condition condition = new Condition(EType.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" detail_answerid = '"+answerid+"' ");
			condition.setOrderByClause(" detail_questiontypeid desc, detail_create_time desc");

			List<EAnswerDetail> list = eAnswerDetailService.findByCondition(condition);
			return ResultGenerator.genSuccessResult(list);
		}
	}
	
	@RequestMapping("/getAnswerDetailsByAid")
	@ResponseBody
	public Result getAnswerDetailsByAid(HttpServletRequest request,String aid,String etype) {
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult("session is not vaid");
		} else {
			Condition condition2 = new Condition(EAnswer.class);
			Example.Criteria criteria2 = condition2.createCriteria();
			criteria2.andCondition(" useranswerid='" + aid + "'");
			criteria2.andCondition(" answer_type=" + etype + " ");
			HttpSession session = request.getSession();
			LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
			criteria2.andCondition(" answer_create_userid='" + loginUser.getUserId() + "'");
			
			List<EAnswer> eAnswers = eAnswerService.findByCondition(condition2);
			if(eAnswers!=null&&eAnswers.size()>0) {
				EAnswer eAnswer = eAnswers.get(0);
				Condition condition = new Condition(EType.class);
				Example.Criteria criteria = condition.createCriteria();
				criteria.andCondition(" detail_answerid = '"+eAnswer.getAnswerId()+"' ");
				condition.setOrderByClause(" detail_questiontypeid desc, detail_create_time desc");
				List<EAnswerDetail> list = eAnswerDetailService.findByCondition(condition);
				return ResultGenerator.genSuccessResult(list);
			}else {
				return ResultGenerator.genSuccessResult(null);
			}
		}
	}
	
	@RequestMapping("/getAnswerDetailsAllByAid")
	@ResponseBody
	public Result getAnswerDetailsAllByAid(HttpServletRequest request,String aid) {
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult("session is not vaid");
		} else {
//			String sql = "SELECT e.*,s1.detail_score score1,s2.detail_score score2,s3.detail_score score3\n" + 
//					"  FROM [NW_EXAMINE].[dbo].[E_QUESTION] e\n" + 
//					"  left join \n" + 
//					"  (SELECT * FROM [NW_EXAMINE].[dbo].E_ANSWER_DETAIL where detail_answerid \n" + 
//					"    in (SELECT answer_id FROM [NW_EXAMINE].[dbo].E_ANSWER where useranswerid='"+aid+"' and answer_type =1)) s1\n" + 
//					"	on e.question_id = s1.detail_questionid \n" + 
//					"  left join \n" + 
//					"  (SELECT * FROM [NW_EXAMINE].[dbo].E_ANSWER_DETAIL where detail_answerid \n" + 
//					"    in (SELECT answer_id FROM [NW_EXAMINE].[dbo].E_ANSWER where useranswerid='"+aid+"' and answer_type =2)) s2\n" + 
//					"	on e.question_id = s2.detail_questionid \n" + 
//					" left join \n" + 
//					"  (SELECT * FROM [NW_EXAMINE].[dbo].E_ANSWER_DETAIL where detail_answerid \n" + 
//					"    in (SELECT answer_id FROM [NW_EXAMINE].[dbo].E_ANSWER where useranswerid='"+aid+"' and answer_type =3)) s3\n" + 
//					"	on e.question_id = s3.detail_questionid ";
			
			
			
//            String sql1 = "SELECT * FROM [NW_EXAMINE].[dbo].E_ANSWER_DETAIL where detail_answerid \n" + 
//            		"    in (SELECT answer_id FROM [NW_EXAMINE].[dbo].E_ANSWER where useranswerid='"+aid+"' and answer_type =1)\n" + 
//            		"	order by detail_answerid";
//            List<Map<String,Object>> list1 = eAnswerService.dynamicSqlQuery(sql1);
//            
//            Map nmap1 = new HashMap();
//            if(list1!=null&&list1.size()>0) {
//            	String s = "";
//            	for(int i=0;i<list1.size();i++) {
//            		Map m1 = (Map)list1.get(i);
//            		String ss = ""+m1.get("detail_answerid");
//            		System.out.println(ss +">>>>>"+m1.get("detail_questionid"));
//            		if(nmap1.get(m1.get("detail_questionid"))!=null) {
//            			String score = nmap1.get(m1.get("detail_questionid"))+"";
//            			nmap1.put(m1.get("detail_questionid"), score + " " +m1.get("detail_score"));
//            		}else {
//            			nmap1.put(m1.get("detail_questionid"), m1.get("detail_score"));
//            		}
//            	}
//            }
//            
//            String sql2 = "SELECT * FROM [NW_EXAMINE].[dbo].E_ANSWER_DETAIL where detail_answerid \n" + 
//            		"    in (SELECT answer_id FROM [NW_EXAMINE].[dbo].E_ANSWER where useranswerid='"+aid+"' and answer_type =2)\n" + 
//            		"	order by detail_answerid";
//            List<Map<String,Object>> list2 = eAnswerService.dynamicSqlQuery(sql2);
//            
//            Map nmap2 = new HashMap();
//            if(list2!=null&&list2.size()>0) {
//            	String s = "";
//            	for(int i=0;i<list2.size();i++) {
//            		Map m1 = (Map)list2.get(i);
//            		String ss = ""+m1.get("detail_answerid");
//            		System.out.println(ss +">>>>>"+m1.get("detail_questionid"));
//            		if(nmap2.get(m1.get("detail_questionid"))!=null) {
//            			String score = nmap2.get(m1.get("detail_questionid"))+"";
//            			nmap2.put(m1.get("detail_questionid"), score + " " +m1.get("detail_score"));
//            		}else {
//            			nmap2.put(m1.get("detail_questionid"), m1.get("detail_score"));
//            		}
//            	}
//            }
//			
//            String sql3 = "SELECT * FROM [NW_EXAMINE].[dbo].E_ANSWER_DETAIL where detail_answerid \n" + 
//            		"    in (SELECT answer_id FROM [NW_EXAMINE].[dbo].E_ANSWER where useranswerid='"+aid+"' and answer_type =3)\n" + 
//            		"	order by detail_answerid";
//			List<Map<String,Object>> list3 = eAnswerService.dynamicSqlQuery(sql3);
//			
//			
//			Map nmap3 = new HashMap();
//            if(list3!=null&&list3.size()>0) {
//            	String s = "";
//            	for(int i=0;i<list3.size();i++) {
//            		Map m1 = (Map)list3.get(i);
//            		String ss = ""+m1.get("detail_answerid");
//            		System.out.println(ss +">>>>>"+m1.get("detail_questionid"));
//            		if(nmap3.get(m1.get("detail_questionid"))!=null) {
//            			String score = nmap3.get(m1.get("detail_questionid"))+"";
//            			nmap3.put(m1.get("detail_questionid"), score + " " +m1.get("detail_score"));
//            		}else {
//            			nmap3.put(m1.get("detail_questionid"), m1.get("detail_score"));
//            		}
//            	}
//            }
//			
//			Map map = new HashMap();
//			map.put("a1", list1);
//			map.put("a2", list2);
//			map.put("a3", list3);
//			map.put("m1", nmap1);
//			map.put("m2", nmap2);
//			map.put("m3", nmap3);
			
			
			  String sql1 = "SELECT * FROM E_ANSWER_DETAIL where detail_answerid \n" + 
	            		"    in (SELECT answer_id FROM E_ANSWER where useranswerid='"+aid+"' )\n" + 
	            		"	order by detail_answerid";
	            List<Map<String,Object>> list1 = eAnswerService.dynamicSqlQuery(sql1);
	            
	            Map nmap1 = new HashMap();
	            if(list1!=null&&list1.size()>0) {
	            	String s = "";
	            	for(int i=0;i<list1.size();i++) {
	            		Map m1 = (Map)list1.get(i);
	            		String ss = ""+m1.get("detail_answerid");
	            		System.out.println(ss +">>>>>"+m1.get("detail_questionid"));
	            		if(nmap1.get(m1.get("detail_questionid"))!=null) {
	            			String score = nmap1.get(m1.get("detail_questionid"))+"";
	            			nmap1.put(m1.get("detail_questionid"), score + " " +m1.get("detail_score"));
	            		}else {
	            			nmap1.put(m1.get("detail_questionid"), m1.get("detail_score"));
	            		}
	            	}
	            }
	            
	            String sql11 = "SELECT e.* FROM E_QUESTION e where QUESTION_TYPES =1 and Question_status = 0 ";
	            List<Map<String,Object>> list11 = eAnswerService.dynamicSqlQuery(sql11);
	            List newList1 = new ArrayList();
	            if(list11!=null&&list11.size()>0) {
	            	for(int i=0;i<list11.size();i++) {
	            		Map m11 = (Map)list11.get(i);
	            		String s = m11.get("QUESTION_ID")+"";
	            		System.out.println("QUESTION_ID:"+s);
	            		m11.put("score", nmap1.get(s));
	            		newList1.add(m11);
	            	}
	            }
	            
	            String sql12 = "SELECT e.* FROM E_QUESTION e where QUESTION_TYPES =2 and Question_status = 0 ";
	            List<Map<String,Object>> list12 = eAnswerService.dynamicSqlQuery(sql12);
	            List newList2 = new ArrayList();
	            if(list12!=null&&list12.size()>0) {
	            	for(int i=0;i<list12.size();i++) {
	            		Map m11 = (Map)list12.get(i);
	            		String s = m11.get("QUESTION_ID")+"";
	            		System.out.println("QUESTION_ID:"+s);
	            		m11.put("score", nmap1.get(s));
	            		newList2.add(m11);
	            	}
	            }
	            
	            String sql13 = "SELECT e.* FROM E_QUESTION e where QUESTION_TYPES =3 and Question_status = 0 ";
	            List<Map<String,Object>> list13 = eAnswerService.dynamicSqlQuery(sql13);
	            List newList3 = new ArrayList();
	            if(list13!=null&&list13.size()>0) {
	            	for(int i=0;i<list13.size();i++) {
	            		Map m11 = (Map)list13.get(i);
	            		String s = m11.get("QUESTION_ID")+"";
	            		System.out.println("QUESTION_ID:"+s);
	            		m11.put("score", nmap1.get(s));
	            		newList3.add(m11);
	            	}
	            }
	            
	            
				Map map = new HashMap();
				map.put("a1", newList1);
				map.put("a2", newList2);
				map.put("a3", newList3);
			return ResultGenerator.genSuccessResult(map);
		
		}
	}
}
