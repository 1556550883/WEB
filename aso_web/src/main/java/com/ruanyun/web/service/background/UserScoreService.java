/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.service.background;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserScoreDao;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserApprentice;
import com.ruanyun.web.model.TUserLevel;
import com.ruanyun.web.model.TUserLevelRate;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserScoreInfo;
import com.ruanyun.web.service.app.AppUserApprenticeService;
import com.ruanyun.web.util.ArithUtil;
import com.ruanyun.web.util.CommonMethod;
import com.ruanyun.web.util.Constants;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Service
public class UserScoreService extends BaseServiceImpl<TUserScore>{
	@Autowired
	@Qualifier("userScoreDao")
	private UserScoreDao userScoreDao;
	@Autowired
	private UserScoreInfoService userScoreInfoService;
	@Autowired
	private UserApprenticeService userApprenticeService;
	@Autowired
	private UserLevelRateService userLevelRateService;
	@Autowired
	private UserLevelService userLevelService;
	@Autowired
	private UserAppService userAppService;
	@Autowired
	private AppUserApprenticeService appUserApprenticeService;
	@Override
	public Page<TUserScore> queryPage(Page<TUserScore> page, TUserScore t) {
		return userScoreDao.queryPage(page, t);
	}
	
	/**
	 * 
	 * 功能描述:根据用户名获取用户积分信息
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-11
	 */
	public TUserScore getScore(String userNum)
	{
		return	userScoreDao.getScore(userNum);
	}
	
	/**
	 * 
	 * 功能描述:根据用户名获取用户积分信息
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-11
	 */
	public List<TUserScore> getScoreListByUserNums(String userNums)
	{
		return	userScoreDao.getScoreListByUserNums(userNums);
	}
	
	
	public List<TUserScoreInfo> getScoreInfoListByUserNums(String userNums)
	{
		return	userScoreInfoService.getScoreInfoListByUserNums(userNums);
	}
	
	/**
	 * 
	 * 功能描述:新建用户保存得分表数据
	 * @param userNum 用户名
	 * @param type	1--管理后台 2--手机app用户
	 *@author feiyang
	 *@date 2016-1-23
	 */
	public void addNewUserScore(String userNum,Integer type)
	{
		TUserScore score=new TUserScore();
		
		if(type == 2) 
		{
			score.setScore(1f);
			score.setScoreSum(1f);
			score.setScoreDay(1f);
		}
		else
		{
			score.setScore(0f);
			score.setScoreSum(0f);
			score.setScoreDay(0f);
		}
		
		score.setType(type);
		score.setUserNum(userNum);
		score.setApprenticeCountDay(0);
		score.setApprenticeCount(0);
		score.setApprenticeScoreDay(0f);
		score.setApprenticeScore(0f);
		score.setHaveRedPackageDay(0);
		score.setRedPackageCountDay(0);
		score.setRedPackageScoreCount(0f);
		score.setRedPackageScoreDay(0f);
		TUserLevel userLevel=userLevelService.getUserLevelByProportionCount(0);
		score.setUserLevelNum(userLevel.getLevelNum());	
		save(score);
	}

	/**
	 * 功能描述: 获取 用户账号信息  key 用户编号 值为 用户分数 对象
	 *
	 * @author yangliu  2016年1月18日 下午8:49:32
	 * 
	 * @param userNums 用户边凹 格式为 'US_0001','US_0002'
	 * @return
	 */
	public Map<String,TUserScore> getMapScore(String userNums)
	{
		Map<String,TUserScore> userScoreMap= new HashMap<String, TUserScore>();
		if(EmptyUtils.isEmpty(userNums))
			return userScoreMap;
		List<TUserScore> list = getScoreListByUserNums(userNums);
		
		if(EmptyUtils.isNotEmpty(list)){
			for(TUserScore userScore: list){
				userScoreMap.put(userScore.getUserNum(),userScore);
			}
		}
		return  userScoreMap;
	}
	
	/**
	 * 功能描述: 添加分数  总分 可用积分  今日收益 等 添加分数 并保存记录
	 *
	 * @author yangliu  2016年1月16日 下午7:50:18
	 * 
	 * @param userNum  用户编号
	 * @param scoreName  得分名称
	 * @param scoreType 分数类型  常量 USER_SCORE_INFO_TYPE_ 值
	 * @param score 分数 1 添加1分  -1 减去一分
	 * @param userType
	 */
	public void addScoreSum(String userNum,String scoreName,Integer scoreType,Float score,Integer userType)
	{
		TUserScore userScore=getScore(userNum);
		if(userScore!=null){
			if(Constants.USER_SCORE_INFO_TYPE_1==scoreType){
				userScore.setRedPackageCountDay(userScore.getRedPackageCountDay()+1);
				userScore.setHaveRedPackageDay(userScore.getHaveRedPackageDay()+1);
			}
			updateScore(userScore, score, 1, scoreName, scoreType, userType);
		}
	}
	
