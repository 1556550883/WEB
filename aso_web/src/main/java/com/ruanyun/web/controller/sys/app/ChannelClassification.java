package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;

public class ChannelClassification
{
	private static java.util.Random random = new java.util.Random();
	
	//检测任务信息
	public static AppCommonModel checkChannelInfo(TChannelInfo channelInfo, TChannelAdverInfo adverInfo, String adid, String idfa, String ip, 
			String userAppId, String adverId, String userNum, String adverName, String phoneModel, String phoneVersion) throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = new AppCommonModel(1, "任务领取成功！");
		
		//分渠道调用排重接口、点击接口
		if(channelInfo == null)
		{
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：渠道不存在！");
		}
		
		int num = Integer.parseInt(channelInfo.getChannelNum());
		switch (num) {
		case 1:
			//云聚
			model = YunJu.isYunJvChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, phoneVersion, phoneModel);
			break;
		case 2:
			//云聚
			model = ZhangShangHuDong.zhangshanghudong(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
			break;
		case 3:
			//自由渠道
			model.setResult(1);
			break;
		case 4:
			//利得基金
			model = LiDeJiJin.isLDJJChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
			break;
		case 5:
			model = DYDChannel.isDYDChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
			break;
		case 6:
			model = MZDQChannel.isMZDQChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName);
			break;
		case 7:
			model = APYSChannel.isAPYSChannel(adverInfo, adid, idfa);
			break;
		case 8:
			model = JvZhangChannel.isJZChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName);
			break;
		case 9:
			model = AiyinliChannel.isAiYLChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName,  phoneModel, phoneVersion);
			break;
		case 10:
			model =  HappyChannel.isHappyChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 11:
			model =  Huizhuan.isHZChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 12:
			model =  BeeChannel.isBeeChannel(adverInfo, idfa, ip, userAppId, adverId, userNum, phoneModel, phoneVersion);
			break;
		case 13:
			model =  FrogsChannel.isFrogsChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 14:
			model =  FrogsTChannel.isFrogsTChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 15:
			model =  FrogsTTChannel.isFrogsTTChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 16:
			model =  GourdChannel.isGourdChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 17:
			model =  limazuanChannel.isLimazuanChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		default:
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：渠道未在后台配置！");
			break;
		}
		return model;
	}
		
	public static AppCommonModel channelActive(AppCommonModel model, TChannelAdverInfo adverInfo,int num, String idfa, String ip, String [] phoneModel, String [] phoneos) {
		
		switch (num)
		{
			case 1:
				//云聚
				//调用第三方激活上报接口
				model = YunJu.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1]);
				break;
			case 2:
				//掌上互动
				//调用第三方激活上报接口
				model = ZhangShangHuDong.activate(adverInfo.getFlag4(), adverInfo.getAdid(), idfa, ip);
				break;
			case 4:
				//云聚
				//调用第三方激活上报接口
				model = LiDeJiJin.activate(adverInfo.getFlag4(), adverInfo.getAdid(), idfa, ip);
				break;
			case 6:
				model = MZDQChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip);
				break;
			case 7:
				model = APYSChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip);
				break;
			case 8:
				model = JvZhangChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip);
				break;
			case 9:
				model = AiyinliChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip);
				break;
			case 10:
				model = HappyChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1]);
				break;
			case 11:
				model = Huizhuan.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneModel[1], phoneos[1]);
				break;
			case 12:
				model = BeeChannel.activate(adverInfo, idfa, ip,phoneModel[1], phoneos[1]);
				break;
			case 13:
				model = FrogsChannel.activate();
				break;
			case 14:
				model = FrogsTChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), idfa, ip);
				break;
			case 15:
				model = FrogsTTChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), idfa, ip, adverInfo.getAdverName(),phoneModel[1], phoneos[1]);
				break;
			case 16:
				model = GourdChannel.activate(adverInfo, idfa, ip,phoneModel[1], phoneos[1]);
				break;
			case 17:
				model = limazuanChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1]);
				break;
			default:
				model.setResult(-1);
				model.setMsg("未完成。原因：渠道未在后台配置！");
				break;
		}
		
		return model;
	}
	
	
	public static String getPhoneModel() 
	{	
		String phonemodel_sim = "iPhone7,1";
		int result = random.nextInt(18);
		switch (result)
		{
		case 0:
			phonemodel_sim = "iPhone10,6";
			break;
		case 1:
			phonemodel_sim = "iPhone8,1";
			break;
		case 2:
			phonemodel_sim = "iPhone8,2";
			break;
		case 3:
			phonemodel_sim = "iPhone8,4";
			break;
		case 4:
			phonemodel_sim = "iPhone9,1";
			break;
		case 5:
			phonemodel_sim = "iPhone9,2";
			break;
		case 6:
			phonemodel_sim = "iPhone9,3";
			break;
		case 7:
			phonemodel_sim = "iPhone9,4";
			break;
		case 8:
			phonemodel_sim = "iPhone9,5";
			break;
		case 9:
			phonemodel_sim = "iPhone9,6";
			break;
		case 10:
			phonemodel_sim = "iPhone10,1";
			break;
		case 11:
			phonemodel_sim = "iPhone10,4";
			break;	
		case 12:
			phonemodel_sim = "iPhone10,2";
			break;
		case 13:
			phonemodel_sim = "iPhone10,5";
			break;
		case 14:
			phonemodel_sim = "iPhone11,2";
			break;
		case 15:
			phonemodel_sim = "iPhone11,4";
			break;
		case 16:
			phonemodel_sim = "iPhone11,6";
			break;
		case 17:
			phonemodel_sim = "iPhone11,8";
			break;
		default:
			phonemodel_sim = "iPhone8,1";
			break;
		}
		
		return phonemodel_sim;
	}
	
	public static String  getPhoneVersion()
	{
		String phoneVersion = "12.1.2";
		int result = random.nextInt(4);
		switch (result)
		{
			case 0:
				phoneVersion = "12.0";
				break;
			case 1:
				phoneVersion = "12.1";
				break;
			case 2:
				phoneVersion = "12.1.1";
				break;	
			case 3:
				phoneVersion = "12.1.2";
				break;	
			default:
				phoneVersion = "12.1.2";
				break;
		}
				
		return phoneVersion;
	}
}
