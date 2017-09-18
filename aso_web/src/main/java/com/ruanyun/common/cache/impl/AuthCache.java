package com.ruanyun.common.cache.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ruanyun.common.cache.SystemCacheService;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.model.sys.TAuthority;
@Service("authCache")
public class AuthCache implements SystemCacheService {
	
	public static List<TAuthority> menuList=new ArrayList<TAuthority>();
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getCacheName() {
		return "加载TAuthority结束时间为:"+TimeUtil.getCurrentDay(SysCode.DATE_FORMAT_NUM_L);
	}

	@Override
	public void run() {
		update();
	}

	@Override
	public void update() {
		menuList=queryAll();
	}
	
	private List<TAuthority> queryAll(){
		String sql="SELECT * FROM t_authority ta WHERE ta.`auth_type`='1'";
		return jdbcTemplate.query(sql,  new BeanPropertyRowMapper<TAuthority>(TAuthority.class));
	}
	
	/**
	 * 获取url对应的权限对象
	 * @param startUrl
	 * @return
	 */
	public static List<TAuthority> getMenuByUrl(String startUrl){
		List<TAuthority> list = new ArrayList<TAuthority>();
		Iterator<TAuthority> it=menuList.iterator();
		TAuthority auth=null;
		while(it.hasNext()){
			auth=it.next();
			if(auth.getAuthUrl().startsWith(startUrl))
				list.add(auth);
		}
		return list;
	}
	
	/**
	 * 获取 url对应的code
	 * @param startUrl
	 * @return
	 */
	public static String getAuthCodeByUrl(String startUrl){
		startUrl=startUrl.substring(1);
		TAuthority auth=null;
		Iterator<TAuthority> it=menuList.iterator();
		StringBuffer authStr=new StringBuffer();
		while(it.hasNext()){
			auth=it.next();
			if(auth.getAuthUrl().startsWith(startUrl))
				authStr.append(","+auth.getAuthCode());
		}
		return authStr.length()>0?authStr.substring(1):null;
	}
	
	public static void main(String[] args) {
		System.out.println("123".startsWith("123"));
	}
}
