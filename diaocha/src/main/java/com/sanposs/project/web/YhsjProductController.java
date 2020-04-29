package com.sanposs.project.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.SysConfig;
import com.sanposs.project.model.YhsjEnclosure;
import com.sanposs.project.model.YhsjProceductType;
import com.sanposs.project.model.YhsjProduct;
import com.sanposs.project.model.YhsjProductTp;
import com.sanposs.project.service.SysConfigService;
import com.sanposs.project.service.YhsjEnclosureService;
import com.sanposs.project.service.YhsjProceductTypeService;
import com.sanposs.project.service.YhsjProductService;
import com.sanposs.project.service.YhsjProductTpService;
import com.sanposs.project.utils.CommonUtil;
import com.sanposs.project.utils.JqgridResponse;
import com.sanposs.project.utils.OSSUnit;
import com.sanposs.project.utils.SessinoManager;
import com.sanposs.project.utils.UUIDGenerator;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by CodeGenerator on 2019/05/07.
 */
@RestController
@RequestMapping("/yhsj/product")
public class YhsjProductController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjProductController.class);
	@Resource
	private YhsjProductService yhsjProductService;
	@Resource
	private YhsjProceductTypeService proceductTypeService;
	@Resource
	private YhsjProductTpService productTpService;
	@Resource
	private YhsjEnclosureService enclosureService;
	@Resource
	private SysConfigService sysConfigService;
	
	private String UPLOAD_FILEPATH;
	private OSSUnit ossunit = null;  
	private OSSClient client = null;  
	private String bucketName = "sbmarket";  
	private String returnUrl = "http://sbmarket.oss-cn-shenzhen.aliyuncs.com/";

	@RequestMapping("/add")
	public Result add(YhsjProduct yhsjProduct) {
		yhsjProductService.save(yhsjProduct);
		return ResultGenerator.genSuccessResult();
	}

	@RequestMapping("/delete")
	public Result delete(@RequestParam Integer id) {
		yhsjProductService.deleteById(id);
		return ResultGenerator.genSuccessResult();
	}

	@RequestMapping("/update")
	public Result update(YhsjProduct yhsjProduct) {
		yhsjProductService.update(yhsjProduct);
		return ResultGenerator.genSuccessResult();
	}

	@RequestMapping("/detail")
	public Result detail(@RequestParam Integer id) {
		YhsjProduct yhsjProduct = yhsjProductService.findById(id);
		return ResultGenerator.genSuccessResult(yhsjProduct);
	}

	@RequestMapping("/list")
	public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
		PageHelper.startPage(page, size);
		List<YhsjProduct> list = yhsjProductService.findAll();
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@RequestMapping("/getProduct")
	public JqgridResponse<YhsjProduct> getProduct(@RequestParam String page, @RequestParam String rows,
			@RequestParam(defaultValue = "") String pro_name, @RequestParam(defaultValue = "") String pro_type,
			HttpServletRequest request) {
		JqgridResponse<YhsjProduct> response = new JqgridResponse<YhsjProduct>();
		int size = Integer.parseInt(rows == null ? "10" : rows);

		if (!SessinoManager.isWebLogin(request)) {
			logger.info("----session is not vaid----");
		} else {
			Condition condition = new Condition(YhsjProduct.class);
			Example.Criteria criteria = condition.createCriteria();

			if (pro_name != null && !pro_name.equals("")) {
				criteria.andCondition(" pro_name like '%" + pro_name + "%'");
			}
			if (pro_type != null && !pro_type.equals("")) {
				criteria.andCondition(" pro_type ='" + pro_type + "'");
			}
			criteria.andCondition("pro_is_delete = 1");
			condition.setOrderByClause("create_date desc");
			PageHelper.startPage(Integer.parseInt(page), size);
			
			List<YhsjProduct> list = yhsjProductService.findByCondition(condition);
			PageInfo<YhsjProduct> pageInfo = new PageInfo<YhsjProduct>(list);

			response.setRows(list);
			response.setRecords(String.valueOf(pageInfo.getTotal()));
			response.setTotal(String.valueOf(pageInfo.getPages()));
			response.setPage(String.valueOf(pageInfo.getPageNum()));
		}

		return response;
	}
	
	@RequestMapping("/getProductDetail")
    @ResponseBody
    public Result getProductDetail(HttpServletRequest request,String pro_id) {
// 	    Long comid = (Long)request.getSession().getAttribute("comid");
	
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("product", new YhsjProduct());
	    	map.put("productTp", new ArrayList<YhsjProductTp>());
	    	map.put("encolsures", new ArrayList<YhsjEnclosure>());
	    	if (pro_id!=null && !pro_id.equals("")) {
				Condition condition = new Condition(YhsjProduct.class);
				Example.Criteria criteria = condition.createCriteria();
				criteria.andCondition(" pro_id='"+pro_id+"'");
				List<YhsjProduct> products = yhsjProductService.findByCondition(condition);
				if (products.size()>0) {
					map.put("product",products.get(0));
				}
				
				Condition tpcondition = new Condition(YhsjProductTp.class);
				Example.Criteria tpcriteria = tpcondition.createCriteria();
				tpcriteria.andCondition(" pro_id='"+pro_id+"'");
				List<YhsjProductTp> tps = productTpService.findByCondition(tpcondition);
				if (tps.size()>0) {
					map.put("productTp",tps);
				}
				
				Condition encondition = new Condition(YhsjEnclosure.class);
				Example.Criteria encriteria = encondition.createCriteria();
				encriteria.andCondition(" enclosure_parent_id='"+pro_id+"'");
				encriteria.andCondition(" enclosure_type=1");
				encondition.setOrderByClause(" enclosure_sort asc");
				List<YhsjEnclosure> enclosureList=enclosureService.findByCondition(encondition);
				if (enclosureList.size()>0) {
					map.put("encolsures",enclosureList);
				}
			}
	        return ResultGenerator.genSuccessResult(map);
	    }
    }
	
	@RequestMapping("/getProductTp")
    @ResponseBody
    public Result getProductTp(HttpServletRequest request,String pro_id) {
// 	    Long comid = (Long)request.getSession().getAttribute("comid");
	
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	    	if(pro_id!=null&&!pro_id.equals("")){

		        Condition condition = new Condition(YhsjProductTp.class);
		        Example.Criteria criteria = condition.createCriteria();
		        criteria.andCondition(" pro_id='"+pro_id+"'");
				condition.setOrderByClause("tp_sort asc");
		        
		        List<YhsjProductTp> list = productTpService.findByCondition(condition);
		        return ResultGenerator.genSuccessResult(list);
	    	}else{
	    		return ResultGenerator.genSuccessResult();
	    	}
	    }
    }
	
	@RequestMapping("/getProductType")
    @ResponseBody
    public Result getProductType(HttpServletRequest request) {
// 	    Long comid = (Long)request.getSession().getAttribute("comid");
	
	    if(!SessinoManager.isWebLogin(request)){
	        logger.info("----session is not vaid----");
	        return ResultGenerator.genFailResult("session is not vaid");
	    }
	    else {
	        Condition condition = new Condition(YhsjProceductType.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" type_delete_flag=1");
			condition.setOrderByClause("type_show_sort asc");
	        
	        List<YhsjProceductType> list = proceductTypeService.findByCondition(condition);
	        return ResultGenerator.genSuccessResult(list);
	    }
    }
	
	@RequestMapping("/saveProduct")
   	public Result saveProduct(HttpServletRequest request, YhsjProduct info,String dtoStr,String control,String deleteImgStr,String[] idList) throws Exception {
		Result result = ResultGenerator.genSuccessResult();
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			try{
   				ObjectMapper mapper = new ObjectMapper();
   				List<YhsjProductTp> dto= new ArrayList<>();
   		        if (dtoStr != null && dtoStr != "" && !dtoStr.equals("null") && !dtoStr.equals("[null]")) {
   		        	dto = mapper.readValue(dtoStr,new TypeReference<List<YhsjProductTp>>(){});
   				}
   				String createProId=UUIDGenerator.getUUID2();
   				int i=0;
   				if(info.getProId()!=null&&info.getProId().compareTo("")>0){
   					Condition condition = new Condition(YhsjEnclosure.class);
   					Example.Criteria criteria = condition.createCriteria();
   					criteria.andCondition(" enclosure_parent_id='"+info.getProId()+"'");
   					criteria.andCondition(" enclosure_type=1");
   					condition.setOrderByClause(" enclosure_sort desc limit 0,1");
   			    	List<YhsjEnclosure> enclosures=enclosureService.findByCondition(condition);
   								
   					if(enclosures!=null&&enclosures.size()>0){
   						i=Integer.valueOf(enclosures.get(0).getEnclosureSort());
   					}
   				}
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
   				        
   				        if("minImgFiles".equals(childControl)){
   				        	info.setProMinImgUrl(returnUrl + diskName + fileName);
   				        }else{
   				        	YhsjEnclosure attach=new YhsjEnclosure();
   					        attach.setEnclosureId(UUIDGenerator.getUUID2());
   					        attach.setEnclosureUrl(returnUrl + diskName + fileName);
   					        attach.setEnclosureName(fileName);
   					        attach.setEnclosureTime(new Date());
   					        attach.setEnclosureType(1);
   				        	attach.setEnclosureSort(String.valueOf((++i)));
   					        if(info.getProId()==null||info.getProId().compareTo("")==0){
   					        	attach.setEnclosureParentId(createProId);
   					        }else{
   					        	attach.setEnclosureParentId(info.getProId());
   					        }
   					        info.setProDetailImg(returnUrl + diskName + fileName);
   					        enclosureService.save(attach);
   				        }
   					}
   				}
   				if(info.getProId()==null||info.getProId().compareTo("")==0){
   					info.setProId(createProId);
   				}
   				result=yhsjProductService.addCyfwProductTp(info, dto,UPLOAD_FILEPATH,deleteImgStr,idList);
   			}catch(Exception e){
   				e.printStackTrace();
   				result.setCode(ResultCode.FAIL);
   				result.setMessage("新增异常");
   			}
   			return result;
   		
   		}
   	}
	
	/**
   	 * 方法描述:删除产品
   	 * @param id
   	 * @param request
   	 * @return
   	 */
   	@RequestMapping("/deleteProduct")
   	public Result deleteProduct(HttpServletRequest request, String pro_id) {
   		if (!SessinoManager.isWebLogin(request)) {
   			logger.info("----session is not vaid----");
   			return ResultGenerator.genFailResult(null);
   		} else {
   			if (pro_id == null) {
   				return ResultGenerator.genFailResult("pro_id为空");
   			}
   			Condition condition = new Condition(YhsjProduct.class);
	        Example.Criteria criteria = condition.createCriteria();
	        criteria.andCondition(" pro_id='"+pro_id+"'");
   			List<YhsjProduct> products = yhsjProductService.findByCondition(condition);
   			if (products.size()>0) {
   				products.get(0).setProIsDelete(0);
			}
   			int r = yhsjProductService.update(products.get(0));
   			return ResultGenerator.genSuccessResult("删除成功");
   		}
   	}
}
