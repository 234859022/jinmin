package com.jinmin.pspay.admin.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jinmin.pspay.admin.common.CommonUtil;
import com.jinmin.pspay.admin.common.Constant;
import com.jinmin.pspay.admin.service.base.SystemmgConfigService;
import com.jinmin.pspay.admin.util.UUIDUtil;
import com.jinmin.pspay.model.base.SystemmgConfig;

/**
 * 鏂囦欢涓婁紶UploadController绫�
 * 
 * @author chenjinmin
 * @since 2016-1-20
 * 
 */
@Controller
@RequestMapping("/uploadManager")
public class UploadController {
	
	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private SystemmgConfigService systemmgConfigService;

	/**
	 * 杩欓噷杩欓噷鐢ㄧ殑鏄疢ultipartFile[] myfiles鍙傛暟,鎵�互鍓嶅彴灏辫鐢�input type="file"
	 * name="myfiles"/>
	 * 涓婁紶鏂囦欢瀹屾瘯鍚庤繑鍥炵粰鍓嶅彴[0`filepath],0琛ㄧず涓婁紶鎴愬姛(鍚庤窡涓婁紶鍚庣殑鏂囦欢璺緞),1琛ㄧず澶辫触(鍚庤窡澶辫触鎻忚堪)
	 * 
	 * @param myfiles
	 * @param myImage
	 * @param request
	 * @param response
	 * @param childPath
	 *            璺緞锛屽皢鍦╱pload涓嬫柊寤烘枃浠跺す淇濆瓨鏂囦欢
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/fileUpload")
	@ResponseBody
	public String fileUpload(HttpServletRequest request,
			HttpServletResponse response, String childPath, String control)
			throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> myfiles = multipartRequest.getFiles(control);

		// 璁剧疆鍝嶅簲缁欏墠鍙板唴瀹圭殑鏁版嵁鏍煎紡
		response.setContentType("text/plain; charset=UTF-8");
		// 璁剧疆鍝嶅簲缁欏墠鍙板唴瀹圭殑PrintWriter瀵硅薄
		PrintWriter out = response.getWriter();
		// 涓婁紶鏂囦欢鐨勫師鍚�鍗充笂浼犲墠鐨勬枃浠跺悕瀛�
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
				out.print("1`璇烽�鎷╂枃浠跺悗涓婁紶");
				out.flush();
				return null;
			} else {
				log.info("========================================");
				log.info("鏂囦欢鍘熷悕: " + originalFilename);
				log.info("鏂囦欢鍚嶇О: " + myfile.getName());
				log.info("鏂囦欢闀垮害: " + myfile.getSize());
				log.info("鏂囦欢绫诲瀷: " + myfile.getContentType());
				log.info("鏂囦欢鍚庣紑鍚�" + fileType);
				log.info("========================================");
				if(CommonUtil.isEmpty(Constant.UPLOAD_FILEPATH)){
					SystemmgConfig systemmgConfig = this.systemmgConfigService.getSystemmgConfigListByNameAndKey("绯荤粺閰嶇疆", "1");
					Constant.UPLOAD_FILEPATH = new File("/").getAbsolutePath() +(systemmgConfig == null ? "" : systemmgConfig.getConfigValue());
				}
				String url = Constant.UPLOAD_FILEPATH +(childPath == null ? "" : childPath);
				//String url = request.getSession().getServletContext().getRealPath("upload"+ (childPath == null ? "" : "/" + childPath));

				String fileName = myfile.getOriginalFilename();
				String fileUUIDName = UUIDUtil.createUUID();
				fileName = fileUUIDName + "." + myfile.getOriginalFilename().split("\\.")[myfile.getOriginalFilename().split("\\.").length - 1];

				File targetFile = new File(url, fileName);
				// 鐢熸垚鍟嗗鏂囦欢澶�
				File file = new File(url);
				if (!file.exists() && !file.isDirectory()) {
					boolean b = file.mkdir();
				} else {
				}
				// 淇濆瓨
				try {
					myfile.transferTo(targetFile);
					out.print("0`" + getPath(request) + "/upload" + (childPath == null ? "" : "/" + childPath) + "/" + fileName);
					out.flush();
				} catch (Exception e) {
					log.info("鏂囦欢[" + originalFilename + "]涓婁紶澶辫触,鍫嗘爤杞ㄨ抗濡備笅");
					log.info("涓婁紶鏂囦欢鍑虹幇寮傚父锛� + e.getMessage(), e);
					out.print("1`鏂囦欢涓婁紶澶辫触锛岃閲嶈瘯锛侊紒");
					out.flush();
				}
			}
		}
		return null;
	}

	/**
	 * 鍒犻櫎鍗曚釜鏂囦欢
	 * 
	 * @param sPath
	 *            琚垹闄ゆ枃浠剁殑鏂囦欢鍚�
	 * @return 鍗曚釜鏂囦欢鍒犻櫎鎴愬姛杩斿洖
	 * @throws IOException
	 */
	// 鍒犻櫎鏂囦欢
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	@ResponseBody
	public void deleteFile(HttpServletRequest request,
			HttpServletResponse response, String sPath, String model)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		if(CommonUtil.isEmpty(Constant.UPLOAD_FILEPATH)){
			SystemmgConfig systemmgConfig = this.systemmgConfigService.getSystemmgConfigListByNameAndKey("绯荤粺閰嶇疆", "1");
			Constant.UPLOAD_FILEPATH = new File("/").getAbsolutePath() +(systemmgConfig == null ? "" : systemmgConfig.getConfigValue());
		}
		String url = Constant.UPLOAD_FILEPATH;
		//String url = request.getSession().getServletContext().getRealPath("upload");
		String msg = "0";
		String path = "";
		if (sPath != null) {
			if (sPath.indexOf("upload") > -1) {
				path = sPath.substring(sPath.indexOf("upload") + 6);
			}
		}
		File file = new File(url, path);
		// 璺緞涓烘枃浠朵笖涓嶄负绌哄垯杩涜鍒犻櫎
		if (file.isFile() && file.exists()) {
			file.delete();
			// 濡傛灉鏄彍鍗曠鐞嗘ā鍧楋紝杩樿鍒犻櫎澶у浘
			if (model != null && model.equals("busiMenu")) {
				String[] str = path.split("\\/");
				String path2 = "\\";
				if (str.length >= 2) {
					for (int i = 0; i < str.length - 1; i++) {
						if (str[i].equals(""))
							continue;
						path2 += str[i] + "\\";
					}
					path2 += "largeImg\\" + str[str.length - 1];
				}
				File file2 = new File(url, path2);
				if (file2.isFile() && file2.exists()) {
					file2.delete();
					// map.put("mes", "1");
				} else {
					// map.put("mes", "0");
				}
			}
			msg = "1";
		} else {
			msg = "0";
		}
		String json = "{\"message\":\"" + msg + "\"}";
		response.getWriter().write(json);
	}
	/**
	 * 閾炬帴鍓嶇紑
	 * @param request
	 * @return
	 */
	private String getPath(HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		sb.append(request.getScheme()).append("://").append(request.getServerName());
		if(request.getServerPort() != 80){
			sb.append(":").append(request.getServerPort());
		}
		sb.append(request.getContextPath());
		return sb.toString();
	}

}
