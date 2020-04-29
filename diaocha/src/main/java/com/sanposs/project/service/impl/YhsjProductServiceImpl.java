package com.sanposs.project.service.impl;

import java.io.File;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.oss.OSSClient;
import com.sanposs.project.core.AbstractService;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.dao.YhsjEnclosureMapper;
import com.sanposs.project.dao.YhsjProductMapper;
import com.sanposs.project.dao.YhsjProductTpMapper;
import com.sanposs.project.model.YhsjEnclosure;
import com.sanposs.project.model.YhsjProduct;
import com.sanposs.project.model.YhsjProductTp;
import com.sanposs.project.service.YhsjProductService;
import com.sanposs.project.utils.CommonUtil;
import com.sanposs.project.utils.MyBeanUtils;
import com.sanposs.project.utils.OSSUnit;
import com.sanposs.project.utils.UUIDGenerator;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * Created by CodeGenerator on 2019/05/07.
 */
@Service
@Transactional
public class YhsjProductServiceImpl extends AbstractService<YhsjProduct> implements YhsjProductService {
    @Resource
    private YhsjProductMapper yhsjProductMapper;
    @Resource
    private YhsjProductMapper productMapper;
    @Resource
    private YhsjProductTpMapper productTpMapper;
    @Resource
    private YhsjEnclosureMapper enclosureMapper;
    
	private OSSClient client = null;  
	private String bucketName = "sbmarket";  

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result addCyfwProductTp(YhsjProduct tmpProduce, List<YhsjProductTp> dto, String attachpath, String deleteImgStr,String[] idList) {
		// TODO Auto-generated method stub
		Result result=ResultGenerator.genSuccessResult();
		try{ 
	        client = OSSUnit.getOSSClient(); 
	        Condition proCondition = new Condition(YhsjProduct.class);
	        Example.Criteria proCriteria = proCondition.createCriteria();
	        proCriteria.andCondition(" pro_id='"+tmpProduce.getProId()+"'");
	        List<YhsjProduct> products = productMapper.selectByCondition(proCondition);
	        YhsjProduct produce=new YhsjProduct();
			if(products.size()<=0){
//				tmpProduce.setCreate_user_id(admin.getId());
				tmpProduce.setCreateDate(new Date());
				tmpProduce.setProMonthCount(0);
				tmpProduce.setProEvaluateCount(0);
				tmpProduce.setProCollections(0);
				tmpProduce.setProPoints(0);
				tmpProduce.setProIsDelete(1);
				tmpProduce.setProTotalCount(0);
				tmpProduce.setProName(URLDecoder.decode(tmpProduce.getProName(),"UTF-8"));
//				tmpProduce.setCompany_id(admin.getId());
//				tmpProduce.setCompany_name(admin.getRealName());
				productMapper.insert(tmpProduce);
				produce=tmpProduce;
			}else{
				produce = products.get(0);
				String tmpfileUrl=produce.getProMinImgUrl();				
				MyBeanUtils.copyBeanNotNull2Bean(tmpProduce, produce);
				int p=productMapper.updateByPrimaryKeySelective(produce);
				if(p>0){
					if(tmpfileUrl!=null&&tmpfileUrl.compareTo("")>0){
						if(tmpProduce.getProMinImgUrl()!=null&&tmpfileUrl.compareTo(tmpProduce.getProMinImgUrl())!=0){
							//删除
							String key=tmpfileUrl.substring(tmpfileUrl.indexOf("seeding"));
							String localfileurl=attachpath+tmpfileUrl.substring(tmpfileUrl.lastIndexOf("/")+1);
							if(client.doesObjectExist(bucketName, key)){
								//删除阿里云服务的文件
								client.deleteObject(bucketName, key);
							}
							File file=new File(localfileurl);
							if(file.exists()){
								file.delete();
							}
						}
					}	
					
					if(deleteImgStr!=null&&deleteImgStr.compareTo("")>0){
						String[] tmparray=deleteImgStr.split(",");
						for(String tmp:tmparray){
							if(tmp==null||tmp.compareTo("")==0){
								continue;
							}
							YhsjEnclosure tmpinfo=enclosureMapper.selectByPrimaryKey(tmp);
							int d=enclosureMapper.deleteByPrimaryKey(tmp);
							if(d>0){
								if(tmpinfo!=null&&tmpinfo.getEnclosureUrl()!=null&&tmpinfo.getEnclosureUrl().compareTo("")!=0){
									//删除
									String key=tmpinfo.getEnclosureUrl().substring(tmpinfo.getEnclosureUrl().indexOf("seeding"));
									String localfileurl=attachpath+tmpinfo.getEnclosureUrl().substring(tmpinfo.getEnclosureUrl().lastIndexOf("/")+1);
									if(client.doesObjectExist(bucketName, key)){
										//删除阿里云服务的文件
										client.deleteObject(bucketName, key);
									}
									File file=new File(localfileurl);
									if(file.exists()){
										file.delete();
									}
								}
							}
						}
					}
				}
			}
			
			if (idList!=null&&idList.length>0) {
				for (int i = 0; i < idList.length; i++) {
					//删除规格
					Condition tpCondition = new Condition(YhsjProductTp.class);
					Example.Criteria tpCriteria = tpCondition.createCriteria();
					tpCriteria.andCondition(" tp_id='"+idList[i]+"'");
					productTpMapper.deleteByCondition(tpCondition);
				}
			}
			
			//新增规格
			if(dto!=null&&dto.size()>0){
				for (int i = 0; i < dto.size(); i++) {
					if (CommonUtil.isNotEmpty(dto.get(i).getTpId())) {
						YhsjProductTp productTp = productTpMapper.selectByPrimaryKey(dto.get(i).getTpId());
		        		MyBeanUtils.copyBeanNotNull2Bean(dto.get(i), productTp);
		        		productTp.setTpSort(i+1);
		        		productTpMapper.updateByPrimaryKeySelective(productTp);
					}else{
						YhsjProductTp productTp = new YhsjProductTp();
		        		MyBeanUtils.copyBeanNotNull2Bean(dto.get(i), productTp);
		        		productTp.setTpId(UUIDGenerator.getUUID2());
		        		productTp.setTpCreateTime(new Date());
		        		productTp.setTpSort(i+1);
		        		productTp.setProId(produce.getProId());
		        		productTpMapper.insertSelective(productTp);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			result.setCode(ResultCode.FAIL);
			result.setMessage("处理异常");
		}
		return result;
	}

}
