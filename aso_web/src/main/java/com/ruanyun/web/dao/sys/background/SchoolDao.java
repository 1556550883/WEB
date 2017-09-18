package com.ruanyun.web.dao.sys.background;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TSchool;

@Repository("schoolDao")
public class SchoolDao extends BaseDaoImpl<TSchool> {

	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 功能描述:查询
	 * @author zhujingbo  
	 * @param page
	 * @param bean
	 * @param user
	 * @return
	 */
	public Page<TSchool> queryPage(Page<TSchool> page,TSchool bean){
		StringBuffer hql = new StringBuffer("from TSchool where 1=1");
		
		if(EmptyUtils.isNotEmpty(bean)){
			hql.append(SQLUtils.popuHqlLike("schoolName", bean.getSchoolName()));
			if(EmptyUtils.isNotEmpty(bean.getSchoolIds())){
				hql.append(" and schoolId not in("+SQLUtils.sqlForIn(bean.getSchoolIds())+")");
			}
		}
				
		hql.append(" order by schoolId DESC");
		return hqlDao.queryPage(page, hql.toString());
	}
	
	/**
	 * 功能描述:获取学校列表
	 * @author cqm  2016-12-19 下午01:44:00
	 * @param school
	 * @return
	 */
	public List<TSchool> getSchoolList(TSchool school){
		StringBuffer sql=new StringBuffer("select * from t_school where 1=1");
		if(school != null){
			sql.append(SQLUtils.popuHqlLike("school_name", school.getSchoolName()));
		}
		return sqlDao.getAll(TSchool.class, sql.toString());
		
	}
	
	
	/**
	 * 
	 * 功能描述:获取省份
	 * @param provinceCode
	 * @return
	 */
	public List<TSchool> getAllschool(Integer  schoolId ){
		StringBuffer sql=new StringBuffer(" SELECT school_id, school_name from t_school WHERE 1=1");
		if (EmptyUtils.isNotEmpty(schoolId)) {
			sql.append(" and  city='"+schoolId+"'");
		}
		return sqlDao.getAll(TSchool.class, sql.toString());
	}

	/**
	 * 功能描述:将TSchool封装到map中  key：shcoolId  value ：TSchool
	 * @author wsp  2017-1-9 下午07:17:16
	 * @param shcoolIds
	 * @return
	 */
	public Map<String, TSchool> getSchoolBySchoolId(String shcoolIds) {
	final HashMap<String, TSchool> map = new HashMap<String, TSchool>();
			
			String sql ="select * from t_school where school_id in ("+SQLUtils.sqlForIn(shcoolIds)+")";
			jdbcTemplate.query(sql, new RowCallbackHandler(){
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					int schoolId=rs.getInt("school_id");
					TSchool school=new TSchool();
					school.setSchoolId(schoolId);
					school.setSchoolName(rs.getString("school_name"));
					map.put(schoolId+"",school);
				}
			});
			return map;
		}
	
}
