package com.nanwang.project.web;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.zxing.WriterException;
import com.nanwang.project.utils.QRCodeGenerator;

/**
 * @ClassName: DefaultController
 * @Description: TODO(描述)
 * @author chenjinmin
 * @date 2020-04-26 08:37:25
 */
@Controller
public class DefaultController {
    
    @GetMapping(value="/qrimage")
	public ResponseEntity<byte[]> getQRImage(String id) {
		
		//二维码内的信息
		String info = "This is my first QR Code";
		if(id!=null&&!"".equals(id)) {
			info  = id;
		}
		
		byte[] qrcode = null;
		try {
			qrcode = QRCodeGenerator.getQRCodeImage(info, 360, 360);
		} catch (WriterException e) {
			System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			
			System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
		} 

	    // Set headers
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);

	    return new ResponseEntity<byte[]> (qrcode, headers, HttpStatus.CREATED);
	}
    
    
    
}
