/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-16
 */
package com.ruanyun.web.dao.sys;

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
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.sys.TProvince;

/**
 *@author feiyang
 *@date 2016-3-16
 */
@Repository
public class ProvinceDao extends BaseDaoImpl<TProvince>{

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	/**
	 * 
	 * 功能描述:获取省份
	 * @param provinceCode
	 * @return
	 *@author feiyang
	 *@date 2016-3-16
	 */
	public List<TProvince> getAllprovice(String provinceCode ){
		StringBuffer sql=new StringBuffer(" SELECT * from t_province WHERE 1=1");
		if (EmptyUtils.isNotEmpty(provinceCode)) {
			sql.append(" and  province_code='"+provinceCode+"'");
		}
		return sqlDao.getAll(TProvince.class, sql.toString());
	}
	
	/**
	 * 功能描述:将TProvince封装到map中  key：provinceCode  value ：TProvince
	 * @author wsp  2017-1-9 下午06:36:10
	 * @param provinces
	 * @return
	 */
	public Map<String, TProvince> getProvinceByprovinces(String provinces) {
		final Map<String,TProvince> map = new HashMap<String, TProvince>();
		if(EmptyUtils.isEmpty(provinces))return map;
		
		String sql ="select * from t_province where province_code in ("+SQLUtils.sqlForIn(provinces)+")";
		jdbcTemplate.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String provinceCode=rs.getString("province_code");
				TProvince province=new TProvince();
				province.setProvinceCode(provinceCode);
				province.setProvinceName(rs.getString("province_name"));
				map.put(provinceCode, province);
			}
		});
		return map;
	}
	
	

}
