package com.ruanyun.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 * 
 *  #(c) IFlytek baseweb <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 
 * 
 *  <br/>创建说明: 2013-7-18 下午05:29:17 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class ImageUtil {
	
	private final static int RESIZETIMES=5;
	
	
	/**
	 * 功能描述:压缩
	 *
	 * @author yangliu  2013-7-18 下午05:30:21
	 * 
	 * @param is
	 * @return
	 */
	public static byte[] getLessenImage(InputStream is){
		BufferedImage im=null;
		try {
			 im=javax.imageio.ImageIO.read(is);
			 BufferedImage bi=zoomImage(im, RESIZETIMES);
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 ImageIO.write(bi,"gif",out);
			 byte[] bs= out.toByteArray();
			 out.close();
			 return bs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	/**
	 * 功能描述:压缩
	 *
	 * @author yangliu  2013-7-18 下午05:30:21
	 * 
	 * @param is
	 * @return
	 */
	public static byte[] getLessenImage(InputStream is,int width){
		
        
		BufferedImage im=null;
		try {
			 im=javax.imageio.ImageIO.read(is);
			 /*原始图像的宽度和高度*/
		     int picwidth = im.getWidth();
		     //如果原始宽度小于 缩小比例宽度 就返回空
		     if (picwidth<=350) {
				  return null;
			}
			 BufferedImage bi=zoomImage(width,im);
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 ImageIO.write(bi,"gif",out);
			 byte[] bs= out.toByteArray();
			 out.close();
			 return bs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	/**
	 * 功能描述:把图片按倍数压缩
	 *
	 * @author yangliu  2013-7-18 下午05:28:22
	 * 
	 * @param im 图片
	 * @param resizeTimes  倍数 如缩小2倍
	 * @return 图片流
	 */
	public static BufferedImage zoomImage(int toWidth,BufferedImage im) {
        /*原始图像的宽度和高度*/
        int picwidth = im.getWidth();
        int toHeight = im.getHeight()*toWidth/picwidth;
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
	
	
	/**
	 * 功能描述:把图片按倍数压缩
	 *
	 * @author yangliu  2013-7-18 下午05:28:22
	 * 
	 * @param im 图片
	 * @param resizeTimes  倍数 如缩小2倍
	 * @return 图片流
	 */
	public static BufferedImage zoomImage(BufferedImage im, int resizeTimes) {
        /*原始图像的宽度和高度*/
        int width = im.getWidth();
        int height = im.getHeight();
        /*调整后的图片的宽度和高度*/
        int toWidth = (int) (Float.parseFloat(String.valueOf(width/resizeTimes)));
        int toHeight = (int) (Float.parseFloat(String.valueOf(height/resizeTimes)));
        /*新生成结果图片*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
	
	/**
	 * 功能描述:缩小MultipartFile上传的图片 ，然后重新写入File里
	 *
	 * @author L H T  2013-12-10 下午02:42:01
	 * 
	 * @param is 输入流
	 * @param lessen_path 缩小后图片地址
	 * @param width 制定的宽度
	 * @return
	 */
	public static File getLessen_Image(InputStream is,String lessen_path,int width ){
		BufferedImage im=null;
		try {
			 im=javax.imageio.ImageIO.read(is);
			 /*原始图像的宽度和高度*/
		     int picwidth = im.getWidth();
		     //如果原始宽度小于 缩小比例宽度 就返回空
		     if (picwidth>width) {
		    	 BufferedImage bi=zoomImage(width, im);
		    	 File destFile = new File(lessen_path);
		    	//判断是否存在，不存在则创建
					if(!destFile.exists()){
						destFile.mkdirs();
					}
				 ImageIO.write(bi,"gif",destFile);
				 return destFile;
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	/**
	 * 功能描述:获得图片的宽高
	 *
	 * @author L H T  2014-3-27 上午11:05:40
	 * 
	 * @return
	 */
	public static Map<String ,Integer> getImageSize(InputStream pic){
		BufferedImage im=null;
		 try {
			im=javax.imageio.ImageIO.read(pic);
			 /*原始图像的宽度和高度*/
		     int picWidth = im.getWidth();
		     int picHeight=im.getHeight();
		     Map<String,Integer> map=new HashMap<String, Integer>();
		     map.put("width", picWidth);
		     map.put("height", picHeight);
		     return map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
			
		}
		
		
	}

	/***
	 * 功能描述:判断上传的图片尺寸是否符合要求
	 * @author  程三发 2015-12-22 下午02:45:29
	 * @param picFile 图片
	 * @param width   宽
	 * @param heigth  高
	 * @return
	 * @throws IOException
	 */
	public static boolean checkeImageSize(InputStream inputStream,int width,int heigth) throws IOException{
		BufferedImage bi = ImageIO.read(inputStream);
		int width1=bi.getWidth();
		int height1=bi.getHeight();
		if(width1!=width&&height1!=heigth){
			return true;
		}
		return false;
	}
}
