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
import com.ruanyun.web.model.sys.TCity;


/**
 *@author feiyang
 *@date 2016-3-16
 */
@Repository
public class CityDao extends BaseDaoImpl<TCity>{
	
	@Autowired
	@Qualifier("jdbcTemplate")
	 private JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	 * 功能描述:根据省份CODE 获取所有城市
	 * @param provinceCode
	 * @return
	 *@author feiyang
	 *@date 2016-3-16
	 */
	public List<TCity> getAllcity(String provinceCode ){
		StringBuffer sql=new StringBuffer(" SELECT * from t_city WHERE 1=1");
		if (EmptyUtils.isNotEmpty(provinceCode)) {
			sql.append(" and  province_code='"+provinceCode+"'");
		}
		return sqlDao.getAll(TCity.class, sql.toString());
	}
	
	/**
	 * 功能描述:将TCity封装到map中  key：cityCode  value ：TCity
	 * @author wsp  2017-1-9 下午06:36:10
	 * @param provinces
	 * @return
	 */
	public Map<String, TCity> getCityByCityCodes(String cityCodes) {
		final Map<String,TCity> map = new HashMap<String, TCity>();
		
		String sql ="select * from t_city where city_code in ("+SQLUtils.sqlForIn(cityCodes)+")";
		jdbcTemplate.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String cityCode=rs.getString("city_code");
				TCity city=new TCity();
				city.setCityCode(cityCode);
				city.setCityName(rs.getString("city_name"));
				city.setProvinceCode(rs.getInt("province_code"));
				map.put(cityCode, city);
			}
		});
		return map;
	}
}
