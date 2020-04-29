package com.nanwang.project.web;

import com.nanwang.project.core.Result;
import com.nanwang.project.core.ResultCode;
import com.nanwang.project.core.ResultGenerator;
import com.nanwang.project.model.EAnswer;
import com.nanwang.project.model.EAnswerDetail;
import com.nanwang.project.model.EQuestion;
import com.nanwang.project.model.EType;
import com.nanwang.project.model.EUserAnswer;
import com.nanwang.project.pojo.LoginUser;
import com.nanwang.project.service.EAnswerDetailService;
import com.nanwang.project.service.EAnswerService;
import com.nanwang.project.service.EQuestionService;
import com.nanwang.project.service.EUserAnswerService;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: EAnswerController
 * @Description: TODO(描述)
 * @author chenjinmin
 * @date 2020-04-26 08:38:46
 */
@RestController
@RequestMapping("/e/answer")
public class EAnswerController {
	private static final Logger logger = LoggerFactory.getLogger(EAnswerController.class);
	@Resource
	private EAnswerService eAnswerService;
	@Resource
	private EAnswerDetailService eAnswerDetailService;
    @Resource
    private EQuestionService eQuestionService;
    @Resource
	private EUserAnswerService eUserAnswerService;

	@RequestMapping("/add")
	public Result add(EAnswer eAnswer) {
		eAnswerService.save(eAnswer);
		return ResultGenerator.genSuccessResult();
	}

	@RequestMapping("/delete")
	public Result delete(@RequestParam Integer id) {
		eAnswerService.deleteById(id);
		return ResultGenerator.genSuccessResult();
	}

	@RequestMapping("/update")
	public Result update(EAnswer eAnswer) {
		eAnswerService.update(eAnswer);
		return ResultGenerator.genSuccessResult();
	}

	@RequestMapping("/detail")
	public Result detail(@RequestParam Integer id) {
		EAnswer eAnswer = eAnswerService.findById(id);
		return ResultGenerator.genSuccessResult(eAnswer);
	}

