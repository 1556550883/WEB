package com.ruanyun.web.service.background;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.ranges.RangeException;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserStudentCartDao;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserStudentCart;
import com.ruanyun.web.util.Constants;

@Service("userStudentCartService")
public class UserStudentCartService extends BaseServiceImpl<TUserStudentCart> {
	@Autowired
	private UserStudentCartDao userStudentCartDao;
	@Autowired
	private UserAppService userAppService;
	
	/**
	 * 
	 * 功能描述:学生卡信息表
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-4-16
	 */
	public Page<TUserStudentCart> pageSql(Page<TUserStudentCart> page, TUserStudentCart info,Integer cartStatus) {
		return userStudentCartDao.queryPage(page, info, cartStatus);
	}
	
	/**
	 * 功能描述:查询学生信息详情
	 * @author cqm  2016-12-17 下午06:15:20
	 * @param userAppNum
	 * @param isRequired
	 * @return
	 */
	public TUserStudentCart getUserStudentCart(String userAppNum){
		if(EmptyUtils.isNotEmpty(userAppNum)){
			TUserStudentCart bean = get(TUserStudentCart.class,"userAppNum", userAppNum);
			return bean;
		}
		return null;
	}
	
	/**
	 * 功能描述:添加学生信息
	 * @author cqm  2016-12-17 下午05:19:04
	 * @param userStudentCart
	 */
	@Transactional
	public void adduserStudentCart(TUserStudentCart userStudentCart){
		if(EmptyUtils.isEmpty(userStudentCart.getUserAppNum())||
		   EmptyUtils.isEmpty(userStudentCart.getSchoolName())||
		   EmptyUtils.isEmpty(userStudentCart.getDepartmentName())||
		   EmptyUtils.isEmpty(userStudentCart.getClassName())){
			throw new NullPointerException("参数不全");
			
		}
		TUserStudentCart beanCart = new TUserStudentCart(); 
		beanCart.setCartStatus(Constants.CART_STATUS_1);//审核中
		beanCart.setClassName(userStudentCart.getClassName());
		beanCart.setDepartmentName(userStudentCart.getDepartmentName());
		beanCart.setSchoolId(userStudentCart.getSchoolId());
		beanCart.setUserAppNum(userStudentCart.getUserAppNum());
		beanCart.setSchoolName(userStudentCart.getSchoolName());
		beanCart.setStudentCar(userStudentCart.getStudentCar());
		save(beanCart);
		
	}
	
	/**
	 * 功能描述:审核
	 * @author zhujingbo
	 * @param type
	 * @param ids
	 * @return
	 */
	public int saveStatus(Integer type, String ids) {
		if (type != 2 & type != 3)return 0;
		String[] id=ids.split(Constants.FILE_COMMA);
		for (String adverId:id) {
		TUserStudentCart bean = super.get(TUserStudentCart.class, Integer.valueOf(adverId));
		bean.setCartStatus(type);
		super.update(bean);
		if(type==2){
			TUserApp userApp= get(TUserApp.class, "userNum",bean.getUserAppNum());
			userApp.setUserApppType(2);
			userAppService.update(userApp);
			}
		}	
			return 1;
	}

}
