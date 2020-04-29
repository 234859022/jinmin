package com.sanposs.project.web;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.utils.UUIDGenerator;

/**
* 类名称: UploadController.java   
* 功能描述: 上传接口
* 创建者: chenjinmin 
* 创建时间:  2019年4月13日 下午3:40:11    
* 版本信息: V1.0
* CopyRight(C)：sanposs 2019 泉州市尚宝网络科技有限公司版权所有
 */
@Controller
@RequestMapping("/UploadController")
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Value("${upload.UPLOAD_FILEPATH}")
	private String UPLOAD_FILEPATH;
	@Value("${upload.ATTACH_URL}")
	private String ATTACH_URL;
	


	
    @RequestMapping("/upload") // 等价于 @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result uplaod(HttpServletRequest req, String childPath, String control, Model m) {//1. 接受上传的文件  @RequestParam("file") MultipartFile file
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		List<MultipartFile> myfiles = multipartRequest.getFiles(control);
		String originalFilename = null;
		for (MultipartFile myfile : myfiles) {
			originalFilename = myfile.getOriginalFilename();
			int index = originalFilename.lastIndexOf(".");
			String strFileType = null;
			String fileType = "jpg";
			if (index >= 0) {
				strFileType = originalFilename.substring(index, originalFilename.length() - 1);
				fileType = originalFilename.substring(index + 1, originalFilename.length());
			}
			if (myfile.isEmpty()) {
				return ResultGenerator.genSuccessResult("请选择文件后上传");
			} else {
				logger.info("========================================");
				logger.info("文件原名: " + originalFilename);
				logger.info("文件名称: " + myfile.getName());
				logger.info("文件长度: " + myfile.getSize());
				logger.info("文件类型: " + myfile.getContentType());
				logger.info("文件后缀名:" + fileType);
				logger.info("========================================");
//				String UPLOAD_FILEPATH= "D://tomcat-credit-web-9000//webapps//sanposs_image";
//				String ATTACH_URL = "http://localhost:8020/sanposs_image/";
				String url = UPLOAD_FILEPATH +(childPath == null ? "" :childPath);

				String fileName = myfile.getOriginalFilename();
				String fileUUIDName = UUIDGenerator.getUUID();
				fileName = fileUUIDName + "." + myfile.getOriginalFilename().split("\\.")[myfile.getOriginalFilename().split("\\.").length - 1];

				File targetFile = new File(url, fileName);
				// 生成文件夹
				File file = new File(url);
				if (!file.exists() && !file.isDirectory()) {
					boolean b = file.mkdir();
				} else {
				}
				// 保存
				try {
					myfile.transferTo(targetFile);
					File file1 = targetFile;  
					return ResultGenerator.genSuccessResult(ATTACH_URL + (childPath == null ? "" : "/" + childPath) + "/" + fileName);

				} catch (Exception e) {
					logger.info("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
					e.printStackTrace();
				}
			}
		}
		
        return ResultGenerator.genSuccessResult("上传失败");
    }
}