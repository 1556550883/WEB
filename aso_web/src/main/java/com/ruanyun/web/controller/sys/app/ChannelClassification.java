package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;

public class ChannelClassification
{
	private static java.util.Random random = new java.util.Random();
	
	//检测任务信息
	public static AppCommonModel checkChannelInfo(TChannelInfo channelInfo, TChannelAdverInfo adverInfo, String adid, String idfa, String ip, 
			String userAppId, String adverId, String userNum, String adverName, String phoneModel, String phoneVersion, String udid) throws NumberFormatException, UnsupportedEncodingException 
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
			model =  BeeChannel.isBeeChannel(adverInfo, idfa, ip, userAppId, adverId, userNum, phoneModel, phoneVersion, udid);
			break;
		case 13:
			model =  FrogsChannel.isFrogsChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 14:
			model =  FrogsTChannel.isFrogsTChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 15:
			model =  FrogsTTChannel.isFrogsTTChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion, udid);
			break;
		case 16:
			model =  GourdChannel.isGourdChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 17:
			model =  limazuanChannel.isLimazuanChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 18:
			model =  XinzhuanChannel.isXinzhuanChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 19:
			model =  HuizhuanSanhuChannel.isHuizhuanSanhuChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 20:
			model =  WanZhuanChannel.isWanZhuanChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, phoneVersion, phoneModel);
			break;
		case 21:
			model =  LanChongChannel.isLanChongChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 22:
			model =  SanhuFrogsChannel.isFrogsTTChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion, udid);
			break;
		case 23:
			model =  YouZhuanChannel.isYouZhuanChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, phoneVersion, phoneModel, udid);
			break;
		case 24:
			model =  PpHongBaoChannnel.isPpHongBaoChannnel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, phoneVersion, phoneModel);
			break;
		case 25:
			model = XiaoshouChannel.isXiaoshouChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, phoneVersion, phoneModel, udid);
			break;
		case 26:
			model = FenQianChannel.isFenQianChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, phoneVersion, phoneModel, udid);
			break;
		default:
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：渠道未在后台配置！");
			break;
		}
		return model;
	}
		
	public static AppCommonModel channelActive(AppCommonModel model, TChannelAdverInfo adverInfo,int num, String idfa, String ip, String [] phoneModel, String [] phoneos, String udid) {
		
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
				model = BeeChannel.activate(adverInfo, idfa, ip,phoneModel[1], phoneos[1], udid);
				break;
			case 13:
				model = FrogsChannel.activate();
				break;
			case 14:
				model = FrogsTChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), idfa, ip);
				break;
			case 15:
				model = FrogsTTChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), idfa, ip, adverInfo.getAdverName(),phoneModel[1], phoneos[1], udid);
				break;
			case 16:
				model = GourdChannel.activate(adverInfo, idfa, ip,phoneModel[1], phoneos[1]);
				break;
			case 17:
				model = limazuanChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1]);
				break;
			case 18:
				model = XinzhuanChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1]);
				break;
			case 19:
				model = HuizhuanSanhuChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneModel[1], phoneos[1]);
				break;
			case 20:
				break;
			case 21:
				model = LanChongChannel.activate(adverInfo, idfa, ip,phoneModel[1], phoneos[1]);
				break;
			case 22:
				model = SanhuFrogsChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), idfa, ip, adverInfo.getAdverName(),phoneModel[1], phoneos[1], udid);
				break;
			case 23:
				model =  YouZhuanChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1]);
				break;
			case 24:
				//model =  PpHongBaoChannnel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1]);
				break;
			case 25:
				model = XiaoshouChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1], udid);
				break;
			case 26:
				model = FenQianChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(),idfa,adverInfo.getAdverName(), phoneos[1], phoneModel[1]);
				break;
			default:
				model.setResult(-1);
				model.setMsg("未完成。原因：渠道未在后台配置！");
				break;
		}
		
		return model;
	}
	
	
	public static String getPhoneModel(String id) 
	{	
		String phonemodel_sim = "iPhone7,1";
		int result = random.nextInt(18);
		//账号77设定为4以上  iphone7
//		if((id.equals("77") || id.equals("183") || id.equals("184")  || id.equals("185") 
//				|| id.equals("197")|| id.equals("798")|| id.equals("818")|| id.equals("414")
//				|| id.equals("812")|| id.equals("83")|| id.equals("542235")|| id.equals("821")|| id.equals("802")|| id.equals("801")|| id.equals("800"))&& result <= 4){
//			result = 4;
//		} 
		
		if(id.equals("77") && result <= 4){
			result = 4;
		} 
		
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
		int result = random.nextInt(6);
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
			case 4:
				phoneVersion = "12.1.3";
				break;
			case 5:
				phoneVersion = "12.1.4";
				break;
			default:
				phoneVersion = "12.1.2";
				break;
		}
				
		return phoneVersion;
	}
	
	//模拟手机udid
	public static String getPhoneUdid(String phoneModel) {
		String udid = "";
		
		switch (phoneModel)
		{
		  case "iPhone5,1":    
		  case "iPhone5,2":   
		   case "iPhone5,3":
		   case"iPhone5,4":                
		   case "iPhone6,1":
		   case "iPhone6,2":                  
		   case "iPhone7,2":                           
		   case "iPhone7,1":                             
		   case "iPhone8,1":                               
		   case "iPhone8,2":                            
		   case "iPhone8,4":                             
			case "iPhone9,1":case "iPhone9,2": case "iPhone9,3":  case "iPhone9,4":  case "iPhone9,6": case "iPhone9,5": 
			case "iPhone10,1": case "iPhone10,4": case "iPhone10,2": case "iPhone10,5":    
			case "iPhone10,3":case  "iPhone10,6":  
				 String Str1=UUID.randomUUID().toString().replace("-", "");
				 udid = Str1 + get8UUID();
				 break;
			case "iPhone11,2": case "iPhone11,4": case "iPhone11,6":    
			case "iPhone11,8":   
				int result = random.nextInt(2);
				String Str = "00008020000";
				switch (result)
				{
					case 0:
						Str = "00008020000";
						break;
					case 1:
						Str ="00008020001";
						break;
					default:
						Str = "00008020000";
						break;
				}
				
				udid = Str + get13UUID();
				udid = udid.toUpperCase();
			default:
				break;
			}
		
		return udid;
	}
	
	public static String get8UUID(){
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0];
    }
	  
	 
  public static String get13UUID(){
	  	//bd76efef-c208-470b-a584-907cbebcd472
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        
        //截取5个
        String str = idd[0].substring(0, 1);
        
        return idd[4] + str;
    }
  
  
	public static String phoneModelChange(String phoneModel) 
	{
		switch (phoneModel)
		{
		   case "iPhone6":                           
			   phoneModel =  "iPhone7,2";
			   break;
		   case "iPhone6S":                               
			   phoneModel =  "iPhone8,1" ;
			   break;
		   case "iPhone6SPlus":                               
			   phoneModel =  "iPhone8,2" ;
			   break;
			case "iPhone7":case "iPhone7S": case "iPhone7SPlus": 
				phoneModel =  "iPhone9,1";
				break;
			case "iPhone8": case "iPhone8S": case "iPhone8SPlus":
				phoneModel =  "iPhone10,1";
				break;
			case "iPhoneX":
				phoneModel = "iPhone10,3"; 
				break;
			case "iPhoneXs":
				phoneModel =  "iPhone11,2"; 
				break;
			case "iPhoneXr":   
				phoneModel =  "iPhone11,8";
				break;
			default:
				 phoneModel =  "iPhone7,2";
				
				break;
			}
		
			return phoneModel;
		}
	
	public static void main(String[] args) {
		 System.err.println(phoneModelChange("iPhone6"));
	}
}
