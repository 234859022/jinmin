package com.nanwang.project.web;

import com.nanwang.project.core.Result;
import com.nanwang.project.core.ResultCode;
import com.nanwang.project.core.ResultGenerator;
import com.nanwang.project.model.EUser;
import com.nanwang.project.model.EUserAnswer;
import com.nanwang.project.pojo.LoginUser;
import com.nanwang.project.service.EUserAnswerService;
import com.nanwang.project.service.EUserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EUserAnswerController
 * @Description: TODO(描述)
 * @author chenjinmin
 * @date 2020-04-26 08:39:32
 */
@RestController
@RequestMapping("/e/user/answer")
public class EUserAnswerController {
	private static final Logger logger = LoggerFactory.getLogger(EUserAnswerController.class);
	@Resource
	private EUserAnswerService eUserAnswerService;
    @Resource
    private EUserService eUserService;

	@RequestMapping("/add")
	public Result add(EUserAnswer eUserAnswer) {
		eUserAnswerService.save(eUserAnswer);
		return ResultGenerator.genSuccessResult();
	}

	@RequestMapping("/delete")
	public Result delete(@RequestParam Integer id) {
		eUserAnswerService.deleteById(id);
		return ResultGenerator.genSuccessResult();
	}

	@RequestMapping("/update")
	public Result update(EUserAnswer eUserAnswer) {
		eUserAnswerService.update(eUserAnswer);
		return ResultGenerator.genSuccessResult();
	}

	@RequestMapping("/detail")
	public Result detail(@RequestParam Integer id) {
		EUserAnswer eUserAnswer = eUserAnswerService.findById(id);
		return ResultGenerator.genSuccessResult(eUserAnswer);
	}

