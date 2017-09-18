package com.ruanyun.web.service.background;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ruanyun.common.orm.ICommonHqlDao;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelAdverStep;
import com.ruanyun.web.util.NumUtils;

@Service
public class AdverStepService extends BaseServiceImpl<TChannelAdverStep> {
	
	
	/**
	 * 
	 * 功能描述：增加广告步骤
	 * @auther xqzhang
	 * 时间：2016-1-19
	 * @param infos
	 * @param stepName
	 * @param stepDesc
	 * @param stepRates
	 * @param stepTime
	 * @param stepScore
	 * @param stepUseTime
	 * @param stepType
	 * @param stepMinCount
	 */
	public void saveAdverStep(TChannelAdverInfo infos,String[] stepName, String[] stepDesc, Integer[] stepRates, String[] stepTime, Float[] stepScore, Integer[] stepUseTime, String[] stepType, Integer[] stepMinCount){
		delete(TChannelAdverStep.class, "adverNum", infos.getAdverNum());
		for(int i=0;i<stepName.length;i++){
			TChannelAdverStep channelAdverStep=new TChannelAdverStep();
			channelAdverStep.setAdverStepCountNum(i+1);
			channelAdverStep.setAdverStepName(stepName[i]);
			channelAdverStep.setStepDesc(stepDesc[i]);
			channelAdverStep.setConversionsRates(100);
			channelAdverStep.setEffectiveTimeRates(new Date());
			channelAdverStep.setPrice(stepScore[i]);
			channelAdverStep.setUseTimeDay(stepUseTime[i]);
			channelAdverStep.setEffectiveType("3");
			channelAdverStep.setEffectiveMinCount(stepMinCount[i]);
			channelAdverStep.setAdverNum(infos.getAdverNum());
			channelAdverStep.setEffectiveSource(infos.getEffectiveSource());
			channelAdverStep.setOrderNum(i+1);
			save(channelAdverStep);
			channelAdverStep.setAdverStepNum(NumUtils.getCommondNum(NumUtils.CHANNEL_ADVER_STEP_INFO, channelAdverStep.getStepId()));
		}
	}
	
	
	/**
	 * 
	 * 功能描述：根据广告编号获得步骤
	 * @auther xqzhang
	 * 时间：2016-1-19
	 * @param adverNum
	 * @return
	 */
	public List<TChannelAdverStep> getAllStepByAdverId(String adverNum){
		return getAll(TChannelAdverStep.class, "adverNum", adverNum,"orderNum",ICommonHqlDao.ORDER_ASC);
	}
}
