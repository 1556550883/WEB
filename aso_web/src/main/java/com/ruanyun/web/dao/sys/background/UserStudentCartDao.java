package com.ruanyun.web.dao.sys.background;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TUserStudentCart;
@Repository("userStudentCartDao")
public class UserStudentCartDao extends BaseDaoImpl<TUserStudentCart> {
	/**
	 * 功能描述:查询
	 * @author zhujingbo  
	 * @param page
	 * @param bean
	 * @param user
	 * @return
	 */
	public Page<TUserStudentCart> queryPage(Page<TUserStudentCart> page,TUserStudentCart bean,Integer cartStatus){
		StringBuffer hql = new StringBuffer("from TUserStudentCart where 1=1 ");
		if(EmptyUtils.isNotEmpty(bean)){
			hql.append(SQLUtils.popuHqlEq("cartStatus",cartStatus));
			hql.append(SQLUtils.popuHqlEq("userAppNum",bean.getUserAppNum()));
		}
		hql.append(" order by studentCartId DESC");
		return hqlDao.queryPage(page, hql.toString());
	}
	
	public List<TUserStudentCart> getListStudentCart(String ids){
		if(EmptyUtils.isEmpty(ids))
			return null;
		String hql="from TUserStudentCart where studentCartId in ("+ids+")";
		return hqlDao.getAll(hql);
	}
}
