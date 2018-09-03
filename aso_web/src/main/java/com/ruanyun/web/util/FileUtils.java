package com.ruanyun.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.utils.EmptyUtils;

public class FileUtils {
	//压缩包
	public static final String FILE_ZIP="rar,zip";
	//视频
	public static final String FILE_VIDEO="mp4,avi,wmv,mkv,mov,rmvb";//mp4、avi、wmv、flv、mkv、mov、rmvb
	//文本
	public static final String FILE_TEXT="pdf";
	//图片
	public static final String FILE_PIC="gif,jpg,jpeg,bmp,png";
	//logo图片
	public static final String FILE_LOGO="png";
	
	//apk文件
	public static final String FILE_APK="apk";
	
	
	public org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
	/**
	 * 功能描述:上传文件
	 *
	 * @author L H T  2014-3-27 下午03:12:16
	 * 
	 * @param file 文件
	 * @param path 路劲
	 * @param name 名称
	 * @param fileType 文件类型
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public int uploadFile(MultipartFile file, String path, String name,String fileType,HttpServletRequest request) throws IOException{
		if(EmptyUtils.isEmpty(file)){
			logger.error("文件不存在");
			return Constants.FILE_ERROR_NOFILE;
		}
		
		if(name.length()>Constants.FILE_NAME_MAX_SIZE){
			logger.error("上传文件名太长");
			return Constants.FILE_ERROR_MOREFILENAME;
		}
		
		if(vidateFileType(file.getOriginalFilename(), fileType)){
			logger.error("格式不正确");
			return Constants.FILE_ERROR_NOTYPE;
		}
		
		if (file.getSize() > Constants.FILE_MAX_SIZE_FILE) {
			logger.error("文件太大");
			return Constants.FILE_ERROR_MOREFILESIZE;
		}
		
		try {
//			/**
//			 * 如果是上传图片的话 就生成一张缩略图 
//			 */
//			if (fileType.equals(FILE_PIC)) {
//				lessenImage(file,name,request);
//			}
			File files = new File(path + name);
			//判断是否存在，不存在则创建
			if(!files.exists()){
				files.mkdirs();
			}
			file.transferTo(files);
		} catch (Exception e) {
			logger.error("文件上传失败");
			return Constants.FILE_FAIL;
		}
		logger.error("文件上传成功");
		return Constants.FILE_SUCCESS;
	}
	/**
	 * 功能描述:获取缩略图
	 *
	 * @author L H T  2014-3-28 下午02:43:58
	 * 
	 * @param file
	 * @param name
	 * @param request
	 * @return
	 */
//	public int lessenImage(MultipartFile file, String name,HttpServletRequest request){
//		//缩小的图片路劲
//		String lessen_path=CommonMethod.getProjectPath(request)+Constants.FILE_NARROW_PATH +name;
//		
//		try {
//			//生成小图文件文件
//			File lessen_files = ImageUtil.getLessen_Image(file.getInputStream(),lessen_path,Constants.PIC_COMPRESS_RATIO);
//			if (EmptyUtils.isEmpty(lessen_files)) {
//				lessen_files=new File(lessen_path);
//				//判断是否存在，不存在则创建
//				if(!lessen_files.exists()){
//					lessen_files.mkdirs();
//				}
//				file.transferTo(lessen_files);
//			}
//			return 1;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return -1;
//		}
//		
//	}
	/**
	 * 功能描述:验证文件格式  true 不合格 false 合格
	 *
	 * @author houkun  2013-10-15 上午09:45:27
	 * 
	 * @param fileName 文件名称
	 * @param fileType 文件类型
	 * @return
	 */
	private boolean vidateFileType(String fileName,String fileType) {
		System.out.println(fileName.lastIndexOf(Constants.FILE_BIT));
		if(EmptyUtils.isNotEmpty(fileName) && fileName.lastIndexOf(Constants.FILE_BIT)>=0){
			String fileSuffix=fileName.substring(fileName.lastIndexOf(Constants.FILE_BIT)+1);
			if(EmptyUtils.isEmpty(fileSuffix))
				return true;
			String[] suffixs=fileType.split(Constants.FILE_COMMA);
			for(String suffix : suffixs){
				if(fileSuffix.equals(suffix))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 功能描述:删除文件
	 *
	 * @author houkun  2013-10-15 下午03:11:20
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path){
		File file = new File(path);
		if(file.isFile() && file.exists()){
			return file.delete();
		}
		return false;
	}
	
	
	/**
	 * 功能描述:文件下载
	 *
	 * @author houkun  2013-10-16 上午09:08:48
	 * 
	 * @param request
	 * @param response
	 */
	public static void downloadFile(HttpServletRequest request,HttpServletResponse response,String path,String name){
		try {
			request.setCharacterEncoding("UTF-8");
			BufferedInputStream inputStream = null;
			BufferedOutputStream outputStream = null;
			long fileLength = new File(path).length();
			response.setHeader("Content-disposition", "attachment; filename=\""+name+"\"");
			response.setHeader("Content-Length", String.valueOf(fileLength));
			inputStream = new BufferedInputStream(new FileInputStream(path));
			outputStream = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = inputStream.read(buff, 0, buff.length))) {  
				outputStream.write(buff, 0, bytesRead);  
			}
			  inputStream.close();  
			  outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 public static void downloadLocal(HttpServletResponse response, String fileName, String conType) throws FileNotFoundException 
	 {
	     // 下载本地文件
	     // 读到流中C:\Program Files\Apache Software Foundation\download
		 String path = "C:/Program Files/Apache Software Foundation/download/";
		 InputStream inStream = new FileInputStream(path + fileName);// 文件的存放路径
        // 设置输出的格式
		 response.reset();
		 response.setContentType(conType);
		 response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		 // 循环取出流中的数据
		 byte[] b = new byte[100];
		 int len;
		 try 
		 {
			 while ((len = inStream.read(b)) > 0)
			 response.getOutputStream().write(b, 0, len);
			 inStream.close();
		 }
		 catch (IOException e) 
		 {
            e.printStackTrace();
		 }
    }

	 public static void downloadMobileConfig(HttpServletResponse response, String fileName, String conType, String masterid, String userId) throws FileNotFoundException 
	 {
	     // 下载本地文件
	     // 读到流中C:\Program Files\Apache Software Foundation\download
		 String path = "C:/Program Files/Apache Software Foundation/download/";
		 InputStream inStream = new FileInputStream(path + fileName);// 文件的存放路径
        // 设置输出的格式
		 response.reset();
		 response.setContentType(conType);
		 response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		 // 循环取出流中的数据
		 byte[] b = new byte[100];
		 ByteBuffer byteBuf = ByteBuffer.allocate(10240);   //临时存放总共读取的字节,大小自己估计
		 int len;
		 int sum = 0;
		 try {
			 while ((len = inStream.read(b)) > 0) 
			 {
				 byteBuf.put(b);
				 sum += len;
			 }
			byte[] bytes = new byte[sum];
			for(int i = 0;i<sum; i++){
			     bytes[i] = byteBuf.get(i);
			}

			String str = new String(bytes,"utf-8");
			String master = "masterid=" + masterid +"_" + userId;
			String s1 = str.replaceAll("masterid=11",master);
			System.out.println("-----------" + s1);
			byte[] result =  s1.getBytes();
			response.getOutputStream().write(result, 0, result.length);
			inStream.close();
		 } 
		 catch (IOException e)
		 {
            e.printStackTrace();
		 }
    }
}
