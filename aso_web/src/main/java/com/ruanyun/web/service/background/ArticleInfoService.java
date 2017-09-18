/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-16
 */
package com.ruanyun.web.service.background;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.ArticleInfoDao;
import com.ruanyun.web.model.TAdverAuth;
import com.ruanyun.web.model.TArticleInfo;
import com.ruanyun.web.model.TSchool;
import com.ruanyun.web.model.TUserStudentCart;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.model.sys.UploadVo;
import com.ruanyun.web.service.sys.UserService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;
import com.ruanyun.web.util.UploadCommon;

/**
 *@author feiyang
 *@date 2016-4-16
 */
@Service
public class ArticleInfoService extends BaseServiceImpl<TArticleInfo>{
	
	@Autowired
	private ArticleInfoDao articleInfoDao;
	@Autowired
	private UserService userService;
	@Autowired
	private AdverauthService adverauthService;//权限
	/**
	 * 
	 * 功能描述:文章信息表
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-4-16
	 */
	public Page<TArticleInfo> pageSql(Page<TArticleInfo> page, TArticleInfo info) {
		
		return articleInfoDao.pageSql(page, info);
	}
	
	/**
	 * 功能描述：增加或者修改类型
	 * 
	 * @param t
	 */
	public Integer saveOrupdate(TArticleInfo info, HttpServletRequest request,MultipartFile picFile,TUser tUser) {	
		try {
			if (picFile.getSize()!=0) {
				UploadVo vo = UploadCommon.uploadPic(picFile, request,
						Constants.FILE_IMG, "gif,jpg,jpeg,bmp,png");
				if (vo.getResult()==1) {
					info.setArticlePic(vo.getFilename());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		if (info != null) {
			if (EmptyUtils.isNotEmpty(info.getArticleId())
					&& info.getArticleId() != 0) {
				TArticleInfo n = get(TArticleInfo.class, info.getArticleId());
				if(EmptyUtils.isNotEmpty(n.getIsAuth()) && !n.getIsAuth().equals(info.getIsAuth())){
					adverauthService.clearValues(n.getArticleNum(),1);//切换添加权限  清除
				}
				BeanUtils.copyProperties(info, n, new String[] { "createTime","createUserNum","createUserName","forwardQuantity","accessTimes"});
				n.setStatus(1);
				articleInfoDao.update(n);
			} else {
				info.setCreateTime(new Date());
				info.setCreateUserNum(tUser.getUserNum());
				info.setCreateUserName(tUser.getUserName());
				info.setForwardQuantity(0);//默认转发量0
				info.setStatus(1);//默认进行中
				info.setAccessTimes(0);//默认访问次数为0
				articleInfoDao.save(info);
				info.setArticleNum(NumUtils.getCommondNum(NumUtils.ARTICLEINFO_NUM, info.getArticleId()));
				update(info);
				
			}
			/*String[] array = info.getco 
			for (int i = 0; i < array.length; i++) {
				AdverauthService.sav
			}
			*/
		}
		return 1;
	}
	
	/**
	 * 
	 * 功能描述:增加转发量
	 * @param info
	 *@author feiyang
	 *@date 2016-4-16
	 */
	public void updateForwardQuantity(TArticleInfo info){
		if (info.getForwardQuantity()<info.getTaskQuantity()) {
			info.setForwardQuantity(info.getForwardQuantity()+1);	//转发量+1
			if (info.getForwardQuantity()>=info.getTaskQuantity()) //达到或者超过任务量
				info.setStatus(2);//任务结束
			update(info);
		}
	}
	
	/**
	 * 功能描述:访问量加1
	 * @author wsp  2017-1-19 下午04:45:52
	 * @param info
	 */
	public void updateAccessTimes(TArticleInfo info){
		if (EmptyUtils.isNotEmpty(info)) {
			Integer accessTimes = info.getAccessTimes();
			if(EmptyUtils.isEmpty(accessTimes)) accessTimes = 0;
			info.setAccessTimes(accessTimes+1);
			update(info);
		}
	}
}
