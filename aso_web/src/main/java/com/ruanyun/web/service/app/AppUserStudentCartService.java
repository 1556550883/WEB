package com.ruanyun.web.service.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserStudentCart;
import com.ruanyun.web.model.UserAppModel;
import com.ruanyun.web.service.background.UserStudentCartService;
import com.ruanyun.web.util.Constants;
@Service
public class AppUserStudentCartService {
	
	@Autowired
	private UserStudentCartService userStudentCartService;
	
	/**
	 * 功能描述:添加学生信息
	 * @author cqm  2016-12-17 下午05:26:16
	 * @param userStudentCart
	 * @return
	 */
	public AppCommonModel addUserStudentCart(TUserStudentCart  userStudentCart){
		AppCommonModel model=new AppCommonModel(1,"");
		if(EmptyUtils.isNotEmpty(userStudentCart)){	
			TUserStudentCart oldStudentCart = userStudentCartService.getUserStudentCart(userStudentCart.getUserAppNum());
			if(EmptyUtils.isNotEmpty(oldStudentCart)){
				oldStudentCart.setCartStatus(Constants.CART_STATUS_1);//审核中
				oldStudentCart.setClassName(userStudentCart.getClassName());
				oldStudentCart.setDepartmentName(userStudentCart.getDepartmentName());
				oldStudentCart.setSchoolId(userStudentCart.getSchoolId());
				oldStudentCart.setUserAppNum(userStudentCart.getUserAppNum());
				oldStudentCart.setSchoolName(userStudentCart.getSchoolName());
				if(EmptyUtils.isNotEmpty(userStudentCart.getStudentCar()))
					oldStudentCart.setStudentCar(userStudentCart.getStudentCar());
//				oldStudentCart.setCartStatus(2);
				userStudentCartService.update(oldStudentCart);
				model.setMsg("修改成功");
				return model;
			}
		}
		userStudentCartService.adduserStudentCart(userStudentCart);
		model.setMsg("申请成功");
		return model;
	}
	
    /**
     * 功能描述:查询学生信息详情
     * @author cqm  2016-12-17 下午06:16:32
     * @param userAppNum
     * @return
     */
	public AppCommonModel getUserStudentCart(String userAppNum){
		
		return new AppCommonModel(1,"",userStudentCartService.getUserStudentCart(userAppNum));
	}

}
