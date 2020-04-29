package com.sanposs.project.web.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjUserAddress;
import com.sanposs.project.pojo.BaseDto;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.pojo.QueryPage;
import com.sanposs.project.service.YhsjUserAddressService;
import com.sanposs.project.utils.ApiSessinoManager;
import com.sanposs.project.utils.UUIDUtil;

/**
* 类名称: UserAddressController 
* 功能描述: (这里用一句话描述这个类的作用) 
* 创建者: xuwf 
* 创建时间: 2017-11-27 下午3:19:55 
* 版本信息: V1.0
* CopyRight(C)：2017陈先生-版权所有
 */
@Controller
@RequestMapping("/addressApi")
public class UserAddressController{
	
	private final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private YhsjUserAddressService userAddressService;
	
	
	/**
	 * 功能描述：获取地址列表
	 * 创建者：xuwf
	 * 创建时间：2017-11-27 下午3:21:39 
	 * @param token
	 * @param cyfwUserAddress
	 * @param baseDto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getUserAddressList")
	@ResponseBody
	public Result getUserAddressList(String token,YhsjUserAddress userAddress,BaseDto baseDto,HttpServletRequest request,HttpServletResponse response){
		ApiSessinoManager.setOriginHeader(request, response);
		log.info("开始----------获地址列表..............");
		Result result = ResultGenerator.genSuccessResult();
		if(token==null||token.compareTo("")==0){
			result.setCode(ResultCode.VERIFY_ERROR);
			result.setMessage("token不存在");
			return result;
		}
		LoginUser user =ApiSessinoManager.getLoginUser(token);
		if(user==null){
			result.setCode(ResultCode.VERIFY_ERROR);
			result.setMessage("用户不存在");
			return result;
		}
        QueryPage page = new QueryPage();
        page.setPageNo(baseDto.getPageNo());
		page.setPageSize(baseDto.getPageSize());
        
		Condition example = new Condition(YhsjUserAddress.class);
		Example.Criteria c = example.or();
		c.andCondition(" user_id='"+user.getUserId()+"'");
		
		String orderStr = " address_create_time desc ";
        if(baseDto.getSortName()!=null&&!"".equals(baseDto.getSortName())){
        	if(baseDto.getSort()!=null&&!"".equals(baseDto.getSort())){
        		orderStr = " "+baseDto.getSortName() + " " + baseDto.getSort()+" ";
        	}else{
        		orderStr = " "+baseDto.getSortName() + " asc ";//没传默认asc
        	}
        }
        example.setOrderByClause(orderStr+" limit "+page.getOffset()+","+page.getPageSize());
        List<YhsjUserAddress> mapList = new ArrayList<YhsjUserAddress>();
        int iTotal = 0;
        try{
        	iTotal = userAddressService.countByCondition(example);
        	if(iTotal>0){
        		mapList = userAddressService.findByCondition(example);
        		result.setCode(ResultCode.SUCCESS);
            	result.setMessage("获取成功");
            	result.setItems(mapList);
            	result.setTotalCount(iTotal);  
        	}else{
        		result.setCode(ResultCode.FAIL);
        		result.setMessage("获取失败");
        	}
    		
        } catch(Exception ex) {
        	log.error("获取数据列表出现异常:"+ ex.getMessage(), ex);
        	result.setCode(ResultCode.FAIL);
    		result.setMessage("获取失败");
        }
		log.info("结束------------获取地址列表...............");
		return result;
	}
	
	
	/**
	 * 功能描述：查看地址信息详情
	 * 创建者：xuwf
	 * 创建时间：2017-8-14 上午10:42:40 
	 * @param token
	 * @param Address_id
	 * @param baseDto
	 * @return
	 */
	@RequestMapping(value = "/getUserAddressDetails")
	@ResponseBody
	public Result getUserAddressDetails(String token,String address_id,BaseDto baseDto,HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result result = ResultGenerator.genSuccessResult();
		if(address_id==null||"".equals(address_id)){
			result.setMessage("address_id不能为空！");
			result.setCode(ResultCode.VERIFY_ERROR);
			return result;
		}
		YhsjUserAddress userAddress = userAddressService.findBy("addressId", address_id);
		if(userAddress==null){
			result.setMessage("找不到对应的记录！");
			result.setCode(ResultCode.VERIFY_ERROR);
			return result;
		}else{
			result.setMessage("获取成功！");
			result.setCode(ResultCode.SUCCESS);
			result.setData(userAddress);
			return result;
		}
	}
	
