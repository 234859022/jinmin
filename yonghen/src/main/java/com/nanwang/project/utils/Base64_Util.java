package com.nanwang.project.utils;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import com.baidu.aip.util.Base64Util;



public class Base64_Util {

	/**
	 * @功能   将图片Base64拆分出文件信息
	 * @Map参数 fileType 文件类型 ,图片是 image     
	 * @Map参数 Suffix 文件名后缀      png\jpg
	 * @Map参数 filebase64  文件内容实体
	 * @Map参数 uuidfileName 随机文件名
	 */
	public static Map<String,String> attribute_Imgsplit( String base64String ) throws Exception {
		
	 
	        //文件头 data:image/png;base64,iVBORw0KGg
	        String anteriorSegment= base64String.substring(0, 30);
	      	
	        int colonOf = anteriorSegment.indexOf(':');
	        int SlashOf = anteriorSegment.indexOf('/');
	        int semicolonOf = anteriorSegment.indexOf(';');
	        int commaOf = anteriorSegment.indexOf(',');
	    
	        if( colonOf ==-1 || SlashOf ==-1 || semicolonOf ==-1 || commaOf ==-1){
	        	throw new Exception("Base64_Util.attribute_Imgsplit()传入的base64缺少文件头信息");
	        }

	        //文件类型 ,图片是 image
	        String fileType = anteriorSegment.substring(colonOf+1, SlashOf);
	        
	        //文件名后缀
	        String Suffix = anteriorSegment.substring(SlashOf+1, semicolonOf);
	        
	        //文件内容实体
	        String filebase64 = base64String.substring(commaOf+1);
	        
	        //随机文件名
	        String fileName = UUID.randomUUID().toString().replace("-", "").toLowerCase()+"."+Suffix;
	        
	        Map<String, String> map = new HashMap<String, String>();
	        
	        map.put("fileType", fileType);
	        map.put("Suffix", Suffix);
	        map.put("filebase64", filebase64);
	        map.put("uuidfileName", fileName);
			return map;
	        
	}

	/**
	 * @功能   精确计算base64字符串文件大小（单位：B）
	 */
	public static double base64file_size( String base64String )  {
		
		
		
		 	//1.获取base64字符串长度
			int size0 = base64String.length();
		
			//2.获取字符串的尾巴的最后10个字符，用于判断等号
			String tail = base64String.substring(size0-10);

			//3.找到等号，把等号也去掉,(等号其实是空的意思)
			int equalIndex = tail.indexOf("=");		
			if(equalIndex > 0) {
			  	size0 = size0 - (10 - equalIndex);
	        }
		
		 	//4.计算后得到的文件流大小，单位为字节
			return size0 -( (double)size0 / 8 ) * 2;
}

	/**
	 * @功能  inputStream_转_base64
	 * @注意    并且关闭InputStream流
	 */
	public static String inputStream_to_base64( InputStream input ) throws Exception {
		
		  ByteArrayOutputStream bos = new ByteArrayOutputStream( input.available());
	        BufferedInputStream in = null;
	        try {
	            in = new BufferedInputStream(input);
	            int bufSize = 1024;
	            byte[] buffer = new byte[bufSize];
	            int len = 0;
	            while (-1 != (len = in.read(buffer, 0, bufSize))) {
	                bos.write(buffer, 0, len);
	            }
	            return Base64Util.encode( bos.toByteArray() );
	        } catch (IOException e) {
				e.printStackTrace();
				throw e;
			} finally {
	            try {
	                if(in != null) in.close();
	                if(bos != null) bos.close();
	                if(input != null) input.close();
	            }catch(IOException e){ 
	            	e.printStackTrace(); 
	            }
	        }
}

	/**
	 * @功能  multipartFile_转_base64
	 */
	public static String multipartFile_to_base64( MultipartFile muin ) throws Exception {


        ByteArrayOutputStream bos = new ByteArrayOutputStream( (int) muin.getSize());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(muin.getInputStream());
            int bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, bufSize))) {
                bos.write(buffer, 0, len);
            }
            return Base64Util.encode( bos.toByteArray() );
        } catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
            try {
                if(in != null) in.close();
                if(bos != null) bos.close();
            }catch(IOException e){ 
            	e.printStackTrace(); 
            }
        }
}
	
	
	public static void main(String[] args) throws Exception {
		attribute_Imgsplit("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAfQAAAH0CAYAAADL1t+KAAAgAElEQVR4nIy9Z5NkaXqetz9K");
	}
}
