/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-8
 */
package com.ruanyun.web.service.background;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.ShopGoodsDao;
import com.ruanyun.web.model.TShopGoods;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.model.sys.UploadVo;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;
import com.ruanyun.web.util.UploadCommon;

/**
 *@author feiyang
 *@date 2016-1-8
 */
@Service
public class ShopGoodsService extends BaseServiceImpl<TShopGoods> {

	@Autowired
	private ShopGoodsDao shopGoodsDao;

	
	public Page<TShopGoods> queryPage(Page<TShopGoods> page, TShopGoods t,TUser tUser) {
		return shopGoodsDao.PageSql(page, tUser, t);
	}

	/**
	 * 功能描述：增加或者修改类型
	 * 
	 * @param t
	 */
	public Integer saveOrupdate(TShopGoods info, HttpServletRequest request,
			TUser user, MultipartFile picFile) {
		try {
			if (picFile.getSize()!=0) {
				UploadVo vo = UploadCommon.uploadPic(picFile, request,
						Constants.FILE_IMG, "gif,jpg,jpeg,bmp,png");
				if (vo.getResult()==1) {
					info.setGoodImg(vo.getFilename());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (info != null) {
			if (EmptyUtils.isNotEmpty(info.getGoodId())
					&& info.getGoodId() != 0) {
				TShopGoods n = super.get(TShopGoods.class, info.getGoodId());
				BeanUtils.copyProperties(info, n, new String[] {"goodNum"});
				shopGoodsDao.update(n);
			} else {
				shopGoodsDao.save(info);
				info.setGoodNum(NumUtils.getCommondNum(NumUtils.GOODS_NUM, info.getGoodId()));
				
			}
		}
		return 1;
	}
}
