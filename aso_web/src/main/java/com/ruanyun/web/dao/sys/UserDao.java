package com.ruanyun.web.dao.sys;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.sys.TUser;


@Repository("userDao")
public class UserDao extends BaseDaoImpl<TUser>{

	@Override
	protected String queryPageSql(TUser tuser, Map<String, Object> params) {
		
		StringBuffer sql = new StringBuffer("from TUser where 1=1 and userStatus=1");
		if(tuser!=null){
			    sql.append(SQLUtils.popuHqlLike("userName", tuser.getUserName(),params));
			
				sql.append(SQLUtils.popuHqlLike("loginName",tuser.getLoginName(),params));
				sql.append(SQLUtils.popuHqlEq("userSex",tuser.getUserSex(),params));
				sql.append(SQLUtils.popuHqlMin("createDate",tuser.getCreateDate(),params));
				sql.append(SQLUtils.popuHqlMax("createDate",tuser.getEndDate(),params));
			
		}
		sql.append(" ORDER BY userId ASC");
		return sql.toString();
	}
	
	/**
	 * 功能描述:ajax判断登录名称是否重复
	 *
	 * @author L H T  2013-11-26 下午06:24:41
	 * 
	 * @param loginName 登录名称
	 * @return
	 */
	public TUser getAjaxLoginName(String loginName){
		String sql = "from TUser where loginName = ?";
		return hqlDao.get(sql.toString(), loginName);
	}
	
	public int getUserCount(Integer orgId){
		String sql = "select count(*) from t_user where org_id = ?";
		return sqlDao.getCount(sql, orgId);
		
	}
	/**
	 * 功能描述:通过用户id查询用户的名称
	 *
	 * @author L H T  2013-12-2 下午02:30:11
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserNameById(Integer userId){
		return hqlDao.get("select loginName from TUser where userId=? ", userId);
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
		return hqlDao.getAll("from TUser where orgId=? and userStatus=1", orgId);
	}
	/**
	 * 功能描述:查询所有用户导出execle
	 *
	 * @author L H T  2013-12-11 下午01:27:02
	 *
	 */
	@SuppressWarnings("unchecked")
	public List exportExcel(){
		StringBuffer sql=new StringBuffer("SELECT  tu.*,td.item_name item_sex,trg.org_name,tus.login_name create_name,");
		sql.append("DATE_FORMAT(tu.create_date,'%Y-%m-%d %H:%i:%s') createdate ");
		sql.append(" FROM t_user tu LEFT JOIN t_dictionary td ON tu.user_sex=td.item_code ");
		sql.append(" LEFT JOIN t_org trg ON tu.org_id=trg.org_id LEFT JOIN t_user tus ON tu.create_code=tus.user_id ");
		sql.append(" WHERE tu.user_status=1 AND td.parent_code='USERSEX' ");
		return sqlDao.getAll(sql.toString());
	}
}
