/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-18
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.service.app.AppChannelAdverStepService;

/**
 *@author feiyang
 *@date 2016-1-18
 */
@Controller
@RequestMapping("app/step")
public class AppChannelAdverStepController extends BaseController{
	@Autowired
	private AppChannelAdverStepService  appChannelAdverStepService;
	/**
	 * 功能描述: 安装软件成功后 保存记录
	 *
	 * @author yangliu  2016年1月21日 下午11:09:50
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param phoneSerialNumber 手机序列号 唯一
	 * @param systemVersion 系统版本
	 * @param imei
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @return
	 */
	@RequestMapping("saveInstaller")
	public void saveInstaller(HttpServletResponse response,String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel commonModel=null;
		try{
			if(EmptyUtils.isEmpty(userNum)||EmptyUtils.isEmpty(adverNum)||EmptyUtils.isEmpty(phoneSerialNumber)){
				commonModel= new AppCommonModel("参数不全","{}");
			}else{
				commonModel= appChannelAdverStepService.saveInstaller(userNum, adverNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
			}
		}catch(Exception e){
			e.printStackTrace();
			commonModel= new AppCommonModel(e.getMessage(),"{}");
		}
		super.writeJsonDataApp(response, commonModel);
	}
	
	/**
	 * 功能描述: 安装软件成功后 保存记录
	 *
	 * @author yangliu  2016年1月21日 下午11:09:50
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param phoneSerialNumber 手机序列号 唯一
	 * @param systemVersion 系统版本
	 * @param imei
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @return
	 */
	@RequestMapping("saveInstallerIos")
	public void saveInstallerIos(HttpServletRequest request,HttpServletResponse response,String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel commonModel=null;
		try{
			if(EmptyUtils.isEmpty(ip)){
				ip=getIpAddress(request);
			}
			if(EmptyUtils.isEmpty(userNum)||EmptyUtils.isEmpty(adverNum)||EmptyUtils.isEmpty(phoneSerialNumber)){
				commonModel= new AppCommonModel("参数不全","{}");
			}else{
				commonModel= appChannelAdverStepService.saveInstallerIos(request,userNum, adverNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
			}
		}catch(Exception e){
			e.printStackTrace();
			commonModel= new AppCommonModel(e.getMessage(),"{}");
		}
		super.writeJsonDataApp(response, commonModel);
	}
	
	@RequestMapping("requestUrl")
	public void requestUrl(HttpServletRequest request,HttpServletResponse response,String typeStep,String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel commonModel=null;
		try{
			if(EmptyUtils.isEmpty(ip)){
				ip=getIpAddress(request);
			}
			if(EmptyUtils.isEmpty(userNum)||EmptyUtils.isEmpty(adverNum)||EmptyUtils.isEmpty(phoneSerialNumber)){
				commonModel= new AppCommonModel("参数不全","{}");
			}else{
				commonModel= appChannelAdverStepService.requestUrlCommon(request, typeStep, userNum, adverNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
			}
		}catch(Exception e){
			e.printStackTrace();
			commonModel= new AppCommonModel(e.getMessage(),"{}");
		}
		super.writeJsonDataApp(response, commonModel);
	}
	
	public static String getIpAddress(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	  } 
	/**
	 * 功能描述:  激活广告
	 *
	 * @author yangliu  2016年1月21日 下午11:09:50
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param adverStepNum  广告步骤编号
	 * @param phoneSerialNumber 手机序列号 唯一
	 * @param systemVersion 系统版本
	 * @param imei
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @return
	 */
	@RequestMapping("updateAdverstepUser")
	public void updateAdverstepUser(HttpServletResponse response,String userNum,String adverNum,String adverStepNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel commonModel=null;
		try{
			commonModel=appChannelAdverStepService.updateAdverstepUser(userNum, adverNum,adverStepNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		}catch(Exception e){
			e.printStackTrace();
			commonModel= new AppCommonModel(e.getMessage(),"{}");
		}
		super.writeJsonDataApp(response, commonModel);
	}
	
	/**
	 * 功能描述:获取广告最新的 任务步骤
	 *
	 * @author yangliu  2016年1月21日 下午11:09:50
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param phoneSerialNumber 手机序列号 唯一
	 * @param systemVersion 系统版本
	 * @param imei
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @return
	 */
	@RequestMapping("getNewChannelAdverStep")
	public void getNewChannelAdverStep(HttpServletResponse response,String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel commonModel=null;
		try{
			
			commonModel=appChannelAdverStepService.getNewChannelAdverStep(userNum, adverNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		}catch(Exception e){
			e.printStackTrace();
			commonModel= new AppCommonModel(e.getMessage(),"{}");
		}
		super.writeJsonDataApp(response, commonModel);
	}
}
