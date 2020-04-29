package com.nanwang.project.web;
import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanwang.project.core.Result;
import com.nanwang.project.core.ResultCode;
import com.nanwang.project.core.ResultGenerator;
import com.nanwang.project.model.YhsjAdvertisement;
import com.nanwang.project.service.YhsjAdvertisementService;
import com.nanwang.project.utils.CommonUtil;
import com.nanwang.project.utils.JqgridResponse;
import com.nanwang.project.utils.MyBeanUtils;
import com.nanwang.project.utils.OSSUnit;
import com.nanwang.project.utils.SessinoManager;
import com.nanwang.project.utils.UUIDGenerator;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
* Created by CodeGenerator on 2019/05/28.
*/
@RestController
@RequestMapping("/yhsj/advertisement")
public class YhsjAdvertisementController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjAdvertisementController.class);
    @Resource
    private YhsjAdvertisementService yhsjAdvertisementService;
	
	private String UPLOAD_FILEPATH;
	private OSSUnit ossunit = null;  
	private OSSClient client = null;  
	private String bucketName = "sbmarket";  
	private String returnUrl = "https://sbmarket.oss-cn-shenzhen.aliyuncs.com/";

    @RequestMapping("/add")
    public Result add(YhsjAdvertisement yhsjAdvertisement) {
        yhsjAdvertisementService.save(yhsjAdvertisement);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjAdvertisementService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjAdvertisement yhsjAdvertisement) {
        yhsjAdvertisementService.update(yhsjAdvertisement);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjAdvertisement yhsjAdvertisement = yhsjAdvertisementService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjAdvertisement);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjAdvertisement> list = yhsjAdvertisementService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
    @RequestMapping("/getAdvertisement")
	public JqgridResponse<YhsjAdvertisement> getAdvertisement(@RequestParam String page, @RequestParam String rows,
			@RequestParam(defaultValue = "") String advertisement_name,HttpServletRequest request) {
		JqgridResponse<YhsjAdvertisement> response = new JqgridResponse<YhsjAdvertisement>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(YhsjAdvertisement.class);
			Example.Criteria criteria = condition.createCriteria();

			if (advertisement_name != null && !advertisement_name.equals("")) {
				criteria.andCondition(" advertisement_name like '%" + advertisement_name + "%'");
			}
			criteria.andCondition("advertisement_channel = 1");
			condition.setOrderByClause("advertisement_channel desc");
			PageHelper.startPage(Integer.parseInt(page), size);
			
			List<YhsjAdvertisement> list = yhsjAdvertisementService.findByCondition(condition);
			PageInfo<YhsjAdvertisement> pageInfo = new PageInfo<YhsjAdvertisement>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}

		return response;
	}
	
	@RequestMapping("/getAdvertisementDetail")
    @ResponseBody
    public Result getAdvertisementDetail(HttpServletRequest request,String advertisement_id) {
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	if (advertisement_id == null || advertisement_id == "") {
   				return ResultGenerator.genFailResult("advertisement_id为空");
   			}
			Condition condition = new Condition(YhsjAdvertisement.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" advertisement_id='"+advertisement_id+"'");
			List<YhsjAdvertisement> advertisements = yhsjAdvertisementService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(advertisements.get(0));
		}
    }
	
	@RequestMapping("/saveAdvertisement")
   	public Result saveAdvertisement(HttpServletRequest request, YhsjAdvertisement info,String control) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			try{
   				int i=0;
   				if(CommonUtil.isEmpty(this.UPLOAD_FILEPATH)){
   					this.UPLOAD_FILEPATH = "D:\\\\jinmin_image\\\\";
   				}
   				String[] controls=control.split(",");			
   				
   				for(String childControl:controls){
   					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
   					List<MultipartFile> myfiles = multipartRequest.getFiles(childControl);
   					String originalFilename = null;
   					ossunit = new OSSUnit();  
   			        client = OSSUnit.getOSSClient();  
   					for (MultipartFile myfile : myfiles) {
   						originalFilename = myfile.getOriginalFilename();
   						String fileType = "png";
   						
   						switch(myfile.getContentType()){
   							case "image/png":
   								fileType="png";
   								break;
   							case "image/jpeg":
   								fileType="jpeg";
   								break;
   							case "image/gif":
   								fileType="gif";
   								break;
   							case "image/x-icon":
   								fileType="ico";
   								break;
   						}
   						if (myfile.isEmpty()) {
   							result.setCode(ResultCode.FAIL);
   							result.setMessage("文件上传失败，请重试！！");
   							return result;
   						}
   						
   						logger.info("========================================");
   						logger.info("文件原名: " + originalFilename);
   						logger.info("文件名称: " + myfile.getName());
   						logger.info("文件长度: " + myfile.getSize());
   						logger.info("文件类型: " + myfile.getContentType());
   						logger.info("文件后缀名:" + fileType);
   						logger.info("========================================");
   						String url = this.UPLOAD_FILEPATH;

   						String fileName = myfile.getOriginalFilename();
   						String fileUUIDName = CommonUtil.getUUID();
   						fileName = fileUUIDName + "." +fileType;

   						File targetFile = new File(url, fileName);
   						// 生成商家文件夹
   						File file = new File(url);
   						if (!file.exists() && !file.isDirectory()) {
   							boolean b = file.mkdir();
   						}
   						myfile.transferTo(targetFile);							
   						File file1 = targetFile;  
   				        String diskName = "seeding/images/";  
   				        String md5key = OSSUnit.uploadObject2OSS(client, file1, bucketName, diskName);  
   				        logger.info("上传后的文件MD5数字唯一签名:" + md5key);  //上传后的文件MD5数字唯一签名:A30B046A34EB326C4A3BBD784333B017  
   				        System.out.println("上传后的文件MD5数字唯一签名:" + md5key);  //上传后的文件MD5数字唯一签名:A30B046A34EB326C4A3BBD784333B017 
   				        
			        	info.setAdvertisementImageUrl(returnUrl + diskName + fileName);
   					}
   				}
   				if(info.getAdvertisementId()==null||info.getAdvertisementId().compareTo("")==0){
   					info.setAdvertisementId(UUIDGenerator.getUUID2());
//   					info.setAdvertisementCreateTime(new Date());
   					info.setAdvertisementChannel("1");
   					int r = yhsjAdvertisementService.save(info);
   					if (r>0) {
   						
					}else{
						result = ResultGenerator.genFailResult("新增失败");
					}
   				}else{
   					Condition proCondition = new Condition(YhsjAdvertisement.class);
   			        Example.Criteria proCriteria = proCondition.createCriteria();
   			        proCriteria.andCondition(" advertisement_id='"+info.getAdvertisementId()+"'");
   			        List<YhsjAdvertisement> advertisements = yhsjAdvertisementService.findByCondition(proCondition);	
   			        YhsjAdvertisement advertisement = advertisements.get(0);
   					MyBeanUtils.copyBeanNotNull2Bean(info, advertisement);
   					int r = yhsjAdvertisementService.update(advertisement);
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
	
	@RequestMapping("/deleteAdvertisement")
   	public Result deleteAdvertisement(HttpServletRequest request, String advertisement_id) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (advertisement_id == null || advertisement_id.equals("")) {
   				return ResultGenerator.genFailResult("advertisement_id为空");
   			}
	    	StringBuffer delete_sql=new StringBuffer("delete from yhsj_advertisement where advertisement_id='"+advertisement_id+"'");
   			Condition condition = new Condition(YhsjAdvertisement.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" advertisement_id='"+advertisement_id+"'");
   			List<YhsjAdvertisement> advertisements = yhsjAdvertisementService.findByCondition(condition);
   			if (advertisements.size()>0) {
   				advertisements.get(0).setAdvertisementStatus(0);
			}
   			yhsjAdvertisementService.dynamicSqlDelete(delete_sql.toString());
   			return ResultGenerator.genSuccessResult("删除成功");
   		}
   	}
}
