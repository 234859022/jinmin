package com.nanwang.project.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *     
 * 项目名称：IotServer    
 * 类名称：SerializeUtil    
 * 类描述：    
 * 创建人：kanghuibin    
 * 创建时间：2018-8-6 下午04:57:02    
 * 修改人：wuyongde    
 * 修改时间：2018-8-6 下午04:57:02    
 * 修改备注：    
 * @version 1.0  
 *
 */
public class SerializeUtil {

	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object unserialize( byte[] bytes) {
		if(bytes !=null){
			ByteArrayInputStream bais = null;
			try {
				// 反序列化
				bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
