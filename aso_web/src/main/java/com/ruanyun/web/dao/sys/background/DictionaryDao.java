package com.ruanyun.web.dao.sys.background;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.sys.TDictionary;
import com.ruanyun.web.model.sys.TUser;

@Repository("dictionaryDao")
public class DictionaryDao extends BaseDaoImpl<TDictionary>{
	
	public Page<TDictionary> queryPage(Page<TDictionary> page,TDictionary activityInfo,TUser user){
		StringBuffer hql = new StringBuffer("from TDictionary where 1=1");
		if(EmptyUtils.isNotEmpty(activityInfo)){
		
		}
		//hql.append(SQLUtils.popuHqlEq("storeNum", user.getStoreNum()));
		hql.append(" order by parentCode orderby DESC");
		return hqlDao.queryPage(page, hql.toString());
	}
	
	/**
	 * 功能描述:根据父类编号分组 
	 * @author wsp  2016-11-24 上午08:57:26
	 * @return
	 */
	public List<TDictionary> getList(){
		String sql = "select  * from t_dictionary where status = 1 group by parent_code order by parent_code DESC";
		return sqlDao.getAll(TDictionary.class, sql);
	}
	
	/**
	 * 功能描述:查询字典表
	 * @author wsp  2016-11-24 上午09:08:31
	 * @param dictionary
	 * @return
	 */
	public List<TDictionary> getList(TDictionary dictionary){
		StringBuffer hql = new StringBuffer("from TDictionary where status = 1");
		if(EmptyUtils.isNotEmpty(dictionary)){
			hql.append(SQLUtils.popuHqlEq("parentCode", dictionary.getParentCode()));
			hql.append(SQLUtils.popuHqlEq("parentName", dictionary.getParentName()));
		}
		hql.append(" order by orderby DESC");
		return hqlDao.getAll(hql.toString());
	}

	/**
	 * 功能描述:将 TDictionary 封装到  map中  key itemCode   value TDictionary
	 * @author wsp  2016-12-10 下午07:37:30
	 * @param parentCode	父类编号
	 * @param itemCodes	编码
	 * @return
	 */
	public Map<Integer, TDictionary> getDictionaryByitemCodes(String parentCode,String itemCodes) {
		final Map<Integer,TDictionary> map = new HashMap<Integer, TDictionary>();
		String sql ="select * from t_dictionary where status=1 and parent_code ='"+parentCode+"' and item_code in ("+SQLUtils.sqlForIn(itemCodes)+")";
		jdbcTemplate.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String itemCode=rs.getString("item_code");
				TDictionary bean = new TDictionary();
				bean.setItemCode(itemCode);
				bean.setItemName(rs.getString("item_name"));
				map.put(Integer.valueOf(itemCode), bean);
			}
		});
		return map;
	}

	/**
	 * 功能描述:获取最大排序值
	 * @author wsp  2016-12-22 下午05:25:54
	 * @param dictionary
	 * @return
	 */
	public int getOrderby(TDictionary dictionary) {
		String sql = "select ifnull(max(orderby),1) from t_dictionary where parent_code='"+dictionary.getParentCode()+"'";
		return sqlDao.getCount(sql.toString());
	}
	
	
}

