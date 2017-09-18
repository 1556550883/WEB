/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-18
 */
package com.ruanyun.web.service.app;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TAdverEffectiveInfo;
import com.ruanyun.web.model.TAdverInferface;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelAdverStep;
import com.ruanyun.web.model.TChannelAdverStepUser;
import com.ruanyun.web.service.background.AdverInferfaceService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverStepService;
import com.ruanyun.web.service.background.ChannelAdverStepUserService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.EncrypDES;
import com.ruanyun.web.util.HttpRequestUtil;

/**
 *@author feiyang
 *@date 2016-1-18
 */
@Service
public class AppChannelAdverStepService extends BaseServiceImpl<TChannelAdverStep>{
	@Autowired
	private ChannelAdverStepUserService channelAdverStepUserService;
	@Autowired
	private ChannelAdverStepService channelAdverStepService;
	@Autowired
	private AdverInferfaceService adverInferfaceService;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	
	/**
	 * 功能描述: 安装后调用的接口
	 *
	 * @author yangliu  2016年1月21日 下午10:52:24
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param phoneSerialNumber 手机序列号
	 * @param systemVersion 系统版本号
	 * @param imei
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @return
	 */
	@Transactional
	public AppCommonModel saveInstaller(String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel model = new AppCommonModel("", "{}");
		TChannelAdverStepUser channelAdverStepUser=channelAdverStepUserService.saveInstall(userNum, adverNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		if(channelAdverStepUser==null)
			return model;
		
		return getNewChannelAdverStep(userNum, adverNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
	}
	
	@Transactional
	public AppCommonModel saveInstallerIos(HttpServletRequest request,String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel model = new AppCommonModel("", "{}");
		TChannelAdverInfo adverInfo=channelAdverInfoService.getInfoByAdverNum(adverNum);
		TChannelAdverStepUser channelAdverStepUser=channelAdverStepUserService.saveInstall(userNum, adverNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		channelAdverStepUser.setFlag1(request.getParameter(Constants.PARAMETER_APPSTORENAME)); // 
		channelAdverStepUserService.update(channelAdverStepUser);
		Map<String, TAdverInferface> map=adverInferfaceService.getMapAdverInferfaceByAdverNum(adverNum, Constants.PARAMETER_INFERFACE_REQUEST_TYPE_SERVER, Constants.PARAMETER_INFERFACE_TYPE_ADVER);
		/*getRequestUrl(request, adverInfo, map, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);*/
		getRequestUrl(request, adverInfo.getFlag3(), "2", adverInfo, map, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		if(channelAdverStepUser==null)
			return model;
		model.setResult(AppCommonModel.SUCCESS);
		return model;
	}
	
	public AppCommonModel requestUrlCommon(HttpServletRequest request,String typeStep,String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel model = new AppCommonModel("", "{}");
		TChannelAdverInfo adverInfo=channelAdverInfoService.getInfoByAdverNum(adverNum);
		Map<String, TAdverInferface> map=adverInferfaceService.getMapAdverInferfaceByAdverNum(adverNum, Constants.PARAMETER_INFERFACE_REQUEST_TYPE_SERVER, Constants.PARAMETER_INFERFACE_TYPE_ADVER);
		String requestUrl="";
		if("1".equals(typeStep)){
			requestUrl=adverInfo.getFlag2();
		}else if("2".equals(typeStep)){
			requestUrl=adverInfo.getFlag3();
		}else if("3".equals(typeStep)){
			requestUrl=adverInfo.getFlag4();
		}
		getRequestUrl(request, requestUrl, typeStep, adverInfo, map, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		model.setResult(AppCommonModel.SUCCESS);
		return model;
	}
	
	/*public void getRequestUrl(HttpServletRequest request,TChannelAdverInfo adverInfo,Map<String, TAdverInferface> map,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		try{
		if(EmptyUtils.isEmpty(idfa))
			idfa=phoneSerialNumber;
		String url="";
		TAdverEffectiveInfo info = new TAdverEffectiveInfo();
		StringBuffer params=new StringBuffer();
		if(EmptyUtils.isNotEmpty(idfa))
			params.append("&").append(getInferfaceValue(map,Constants.PARAMETER_IDFA)).append("=").append(idfa);
		if(EmptyUtils.isNotEmpty(ip))
			params.append("&").append(getInferfaceValue(map,Constants.PARAMETER_IP)).append("=").append(ip);
		String callback=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/app/activation/adver/"+EncrypDES.encryptEde(adverInfo.getAdverNum())+"?idfa="+idfa+"&phoneSerialNumber="+phoneSerialNumber+"&score="+adverInfo.getAdverPrice()+"&uniquePrimaryKey="+System.nanoTime()+"&adverName="+adverInfo.getAdverName();
		System.out.println("callback========="+callback);
		params.append("&callback").append("=").append(URLEncoder.encode(callback,"utf-8"));
		System.out.println("params"+params.toString());
		if(EmptyUtils.isNotEmpty(adverInfo.getFlag2())&& adverInfo.getFlag2().indexOf("?")>-1){
			url= adverInfo.getFlag2()+params.toString().trim();
		}else{
			url=adverInfo.getFlag2()+"?"+params.toString().trim();
		}
		System.out.println("url:=============="+url);
		String result=HttpRequestUtil.sendGet(url, "");
		String result2=HttpRequestUtil.sendGet(adverInfo.getFlag3()+params.toString().trim(), "");
		System.out.println("result==============="+result);
		}catch(Exception e){
			System.out.println("error:"+e.getMessage());
		}
	}*/
	
	public void getRequestUrl(HttpServletRequest request,String requestUrl,String typeStep,TChannelAdverInfo adverInfo,Map<String, TAdverInferface> map,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		try{
		if(EmptyUtils.isEmpty(idfa))
			idfa=phoneSerialNumber;
		String url="";
		TAdverEffectiveInfo info = new TAdverEffectiveInfo();
		StringBuffer params=new StringBuffer();
		if(EmptyUtils.isNotEmpty(idfa))
			params.append("&").append(getInferfaceValue(map,Constants.PARAMETER_IDFA)).append("=").append(idfa);
		if(EmptyUtils.isNotEmpty(ip))
			params.append("&").append(getInferfaceValue(map,Constants.PARAMETER_IP)).append("=").append(ip);
		String callback=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/app/activation/adver/"+EncrypDES.encryptEde(adverInfo.getAdverNum())+"?idfa="+idfa+"&phoneSerialNumber="+phoneSerialNumber+"&score="+adverInfo.getAdverPrice()+"&uniquePrimaryKey="+System.nanoTime()+"&adverName="+adverInfo.getAdverName();
		System.out.println("callback========="+callback);
		if("2".equals(typeStep)) //第二步 需要添加 回调
		params.append("&callback").append("=").append(URLEncoder.encode(callback,"utf-8"));
		System.out.println("params"+params.toString());
		if(EmptyUtils.isNotEmpty(requestUrl)&& requestUrl.indexOf("?")>-1){
			url= requestUrl+params.toString().trim();
		}else{
			url=requestUrl+"?"+params.toString().trim();
		}
		System.out.println("url:=============="+url);
		String result=HttpRequestUtil.sendGet(url, "");
		System.out.println("result==============="+result);
		}catch(Exception e){
			System.out.println("error:"+e.getMessage());
		}
	}
	
	public String getInferfaceValue(Map<String, TAdverInferface> map,String key){
		TAdverInferface adverInferface=map.get(key);
		if(EmptyUtils.isNotEmpty(adverInferface))
			return adverInferface.getParameterName().trim();
		return "";
	}
	
	/**
	 * 功能描述: 获取最新步骤
	 *
	 * @author yangliu  2016年1月21日 下午10:54:20
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param phoneSerialNumber 手机序列号
	 * @param systemVersion 系统版本号
	 * @param imei
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @return
	 */
	@Transactional
	public AppCommonModel getNewChannelAdverStep(String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel model = new AppCommonModel("", "{}");
		TChannelAdverStep channelAdverStep=channelAdverStepService.getTChannelAdverStep(userNum, adverNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		if(channelAdverStep==null)
			return model;
		model.setResult(AppCommonModel.SUCCESS);
		model.setObj(channelAdverStep);
		return model;
	}
	
	/**
	 * 功能描述: 已获取奖励
	 *
	 * @author yangliu  2016年1月21日 下午11:00:11
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param adverStepNum 广告步骤表
	  * @param phoneSerialNumber 手机序列号
	 * @param systemVersion 系统版本号
	 * @param imei
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @return
	 */
	public AppCommonModel updateAdverstepUser(String userNum,String adverNum,String adverStepNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		AppCommonModel model = new AppCommonModel("", "{}");
		Integer result=channelAdverStepUserService.updateAdverstepUser(userNum, adverNum, adverStepNum, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		if(result<0)
			return model;
		model.setResult(AppCommonModel.SUCCESS);
		model.setMsg("已获取奖励");
		return model;
	}
	
}
