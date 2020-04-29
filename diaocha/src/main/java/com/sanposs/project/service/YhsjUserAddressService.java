package com.sanposs.project.service;
import com.sanposs.project.model.YhsjUserAddress;
import com.sanposs.project.core.Service;


/**
 * Created by CodeGenerator on 2019/05/13.
 */
public interface YhsjUserAddressService extends Service<YhsjUserAddress> {

	public int setDefault1ByUserid(YhsjUserAddress record);
	
	public int setDefault0ByUserid(YhsjUserAddress record);
	
	public int deleteAddress(String address_id);
}