	/**
	 * 功能描述: 添加分数 只给可用积分 增减 并保存得分记录
	 *
	 * @author yangliu  2016年1月16日 下午7:50:18
	 * 
	 * @param userNum  用户编号
	 * @param scoreName  得分名称
	 * @param scoreType 分数类型  常量 USER_SCORE_INFO_TYPE_ 值
	 * @param score 分数 1 添加1分  -1 减去一分
	 * @param userType
	 */
	public void addScore(String userNum, String scoreName, Integer scoreType, Float score, Integer userType)
	{
		TUserScore userScore = getScore(userNum);
		
		if(userScore!=null)
		{
			updateScore(userScore, score, 0, scoreName, scoreType, userType);
		}
	}
	
	public int addPutForward(String userNum, Float forward)
	{
		TUserApp userApp = userAppService.getUserAppByNum(userNum);
		userScoreInfoService.saveUserScoreInfo(userNum,"forward", forward, 1, userApp.getUserApppType());
		return 1;
	}
	
	/**
	 * 功能描述:直接修改分数 不做记录
	 *
	 * @author yangliu  2016年7月22日 上午10:02:33
	 * 
	 * @param userScore 用户分数对象
	 * @param score 分数
	 */
	public int updateScore(TUserScore userScore, Float score, int type)
	{
		if(userScore != null) 
		{
			System.out.println(score + "++++1");
			System.out.println(userScore.getScore() + "++++2");
			//(float)ArithUtil.add(userScore.getScore(), score)
			userScore.setScore(ArithUtil.addf(userScore.getScore(), score));
			userScore.setScoreDay(ArithUtil.addf(userScore.getScoreDay(), score));
			if(type != 2) 
			{
				userScore.setScoreSum(ArithUtil.addf(userScore.getScoreSum(), score));
			}
			//1代表师傅获取的分红  0表示标准任务的金额
			if(type == 1)
			{
				userScore.setApprenticeScore(ArithUtil.addf(userScore.getApprenticeScore(), score));
				appUserApprenticeService.addMyApprenticeScore(userScore.getUserNum(), userScore.getRankingNum(), score);
			}
			System.out.println(userScore.getScore() + "++++3");
			update(userScore);
			
			return 1;
		}

		return -1;
	}
	
	/**
	 * 功能描述: 修改积分
	 *
	 * @author yangliu  2016年1月19日 上午10:00:28
	 * 
	 * @param userScore
	 * @param score
	 * @param type
	 * @param scoreName
	 * @param scoreType
	 * @param userType
	 */
	private void updateScore(TUserScore userScore,Float score, int type,String scoreName,Integer scoreType,Integer userType)
	{
		userScore.setScore(userScore.getScore()+score);
		switch (type) 
		{
		case 1:
			userScore.setScoreDay(userScore.getScoreDay()+score);
			userScore.setScoreSum(userScore.getScoreSum()+score);
			break;
		case 2:
			userScore.setScoreDay(userScore.getScoreDay()+score);
			userScore.setScoreSum(userScore.getScoreSum()+score);
			userScore.setApprenticeScore(userScore.getApprenticeScore()+score);
			userScore.setApprenticeScoreDay(userScore.getApprenticeScoreDay()+score);
			break;
		}
		userScoreInfoService.saveUserScoreInfo(userScore.getUserNum(), scoreName, score, scoreType, userType);
		update(userScore);
	}
	
	public int updateApprentice(String usernum, int count)
	{
		return userScoreDao.updateApprentice(usernum, count);
	}
	
	/**
	 * 功能描述: 添加一级师傅
	 *
	 * @author yangliu  2016年1月19日 上午11:30:52
	 * 
	 * @param parentUserAppNum
	 * @param userNum
	 */
	public void addOneApprentice(String parentUserAppNum,String userNum){
		TUserScore parentUserScore=getScore(parentUserAppNum);
		parentUserScore.setApprenticeCount(parentUserScore.getApprenticeCount()+1);
		parentUserScore.setApprenticeCountDay(parentUserScore.getApprenticeCountDay()+1);
		update(parentUserScore);
		userApprenticeService.saveApprentice(parentUserAppNum, userNum,1);
		if(Constants.APPRENTICE_LEVEL>1)
			addOtherApprentice(parentUserScore.getUserNum(), userNum);
	}
	
