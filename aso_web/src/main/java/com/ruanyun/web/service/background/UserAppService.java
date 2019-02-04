/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.service.background;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.ruanyun.web.util.NumUtils;
import com.ruanyun.web.util.UploadCommon;

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
					,"invitationCode","phoneVersion","phoneModel","idfa","taskNewStatus","zhifubao","weixin","zhifubaoName","userApppType","appStore", "masterID"});
			olUuser.setLevel(userApp.getLevel());
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

	public void updatePhoneNum(HttpServletRequest request, TUserApp userApp,String phoneNum) 
	{
		userApp.setPhoneNum(phoneNum);;
		super.update(userApp);
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
}
