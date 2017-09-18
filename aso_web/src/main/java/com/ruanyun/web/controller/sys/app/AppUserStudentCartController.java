package com.ruanyun.web.controller.sys.app;

import javax.management.loading.PrivateClassLoader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserStudentCart;
import com.ruanyun.web.model.sys.UploadVo;
import com.ruanyun.web.service.app.AppUserStudentCartService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.UploadCommon;

@Controller
@RequestMapping("app/userStudent")

public class AppUserStudentCartController extends BaseController{
	
	@Autowired
	private AppUserStudentCartService appUserStudentCartService;
	
	/**
	 * 功能描述:添加学生信息
	 * @author cqm  2016-12-17 下午05:31:46
	 * @param response
	 * @param userStudentCart
	 */
	@RequestMapping("add")
	public void saveUserStudentCart(HttpServletResponse response,HttpServletRequest request,MultipartFile picFile,TUserStudentCart userStudentCart){
		AppCommonModel model=null;
		try {
			if (EmptyUtils.isNotEmpty(picFile)) {				
				if (picFile.getSize() != 0) {
					UploadVo vo = UploadCommon.uploadPic(picFile, request,
							Constants.FILE_IMG, "gif,jpg,jpeg,bmp,png");
					if (vo.getResult() == 1) {
						userStudentCart.setStudentCar(vo.getFilename());
					}
				}
			}
			model=appUserStudentCartService.addUserStudentCart(userStudentCart);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("/app/userStudent/add:"+e.getMessage());
			model = new AppCommonModel(-1,e.getMessage());
		}
		super.writeJsonDataApp(response, model);
	}
	
	/**
	 * 功能描述:查询学生信息
	 * @author cqm  2016-12-17 下午05:38:43
	 * @param response
	 * @param page
	 * @param userStudentCart
	 */
	@RequestMapping("list")
	public void getUserStudentCartList(HttpServletResponse response,String userAppNum){
		AppCommonModel model=null;
		try {
			model=appUserStudentCartService.getUserStudentCart(userAppNum);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("/app/userStudent/list:"+e.getMessage());
			model = new AppCommonModel(-1,e.getMessage());
		}
		super.writeJsonDataApp(response, model);
	}

}
