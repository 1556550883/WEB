/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-29
 */
package com.ruanyun.web.service.background;

import java.util.Date;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.NoticeDao;
import com.ruanyun.web.model.TNotice;

/**
 *@author feiyang
 *@date 2016-4-29
 */
@Service
public class NoticeService extends BaseServiceImpl<TNotice> {

	@Autowired
	private NoticeDao noticeDao;

	/**
	 * 
	 * 功能描述:后台展示
	 * 
	 * @param page
	 * @param t
	 * @return
	 *@author feiyang
	 *@date 2016-1-23
	 */
	public Page<TNotice> queryPageSql(Page<TNotice> page, TNotice info) {
		return noticeDao.queryPageSql(page, info);
	}

	/**
	 * 功能描述：增加或者修改类型
	 * 
	 * @param t
	 */
	public Integer saveOrupdate(TNotice info) {
		if (info != null) {
			if (EmptyUtils.isNotEmpty(info.getNoticeId())
					&& info.getNoticeId() != 0) {
				TNotice n = super.get(TNotice.class, info.getNoticeId());
				BeanUtils
						.copyProperties(info, n, new String[] { "createDate" });
				update(n);
			} else {
				info.setCreateDate(new Date());
				save(info);

			}
		}
		return 1;
	}

	/**
	 * 
	 * 功能描述:获取最新的一条公告
	 * 
	 * @return
	 *@author feiyang
	 *@date 2016-4-29
	 */
	public TNotice getNotice() {
		return noticeDao.getNotice();
	}
}
