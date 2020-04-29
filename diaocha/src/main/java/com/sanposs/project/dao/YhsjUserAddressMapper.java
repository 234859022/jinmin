package com.sanposs.project.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

import com.sanposs.project.core.Mapper;
import com.sanposs.project.model.YhsjUserAddress;

public interface YhsjUserAddressMapper extends Mapper<YhsjUserAddress> {
	
	@Update({
        "update yhsj_user_address",
        "set ",
        "user_is_default = 1 ",
        "where address_id = #{addressId,jdbcType=VARCHAR}"
    })
    int setDefault1ByUserid(YhsjUserAddress record);
    
    @Update({
        "update yhsj_user_address",
        "set ",
        "user_is_default = 0 ",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int setDefault0ByUserid(YhsjUserAddress record);
    
    @Delete({
        "delete from yhsj_user_address",
        "where address_id = #{addressId,jdbcType=VARCHAR}"
    })
    int deleteAddress(String address_id);
}