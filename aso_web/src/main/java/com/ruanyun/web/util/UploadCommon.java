package com.ruanyun.web.util;

import java.io.IOException;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.model.sys.UploadVo;
import com.ruanyun.web.util.CommonMethod;
import com.ruanyun.web.util.FileUtils;
/**
 * 
 *  #(c) IFlytek ahsw <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 上传管理
 * 
 *  <br/>创建说明: 2013-12-3 下午12:22:10 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class UploadCommon{
	/**
	 * 功能描述:文件上传
	 *
	 * @author L H T  2013-12-3 下午05:49:12
	 * 
	 * @param files
	 * @param request
	 * @param folderUrl 需要保存的文件路劲
	 * @param validType  文件验证的类型
	 * @param id          数据的id，根据数据的id创建个文件夹
	 * @return
	 * @throws IOException
	 */
	public static UploadVo uploadPic(MultipartFile files,HttpServletRequest request,String folderUrl,String validType) throws IOException{
		//文件上传结果保存对象
		UploadVo vo=new UploadVo();
//		//上传文件的二进制请求
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		// 文件
//		MultipartFile files = multipartRequest.getFile("myfile");
		//文件名称
		String name = files.getOriginalFilename();
		//改变图片名称为当前时间 
		name=TimeUtil.getCurrentDay("yyyyMMddHHmmssSSS")+name.substring(name.lastIndexOf("."));//截取文件名称

		//获取文件保存路劲
		String path= CommonMethod.getProjectPath(request)+folderUrl+"//";
		//保存文件  --
		FileUtils upload = new FileUtils();
		/**
		 * 返回上传结果
		 * 1  上传成功
		 * -1 上传失败（或者创建文件夹时失败）
		 * -2 上传文件文件名太长
		 * -3 上传文件格式不正确
		 * -4 上传文件太大
		 * -5 上传 文件不存在
		 * 
		 */
		int upload_result=upload.uploadFile(files,path,name,validType,request);
		//判断返回的结果  --显示给用户
		if (upload_result==Constants.FILE_SUCCESS) {
			//文件保存所在路径
			vo.setFilename(name);
		}
		vo.setResult(upload_result);
		
		return vo;
		
	}
	//public void uploadVideo(MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws UnknownHostException{
//		//上传文件的二进制请求
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		// 文件
//		MultipartFile files = multipartRequest.getFile("myfile");
//		String contextPath=request.getContextPath();//项目名称
//		String fileName=files.getOriginalFilename();//视频名称
//		String type=fileName.substring(fileName.lastIndexOf("."));//获取视频格式
//		String reg = "(.MP4|.mp4|.SWF|.swf|.FLV|.flv)$";
//        Pattern pattern = Pattern.compile(reg);
//        Matcher matcher = pattern.matcher(type.toLowerCase());
//		if(!matcher.find()){
//			super.writeText(response, "false");
//		}else{
//		String path =request.getSession().getServletContext().getRealPath("/");
//		path=path+Constants.FILE_VIDEO_PATH_NAME+"\\";
//		System.out.println("+++++++"+path);
//		String video_result=picService.uploadVideo(files, path, "video");
//		contextPath="/"+Constants.FILE_VIDEO_PATH_NAME+"/"+video_result;//视频地址
//		super.writeText(response, contextPath);
//		}
	//}

}