	@RequestMapping("/list")
	public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
		PageHelper.startPage(page, size);
		List<EUserAnswer> list = eUserAnswerService.findAll();
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@RequestMapping("/getUserAnswerList")
	public JqgridResponse<EUserAnswer> getUserAnswerList(@RequestParam String page, @RequestParam String rows,String isOne,
			EUserAnswer userAnswer, HttpServletRequest request) {
		JqgridResponse<EUserAnswer> response = new JqgridResponse<EUserAnswer>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(EUserAnswer.class);
			Example.Criteria criteria = condition.createCriteria();

			if (userAnswer.getaUserid() != null && !userAnswer.getaUserid().equals("")) {
				criteria.andCondition(" A_USERID = '" + userAnswer.getaUserid() + "'");
			}
			if (userAnswer.getaUsername() != null && !userAnswer.getaUsername().equals("")) {
				criteria.andCondition(" A_USERNAME like '%" + userAnswer.getaUsername() + "%'");
			}
			if (userAnswer.getaStatus() != null) {
				criteria.andCondition(" A_STATUS = '" + userAnswer.getaStatus() + "'");
			}
			if (userAnswer.getaStatus() != null) {
				criteria.andCondition(" A_STATUS = '" + userAnswer.getaStatus() + "'");
			}
			if(isOne!=null&&isOne.equals("1")) {//查看自己能评论的人员
				HttpSession session = request.getSession();
				LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
				criteria.andCondition(" (A_YI_ADMINID like '%" + loginUser.getUserId() + "%' or A_ER_ADMINID like '%"+loginUser.getUserId()+"%' or A_SAN_ADMINID like '%"+loginUser.getUserId()+"%' ) ");
				response.setRemark(loginUser.getUserId());
			}
			criteria.andCondition(" A_STATUS != 4 ");
			condition.setOrderByClause(" A_CREATE_TIME desc");
			PageHelper.startPage(Integer.parseInt(page), size);

			List<EUserAnswer> list = eUserAnswerService.findByCondition(condition);
			PageInfo<EUserAnswer> pageInfo = new PageInfo<EUserAnswer>(list);
			
			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}
		return response;
	}

	@RequestMapping("/getUserAnswerDetail")
	@ResponseBody
	public Result getUserAnswerDetail(HttpServletRequest request, String a_id) {
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult("session is not vaid");
		} else {
			if (a_id == null || a_id == "") {
				return ResultGenerator.genFailResult("a_id为空");
			}
			Condition condition = new Condition(EUserAnswer.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" a_id='" + a_id + "'");
			List<EUserAnswer> eUserAnswers = eUserAnswerService.findByCondition(condition);
			return ResultGenerator.genSuccessResult(eUserAnswers.get(0));
		}
	}

	@RequestMapping("/saveUserAnswer")
	public Result saveUserAnswer(HttpServletRequest request, EUserAnswer info, String control) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult(null);
		} else {
			try {
				if (info.getaId() == null || info.getaId().compareTo("") == 0) {

					info.setaId(UUIDGenerator.getUUID2());
//      					info.setAdvertisementCreateTime(new Date());
					info.setaCreateTime(new Date());
					info.setaStatus(0);
					HttpSession session = request.getSession();
					LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
					info.setaCreateUser(loginUser.getNickname());
					info.setaCreateUserid(loginUser.getUserId());
					
//					if(info.getaYiAdminid()!=null&&!"".equals(info.getaYiAdminid())) {
//						if(info.getaYiAdminid().indexOf(",")>0) {
//							String[] str = info.getaYiAdminid().split(",");
//							String zero = "0";
//							for(int i=0;i<str.length-1;i++) {
//								zero +=",0";
//							}
//							info.setaYiScores(zero);
//							info.setaYiCount(str.length);
//						}else {
//							info.setaYiCount(1);
//							info.setaYiScores("0");
//						}
//					}else {
//						info.setaYiCount(0);
//						info.setaYiScores("0");
//					}
//					
//					if(info.getaErAdminid()!=null&&!"".equals(info.getaErAdminid())) {
//						if(info.getaErAdminid().indexOf(",")>0) {
//							String[] str = info.getaErAdminid().split(",");
//							String zero = "0";
//							for(int i=0;i<str.length-1;i++) {
//								zero +=",0";
//							}
//							info.setaErScores(zero);
//							info.setaErCount(str.length);
//						}else {
//							info.setaErCount(1);
//							info.setaErScores("0");
//						}
//					}else {
//						info.setaErCount(0);
//						info.setaErScores("0");
//					}
//					
//					if(info.getaSanAdminid()!=null&&!"".equals(info.getaSanAdminid())) {
//						if(info.getaSanAdminid().indexOf(",")>0) {
//							String[] str = info.getaSanAdminid().split(",");
//							String zero = "0";
//							for(int i=0;i<str.length-1;i++) {
//								zero +=",0";
//							}
//							info.setaSanScores(zero);
//							info.setaErCount(str.length);
//						}else {
//							info.setaSanCount(1);
//							info.setaSanScores("0");
//						}
//					}else {
//						info.setaSanCount(0);
//						info.setaSanScores("0");
//					}
					
//					if(info.getaYiAdminid()==null||"".equals(info.getaYiAdminid())) {
//						info.setaYiStatus(1);
//					}
//					if(info.getaErAdminid()==null||"".equals(info.getaErAdminid())) {
//						info.setaErStatus(1);
//					}
//					if(info.getaSanAdminid()==null||"".equals(info.getaSanAdminid())) {
//						info.setaSanSatatus(1);
//					}
					
					if(info.getaUserid()!=null&&!"".equals(info.getaUserid())) {
						Condition proCondition = new Condition(EUser.class);
	   			        Example.Criteria proCriteria = proCondition.createCriteria();
	   			        proCriteria.andCondition(" user_id='"+info.getaUserid()+"'");
	   			        List<EUser> eUsers = eUserService.findByCondition(proCondition);	
	   			        EUser eUser = eUsers.get(0);
	   			        info.setaUsername(eUser.getUserName());
					}

					int r = eUserAnswerService.save(info);
					if (r > 0) {

					} else {
						result = ResultGenerator.genFailResult("新增失败");
					}
				} else {
					if(info.getaUserid()!=null&&!"".equals(info.getaUserid())) {
						Condition proCondition = new Condition(EUser.class);
	   			        Example.Criteria proCriteria = proCondition.createCriteria();
	   			        proCriteria.andCondition(" user_id='"+info.getaUserid()+"'");
	   			        List<EUser> eUsers = eUserService.findByCondition(proCondition);	
	   			        EUser eUser = eUsers.get(0);
	   			        info.setaUsername(eUser.getUserName());
					}

					Condition proCondition = new Condition(EUserAnswer.class);
					Example.Criteria proCriteria = proCondition.createCriteria();
					proCriteria.andCondition(" A_ID='" + info.getaId() + "'");
					List<EUserAnswer> eUserAnswers = eUserAnswerService.findByCondition(proCondition);
					EUserAnswer eUserAnswer = eUserAnswers.get(0);
					
//					Map map1 = new HashMap();
//					System.out.println("-------map1-------");
//					if(eUserAnswer.getaYiAdminid()!=null&&!"".equals(eUserAnswer.getaYiAdminid())) {
//						//先找出旧的数据，赋值再map
//						if(eUserAnswer.getaYiScores().indexOf(",")>0) {
//							String[] ys= eUserAnswer.getaYiScores().split(",");
//							String[] str= eUserAnswer.getaYiAdminid().split(",");
//							for(int i=0;i<ys.length;i++) {
//								System.out.println(str[i]+"------"+ys[i]);
//								map1.put(str[i], ys[i]);
//							}
//						}else {
//							System.out.println(eUserAnswer.getaYiAdminid()+"---"+ eUserAnswer.getaYiScores());
//							map1.put(eUserAnswer.getaYiAdminid(), eUserAnswer.getaYiScores());
//						}
//					}else {
//						
//					}
//					
//					if(info.getaYiAdminid()!=null&&!"".equals(info.getaYiAdminid())) {
//						System.out.println("-------开始判断-------");
//						if(info.getaYiAdminid().indexOf(",")>0) {
//							String[] str = info.getaYiAdminid().split(",");
//							String zero = "";
//							for(int i=0;i<str.length;i++) {
//								if(map1.get(str[i])!=null&&!"".equals(map1.get(str[i]))) {
//									if(zero.equals("")) {
//										zero = ""+map1.get(str[i]);
//									}else {
//										zero +=","+map1.get(str[i]);
//									}
//								}else {
//									if(zero.equals("")) {
//										zero = "0";
//									}else {
//										zero +=",0";
//									}
//								}
//							}
//							info.setaYiScores(zero);
//							info.setaYiCount(str.length);
//						}else {
//							info.setaYiCount(1);
//							info.setaYiScores(map1.get(info.getaYiAdminid())==null||"".equals(map1.get(info.getaYiAdminid()))?"":""+map1.get(info.getaYiAdminid()));
//						}
//					}
//					
//					
//					Map map2 = new HashMap();
//					System.out.println("-------map2-------");
//					if(eUserAnswer.getaErAdminid()!=null&&!"".equals(eUserAnswer.getaErAdminid())) {
//						//先找出旧的数据，赋值再map
//						if(eUserAnswer.getaErScores().indexOf(",")>0) {
//							String[] ys= eUserAnswer.getaErScores().split(",");
//							String[] str= eUserAnswer.getaErAdminid().split(",");
//							for(int i=0;i<ys.length;i++) {
//								System.out.println(str[i]+"------"+ys[i]);
//								map2.put(str[i], ys[i]);
//							}
//						}else {
//							System.out.println(eUserAnswer.getaErAdminid()+"---"+ eUserAnswer.getaErScores());
//							map2.put(eUserAnswer.getaErAdminid(), eUserAnswer.getaErScores());
//						}
//					}else {
//						
//					}
//					
//					if(info.getaErAdminid()!=null&&!"".equals(info.getaErAdminid())) {
//						System.out.println("-------开始判断-------");
//						if(info.getaErAdminid().indexOf(",")>0) {
//							String[] str = info.getaErAdminid().split(",");
//							String zero = "";
//							for(int i=0;i<str.length;i++) {
//								if(map2.get(str[i])!=null&&!"".equals(map2.get(str[i]))) {
//									if(zero.equals("")) {
//										zero = ""+map2.get(str[i]);
//									}else {
//										zero +=","+map2.get(str[i]);
//									}
//								}else {
//									if(zero.equals("")) {
//										zero = "0";
//									}else {
//										zero +=",0";
//									}
//								}
//							}
//							info.setaErScores(zero);
//							info.setaErCount(str.length);
//						}else {
//							info.setaErCount(1);
//							info.setaErScores(map2.get(info.getaErAdminid())==null||"".equals(map2.get(info.getaErAdminid()))?"":""+map1.get(info.getaErAdminid()));
//						}
//					}
//					
//					Map map3 = new HashMap();
//					System.out.println("-------map3-------");
//					if(eUserAnswer.getaSanAdminid()!=null&&!"".equals(eUserAnswer.getaSanAdminid())) {
//						//先找出旧的数据，赋值再map
//						if(eUserAnswer.getaSanScores().indexOf(",")>0) {
//							String[] ys= eUserAnswer.getaSanScores().split(",");
//							String[] str= eUserAnswer.getaSanAdminid().split(",");
//							for(int i=0;i<ys.length;i++) {
//								System.out.println(str[i]+"------"+ys[i]);
//								map3.put(str[i], ys[i]);
//							}
//						}else {
//							System.out.println(eUserAnswer.getaSanAdminid()+"---"+ eUserAnswer.getaSanScores());
//							map3.put(eUserAnswer.getaSanAdminid(), eUserAnswer.getaSanScores());
//						}
//					}else {
//						
//					}
//					
//					if(info.getaSanAdminid()!=null&&!"".equals(info.getaSanAdminid())) {
//						System.out.println("-------开始判断3-------");
//						if(info.getaSanAdminid().indexOf(",")>0) {
//							String[] str = info.getaSanAdminid().split(",");
//							String zero = "";
//							for(int i=0;i<str.length;i++) {
//								if(map3.get(str[i])!=null&&!"".equals(map3.get(str[i]))) {
//									if(zero.equals("")) {
//										zero = ""+map3.get(str[i]);
//									}else {
//										zero +=","+map3.get(str[i]);
//									}
//								}else {
//									if(zero.equals("")) {
//										zero = "0";
//									}else {
//										zero +=",0";
//									}
//								}
//							}
//							info.setaSanScores(zero);
//							info.setaSanCount(str.length);
//						}else {
//							info.setaSanCount(1);
//							info.setaSanScores(map3.get(info.getaErAdminid())==null||"".equals(map3.get(info.getaErAdminid()))?"":""+map3.get(info.getaErAdminid()));
//						}
//					}
					
//					if(info.getaYiAdminid()==null||"".equals(info.getaYiAdminid())) {
//						info.setaYiStatus(1);
//					}
//					if(info.getaErAdminid()==null||"".equals(info.getaErAdminid())) {
//						info.setaErStatus(1);
//					}
//					if(info.getaSanAdminid()==null||"".equals(info.getaSanAdminid())) {
//						info.setaSanSatatus(1);
//					}
					
					MyBeanUtils.copyBeanNotNull2Bean(info, eUserAnswer);
					int r = eUserAnswerService.update(eUserAnswer);
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

	@RequestMapping("/deleteUserAnswer")
	public Result deleteUserAnswer(HttpServletRequest request, String A_ID) {
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult(null);
		} else {
			if (A_ID == null || A_ID.equals("")) {
				return ResultGenerator.genFailResult("A_ID为空");
			}
			Condition condition = new Condition(EUserAnswer.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" A_ID='" + A_ID + "'");
			List<EUserAnswer> eUserAnswers = eUserAnswerService.findByCondition(condition);
			EUserAnswer eUserAnswer = eUserAnswers.get(0);
			eUserAnswer.setaStatus(4);
			int r = eUserAnswerService.update(eUserAnswer);
			return ResultGenerator.genSuccessResult("删除成功");
		}
	}
	
	@RequestMapping("/checkUserAnswer")
	public Result checkUserAnswer(HttpServletRequest request, String A_ID,Integer status) {
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult(null);
		} else {
			if (A_ID == null || A_ID.equals("")) {
				return ResultGenerator.genFailResult("A_ID为空");
			}
			Condition condition = new Condition(EUserAnswer.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" A_ID='" + A_ID + "'");
			List<EUserAnswer> eUserAnswers = eUserAnswerService.findByCondition(condition);
			EUserAnswer eUserAnswer = eUserAnswers.get(0);
			eUserAnswer.setaStatus(status);
			int r = eUserAnswerService.update(eUserAnswer);
			if(r>0) {
				if(status==2) {
					return ResultGenerator.genSuccessResult("已改成已处理");
				}else {
					return ResultGenerator.genSuccessResult("已修改成功");
				}
			}
			return ResultGenerator.genSuccessResult("成功");
		}
	}


	@RequestMapping("/getUserAnswers")
	@ResponseBody
	public Result getUserAnswers(HttpServletRequest request) {
		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
			return ResultGenerator.genFailResult("session is not vaid");
		} else {
			Condition condition = new Condition(EUserAnswer.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" A_STATUS != 4 ");
			condition.setOrderByClause(" A_CREATE_TIME desc");

			List<EUserAnswer> list = eUserAnswerService.findByCondition(condition);
			return ResultGenerator.genSuccessResult(list);
		}
	}

}
