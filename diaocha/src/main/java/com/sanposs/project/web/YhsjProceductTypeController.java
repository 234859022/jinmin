package com.sanposs.project.web;
import java.io.File;
import java.util.Date;
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
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.SysConfig;
import com.sanposs.project.model.YhsjProceductType;
import com.sanposs.project.model.YhsjProduct;
import com.sanposs.project.service.SysConfigService;
import com.sanposs.project.service.YhsjProceductTypeService;
import com.sanposs.project.utils.CommonUtil;
import com.sanposs.project.utils.JqgridResponse;
import com.sanposs.project.utils.MyBeanUtils;
import com.sanposs.project.utils.OSSUnit;
import com.sanposs.project.utils.SessinoManager;
import com.sanposs.project.utils.UUIDGenerator;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
* Created by CodeGenerator on 2019/05/07.
*/
@RestController
@RequestMapping("/yhsj/proceduct/type")
public class YhsjProceductTypeController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjProceductTypeController.class);
    @Resource
    private YhsjProceductTypeService yhsjProceductTypeService;
	@Resource
	private SysConfigService sysConfigService;
	
	private String UPLOAD_FILEPATH;
	private OSSUnit ossunit = null;  
	private OSSClient client = null;  
	private String bucketName = "sbmarket";  
	private String returnUrl = "http://sbmarket.oss-cn-shenzhen.aliyuncs.com/";

    @RequestMapping("/add")
    public Result add(YhsjProceductType yhsjProceductType) {
        yhsjProceductTypeService.save(yhsjProceductType);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjProceductTypeService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjProceductType yhsjProceductType) {
        yhsjProceductTypeService.update(yhsjProceductType);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjProceductType yhsjProceductType = yhsjProceductTypeService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjProceductType);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjProceductType> list = yhsjProceductTypeService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    
    @RequestMapping("/getProductType")
	public JqgridResponse<YhsjProceductType> getProductType(@RequestParam String page, @RequestParam String rows,
			@RequestParam(defaultValue = "") String type_name,HttpServletRequest request) {
		JqgridResponse<YhsjProceductType> response = new JqgridResponse<YhsjProceductType>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(YhsjProceductType.class);
			Example.Criteria criteria = condition.createCriteria();

			if (type_name != null && !type_name.equals("")) {
				criteria.andCondition(" type_name like '%" + type_name + "%'");
			}
			criteria.andCondition("type_delete_flag = 1");
			condition.setOrderByClause("type_show_sort asc");
			PageHelper.startPage(Integer.parseInt(page), size);
			
			List<YhsjProceductType> list = yhsjProceductTypeService.findByCondition(condition);
			PageInfo<YhsjProceductType> pageInfo = new PageInfo<YhsjProceductType>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}

		return response;
	}
	
	@RequestMapping("/getProductTypeDetail")
    @ResponseBody
    public Result getProductTypeDetail(HttpServletRequest request,String type_id) {
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	if (type_id == null || type_id == "") {
   				return ResultGenerator.genFailResult("type_id为空");
   			}
			Condition condition = new Condition(YhsjProduct.class);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andCondition(" type_id='"+type_id+"'");
			List<YhsjProceductType> types = yhsjProceductTypeService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(types.get(0));
		}
    }
	
	@RequestMapping("/saveProductType")
   	public Result saveProductType(HttpServletRequest request, YhsjProceductType info,String control) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			try{
   				int i=0;
   				if(CommonUtil.isEmpty(this.UPLOAD_FILEPATH)){
   					Condition condition = new Condition(SysConfig.class);
   					Example.Criteria criteria = condition.createCriteria();
   					criteria.andCondition(" id='ATTACH_PATH'");
   					List<SysConfig> config = sysConfigService.findByCondition(condition);
   					SysConfig systemmgConfig = config.get(0);
   					this.UPLOAD_FILEPATH = (systemmgConfig == null ? "" : systemmgConfig.getConfigValue());
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
   				        
			        	info.setTypeLogImg(returnUrl + diskName + fileName);
   					}
   				}
   				if(info.getTypeId()==null||info.getTypeId().compareTo("")==0){
   					info.setTypeId(UUIDGenerator.getUUID2());
   					info.setTypeDeleteFlag("1");
   					info.setTypeCreateDate(new Date());
   					int r = yhsjProceductTypeService.save(info);
   					if (r>0) {
   						
					}else{
						result = ResultGenerator.genFailResult("新增失败");
					}
   				}else{
   					Condition proCondition = new Condition(YhsjProceductType.class);
   			        Example.Criteria proCriteria = proCondition.createCriteria();
   			        proCriteria.andCondition(" type_id='"+info.getTypeId()+"'");
   			        List<YhsjProceductType> products = yhsjProceductTypeService.findByCondition(proCondition);	
   			        YhsjProceductType proceductType = products.get(0);
   					MyBeanUtils.copyBeanNotNull2Bean(info, proceductType);
   					int r = yhsjProceductTypeService.update(proceductType);
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
	
	@RequestMapping("/deleteProductType")
   	public Result deleteProductType(HttpServletRequest request, String type_id) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (type_id == null || type_id.equals("")) {
   				return ResultGenerator.genFailResult("type_id为空");
   			}
   			Condition condition = new Condition(YhsjProceductType.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" type_id='"+type_id+"'");
   			List<YhsjProceductType> products = yhsjProceductTypeService.findByCondition(condition);
   			if (products.size()>0) {
   				products.get(0).setTypeDeleteFlag("0");
			}
   			int r = yhsjProceductTypeService.update(products.get(0));
   			return ResultGenerator.genSuccessResult("删除成功");
   		}
   	}
	
}


