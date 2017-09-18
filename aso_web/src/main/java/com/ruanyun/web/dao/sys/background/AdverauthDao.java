package com.ruanyun.web.dao.sys.background;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TAdverAuth;
@Repository("adverauthDao")
public class AdverauthDao extends BaseDaoImpl<TAdverAuth> {

	public Page<TAdverAuth> getList(Page<TAdverAuth>page, TAdverAuth t) {
		StringBuffer sql = new StringBuffer("select * from t_adver_auth where 1=1 ");
		
		if (EmptyUtils.isNotEmpty(t)) {
			sql.append(SQLUtils.popuHqlEq("commonNum", t.getCommonNum()));
			sql.append(SQLUtils.popuHqlEq("commonAuthNum", t.getCommonAuthNum()));
			sql.append(SQLUtils.popuHqlEq("auth_type", t.getAuthType()));
			sql.append(SQLUtils.popuHqlEq("common_type", t.getCommonType()));
		}
		sql.append(" order by auth_id ASC");
		return sqlDao.queryPage(page,TAdverAuth.class, sql.toString());
		}
}
