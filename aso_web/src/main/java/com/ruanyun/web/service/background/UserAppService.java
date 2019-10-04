/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.service.background;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.CommonUtils;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.dao.sys.background.ChannelAdverInfoDao;
import com.ruanyun.web.dao.sys.background.UserAppDao;
import com.ruanyun.web.model.HUserAppModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserLogin;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserStudentCart;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.model.sys.UploadVo;
import com.ruanyun.web.service.sys.UserRoleService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.ExcelUtils;
import com.ruanyun.web.util.HttpRequestUtil;
import com.ruanyun.web.util.NumUtils;
import com.ruanyun.web.util.SLEmojiFilter;
import com.ruanyun.web.util.UploadCommon;

import net.sf.json.JSONObject;

@Service
public class UserAppService extends BaseServiceImpl<TUserApp>
{
	@Autowired
	@Qualifier("userAppDao")
	private UserAppDao userAppDao;
	@Autowired
	@Qualifier("channelAdverInfoDao")
	private ChannelAdverInfoDao channelAdverInfoDao;
	@Autowired
	@Qualifier("userRoleService")
	private UserRoleService userRoleService;
	@Autowired
	@Qualifier("userStudentCartService")
	private UserStudentCartService userStudentCartService;
	@Autowired
	@Qualifier("userScoreService")
	private UserScoreService userScoreService;
	@Autowired
	@Qualifier("userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public Page<TUserApp> queryPage(Page<TUserApp> page, TUserApp t) 
	{
		Page<TUserApp> _page = userAppDao.queryPage(page, t);
		
		String userNums = CommonUtils.getAttributeValue(TUserApp.class, _page.getResult(), "userNum");
		if(EmptyUtils.isNotEmpty(userNums))
		{
			Map<Integer,TUserScore> userMap = userScoreService.getUserScoreByUserNums(userNums);
			CommonUtils.setAttributeValue(TUserApp.class,  _page.getResult(), userMap, "userAppId", "userScore");
		}
		
		return _page;
	}
	
	public void clearScore() {
		userAppDao.clearScore();
	}
	
	public void removeMaster(String appid, String masterID) {
		userAppDao.removeMaster(appid);
		int count = getApprenticeNum(masterID);
		String userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, Integer.parseInt(masterID));
		userScoreService.updateApprentice(userNum, count);
	}
	
