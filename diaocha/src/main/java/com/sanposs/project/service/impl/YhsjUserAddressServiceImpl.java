package com.sanposs.project.service.impl;

import com.sanposs.project.dao.YhsjUserAddressMapper;
import com.sanposs.project.model.YhsjUserAddress;
import com.sanposs.project.service.YhsjUserAddressService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/13.
 */
@Service
@Transactional
public class YhsjUserAddressServiceImpl extends AbstractService<YhsjUserAddress> implements YhsjUserAddressService {
    @Resource
    private YhsjUserAddressMapper yhsjUserAddressMapper;

	@Override
	public int setDefault1ByUserid(YhsjUserAddress record) {
		// TODO Auto-generated method stub
		return yhsjUserAddressMapper.setDefault1ByUserid(record);
	}

	@Override
	public int setDefault0ByUserid(YhsjUserAddress record) {
		// TODO Auto-generated method stub
		return yhsjUserAddressMapper.setDefault0ByUserid(record);
	}

	@Override
	public int deleteAddress(String address_id) {
		// TODO Auto-generated method stub
		return yhsjUserAddressMapper.deleteAddress(address_id);
	}

}
