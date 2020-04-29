package com.sanposs.project.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class FastDFS_Http {


	@Value("${fastDFS_url}")
	private String fastDFS_url;

	@Value("${fastdfs_upload_file}")
	private String fastdfs_upload_file;//="/message/fastdfs/upload/file/sample";

	@Value("${fastdfs_upload_base64}")
	private String fastdfs_upload_base64;//="/message/fastdfs/upload/base64/sample";

	final static String BOUNDARY = "========7d4a6d158c9";
	final static String NEWLINE = "\r\n";
    final static String BOUNDARYPREFIX = "--";
	
 
    public String fastdfs_upload_httpURL(String Httpurl) throws IOException {
 
		InputStream inStream = null;
        try {

         	Httpurl = Httpurl.split(".")[1];
        	
         	if( Httpurl.contains("?") ){
         		Httpurl = Httpurl.split("\\?")[0];
         	}
   
        	URL urlphoto = new URL( Httpurl ); 
    		HttpURLConnection connHttp = (HttpURLConnection)urlphoto.openConnection();  
    		connHttp.setRequestMethod("GET");  
    		connHttp.setConnectTimeout(10 * 1000);  
    		inStream = connHttp.getInputStream();//通过输入流获取图片数据  
       
    	    //截取图片名后缀
    	    String ext = FilenameUtils.getExtension( Httpurl );
    	    String uuidName = UUID.randomUUID().toString().replace("-", "").toLowerCase()+"."+ext;
      
            return fastdfs_upload(new DataInputStream(inStream), uuidName);
            
        } catch (Exception e) {
          	e.printStackTrace();
        }finally {
            if(inStream != null) inStream.close();
         }
		return "";  
    }
    
    public String fastdfs_upload_poth(String poth) throws Exception {
    	 
        DataInputStream in = null;
        try {
 
        	File file = new File(poth);
        	 
            // 数据输入流,用于读取文件数据
            in = new DataInputStream(new FileInputStream(file));
     
            return  fastdfs_upload(in, file.getName());
        } catch (Exception e) {
          	e.printStackTrace();
          	throw e;
        } finally {
           if(in != null) in.close();
        }
         
    }

    public String fastdfs_upload_file(MultipartFile file) throws Exception {
     
        DataInputStream in = null;
        try {

        	in = new DataInputStream( file.getInputStream() );
        	
            return fastdfs_upload(in , file.getOriginalFilename());
            
        } catch (Exception e) {
          		throw e;
        } finally {
           if(in != null) in.close();
 
        }
 
    }

    public String fastdfs_upload_base64(String base64) throws Exception {
        
    	Map<String, String> base64Map = Base64_Util.attribute_Imgsplit(base64);

        Map<String, String> base = new HashMap<String, String>();
                
        	base.put("filename", base64Map.get("uuidfileName"));
        	base.put("base64", base64Map.get("filebase64"));
 
          	return fastdfs_upload(base);
    }

    /**
     *   真实动作层
     */
    public String fastdfs_upload(DataInputStream in , String filename) throws IOException {
   	 
        BufferedReader reader = null;
        OutputStream out = null;
       
        try {

            // 服务器的域名
           // URL url = new URL(fastDFS_url+fastdfs_upload_file);
            URL url = new URL("http://filews.yilufa.net/fastdfs/upload/file/sample");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            out = new DataOutputStream(conn.getOutputStream());
 
            StringBuilder sb = new StringBuilder();


//            sb.append(BOUNDARYPREFIX + BOUNDARY + NEWLINE).append("Content-Disposition: form-data;name=\"A\"");
//            sb.append("\r\n\r\n").append("ASD").append("\r\n\r\n");
           
            sb.append(BOUNDARYPREFIX).append(BOUNDARY).append(NEWLINE);

            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"").append(filename).append("\"").append(NEWLINE);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(NEWLINE);
            sb.append(NEWLINE);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());
 
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(NEWLINE.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (NEWLINE + BOUNDARYPREFIX + BOUNDARY + BOUNDARYPREFIX + NEWLINE).getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader( conn.getInputStream()) );
 
            return reader.readLine();
            
        } catch (Exception e) {
          	e.printStackTrace();
        } finally {
           if(in != null) in.close();
           if(out != null) out.close();
           if(reader != null) reader.close();
        }
        return "请求错误";
    }
    /**
     *   真实动作层
     */
    public String fastdfs_upload(Map<String,String> parameters) throws IOException {
      	 
  
    	   // 创建URL对象  
        java.net.URL connURL = new java.net.URL(fastDFS_url+fastdfs_upload_base64);  
    	
    	 String result = "";// 返回的结果  
         BufferedReader in = null;// 读取响应输入流  
         PrintWriter out = null;  
         StringBuffer sb = new StringBuffer();// 处理请求参数  
         String params = null;// 编码之后的参数  
         try {  
             // 编码请求参数  
             if (parameters.size() == 1) {  
                 for (String name : parameters.keySet()) {  
                     sb.append(name).append("=").append( java.net.URLEncoder.encode(parameters.get(name),"UTF-8"));  
                 }
                 params = sb.toString();  
             } else {
                 for (String name : parameters.keySet()) {  
                     sb.append(name).append("=").append( java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");  
                 }
                 params = sb.toString();   
             } 

             // 打开URL连接  
             java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();  
             // 设置通用属性  
             httpConn.setRequestProperty("Accept", "*/*");  
             httpConn.setRequestProperty("Connection", "Keep-Alive");  
             httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
             // 设置POST方式  
             httpConn.setDoInput(true);  
             httpConn.setDoOutput(true);  
             // 获取HttpURLConnection对象对应的输出流  
             out = new PrintWriter(httpConn.getOutputStream());  
             // 发送请求参数  
             out.write(params);
             // flush输出流的缓冲  
             out.flush();  
             // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
             in = new BufferedReader(new InputStreamReader(httpConn  
                     .getInputStream(), "UTF-8"));  
             String line;  
             // 读取返回的内容  
             while ((line = in.readLine()) != null) {  
                 result += line;  
             }  
 
         } catch (Exception e) {  
             e.printStackTrace();  
         } finally {  
             try {  
                 if (out != null){ out.close();}  
                 if (in != null){ in.close();}  
             } catch (IOException ex) {  
                 ex.printStackTrace();  
             }  
         }  
         return result;  
   
    }
    
    
    public static String sendPost(String url, Map<String, String> parameters) {  
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;  
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append( java.net.URLEncoder.encode(parameters.get(name),"UTF-8"));  
                }
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append( java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params;//.substring(0, temp_params.length() - 1);  
            } 
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 设置POST方式  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());  
            // 发送请求参数  
            out.write(params);
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
 
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null){ out.close();}  
                if (in != null){ in.close();}  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
 
	 public static void main(String[] args) throws Exception {
		 
		 Map<String, String> parameters  = new HashMap<String, String>();
		 
		   String base64="12345678901234567890";
       	
		 parameters.put("base64", base64);
		 parameters.put("filename", "aaa.png");
		 
		 //new FastDFS_Http().fastdfs_upload_poth( "http://filews.yilufa.net/fastdfs/upload/file/sample");
	 
		System.err.println(new FastDFS_Http().fastdfs_upload_poth("C://s2.jpg"));
	}
 
    
}
