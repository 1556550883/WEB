/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.service.background;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.ChannelAdverInfoDao;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.sys.UploadVo;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;
import com.ruanyun.web.util.UploadCommon;

@Service
public class ChannelAdverInfoService extends BaseServiceImpl<TChannelAdverInfo> 
{
	@Autowired
	@Qualifier("channelAdverInfoDao")
	private ChannelAdverInfoDao channelAdverInfoDao;
	
	@Override
	public Page<TChannelAdverInfo> queryPage(Page<TChannelAdverInfo> page, TChannelAdverInfo t) 
	{
		return channelAdverInfoDao.queryPage(page, t);
	}
	
	/**
	 * 查询广告列表（后台显示）
	 */
	public Page<TChannelAdverInfo> queryAdverList(Page<TChannelAdverInfo> page, TChannelAdverInfo t) 
	{
		return channelAdverInfoDao.PageSql3(page, t);
	}
	
	public int createAdverTable(String adid, String channelID) 
	{
		return channelAdverInfoDao.createAdverTable(adid, channelID);
	}
	
	public void createAdverTable() 
	{
		
	}
	
	/**
	 * 
	 * 功能描述：增加或者修改
	 * @param info
	 * @param stepMinCount 
	 * @param stepType 
	 * @param stepUseTime 
	 * @param stepScore 
	 * @param stepTime 
	 * @param stepRates 
	 * @param stepDesc 
	 * @param stepName 
	 */
	public void saveOrUpd(TChannelAdverInfo info,MultipartFile file,HttpServletRequest request, MultipartFile fileAdverImg)
	{
		try
		{
			if("1".equals(info.getFileType()) && file.getSize() != 0)
			{
				UploadVo vo = UploadCommon.uploadPic(file, request, Constants.FILE_ADVER_APK, "apk,ipa");
				info.setFileUrl(vo.getFilename());
				info.setFileSize(Float.valueOf(file.getSize() / 1000));
			}
			
			if(fileAdverImg.getSize() != 0)
			{
				UploadVo vo=UploadCommon.uploadPic(fileAdverImg, request, Constants.FILE_ADVER_IMG, "jpg,gif,png");
				if(StringUtils.hasText(vo.getFilename()))
				{
					info.setAdverImg(vo.getFilename());
				}
			}
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
		
		if(EmptyUtils.isNotEmpty(info.getAdverId()))
		{
			TChannelAdverInfo oldAdverInfo = getInfoById(info.getAdverId());
			info.setAdverActivationCount((info.getAdverCount() - oldAdverInfo.getAdverCount()) + oldAdverInfo.getAdverActivationCount());
			BeanUtils.copyProperties(info, oldAdverInfo, new String[]{"adverCreatetime","adverStatus","channelNum","adverNum","downloadCount","adverCountRemain"});
			update(oldAdverInfo);
			//向轴 注释
			//adverStepService.saveAdverStep(shopsInfo, stepName, stepDesc, stepRates, stepTime, stepScore, stepUseTime, stepType, stepMinCount);
		}
		else
		{
			info.setAdverCreatetime(new Date());
			info.setAdverStatus(0);
			if(info.getAdverCountRemain() == null)
			{
				info.setAdverCountRemain(info.getAdverCount());
			}
			
			save(info);
			info.setAdverNum(NumUtils.getCommondNum(NumUtils.ADVER_INFO, info.getAdverId()));
			//向轴 注释
			//adverStepService.saveAdverStep(info, stepName, stepDesc, stepRates, stepTime, stepScore, stepUseTime, stepType, stepMinCount);
		}
	}

	/**
	 * 
	 * 功能描述：删除
	 * @param ids
	 */
	public void delAll(String ids)
	{
		channelAdverInfoDao.delAll(ids);
	}
	
	/**
	 * 
	 * 功能描述：根据Id获得详细信息
	 * @param id
	 * @return
	 */
	public TChannelAdverInfo getInfoById(Integer id)
	{
		TChannelAdverInfo adverInfo = super.get(TChannelAdverInfo.class, id);
		
		return adverInfo;
	}
	
	
	public List<TChannelAdverInfo>  getInfoByIds(String ids)
	{
		return channelAdverInfoDao.queryAdversByIds(ids);
	}
	
	
	public List<TChannelAdverInfo>  queryAllStartAdvers()
	{
		return channelAdverInfoDao.queryAllStartAdvers();
	}
	
	/**
	 * 功能描述: 更加广告编号 获取对象
	 *
	 * @author yangliu  2016年1月20日 下午9:28:20
	 * 
	 * @param adverNum 广告编号
	 * @return
	 */
	public TChannelAdverInfo getInfoByAdverNum(String adverNum)
	{
		TChannelAdverInfo adverInfo=super.get(TChannelAdverInfo.class, "adverNum",adverNum);
		return adverInfo;
	}
	
	/**
	 * 批量审核
	 */
	public void updateAdverStatus(Integer status,String ids)
	{
		channelAdverInfoDao.updateAdverStatus(status, ids);
	}
	
	//自动增加任务更新任务信息
	public void autoAddAdverCount(TChannelAdverInfo info)
	{
		channelAdverInfoDao.autoAddAdverCount(info);
	}
	
	/**
	 * 修改所有任务的状体
	 * @param status
	 * @param ids
	 */
	public void updateAdverStatusAll (Integer status)
	{
		channelAdverInfoDao.updateAdverStatusAll(status);
	}
	/**
	 * 批量支付
	 */
	public String updateAdverStatus2Pay(Integer status,String ids)
	{
		String[] adverIds = ids.split(",");
		for(String adverId : adverIds)
		{
			TChannelAdverInfo adverInfo = get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
			if(adverInfo == null)
			{
				return "选中的广告不存在！";
			}
			else if(adverInfo.getAdverStatus() != 2)
			{
				return "广告支付前必须先停用！";
			}
		}
		channelAdverInfoDao.updateAdverStatus2Pay(status, ids);
		
		return null;
	}
	
	
	public int updateAdverActivationCount(TChannelAdverInfo adverInfo) 
	{
		return channelAdverInfoDao.updateAdverActivationCount(adverInfo);
	}
	
	public int getCountComplete(String adverId) 
	{
		return channelAdverInfoDao.getCountComplete(adverId);
	}
	
	/*
	 * CREATE TABLE 新表
	 *SELECT * FROM 旧表 
	 */
	@Transactional
	public void adverInfoTableBak() 
	{
		channelAdverInfoDao.adverInfoTableBak();
	}
}
