package com.sanposs.project.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanposs.project.common.PlatformException;
import com.sanposs.project.core.AbstractService;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.dao.YhsjProductMapper;
import com.sanposs.project.dao.YhsjProductTpMapper;
import com.sanposs.project.dao.YhsjPurchaseOrderDetailMapper;
import com.sanposs.project.dao.YhsjPurchaseOrderMapper;
import com.sanposs.project.dao.YhsjShoppingCatMapper;
import com.sanposs.project.dao.YhsjUserAddressMapper;
import com.sanposs.project.model.YhsjProduct;
import com.sanposs.project.model.YhsjProductTp;
import com.sanposs.project.model.YhsjPurchaseOrder;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.model.YhsjShoppingCat;
import com.sanposs.project.model.YhsjUserAddress;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.pojo.YhsjShoppingCatExpand;
import com.sanposs.project.service.YhsjPurchaseOrderService;
import com.sanposs.project.service.YhsjShoppingCatService;
import com.sanposs.project.utils.CommonUtil;
import com.sanposs.project.utils.UUIDUtil;
import com.sun.webkit.ContextMenu.ShowContext;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * Created by CodeGenerator on 2019/05/13.
 */
@Service
@Transactional
public class YhsjShoppingCatServiceImpl extends AbstractService<YhsjShoppingCat> implements YhsjShoppingCatService {
    @Resource
    private YhsjShoppingCatMapper yhsjShoppingCatMapper;
    
    @Resource
    private YhsjPurchaseOrderMapper yhsjPurchaseOrderMapper;
    
    @Resource
    private YhsjPurchaseOrderDetailMapper yhsjPurchaseOrderDetailMapper;
    
    @Resource
    private YhsjUserAddressMapper yhsjUserAddressMapper;
    
    @Resource
    private YhsjProductTpMapper productTpMapper;
    
    @Resource
    private YhsjProductMapper productMapper;