	@RequestMapping("/list")
	public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
		PageHelper.startPage(page, size);
		List<EAnswer> list = eAnswerService.findAll();
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@RequestMapping("/getAnswerList")
	public JqgridResponse<EAnswer> getAnswerList(@RequestParam String page, @RequestParam String rows, EAnswer answer,
			HttpServletRequest request) {
		JqgridResponse<EAnswer> response = new JqgridResponse<EAnswer>();
		int size = Integer.parseInt(rows == null ? "10" : rows);
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(EAnswer.class);
			Example.Criteria criteria = condition.createCriteria();

			if (answer.getAnswerTitle() != null && !answer.getAnswerTitle().equals("")) {
				criteria.andCondition(" Answer_title like '%" + answer.getAnswerTitle() + "%'");
			}
			if (answer.getAnswerUsername() != null && !answer.getAnswerUsername().equals("")) {
				criteria.andCondition(" answer_username like '%" + answer.getAnswerUsername() + "%'");
			}
			PageHelper.startPage(Integer.parseInt(page), size);
			
			HttpSession session = request.getSession();
			LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
			if(loginUser.getUserId()!=null&&loginUser.getUserId().equals("192441ffba704373a5fd9c1a6fdc522g")) {
				
			}else {
				criteria.andCondition(" answer_create_userid = '" + loginUser.getUserId() + "' ");
			}
			
			condition.setOrderByClause("answer_create_time desc");

			List<EAnswer> list = eAnswerService.findByCondition(condition);
			PageInfo<EAnswer> pageInfo = new PageInfo<EAnswer>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}
		return response;
	}

	@RequestMapping("/getAnswerDetail")
	@ResponseBody
	public Result getAnswerDetail(HttpServletRequest request, String answer_id) {
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult("session is not vaid");
		} else {
			if (answer_id == null || answer_id == "") {
				return ResultGenerator.genFailResult("answer_id为空");
			}
			Condition condition = new Condition(EAnswer.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" answer_id='" + answer_id + "'");
			List<EAnswer> eAnswers = eAnswerService.findByCondition(condition);
			return ResultGenerator.genSuccessResult(eAnswers.get(0));
		}
	}
	

	@RequestMapping("/getAnswerDetailByAid")
	@ResponseBody
	public Result getAnswerDetailByAid(HttpServletRequest request, String aid,String etype) {
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
			return ResultGenerator.genSuccessResult(eAnswers.get(0));
		}
	}

	@RequestMapping("/saveAnswer")
	public Result saveAnswer(HttpServletRequest request, EAnswer info) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult(null);
		} else {
			try {
				if (info.getAnswerId() == null || info.getAnswerId().compareTo("") == 0) {
					info.setAnswerId(UUIDGenerator.getUUID2());
					info.setAnswerCreateTime(new Date());
					info.setAnswerStatus(0);
					HttpSession session = request.getSession();
					LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
					info.setAnswerCreateUserid(loginUser.getUserId());
					info.setAnswerCreateUser(loginUser.getUserName());
					//info.setAnswerType(0);
					
//					Condition condition11 = new Condition(EType.class);
//					Example.Criteria criteria11 = condition11.createCriteria();
//					criteria11.andCondition(" answer_create_userid = '"+loginUser.getUserId()+"' ");
//					criteria11.andCondition(" answer_userid = '"+info.getAnswerUserid()+"' ");
//					criteria11.andCondition(" answer_date = '"+info.getAnswerDate()+"' ");
//
//					List<EAnswer> list11 = eAnswerService.findByCondition(condition11);
//					if(list11!=null&&list11.size()>0) {
//						return ResultGenerator.genFailResult("新增失败,该被评人已经考核过！");
//					}
					if(info.getAnswerType()==null) {
						return ResultGenerator.genFailResult("分类不能为空！");
					}
					
					Condition condition = new Condition(EType.class);
					Example.Criteria criteria = condition.createCriteria();
					criteria.andCondition(" Question_status = 0 ");
					criteria.andCondition(" QUESTION_TYPES = '" + info.getAnswerType() + "'");
					condition.setOrderByClause(" QUESTION_TYPEID desc, Question_CREATE_TIME desc");

					List<EQuestion> list = eQuestionService.findByCondition(condition);
					if(list!=null&&list.size()>0) {
						for(int i =0;i<list.size();i++) {
							EQuestion eQuestion = list.get(i);
							System.out.println(eQuestion.getQuestionId());
							
							String str1 = (String)request.getParameter("optionsRadios"+eQuestion.getQuestionId());
							System.out.println(">>>:"+str1);
							BigDecimal detailScore = new BigDecimal(str1);
							EAnswerDetail eAnswerDetail = new EAnswerDetail();
							eAnswerDetail.setDetailId(UUIDGenerator.getUUID2());
							eAnswerDetail.setDetailCreateTime(new Date());
							eAnswerDetail.setDetailQuestionid(eQuestion.getQuestionId());
							eAnswerDetail.setDetailQuestiontitle(eQuestion.getQuestionTitle());
							eAnswerDetail.setDetailQuestiontypeid(eQuestion.getQuestionTypeid());
							eAnswerDetail.setDetailQuestiontype(eQuestion.getQuestionTypename());
							eAnswerDetail.setDetailQuestionweightd(eQuestion.getQuestionWeighted());
							eAnswerDetail.setDetailScore(detailScore);
							eAnswerDetail.setDetailAnswerid(info.getAnswerId());
							this.eAnswerDetailService.save(eAnswerDetail);
						}
					}
					
					if(info.getUseranswerid()!=null&&!info.getUseranswerid().equals("")) {
						EUserAnswer eUserAnswer = eUserAnswerService.findById(info.getUseranswerid());
						if(info.getAnswerType()==1) {
							System.out.println("-----------上级判断-----------");
							String checkStr = info.getAnswerType()+","+loginUser.getUserId();
							if(eUserAnswer.getaYiScores()!=null&&!"".equals(eUserAnswer.getaYiScores())) {
								if(eUserAnswer.getaYiScores().indexOf(checkStr)>0) {//表示存在，已评价过
									return ResultGenerator.genFailResult("已考核过该被评人！");
								}else {
									String scoreStr = eUserAnswer.getaYiScores()+";"+checkStr+","+loginUser.getUserName()+","+info.getAnswerTotalScore();
									eUserAnswer.setaYiScores(scoreStr);
								}
							}else {
								String scoreStr = checkStr+","+loginUser.getUserName()+","+info.getAnswerTotalScore();
								eUserAnswer.setaYiScores(scoreStr);
							}
							
							//判断是否全部评论
							if(eUserAnswer.getaYiAdminid()!=null&&!"".equals(eUserAnswer.getaYiAdminid())) {
								boolean bl = true;
								if(eUserAnswer.getaYiAdminid().indexOf(",")>-1) {
									String[] s = eUserAnswer.getaYiAdminid().split(",");
									for(int i=0;i<s.length;i++) {
										if(eUserAnswer.getaYiScores().indexOf(s[i])>-1) {//存在
											
										}else {//不存在，表示未评论
											bl = false;//未完成
										}
									}
								}else {
									if(eUserAnswer.getaYiScores().indexOf(eUserAnswer.getaYiAdminid())>-1) {
										
									}else {
										bl = false;//未完成
									}
								}
								
								if(bl) {//计算上级总分
									System.out.println("-----------计算上级总分-----------");
									BigDecimal score = BigDecimal.ZERO;
									if(eUserAnswer.getaYiScores().indexOf(";")>-1) {
										String[] yiss = eUserAnswer.getaYiScores().split(";");
										BigDecimal ascore = BigDecimal.ZERO;
										for(int i=0;i<yiss.length;i++) {
											String yisss = yiss[i];
											if(yisss.indexOf(",")>-1) {
												String[] yi = yisss.split(",");
												BigDecimal aascore = new  BigDecimal(yi[3]);
												score =score.add(aascore);
											}
										}
										score =score.divide(new  BigDecimal(yiss.length));
									}else {
										if(eUserAnswer.getaYiScores().indexOf(",")>-1) {
											String[] yi = eUserAnswer.getaYiScores().split(",");
											BigDecimal aascore = new  BigDecimal(yi[3]);
											score =score.add(aascore);
										}
									}
									eUserAnswer.setaYiStatus(1);
									eUserAnswer.setaYiScore(score);
								}
							}else {
								eUserAnswer.setaYiStatus(1);
								eUserAnswer.setaYiScore(BigDecimal.ZERO);
							}
						}else if(info.getAnswerType()==2) {
							System.out.println("-----------评级判断-----------");
							String checkStr = info.getAnswerType()+","+loginUser.getUserId();
							if(eUserAnswer.getaErScores()!=null&&!"".equals(eUserAnswer.getaErScores())) {
								if(eUserAnswer.getaErScores().indexOf(checkStr)>0) {//表示存在，已评价过
									return ResultGenerator.genFailResult("已考核过该被评人！");
								}else {
									String scoreStr = eUserAnswer.getaErScores()+";"+checkStr+","+loginUser.getUserName()+","+info.getAnswerTotalScore();
									eUserAnswer.setaErScores(scoreStr);
								}
							}else {
								String scoreStr = checkStr+","+loginUser.getUserName()+","+info.getAnswerTotalScore();
								eUserAnswer.setaErScores(scoreStr);
							}
							
							//判断是否全部评论
							if(eUserAnswer.getaErAdminid()!=null&&!"".equals(eUserAnswer.getaErAdminid())) {
								boolean bl = true;
								if(eUserAnswer.getaErAdminid().indexOf(",")>-1) {
									String[] s = eUserAnswer.getaErAdminid().split(",");
									for(int i=0;i<s.length;i++) {
										if(eUserAnswer.getaErScores().indexOf(s[i])>-1) {//存在
											
										}else {//不存在，表示未评论
											bl = false;//未完成
										}
									}
								}else {
									if(eUserAnswer.getaErScores().indexOf(eUserAnswer.getaErAdminid())>-1) {
										
									}else {
										bl = false;//未完成
									}
								}
								
								if(bl) {//计算上级总分
									System.out.println("-----------计算平级总分-----------");
									BigDecimal score = BigDecimal.ZERO;
									if(eUserAnswer.getaErScores().indexOf(";")>-1) {
										String[] Erss = eUserAnswer.getaErScores().split(";");
										BigDecimal ascore = BigDecimal.ZERO;
										for(int i=0;i<Erss.length;i++) {
											String Ersss = Erss[i];
											if(Ersss.indexOf(",")>-1) {
												String[] Er = Ersss.split(",");
												BigDecimal aascore = new  BigDecimal(Er[3]);
												score =score.add(aascore);
											}
										}
										score =score.divide(new  BigDecimal(Erss.length));
									}else {
										if(eUserAnswer.getaErScores().indexOf(",")>-1) {
											String[] Er = eUserAnswer.getaErScores().split(",");
											BigDecimal aascore = new  BigDecimal(Er[3]);
											score =score.add(aascore);
										}
									}
									eUserAnswer.setaErStatus(1);
									eUserAnswer.setaErScore(score);
								}
							}else {
								eUserAnswer.setaErStatus(1);
								eUserAnswer.setaErScore(BigDecimal.ZERO);
							}
						}else if(info.getAnswerType()==3) {
							System.out.println("-----------下级判断-----------");
							String checkStr = info.getAnswerType()+","+loginUser.getUserId();
							if(eUserAnswer.getaSanScores()!=null&&!"".equals(eUserAnswer.getaSanScores())) {
								if(eUserAnswer.getaSanScores().indexOf(checkStr)>0) {//表示存在，已评价过
									return ResultGenerator.genFailResult("已考核过该被评人！");
								}else {
									String scoreStr = eUserAnswer.getaSanScores()+";"+checkStr+","+loginUser.getUserName()+","+info.getAnswerTotalScore();
									eUserAnswer.setaSanScores(scoreStr);
								}
							}else {
								String scoreStr = checkStr+","+loginUser.getUserName()+","+info.getAnswerTotalScore();
								eUserAnswer.setaSanScores(scoreStr);
							}
							
							//判断是否全部评论
							if(eUserAnswer.getaSanAdminid()!=null&&!"".equals(eUserAnswer.getaSanAdminid())) {
								boolean bl = true;
								if(eUserAnswer.getaSanAdminid().indexOf(",")>-1) {
									String[] s = eUserAnswer.getaSanAdminid().split(",");
									for(int i=0;i<s.length;i++) {
										if(eUserAnswer.getaSanScores().indexOf(s[i])>-1) {//存在
											
										}else {//不存在，表示未评论
											bl = false;//未完成
										}
									}
								}else {
									if(eUserAnswer.getaSanScores().indexOf(eUserAnswer.getaSanAdminid())>-1) {
										
									}else {
										bl = false;//未完成
									}
								}
								
								if(bl) {//计算上级总分
									System.out.println("-----------计算上级总分-----------");
									BigDecimal score = BigDecimal.ZERO;
									if(eUserAnswer.getaSanScores().indexOf(";")>-1) {
										String[] Sanss = eUserAnswer.getaSanScores().split(";");
										BigDecimal ascore = BigDecimal.ZERO;
										for(int i=0;i<Sanss.length;i++) {
											String Sansss = Sanss[i];
											if(Sansss.indexOf(",")>-1) {
												String[] San = Sansss.split(",");
												BigDecimal aascore = new  BigDecimal(San[3]);
												score =score.add(aascore);
											}
										}
										score =score.divide(new  BigDecimal(Sanss.length));
									}else {
										if(eUserAnswer.getaSanScores().indexOf(",")>-1) {
											String[] San = eUserAnswer.getaSanScores().split(",");
											BigDecimal aascore = new  BigDecimal(San[3]);
											score =score.add(aascore);
										}
									}
									eUserAnswer.setaSanSatatus(1);
									eUserAnswer.setaSanScore(score);
								}
							}else {
								eUserAnswer.setaSanSatatus(1);
								eUserAnswer.setaSanScore(BigDecimal.ZERO);
							}
						}
						if(eUserAnswer.getaYiStatus()!=null&&eUserAnswer.getaYiStatus()==1
							&&eUserAnswer.getaErStatus()!=null&&eUserAnswer.getaErStatus()==1
							&&eUserAnswer.getaSanSatatus()!=null&&eUserAnswer.getaSanSatatus()==1) {
							eUserAnswer.setaStatus(1);
						}
						eUserAnswerService.update(eUserAnswer);
					}
					
					int r = eAnswerService.save(info);
					if (r > 0) {

					} else {
						result = ResultGenerator.genFailResult("新增失败");
					}
				} else {
					Condition proCondition = new Condition(EAnswer.class);
					Example.Criteria proCriteria = proCondition.createCriteria();
					proCriteria.andCondition(" Answer_id='" + info.getAnswerId() + "'");
					List<EAnswer> eAnswers = eAnswerService.findByCondition(proCondition);
					EAnswer eAnswer = eAnswers.get(0);
					MyBeanUtils.copyBeanNotNull2Bean(info, eAnswer);
					int r = eAnswerService.update(eAnswer);
					if (r > 0) {

					} else {
						result = ResultGenerator.genFailResult("修改失败");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setCode(ResultCode.FAIL);
				result.setMessage("新增异常");
			}
			return result;

		}
	}
	
}
