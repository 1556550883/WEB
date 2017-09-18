/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-23
 */
package com.ruanyun.web.service.background;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.PicDao;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TPic;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.model.sys.UploadVo;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;
import com.ruanyun.web.util.UploadCommon;

/**
 *@author feiyang
 *@date 2016-1-23
 */
@Service
public class PicService extends BaseServiceImpl<TPic>{
	@Autowired
	@Qualifier("picDao")
	private PicDao picDao;
	
	/**
	 * 
	 * 功能描述:后台展示
	 * @param page
	 * @param t
	 * @return
	 *@author feiyang
	 *@date 2016-1-23
	 */
	public Page<TPic> pageSql(Page<TPic>page,TPic t) {
		return picDao.pageSql(page, t);
	}
	

	/**
	 * 功能描述：增加或者修改类型
	 * 
	 * @param t
	 */
	public Integer saveOrupdate(TPic info, HttpServletRequest request,MultipartFile picFile) {
	
		try {
			if (picFile.getSize()!=0) {
				UploadVo vo = UploadCommon.uploadPic(picFile, request,
						Constants.FILE_IMG, "gif,jpg,jpeg,bmp,png");
				if (vo.getResult()==1) {
					info.setPicPath(vo.getFilename());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (info != null) {
			if (EmptyUtils.isNotEmpty(info.getPicId())
					&& info.getPicId() != 0) {
				TPic n = super.get(TPic.class, info
						.getPicId());
				BeanUtils
						.copyProperties(info, n, new String[] { "picNum","createtime"});
				picDao.update(n);
			} else {
				info.setCreatetime(new Date());			
				picDao.save(info);
				info.setPicNum(NumUtils.getCommondNum(
						NumUtils.CHANNEL_ADVER_PIC, info.getPicId()));

			}
		}
		return 1;
	}
}
