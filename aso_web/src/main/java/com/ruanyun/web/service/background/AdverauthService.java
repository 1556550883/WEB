package com.ruanyun.web.service.background;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.CommonUtils;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.AdverauthDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TAdverAuth;
import com.ruanyun.web.model.TArticleInfo;
import com.ruanyun.web.model.TSchool;
import com.ruanyun.web.model.TUserStudentCart;
import com.ruanyun.web.model.sys.TProvince;
@Service("adverauthService")
public class AdverauthService extends BaseServiceImpl<TAdverAuth> {
	@Autowired
	@Qualifier("adverauthDao")
	private AdverauthDao adverauthDao;
	@Autowired
	@Qualifier("schoolService")
	private SchoolService schoolService;
	@Autowired
	@Qualifier("userStudentCartService")
	private UserStudentCartService userStudentCartService;
	
	
	
	@Override
	public Page<TAdverAuth> queryPage(Page<TAdverAuth> page, TAdverAuth t) {
		List<TAdverAuth> adverAuthList = new ArrayList<TAdverAuth>();
		Page<TAdverAuth> _page = adverauthDao.getList(page, t);
			if(EmptyUtils.isNotEmpty(_page) && EmptyUtils.isNotEmpty(_page.getResult())){
				for (TAdverAuth tAdverAuth : _page.getResult()) {
					if(EmptyUtils.isNotEmpty(tAdverAuth) && tAdverAuth.getAuthType() == 2){//学校
						adverAuthList.add(tAdverAuth);
					}
					
				}
			}
			
		if(EmptyUtils.isNotEmpty(adverAuthList)){
			String shcoolIds=CommonUtils.getAttributeValue(TAdverAuth.class, adverAuthList, "commonAuthNum");
			if(EmptyUtils.isNotEmpty(shcoolIds)){
				Map<String, TSchool> provinceMap=schoolService.getSchoolBySchoolId(shcoolIds);
				CommonUtils.setAttributeValue(TAdverAuth.class,  _page.getResult(), provinceMap, "commonAuthNum", "school");
			}
		}
			
		return _page;
	}

	/**
	 * 功能描述:保存 广告文章认证
	 * @author wsp  2017-1-9 下午04:11:32
	 * @param adverAuth
	 * @param jsonStr
	 * @return
	 */
	public int saveOrupdate(TAdverAuth adverAuth, String jsonStr) {
		if(EmptyUtils.isNotEmpty(adverAuth) && EmptyUtils.isNotEmpty(adverAuth.getAuthType()) && adverAuth.getAuthType()==1){//1-城市
			String[] commonAuthNums = null;
			if(EmptyUtils.isNotEmpty(adverAuth.getCommonAuthNum())){
				commonAuthNums = adverAuth.getCommonAuthNum().split(",");
				for (String commonAuthNum : commonAuthNums) {
					if(commonAuthNum == "")continue;
					TAdverAuth tAdverAuth = new TAdverAuth(adverAuth.getCommonNum(), commonAuthNum, adverAuth.getAuthType(), adverAuth.getCommonType());
					super.save(tAdverAuth);
				}
			}
		}
		
		else{// 2-学校
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			JSONArray array = jsonObject.getJSONArray("arrayObj"); 
			for (Object object : array) {
				JSONObject json = (JSONObject) object;
				String commonAuthNum = json.getString("schoolId");
				if(commonAuthNum == "")continue;
				TAdverAuth tAdverAuth = new TAdverAuth(adverAuth.getCommonNum(), commonAuthNum, adverAuth.getAuthType(), adverAuth.getCommonType());
				super.save(tAdverAuth);
			}
		}
		
		return 1;
	}

	/**
	 * 功能描述:批量删除
	 * @author wsp  2017-1-9 下午05:36:47
	 * @param authId
	 * @return
	 */
	public int delAll(String ids) {
		if(EmptyUtils.isNotEmpty(ids)){
			String[] authIds = ids.split(",");
			for (String id : authIds) {
				super.delete(TAdverAuth.class,Integer.parseInt(id));
			}
			return 1;
		}
		return 0;
	}

	/**
	 * 功能描述:权限判断
	 * @author wsp  2017-1-10 下午02:38:44
	 * @param response
	 * @param userNum	用户编号
	 * @param commonNum	文章编号或者广告编号
	 * @param commonType 类型 1-文章 2-广告
	 * @param flag 
	 * @return 
	 */
	public Integer getIsAuthority(String userNum, String commonNum,Integer commonType,Boolean flag) {
		TSchool school = null;
		if(flag &&(EmptyUtils.isEmpty(userNum) || EmptyUtils.isEmpty(commonNum) || EmptyUtils.isEmpty(commonType)))
			return -1;//参数不全
		//权限
		List<TAdverAuth> adverAuthList = super.getAllByCondition(TAdverAuth.class, new String[]{"commonNum","commonType"}, new Object[]{commonNum,commonType});//commonType类型 1-文章 2-广告
		//学生-学校管理
		TUserStudentCart userStudentCart = userStudentCartService.get(TUserStudentCart.class, new String[]{"userAppNum","cartStatus"}, new Object[]{userNum,2});//状态 1-审核中 2-审核通过 3-审核失败
		//所在学校
		if(EmptyUtils.isNotEmpty(userStudentCart) && EmptyUtils.isNotEmpty(userStudentCart.getSchoolId()))
			school = schoolService.get(TSchool.class, userStudentCart.getSchoolId());
		if(EmptyUtils.isEmpty(adverAuthList) || EmptyUtils.isEmpty(userStudentCart))
			return 2;//没有设置权限  
		
		for (TAdverAuth tAdverAuth : adverAuthList) {
			if(EmptyUtils.isEmpty(tAdverAuth))continue;
			if(EmptyUtils.isNotEmpty(tAdverAuth.getAuthType()) && tAdverAuth.getAuthType() == 2 && EmptyUtils.isNotEmpty(userStudentCart.getSchoolId()) && userStudentCart.getSchoolId().toString().equals(tAdverAuth.getCommonAuthNum()))//auth_Type   2-学校
				return 1;
			
			if(EmptyUtils.isNotEmpty(tAdverAuth.getAuthType()) && tAdverAuth.getAuthType() == 1 && EmptyUtils.isNotEmpty(school) && school.getCity().equals(tAdverAuth.getCommonAuthNum()))//城市
				return 1;
		}
		return 2;
	}

	/**
	 * 功能描述:清除
	 * @author wsp  2017-1-10 下午04:45:42
	 * @param commonNum	文章编号或者广告编号
	 * @param articleNum	类型 1-文章 2-广告
	 */
	public void clearValues(String commonNum, int commonType) {
		if(EmptyUtils.isNotEmpty(commonNum)){
			super.delete(TAdverAuth.class, new String[]{"commonNum","commonType"}, new Object[]{commonNum,commonType});
		}
	}
}