    @Override
	@Transactional(rollbackFor = Exception.class)
	public Result addShoppingCat(List<YhsjShoppingCat> list, LoginUser user) throws PlatformException{
		// TODO Auto-generated method stub
    	Result result = ResultGenerator.genSuccessResult();	
		try{
			if(list!=null&&list.size()>0){
				for(YhsjShoppingCat info:list){
					if(info==null){
						continue;
					}
					if(info.getShoppingCatId()==null||info.getShoppingCatId().compareTo("")==0){
						Condition condition = new Condition(YhsjShoppingCat.class);
						Example.Criteria criteria = condition.createCriteria();
						
						if(info.getProId()!=null&&info.getProId().compareTo("")>0){
							criteria.andCondition(" pro_id='"+info.getProId()+"'");
						}
						
						if(info.getScaleId()!=null&&info.getScaleId().compareTo("")>0){
							criteria.andCondition(" scale_id='"+info.getScaleId()+"'");
						}
						if(info.getProduceDetailId()!=null&&info.getProduceDetailId().compareTo("")>0){
							criteria.andCondition(" produce_detail_id='"+info.getProduceDetailId()+"'");
						}
						criteria.andCondition(" shopping_cat_create_user_id='"+user.getUserId()+"'");
						List<YhsjShoppingCat> cats=yhsjShoppingCatMapper.selectByCondition(condition);
						if(cats!=null&&cats.size()>0){
							YhsjShoppingCat cat=cats.get(0);
							cat.setProNum(info.getProNum()+cat.getProNum());
							cat.setProPrice(info.getProPrice());
							int n=yhsjShoppingCatMapper.updateByPrimaryKeySelective(cat);
							if(n==0){
								throw new PlatformException("购物车商品添加异常", 1);
							}
						}else{
							info.setShoppingCatId(UUIDUtil.createUUID());
							info.setShoppingCatCreateTime(new Date());
							info.setShoppingCatCreateUserId(user.getUserId());
							int n=yhsjShoppingCatMapper.insert(info);
							if(n==0){
								throw new PlatformException("购物车商品添加异常", 1);
							}
						}
					}else{
						YhsjShoppingCat shoppingcat=yhsjShoppingCatMapper.selectByPrimaryKey(info.getShoppingCatId());
						if(shoppingcat!=null){
							shoppingcat.setProId(info.getProId());
							shoppingcat.setProPrice(info.getProPrice());
							shoppingcat.setProNum(info.getProNum());
							shoppingcat.setScaleId(info.getScaleId());
							shoppingcat.setScaleLabel(info.getScaleLabel());
							shoppingcat.setShoppingCatState(info.getShoppingCatState());
							int n=yhsjShoppingCatMapper.updateByPrimaryKey(shoppingcat);
							if(n==0){
								throw new PlatformException("购物车商品添加异常", 1);	
							}
						}
					}
				}
			}
			result.setCode(ResultCode.SUCCESS);
			result.setMessage("添加成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("添加操作异常");
			result.setCode(ResultCode.FAIL);
			result.setMessage("添加出现异常");
			throw new PlatformException("添加操作异常", 1);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> dynamicSqlQuery(String sql) {
		// TODO Auto-generated method stub
		return yhsjShoppingCatMapper.dynamicSqlQuery(sql);
	}

	@Override
	public int dynamicSqlByCount(String sql) {
		// TODO Auto-generated method stub
		return yhsjShoppingCatMapper.dynamicSqlByCount(sql);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result balancePurchase(List<YhsjShoppingCatExpand> shoppingCatList,
			LoginUser user,String remark) throws PlatformException {
		// TODO Auto-generated method stub
		Result result= ResultGenerator.genSuccessResult();
		try{
			//创建采购单
			Condition address_condition=new Condition(YhsjUserAddress.class);
			Example.Criteria address_criteria=address_condition.createCriteria();
			address_criteria.andCondition("user_id='"+user.getUserId()+"'");
			address_criteria.andCondition("user_is_default=1");
			List<YhsjUserAddress> addresslist=yhsjUserAddressMapper.selectByCondition(address_condition);
			if(addresslist==null||addresslist.size()==0){
				result.setCode(ResultCode.VERIFY_ERROR);
				result.setMessage("还未设置收货地址");
				return result;
			}
			
			YhsjUserAddress yhsjUserAddress=addresslist.get(0);
			
			YhsjPurchaseOrder purchaseOrder=new YhsjPurchaseOrder();
			purchaseOrder.setPurchaseId(UUIDUtil.createUUID());
			purchaseOrder.setPurchaseNo(CommonUtil.createNo("C"));
			purchaseOrder.setPurchaseStatus(1);
			purchaseOrder.setPurchaseCreateUserId(user.getUserId());
			purchaseOrder.setPurchaseCreateUserName(yhsjUserAddress.getUserName());
			purchaseOrder.setPurchaseCreateTime(new Date());
			purchaseOrder.setPurchasePhone(yhsjUserAddress.getUserPhone());
			purchaseOrder.setPurchaseAddress(yhsjUserAddress.getAddressPrivince()+yhsjUserAddress.getAddressCity()+yhsjUserAddress.getAddressTown()+yhsjUserAddress.getAddressDetail());
			purchaseOrder.setPurchaseAddressId(yhsjUserAddress.getAddressId());
			purchaseOrder.setPurchaseIsDelete(0);
			purchaseOrder.setPurchaseRemark(remark);
			int n=yhsjPurchaseOrderMapper.insert(purchaseOrder);
			if(n==0){
				throw new PlatformException("采购单添加异常", 1);
			}
			BigDecimal totalPrice=BigDecimal.ZERO;
			for(YhsjShoppingCatExpand shoppingCat:shoppingCatList){
				if(shoppingCat.getScale_id()!=null&&shoppingCat.getScale_id().compareTo("")>0){
					YhsjProductTp productTp=productTpMapper.selectByPrimaryKey(shoppingCat.getScale_id());
					if(shoppingCat.getPro_num()>productTp.getTpNum()){
						throw new PlatformException("库存不足", ResultCode.VERIFY_ERROR.code());
					}
					productTp.setTpNum(productTp.getTpNum()-shoppingCat.getPro_num());
					int pt=productTpMapper.updateByPrimaryKey(productTp);
					if(pt==0){
						throw new PlatformException("更新规格库存异常", 1);
					}
				}else{
					YhsjProduct product=productMapper.selectByPrimaryKey(shoppingCat.getPro_id());
					if(shoppingCat.getPro_num()>product.getProNum()){
						throw new PlatformException("库存不足", ResultCode.VERIFY_ERROR.code());
					}
					product.setProNum(product.getProNum()-shoppingCat.getPro_num());
					int p=productMapper.updateByPrimaryKey(product);
					if(p==0){
						throw new PlatformException("更新商品库存异常", 1);
					}
				}
				YhsjPurchaseOrderDetail purchaseOrderDetail=new YhsjPurchaseOrderDetail();
				purchaseOrderDetail.setDetailId(UUIDUtil.createUUID());
				purchaseOrderDetail.setPurchaseId(purchaseOrder.getPurchaseId());
				purchaseOrderDetail.setProductId(shoppingCat.getPro_id());
				purchaseOrderDetail.setProductName(shoppingCat.getPro_name());
				purchaseOrderDetail.setProductPrice(shoppingCat.getPro_price());
				purchaseOrderDetail.setProductCount(shoppingCat.getPro_num());
				purchaseOrderDetail.setProductWeight(shoppingCat.getPro_weight());
				purchaseOrderDetail.setProductTotalPrice(shoppingCat.getPro_price().multiply(new BigDecimal(shoppingCat.getPro_num())).setScale(2,RoundingMode.HALF_UP));
				purchaseOrderDetail.setTpId(shoppingCat.getScale_id());
				purchaseOrderDetail.setTpName(shoppingCat.getScale_label());
				purchaseOrderDetail.setDetailStatue(1);
				purchaseOrderDetail.setDetailIsDelete(0);
				purchaseOrderDetail.setProduceDetailId(shoppingCat.getProduce_detail_id());
				
				totalPrice=totalPrice.add(purchaseOrderDetail.getProductTotalPrice());
				
				int s=yhsjPurchaseOrderDetailMapper.insert(purchaseOrderDetail);
				if(s==0){
					throw new PlatformException("采购单商品详情添加异常", 1);
				}
				
				int d=yhsjShoppingCatMapper.deleteByPrimaryKey(shoppingCat.getShopping_cat_id());
				if(d==0){
					throw new PlatformException("购物车商品删除异常", 1);
				}
			}
			
			purchaseOrder.setPurchaseSumprice(totalPrice);
			int ss=yhsjPurchaseOrderMapper.updateByPrimaryKey(purchaseOrder);
			if(ss==0){
				throw new PlatformException("采购单更新异常", 1);
			}
			result.setCode(ResultCode.SUCCESS);
			result.setMessage("结算成功");
		}catch(Exception e){
			e.printStackTrace();
			if(e instanceof PlatformException){
				PlatformException pl=(PlatformException)e;
				throw new PlatformException(pl.getLocalizedMessage(), pl.getExceptionCode());
        	}else{
        		throw new PlatformException(e.getLocalizedMessage(), 1);
        	}
		}
		return result;
	}

	@Override
	public int deleteShopCatBatch(List<String> ids) {
		// TODO Auto-generated method stub
		return yhsjShoppingCatMapper.deleteShopCatBatch(ids);
	}

}
