package com.ruanyun.web.controller.sys.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.producer.UdidQueueConsumer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChannelClassification
{
	private static java.util.Random random = new java.util.Random();
	private static List<String> userIDForIphone7 = new ArrayList<String>();
	private static Map<String, UdidQueueConsumer> udidQueue = new HashMap<String, UdidQueueConsumer>();
	
	//检测任务信息
	public static AppCommonModel checkChannelInfo( TChannelAdverInfo adverInfo, String adid, String idfa, String ip, 
			String userAppId, String adverId, String userNum, String adverName, String phoneModel, String phoneVersion, String udid) throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = new AppCommonModel(1, "任务领取成功！");
		
		//分渠道调用排重接口、点击接口
		int num = Integer.parseInt(adverInfo.getChannelNum());
		switch (num) {
		case 1:
			//云聚
			model = YunJu.isYunJvChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, phoneVersion, phoneModel);
			break;
		case 2:
			//云聚
			model = ZhangShangHuDong.zhangshanghudong(adverInfo, adid, idfa, ip, userAppId, adverId, userNum,phoneVersion, phoneModel,udid, adverName);
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
			model = JvZhangChannel.isJZChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
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
			model =  GourdChannel.isGourdChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion,udid);
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
		case 27:
			model = YYMChannel.isYYMChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, phoneVersion, phoneModel, udid);
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
				//调用第三方激活上报接口(String domain, String adid, String idfa, String ip,String KeyWords, String  phoneVersion, String  phoneModel,String udid )
				model = ZhangShangHuDong.activate(adverInfo.getFlag4(), adverInfo.getAdid(), idfa, ip,adverInfo.getAdverName(),phoneos[1], phoneModel[1],udid);
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
				model = GourdChannel.activate(adverInfo, idfa, ip,phoneModel[1], phoneos[1],udid);
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
				model =  YouZhuanChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1], udid);
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
			case 27:
				model = YYMChannel.activate(adverInfo.getFlag4(), adverInfo.getAdid(), adverInfo.getAdverName(), idfa, ip, phoneos[1], phoneModel[1], udid);
				break;
			default:
				model.setResult(-1);
				model.setMsg("未完成。原因：渠道未在后台配置！");
				break;
		}
		
		return model;
	}
	
	
	public static String getPhoneWithUdid() {
		String phonemodel_sim = "iPhone9,2";
		int result = random.nextInt(3);
		switch (result)
		{
		case 0:
			phonemodel_sim = "iPhone9,2"; //1
			break;
		case 1:
			phonemodel_sim = "iPhone10,2";//2
			break;
		case 2:
			phonemodel_sim = "iPhone10,3";//3
			break;
//		case 3:
//			phonemodel_sim = "iPhone11,2";//4
//			break;
//		case 4:
//			phonemodel_sim = "iPhone11,4";//4
//			break;
		default:
			phonemodel_sim = "iPhone9,2";
			break;
		}
		return phonemodel_sim;
	}
	
	public static String getPhoneModel(String id) 
	{	
		String phonemodel_sim = "iPhone7,1";
		int result = random.nextInt(14);
		//账号77设定为4以上  iphone7
		if(id.equals("77") && result <= 4){
			result = 4;
		} 
		
		switch (result)
		{
		case 0:
			phonemodel_sim = "iPhone9,1";
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
			phonemodel_sim = "iPhone9,2"; //1
			break;
		case 6:
			phonemodel_sim = "iPhone9,3";
			break;
		case 7:
			phonemodel_sim = "iPhone9,4";
			break;
		case 8:
			phonemodel_sim = "iPhone10,2";//2
			break;
		case 9:
			phonemodel_sim = "iPhone10,5";
			break;
		case 10:
			phonemodel_sim = "iPhone10,3";//3
			break;
		case 11:
			phonemodel_sim = "iPhone10,6";
			break;
		case 12:
			phonemodel_sim = "iPhone11,2";//4
			break;
		case 13:
			phonemodel_sim = "iPhone11,4";//4
			break;
		default:
			phonemodel_sim = "iPhone9,1";
			break;
		}
		
		return phonemodel_sim;
	}
	
	public static String  getPhoneVersion()
	{
		String phoneVersion = "12.1.2";
		int result = random.nextInt(10);
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
			case 6:
				phoneVersion = "12.2";
				break;
			case 7:
				phoneVersion = "12.3.1";
				break;
			case 8:
				phoneVersion = "12.4";
				break;
			case 9:
				phoneVersion = "12.4.1";
				break;
			default:
				phoneVersion = "12.1.2";
				break;
		}
				
		return phoneVersion;
	}
	
	public static String  get11PhoneVersion()
	{
		String phoneVersion = "11.1.1";
		int result = random.nextInt(7);
		switch (result)
		{
			case 0:
				phoneVersion = "11.1.1";
				break;
			case 1:
				phoneVersion ="11.4.1";
				break;
			case 2:
				phoneVersion = "11.3.1";
				break;	
			case 3:
				phoneVersion = "11.0.1";
				break;	
			case 4:
				phoneVersion = "11.2.1";
				break;
			case 5:
				phoneVersion = "11.0";
				break;
			case 6:
				phoneVersion = "11.3";
				break;
			default:
				phoneVersion = "11.1.1";
				break;
		}
				
		return phoneVersion;
	}
	
	//模拟手机udid
	public static String getPhoneUdid(String phoneModel, int isTrue) {
		//根据机型获取配套的udid
		String udid = "0";
		
		//1是需要真实udid
		if(isTrue == 1) {
			//先去查看数据库中是否存在此idfa对应的udid，如果存在就提出，否则去获取新的udid
			if(!udidQueue.containsKey(phoneModel)) {
				try {
					udidQueue.put(phoneModel, new UdidQueueConsumer(phoneModel,false));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
			
			try {
				UdidQueueConsumer udidQ = udidQueue.get(phoneModel);
				udid = udidQ.getMessage(phoneModel);
			} 
			catch (Exception e)
			{
				udidQueue.remove(phoneModel);
			}
			
			if(udid == null) {
				//消耗没了就进行提示
				return "0";
			}
		}
		else 
		{
			if(phoneModel.contains("iPhone11,") || phoneModel.contains("iPhone12,")){
				int result = random.nextInt(2);
				String Str = "00008020-000";
				switch (result)
				{
					case 0:
						Str = "00008020-000";
						break;
					case 1:
						Str ="00008020-001";
						break;
					default:
						Str = "00008020-000";
						break;
				}
				
				udid = Str + get13UUID();
				udid = udid.toUpperCase();
			}else {
				 String Str1=UUID.randomUUID().toString().replace("-", "");
				 //String Str1=UUID.randomUUID().toString();
				 udid = Str1 + get8UUID();
			}
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
	  // 00008020-000A09183CDA002E
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
	
	public static long getTimestamp() 
	{
		 String _timeZone = "GMT+8:00";
	     TimeZone timeZone = null;  
	     timeZone = TimeZone.getTimeZone(_timeZone);  
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     sdf.setTimeZone(timeZone);  
	     
		String stridddg = sdf.format(new Date());
		Date d = null;
		try 
		{
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stridddg);
		} 
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		long t1 = d.getTime();
		return t1 = t1/1000;
	}
	
	
	//上月
	@SuppressWarnings("static-access")
	public static String GetYestMonthDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(calendar.MONTH,-1);
		String date = simpleDateFormat.format(calendar.getTime());
		
		return date;
	}
	
	//获取昨天的日期
	@SuppressWarnings("static-access")
	public static String GetYestdayDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(calendar.DATE,-1);
		String date = simpleDateFormat.format(calendar.getTime());
		
		return date;
	}
	
	//获取当月
	public static String GetMonthDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String date = simpleDateFormat.format(calendar.getTime());
		
		return date;
	}
	
	//获取今日的日期
	public static String GetdayDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String date = simpleDateFormat.format(calendar.getTime());
		
		return date;
	}

	public static String beforeHourToNowDate(int i) 
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - i);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(calendar.getTime());
	}
	
	//获取需要传递真实信息的用户id
	public static List<String> getUserIDListForIphone7(){
		userIDForIphone7.add("197");
		userIDForIphone7.add("798");
		userIDForIphone7.add("184");
		userIDForIphone7.add("821");
		userIDForIphone7.add("183");
		userIDForIphone7.add("800");
		userIDForIphone7.add("79");
		userIDForIphone7.add("802");
		return userIDForIphone7;
	}
	
	public static void main(String[] args) {
		//double ran = Math.random();
		String result = "{\"creationTimestamp\":\"2019-10-16T07:11:51Z\",\"resultCode\":0,\"userLocale\":\"en_US\",\"protocolVersion\":\"QH65B2\",\r\n" + 
				"\"requestUrl\":\"https://developer.apple.com:443/services-account/QH65B2/account/device/validateDevices.action\",\r\n" + 
				"\"responseId\":\"b29b6f62-d92f-48ba-baed-4e1339dd53fe\",\"isAdmin\":true,\"isMember\":false,\"isAgent\":true,\r\n" + 
				"\"devices\":[{\"name\":\"device0\",\"deviceNumber\":\"312cdae4d6345fb9c413c79cdc9913ec72a2f807\",\"devicePlatform\":\"ios\",\"deviceClass\":\"iphone\",\"model\":\"iPhone 8 Plus\",\"serialNumber\":\"C39VDGPCJCLM\"},\r\n" + 
				"{\"name\":\"device2\",\"deviceNumber\":\"b089a8a92ad0a279355ac0ed358dbfcd62f79562\",\"devicePlatform\":\"ios\",\"deviceClass\":\"iphone\",\"model\":\"iPhone 8 Plus\",\"serialNumber\":\"C39VDGFWJCLM\"},\r\n" + 
				"{\"name\":\"device3\",\"deviceNumber\":\"d4c3c80bb78e08eb691999721508bf1bca30a089\",\"devicePlatform\":\"ios\",\"deviceClass\":\"iphone\",\"model\":\"iPhone 8 Plus\",\"serialNumber\":\"C39VDGJJJCLM\"},\r\n" + 
				"{\"name\":\"device4\",\"deviceNumber\":\"51470d2cbab17f8d57e22fbcb929f0bcdca2bf52\",\"devicePlatform\":\"ios\",\"deviceClass\":\"iphone\",\"model\":\"iPhone 8 Plus\",\"serialNumber\":\"C39VDG8MJCLM\"},\r\n" + 
				"{\"name\":\"device5\",\"deviceNumber\":\"8ff75a26802fdd89017a005e84882fbf09154613\",\"devicePlatform\":\"ios\",\"deviceClass\":\"iphone\",\"model\":\"iPhone 8 Plus\",\"serialNumber\":\"C39VDG1YJCLM\"},\r\n" + 
				"{\"name\":\"device6\",\"deviceNumber\":\"f16306f4f3a915d00016b3848b12155a289b1352\",\"devicePlatform\":\"ios\",\"deviceClass\":\"iphone\",\"model\":\"iPhone 8 Plus\",\"serialNumber\":\"C39VDDBGJCLM\"},\r\n" + 
				"{\"name\":\"device7\",\"deviceNumber\":\"f21dc5d61557c85fdc62fdae78807bc5964c0ceb\",\"devicePlatform\":\"ios\",\"deviceClass\":\"iphone\",\"model\":\"iPhone 8 Plus\",\"serialNumber\":\"C39VDF2FJCLM\"},\r\n" + 
				"{\"name\":\"device8\",\"deviceNumber\":\"e59f27fbda8d9279921bccd63a229062690fd32f\",\"devicePlatform\":\"ios\",\"deviceClass\":\"iphone\",\"model\":\"iPhone 8 Plus\",\"serialNumber\":\"C39VDFAUJCLM\"},\r\n" + 
				"{\"name\":\"device9\",\"deviceNumber\":\"02608309e75543c5eefb96874411d22b96586baa\",\"devicePlatform\":\"ios\",\"deviceClass\":\"iphone\",\"model\":\"iPhone 8 Plus\",\"serialNumber\":\"C39VDF1JJCLM\"}],\r\n" + 
				"\"failedDevices\":[],\"validationMessages\":[]}";
		JSONObject jsonObject = JSONObject.fromObject(result);
		JSONArray arr = (JSONArray) jsonObject.get("devices");
		
		if(arr.size()>0)
		{
			for(int i=0;i<arr.size();i++)
			{
				JSONObject job = arr.getJSONObject(i); 
				System.out.println(job.get("deviceNumber")+"=") ; 
				System.out.println(job.get("model")+"=") ;
			}
		}
	}
}
