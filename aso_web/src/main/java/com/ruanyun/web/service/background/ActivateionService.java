package com.ruanyun.web.service.background;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TAdverEffectiveInfo;
import com.ruanyun.web.model.TAdverInferface;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelAdverStepUser;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.EncrypDES;

import net.sf.json.JSONObject;

@Service
public class ActivateionService 
{
	protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
	
	@Autowired
	private AdverInferfaceService adverInferfaceService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private AdverEffectiveInfoService adverEffectiveInfoService;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	@Autowired
	private ChannelAdverStepUserService channelAdverStepUserService;
	
	/**
	 * 功能描述: 广告回调有效
	 *
	 * @param adverNum
	 * @param request
	 * @return
	 */
	@Transactional
	public String activateionAdver(String adverNum,HttpServletRequest request)
	{
		adverNum = EncrypDES.decryptEde(adverNum);
		String msg = "";

		if(EmptyUtils.isEmpty(adverNum)) 
		{
			return msg;
		}
		
		Map<String, TAdverInferface> map = adverInferfaceService.getMapAdverInferfaceByAdverNum(adverNum, 
				Constants.PARAMETER_INFERFACE_REQUEST_TYPE_SERVER, Constants.PARAMETER_INFERFACE_TYPE_ADVER);
		TAdverEffectiveInfo errectiveInfo = getErrectiveInfo(request,map);
		msg = getInferfaceValue(map, Constants.PARAMETER_ERROR);

		if(EmptyUtils.isEmpty(errectiveInfo.getPhoneSerialNumber()))
		{  
			//用户为空
			logger.error("唯一标识不存在");
			return msg;
		}
		
		TChannelAdverInfo  adverInfo = channelAdverInfoService.getInfoByAdverNum(adverNum);
		
		if(EmptyUtils.isEmpty(adverInfo))
		{
			logger.error("广告不存在:"+adverNum);
			return msg;
		}
		
		Integer count = adverEffectiveInfoService.getEffectiveInfoByAdverNum(adverNum, errectiveInfo.getUniquePrimaryKey());
		if(count>0)
		{
			logger.error("数据已重复 重复编号为:"+errectiveInfo.getUniquePrimaryKey());
			return msg;
		}
		//TChannelInfo channelInfo=channelInfoService.getInfoByNum(channelNum);
	//	errectiveInfo.setChannelName(channelInfo.getChannelName());
		TChannelAdverStepUser adverStepUser = channelAdverStepUserService.getNewAdverStepUser(adverNum, "", errectiveInfo.getPhoneSerialNumber(),"");
		
		if(EmptyUtils.isNotEmpty(adverStepUser))
		{
			errectiveInfo.setChannelNum(adverInfo.getChannelNum());
			adverEffectiveInfoService.saveAdverEffectiveInfo(adverStepUser.getUserAppNum(), errectiveInfo);
		}
		
		return getInferfaceValue(map, Constants.PARAMETER_SUCCESS);
	}
	
	/**
	 * 功能描述: 渠道回调有效
	 *
	 * @param channelNum
	 * @param request
	 * @return
	 */
	@Transactional
	public String activateionChannel(String channelNum,HttpServletRequest request)
	{
		String msg="";
		channelNum=EncrypDES.decryptEde(channelNum);
		
		logger.error("channelNum:"+channelNum+"====="+JSONObject.fromObject(request.getParameterMap()).toString());
		System.out.println("==========="+JSONObject.fromObject(request.getParameterMap()).toString());
		if(EmptyUtils.isEmpty(channelNum))
			return msg;
		Map<String, TAdverInferface> map=adverInferfaceService.getMapAdverInferfaceByAdverNum(channelNum, Constants.PARAMETER_INFERFACE_REQUEST_TYPE_SERVER, Constants.PARAMETER_INFERFACE_TYPE_CHANNEL);
		TAdverEffectiveInfo errectiveInfo=getErrectiveInfo(request,map);
		msg=getInferfaceValue(map, Constants.PARAMETER_ERROR);
		if(EmptyUtils.isEmpty(errectiveInfo.getUserNum())){  //用户为空
			logger.error("用户编号为空");
			return msg;
		}
		if(EmptyUtils.isEmpty(errectiveInfo.getUniquePrimaryKey()))
		{
			logger.error("唯一标识符 为空");
			return msg;
		}
		
		TChannelInfo channelInfo = channelInfoService.getInfoByNum(channelNum);
		
		if(EmptyUtils.isEmpty(channelInfo))
		{
			logger.error("渠道不存在:"+channelNum);
			return msg;
		}
		
		Integer count = adverEffectiveInfoService.getEffectiveInfo(channelNum,errectiveInfo.getUniquePrimaryKey());
		
		if(count > 0)
		{
			logger.error("数据已重复 重复编号为:" + errectiveInfo.getUniquePrimaryKey());
			return msg;
		}
		
		errectiveInfo.setChannelName(channelInfo.getChannelName());
		errectiveInfo.setChannelNum(channelNum);
		adverEffectiveInfoService.saveAdverEffectiveInfo(errectiveInfo.getUserNum(), errectiveInfo);
		return getInferfaceValue(map, Constants.PARAMETER_SUCCESS);
	}
	
	public static void main(String[] args) 
	{
		System.out.println(EncrypDES.encryptEde("chdomob0000003"));
	}
	
	/**
	 * 功能描述: 重request 取值放到 有效记录中去
	 *
	 * @param request 
	 * @param map
	 * @return
	 */
	private TAdverEffectiveInfo getErrectiveInfo(HttpServletRequest request, Map<String, TAdverInferface> map) 
	{
		TAdverEffectiveInfo info = new TAdverEffectiveInfo();
		info.setIdfa(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_IDFA)));
		info.setImei(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_IMEI)));
		info.setMac(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_MAC)));
		info.setOpenudid(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_OPENUDID)));
		info.setPhoneModel(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_PHONEMODEL)));
		info.setSystemVersion(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_SYSTEMVERSION)));
		info.setIp(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_IP)));
		String uniquePrimaryKey=getInferfaceValue(map, Constants.PARAMETER_UNIQUEPRIMARYKEY);
		
		if(uniquePrimaryKey.indexOf(",")>0)
		{  //判断唯一标识
			String[] params=uniquePrimaryKey.split(",");
			String values="";
			for(String param : params)
			{
				values=values+"_"+request.getParameter(param);
			}
			
			info.setUniquePrimaryKey(values);	
		}
		else
		{
			info.setUniquePrimaryKey(request.getParameter(uniquePrimaryKey));
		}
		
		String score=request.getParameter(getInferfaceValue(map, Constants.PARAMETER_ADVER_SCORE));
		
		if(EmptyUtils.isNotEmpty(score))  //记录分数
		{
			info.setScore(Float.parseFloat(score));
		}
		
		info.setPhoneSerialNumber(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_PHONESERIALNUMBER)));
		info.setUserNum(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_USERNUM)));
		info.setAdverName(request.getParameter(getInferfaceValue(map, Constants.PARAMETER_ADVER_NAME)));
		return info;
	}
	
	/**
	 * 功能描述: 获取 客户的参数与我们参数对应的名称
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public String getInferfaceValue(Map<String, TAdverInferface> map,String key)
	{
		TAdverInferface adverInferface=map.get(key);
		if(EmptyUtils.isNotEmpty(adverInferface))
			return adverInferface.getParameterName().trim();
		return "";
	}
}