	public void exportScore(HttpServletResponse response, List<TUserApp> list, Boolean islocal) {
		String fileName = "score";
		String[] columns = {"userAppId","userNick","loginName","score"};
		String[] headers = {"id","姓名","登陆名","得分"};
		try {
			for(TUserApp sApp : list) {
				sApp.setScore(sApp.getUserScore().getScore());
			}
			
			if(islocal) {
				ExcelUtils.exportExcelTolocal(fileName, list, columns, headers);
			}else {
				ExcelUtils.exportExcel(response, fileName, list, columns, headers, SysCode.DATE_FORMAT_STR_L);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述: 根据主键获取对象
	 *
	 * @author yangliu  2016年1月19日 下午2:28:28
	 * 
	 * @param userId 用户编号
	 * @return
	 */
	public TUserApp getUserAppById(Integer userId)
	{
		return super.get(TUserApp.class, userId);
	}
	
	
	
	public TUserApp getUserAppByUserName(String  loginName)
	{
		return super.get(TUserApp.class, "loginName", loginName);
	}
	
	public TUserApp getUserAppByNum(String userNum)
	{
		return super.get(TUserApp.class,"userNum", userNum);
	}
	
	/**
	 * 
	 * 功能描述:获取手机的所有用户数量
	 * @return
	 */
	public int getUserNum(Integer type,Date createTime)
	{		
		return	userAppDao.getUserNum(type,createTime);
	}
	
	public int getApprenticeNum(String id) 
	{
		return userAppDao.getApprenticeNum(id);
	}

	public int geteffApprenticeNum(String id) 
	{
		return userAppDao.geteffApprenticeNum(id);
	}
	
	/**
	 * 功能描述:修改手机用户
	 * @author wsp  2017-1-16 上午10:36:45
	 * @param userApp
	 * @param request
	 * @param picFile
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int saveOrUpdate(TUserApp userApp,HttpServletRequest request,MultipartFile picFile)
	{
		UploadVo vo = null;
		if (EmptyUtils.isNotEmpty(picFile) && picFile.getSize() != 0)
		{
			try
			{
				vo = UploadCommon.uploadPic(picFile, request,Constants.FILE_HEAD_IMG, "gif,jpg,jpeg,bmp,png");
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
			
		if (userApp != null && EmptyUtils.isNotEmpty(userApp.getUserNum())) 
		{
			TUserApp olUuser = super.get(TUserApp.class,"userNum", userApp.getUserNum());
			if(EmptyUtils.isNotEmpty(olUuser.getLoginName()) && olUuser.getLoginName().equals(userApp.getLoginName()))
			{
				//未修改登录名
			}else
			{
				TUserApp app = super.get(TUserApp.class,"loginName", userApp.getLoginName());
				if(EmptyUtils.isNotEmpty(app))
				{
					return 2;//登录名重复
				}
			}
			
			BeanUtils.copyProperties(userApp, olUuser, new String[] {"userAppId", "userNum", "headImg","phoneSerialNumber",
					"createDate"
					,"invitationCode","phoneVersion","phoneModel","idfa","taskNewStatus","zhifubao","weixin","flag5","phoneNum",
					"zhifubaoName","userApppType","appStore", "masterID","isEffective","limitTime","isEffective","openID","loginDesc"});
			olUuser.setLevel(userApp.getLevel());
			
			//用户被禁止添加描述
			if(userApp.getLoginControl().equals("0")) {
				olUuser.setLoginDesc("管理员禁止");
			}
			if (EmptyUtils.isNotEmpty(vo) && vo.getResult()==1) 
			{
				olUuser.setHeadImg(vo.getFilename());
			} 
			
			update(olUuser);
			TUserLogin tUserLogin = userLoginService.getUserByUserNum(userApp.getUserNum(), 1);
			tUserLogin.setLoginName(olUuser.getLoginName());
			tUserLogin.setPassword(olUuser.getLoginPwd());
			userLoginService.update(tUserLogin);
		}
		else
		{
			TUserApp app = super.get(TUserApp.class,"loginName", userApp.getLoginName());
			
			if(EmptyUtils.isNotEmpty(app))
			{
				return 2;//登录名重复
			}
			
			if (EmptyUtils.isNotEmpty(vo) && vo.getResult()==1) 
			{
				userApp.setHeadImg(vo.getFilename());
			}
			
			userApp.setCreateDate(new Date());
			userApp.setUserAppId(getLastUserApp().getUserAppId() + 1);
			//userApp.setLoginPwd(MD5Util.encoderByMd5(Constants.USER_DEFULT_PASSWORD));
			super.save(userApp);
			userApp.setUserNum(NumUtils.getCommondNum(NumUtils.USER_APP_NUM, userApp.getUserAppId()));
			
			//保存用户登录
			TUserLogin userLogin = new TUserLogin();
			userLogin.setUserNum(userApp.getUserNum());
			userLogin.setPassword(userApp.getLoginPwd());
			userLogin.setLoginName(userApp.getLoginName());
			userLogin.setLoginType(1);
			userLoginService.save(userLogin);
			
			//保存用户积分
			userScoreService.addNewUserScore(userApp.getUserNum(), userApp.getUserApppType());//手机app
		}
		
		return 1;
	}
	
	/**
	 * 功能描述:批量删除
	 * @author wsp  2017-1-16 下午01:49:40
	 * @param ids
	 * @return
	 */
	public int delete(String ids) 
	{
		if(EmptyUtils.isEmpty(ids)) 
		{
			return 0;
		}
		
		String[] userNums = ids.split(",");
		
		for (String userNum : userNums) 
		{
			if(EmptyUtils.isEmpty(userNum)) 
			{
				continue;
			}
			
			 super.delete(TUserApp.class,"userNum",userNum);
			 userLoginService.delete(TUserLogin.class, "userNum", userNum);//删除登录
		}
		
		return 1;
	}

	/**
	 * 功能描述:将学生用户设置为普通用户
	 * @author wsp  2017-1-19 下午03:58:21
	 * @param ids
	 * @param currentUser
	 * @return
	 */
	public int updateUserApp(String ids,TUser currentUser) 
	{
		TUserApp userApp = null;
		if(EmptyUtils.isEmpty(ids))return 0;
		String[] userNums = ids.split(",");
		for (String userNum : userNums)
		{
			if(EmptyUtils.isEmpty(userNum))
				continue;
			userApp = super.get(TUserApp.class, "userNum", userNum);
			userApp.setUserApppType(1);
			//删除用户角色
			userRoleService.deleteByUserId(userApp.getUserAppId());
			// 保存用户角色
			userRoleService.save("1", String.valueOf(userApp.getUserAppId()), currentUser);
			//删除学生信息
			userStudentCartService.delete(TUserStudentCart.class,"userAppNum",userApp.getUserNum());
			update(userApp);
		}
		
		return 1;
	}

	/**
	 * 功能描述:保存最近登录的ip地址
	 */
	public void updateIp(HttpServletRequest request, TUserApp userApp,String ip) 
	{
		userApp.setFlag2(ip);
		super.update(userApp);
	}
	
	//updateMaterID
	public void updateMaterID(TUserApp userApp,String masterID) 
	{
		userApp.setMasterID(masterID);
		super.update(userApp);
	}

	public int userPhoneEffective(String phonenum) throws Exception {
		int code = -1;
		Map<String, String> params = new HashMap<String, String>();
        params.put("appId", "o470S630");
        params.put("appKey", "K5wsHKEp");
        //手机号码
        params.put("mobile", phonenum);
		String result = HttpRequestUtil.doPost("https://api.253.com/open/wool/wcheck", params);
		JSONObject jsonObject = JSONObject.fromObject(result);
		if (jsonObject != null) {
            //响应code码。200000：成功，其他失败
            String ecode = (String)jsonObject.get("code");
            if ("200000".equals(ecode)) {
                // 调用羊毛党检测成功
                // 解析结果数据，进行业务处理
                // 检测结果  W1：白名单 B1 ：黑名单  B2 ：可信用度低  N：未找到
            	JSONObject data = (JSONObject)jsonObject.get("data");
                String status = (String)data.get("status");
                System.out.println("调用羊毛党检测成功,status:" + status);
                if(status.equalsIgnoreCase("W1")) {
                	code = 0; //白名单
                }
                if(status.equalsIgnoreCase("B1")) {
                	code = 1; //黑名单
                }
                if(status.equalsIgnoreCase("B2")) {
                	code = 2; //可疑名单
                }
                if(status.equalsIgnoreCase("N")) {
                	code = 3; //库无
                }
            } else {
                // 记录错误日志，正式项目中请换成log打印
                System.out.println("调用羊毛党检测失败,code:" + code + ",msg:" + jsonObject.get("message"));
            }
        }
		
		return code;
	}
	
	public static void main(String[] args) throws Exception {
		//System.err.println(userPhoneEffective("18762672247"));
		//JSONString
	}
	
	public int updatePhoneNum(HttpServletRequest request, TUserApp userApp, String phoneNum) throws Exception 
	{
		if(userApp.getPhoneNum() != null) {
			return -1;
		}
		
		//0 白名单 1 黑名单 2 可疑名单 3 库无
		int code = userPhoneEffective(phoneNum);
		if(code == -1) {
			return -1;
		}
		
		userApp.setPhoneNum(phoneNum);
		userApp.setIsEffective(code);
		update(userApp);
		
		return 1;
	}
	
	public TUserApp getUserByUdid(String udid)
	{		
		return userAppDao.getUserByUdid(udid);
	}
	
	public TUserApp getUserByOpenID(String openID)
	{		
		return userAppDao.getUserByOpenID(openID);
	}
	
	public int updateUserWeiXin(String udid,String weiXinName,String headImgUrl, String openID)
	{
		TUserApp userApp = getUserByUdid(udid);
		if(userApp == null || userApp.getOpenID() !=null) {
			return -1;
		}
		
		if(EmptyUtils.isEmpty(userApp))
		{
			return 2;
		}
		
		try
		{
			weiXinName = SLEmojiFilter.filterEmoji(weiXinName);
			userApp.setWeixin(URLDecoder.decode(weiXinName, "UTF-8"));
			userApp.setFlag5(URLDecoder.decode(headImgUrl, "UTF-8"));
			userApp.setOpenID(URLDecoder.decode(openID, "UTF-8"));
			//update(userApp);
			userAppDao.updateWechat(udid, URLDecoder.decode(weiXinName, "UTF-8"), URLDecoder.decode(headImgUrl, "UTF-8"), URLDecoder.decode(openID, "UTF-8"));
			//masterUserAppAward(userApp);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		
		return 1;
	}
	
	//updateUserAlipay
	public int updateUserAlipay(String alipay, String udid, String usernick) 
	{
		TUserApp userApp = getUserByUdid(udid);
		if(userApp== null || userApp.getZhifubao() != null){
			return -1;
		}
		
		try
		{
			userApp.setUserNick(URLDecoder.decode(usernick, "UTF-8"));
			userApp.setZhifubao(URLDecoder.decode(alipay, "UTF-8"));
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}

		update(userApp);
		//masterUserAppAward(userApp);
		return 1;
	}
	
	public void updateSmsCode(HttpServletRequest request, TUserApp userApp,String smsCode) 
	{
		userApp.setInvitationCode(smsCode);
		super.update(userApp);
	}
		
	//updateLimitTime
	public void updateLimitTime(TUserApp userApp,Integer limitTime) 
	{
		userApp.setLimitTime(limitTime);;
		super.update(userApp);
	}
	
	public void updateIdfa(TUserApp userApp, String idfa) 
	{
		userApp.setIdfa(idfa);
		userAppDao.updateIdfa(userApp.getUserAppId(), idfa);
		//super.update(userApp);
	}
	
	/**
	 * 获取当前的广告列表
	 */
	public List<Map<String,Object>> queryCurrentAdverList(String excludeAdverId)
	{
		List<Map<String,Object>> adverAuthoritys = new ArrayList<Map<String,Object>>();
		
		List<TChannelAdverInfo> adverList = channelAdverInfoDao.queryCurrentAdverList();
		if(adverList != null)
		{
			for(TChannelAdverInfo adver:adverList)
			{
				Map<String,Object> adverAuthority = new HashMap<String,Object>();
				adverAuthority.put("adverId",adver.getAdverId());
				adverAuthority.put("adverName",adver.getAdverName());
				adverAuthority.put("adid",adver.getAdid());
				adverAuthority.put("adverPrice",adver.getAdverPrice());
				adverAuthority.put("adverCount",adver.getAdverCount());
				adverAuthority.put("adverCountRemain",adver.getAdverCountRemain());
				adverAuthority.put("adverCountComplete",adver.getAdverCountComplete());
				adverAuthority.put("adverDayStart",adver.getAdverDayStart());
				adverAuthority.put("adverDayEnd",adver.getAdverDayEnd());
				adverAuthority.put("adverStatus",adver.getAdverStatus());
				if(!StringUtils.hasText(excludeAdverId) || excludeAdverId.indexOf(String.valueOf(adver.getAdverId())) < 0)
				{
					adverAuthority.put("authority","1");
				}
				else
				{
					adverAuthority.put("authority","0");
				}
				
				adverAuthoritys.add(adverAuthority);
			}
		}
		
		return adverAuthoritys;
	}
	
	public HUserAppModel getHUserAppModelbyid(String appid) {
		return userAppDao.getHUserModelByappid(appid);
	}
	
	public Page<TUserApp> queryEffUserAppByMasterID(Page<TUserApp> page, String masterid)
	{
		return userAppDao.queryEffUserAppByMasterID(page, masterid);
	}
	
	
	public Page<TUserApp> queryUserAppByMasterID(Page<TUserApp> page, String masterid)
	{
		Page<TUserApp> _page = userAppDao.queryUserAppByMasterID(page, masterid);
		String userNums = CommonUtils.getAttributeValue(TUserApp.class, _page.getResult(), "userNum");
		if(EmptyUtils.isNotEmpty(userNums))
		{
			Map<Integer,TUserScore> userMap = userScoreService.getUserScoreByUserNums(userNums);
			CommonUtils.setAttributeValue(TUserApp.class,  _page.getResult(), userMap, "userAppId", "userScore");
		}
		
		return _page;
	}
	
	
	public Page<TUserApp> queryMasterUserAppByID(Page<TUserApp> page, String id)
	{
		Page<TUserApp> _page = userAppDao.queryMasterUserAppByID(page, id);
		String userNums = CommonUtils.getAttributeValue(TUserApp.class, _page.getResult(), "userNum");
		if(EmptyUtils.isNotEmpty(userNums))
		{
			Map<Integer,TUserScore> userMap = userScoreService.getUserScoreByUserNums(userNums);
			CommonUtils.setAttributeValue(TUserApp.class,  _page.getResult(), userMap, "userAppId", "userScore");
		}
		
		return _page;
	}
	
	public Page<TUserApp> getUserByUserNum(Page<TUserApp> page, String userNum)
	{
		Page<TUserApp> _page = userAppDao.getUserByUserNum(page, userNum);
		String userNums = CommonUtils.getAttributeValue(TUserApp.class, _page.getResult(), "userNum");
		if(EmptyUtils.isNotEmpty(userNums))
		{
			Map<Integer,TUserScore> userMap = userScoreService.getUserScoreByUserNums(userNums);
			CommonUtils.setAttributeValue(TUserApp.class,  _page.getResult(), userMap, "userAppId", "userScore");
		}
		
		return _page;
	}
	
	
	public TUserApp getLastUserApp() {
		TUserApp  tUserApp = userAppDao.getLastUserApp();
		return tUserApp;
	}
	
	
}