	/**
	 * 功能描述: 添加二级以上的师傅
	 *
	 * @author yangliu  2016年1月19日 上午11:31:16
	 * 
	 * @param parentUserAppNum
	 * @param userNum
	 */
	private void addOtherApprentice(String parentUserAppNum,String userNum){
		// 获取所有的师傅
		List<TUserApprentice> userApprenticeList=userApprenticeService.getListAllByApprenticeUserNum(parentUserAppNum);
		String parentUserNums=userApprenticeService.getUserNums(userApprenticeList, 1);
		// 获取所有师傅的等级账号
		Map<String,TUserScore> userScoreMap=getMapScore(parentUserNums);
		TUserScore userScore=null;
		for(TUserApprentice userApprentice : userApprenticeList){
			if(userApprentice.getUserApprenticeType()>=Constants.APPRENTICE_LEVEL){
				continue;
			}
			userScore=userScoreMap.get(userApprentice.getUserNum());
			userScore.setApprenticeCount(userScore.getApprenticeCount()+1);
			userScore.setApprenticeCountDay(userScore.getApprenticeCountDay()+1);
			TUserLevel userLevel=userLevelService.getUserLevelByProportionCount(userScore.getApprenticeCount());
			userScore.setUserLevelNum(userLevel.getLevelNum());
			update(userScore);
			userApprenticeService.saveApprentice(userApprentice.getUserNum(), userNum, userApprentice.getUserApprenticeType()+1);
		}
	}
	
	
	/**
	 * 功能描述: 得分并给师傅分苏
	 *
	 * @author yangliu  2016年1月18日 下午10:03:34
	 * 
	 * @param userNum 得分用户编号
	 * @param scoreName 分数类型
	 * @param score  分数
	 */
	public void addApprenticeScore(String userNum,String scoreName,Float score){
		addScoreSum(userNum, scoreName, Constants.USER_SCORE_INFO_TYPE_1, score, Constants.USER_TYPE_APP);
		// 获取所有的师傅
		List<TUserApprentice> userApprenticeList=userApprenticeService.getListAllByApprenticeUserNum(userNum);
		// 获取等级返回的比例
		Map<String,TUserLevelRate> levelRateMap = userLevelRateService.getMapAllLevelRate();
		String parentUserNums=userApprenticeService.getUserNums(userApprenticeList, 1);
		// 获取所有师傅的等级账号
		Map<String,TUserScore> userScoreMap=getMapScore(parentUserNums);
		TUserScore userScore=null;
		TUserLevelRate userLevelRate=null;
		Float score1=0f;
		for(TUserApprentice userApprentice : userApprenticeList){
			userScore=userScoreMap.get(userApprentice.getUserNum());
			userLevelRate=levelRateMap.get(userScore.getUserLevelNum()+"_"+userApprentice.getUserApprenticeType()); //获取用户得分的对象
			score1=CommonMethod.formatFloat(score*userLevelRate.getLevelRate()/100);
			if(score1<=0)
				continue;
			userApprentice.setScore(userApprentice.getScore()+score1);
			updateScore(userScore, score1, 2, scoreName, Constants.USER_SCORE_INFO_TYPE_5, Constants.USER_TYPE_APP);
			userApprenticeService.update(userApprentice);
		}
	}
	
	@Transactional
	public void clearUserScoreDay(){
		userScoreDao.clearUserScoreDay();
	}
	
	@Transactional
	public int clearUserRedPackageCount(){
		return userScoreDao.clearUserRedPackageCount();
	}
	
	/**
	 * 功能描述:根据userNums   map   key userNums  value  TUserScore
	 * @author wsp  2017-1-22 下午02:51:05
	 * @param userNums
	 * @return
	 */
	public Map<Integer,TUserScore> getUserScoreByUserNums(String userNums)
	{
		Map<Integer, TUserScore> map = new HashMap<Integer, TUserScore>();
		
		for(String userNum:userNums.split(","))
		{
			TUserScore userScore = getScore(userNum);
			TUserApp userApp = userAppService.getUserAppByNum(userNum);
			map.put(Integer.valueOf(userApp.getUserAppId()), userScore);
		}
		
		return map;
	}
}