	/**
	 * 功能描述：保存收货地址信息
	 * 创建者：xuwf
	 * 创建时间：2017-8-14 上午10:50:48 
	 * @param po
	 * @param token
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saveUserAddress")
	@ResponseBody
	public Result saveUserAddress(YhsjUserAddress po,String token,
			HttpServletRequest request, HttpServletResponse response)
			{
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result result = ResultGenerator.genSuccessResult();
		LoginUser user = ApiSessinoManager.getLoginUser(token);
		if(user==null){
			result.setMessage("该用户不存在！");
			result.setCode(ResultCode.VERIFY_ERROR);
			return result;
		}
		
		if(po==null){
			result.setMessage("地址信息不存在！");
			result.setCode(ResultCode.VERIFY_ERROR);
			return result;
		}
		
		if(po.getAddressDetail()==null||po.getAddressDetail().compareTo("")==0){
			result.setMessage("详细地址信息不能为空！");
			result.setCode(ResultCode.VERIFY_ERROR);
			return result;
		}
		

		try {
			if (po.getAddressId()!=null&&po.getAddressId().compareTo("")>0) {	
            	int n=userAddressService.update(po);
            	if(n>0){
            		result.setCode(ResultCode.SUCCESS);
            		result.setMessage("更新成功");
            	}else{
            		result.setCode(ResultCode.FAIL);
            		result.setMessage("更新失败");
            	}
            } else {
            	Condition ex=new Condition(YhsjUserAddress.class);
              	Example.Criteria cri=ex.createCriteria();
              	cri.andCondition(" user_id='"+user.getUserId()+"'");
              	int a=userAddressService.countByCondition(ex);
              	if(a==0){
              		po.setUserIsDefault(1);
              	}else{
              		po.setUserIsDefault(0);
              	}
            	
            	po.setAddressCreateTime(new Date());
            	po.setAddressId(UUIDUtil.createUUID());
            	po.setUserId(user.getUserId());
            	int n=this.userAddressService.save(po);
            	if(n>0){
            		result.setCode(ResultCode.SUCCESS);
            		result.setMessage("新增成功");
            	}else{
            		result.setCode(ResultCode.FAIL);
            		result.setMessage("新增失败");
            	}
            }
		} catch (Exception e) {
			log.error("操作出现异常:" + e.getMessage(), e);
			result.setMessage("操作异常");
			result.setCode(ResultCode.FAIL);
		}
		return result;
	}
	
	/**
	 * 功能描述：设置默认收货地址
	 * 创建者：xuwf
	 * 创建时间：2017-8-14 上午11:15:29 
	 * @param po
	 * @param token
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("setDefaultByUserid")
	@ResponseBody
	public Result setDefaultByUserid(YhsjUserAddress po,String token,
			HttpServletRequest request, HttpServletResponse response)
			{
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result result = ResultGenerator.genSuccessResult();
		LoginUser user = ApiSessinoManager.getLoginUser(token);
		if(user==null){
			result.setMessage("该用户不存在！");
			result.setCode(ResultCode.VERIFY_ERROR);
			return result;
		}
		if(po.getAddressId()==null||"".equals(po.getAddressId())){
			result.setMessage("address_id不能为空！");
			result.setCode(ResultCode.FAIL);
			return result;
		}
		po.setUserId(user.getUserId());
		try {
			int t0 = userAddressService.setDefault0ByUserid(po);
			int t1 = userAddressService.setDefault1ByUserid(po);
			result.setMessage("设置成功！");
			result.setCode(ResultCode.SUCCESS);
			result.setData(po);
		} catch (Exception e) {
			log.error("设置出现异常:" + e.getMessage(), e);
			result.setMessage("保存异常");
			result.setCode(ResultCode.FAIL);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/delUserAddress")
	@ResponseBody
	public Result delUserAddress(String token,String address_id,BaseDto baseDto,HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result result = ResultGenerator.genSuccessResult();
		if(address_id==null||"".equals(address_id)){
			result.setMessage("address_id不能为空！");
			result.setCode(ResultCode.FAIL);
			return result;
		}
		int i = userAddressService.deleteAddress(address_id);
		if(i>0){
			result.setMessage("删除成功！");
			result.setCode(ResultCode.SUCCESS);
			return result;
		}else{
			result.setMessage("删除失败！");
			result.setCode(ResultCode.FAIL);
			return result;
		}
	}
	
	/**
	 * 功能描述:获取当前默认收货地址
	 * 创建者:xuwf
	 * 创建时间:2018-4-18 上午10:42:40 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/getUserDefalutAddress")
	@ResponseBody
	public Result getUserDefalutAddress(String token,HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result result = ResultGenerator.genSuccessResult();
		LoginUser user = ApiSessinoManager.getLoginUser(token);
		if(user==null){
			result.setMessage("该用户不存在!");
			result.setCode(ResultCode.VERIFY_ERROR);
			return result;
		}
		Condition address_ex=new Condition(YhsjUserAddress.class);
		Example.Criteria address_cri=address_ex.createCriteria();
		address_cri.andCondition(" user_id='"+user.getUserId()+"'");
		address_cri.andCondition(" user_is_default=1");
		List<YhsjUserAddress> list=userAddressService.findByCondition(address_ex);
		
		if(list==null||list.size()==0){
			result.setMessage("找不到对应的记录！");
			result.setCode(ResultCode.VERIFY_ERROR);
			return result;
		}else{
			result.setMessage("操作成功！");
			result.setCode(ResultCode.SUCCESS);
			result.setData(list.get(0));
			return result;
		}
	}
}
