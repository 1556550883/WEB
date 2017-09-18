package com.ruanyun.web.service.background;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.CommonUtils;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.ProvinceDao;
import com.ruanyun.web.dao.sys.background.SchoolDao;
import com.ruanyun.web.model.TSchool;
import com.ruanyun.web.model.sys.TCity;
import com.ruanyun.web.model.sys.TProvince;
import com.ruanyun.web.service.sys.CityService;
@Service("schoolService")
public class SchoolService extends BaseServiceImpl<TSchool> {
	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private ProvinceDao provinceDao;//省份
	@Autowired
	private CityService cityService;//市
		/**
		 * 学校查看（后台）带分页	
		 * @param page
		 * @param info
		 * @return
		 */
		public Page<TSchool> pageSql(Page<TSchool> page, TSchool info) {
			Page<TSchool> _page = schoolDao.queryPage(page,  info);
				if(EmptyUtils.isNotEmpty(info) && EmptyUtils.isNotEmpty(info.getCommonNumName())){
					String provinces=CommonUtils.getAttributeValue(TSchool.class, _page.getResult(), "province");
					if(EmptyUtils.isNotEmpty(provinces)){
						Map<String,TProvince> provinceMap=provinceDao.getProvinceByprovinces(provinces);
						CommonUtils.setAttributeValue(TSchool.class,  _page.getResult(), provinceMap, "province", "tProvince");
					}
					String citys=CommonUtils.getAttributeValue(TSchool.class, _page.getResult(), "province");
					if(EmptyUtils.isNotEmpty(provinces)){
						Map<String,TCity> cityMap=cityService.getCityByCityCodes(citys);
						CommonUtils.setAttributeValue(TSchool.class,  _page.getResult(), cityMap, "province", "tCity");
					}
				}
				return _page;
			}
		
		
		/**
		 * 功能描述:修改和添加
		 * @author zhujingbo  
		 * @param bean
		 * @return
		 */
		public Integer saveOrUpdate(TSchool bean,HttpServletRequest request) {
			if (EmptyUtils.isNotEmpty(bean.getSchoolId())) {
				TSchool school = get(TSchool.class,"schoolId", bean.getSchoolId());
				BeanUtils.copyProperties(bean,school, new String[]{ "schoolId"});
				update(school);
			} else {
				save(bean);
			}
		return 1;
	 }
		
		/**
		 * 功能描述:查询学校列表
		 * @author cqm  2016-12-19 下午01:45:52
		 * @param school
		 * @return
		 */
		public List<TSchool> getSchoolList(TSchool school) {
			return schoolDao.getSchoolList(school);
		}
		
		/**
		 * 
		 * @param schoolId
		 * @return
		 */
		public List<TSchool> getAllschool(Integer  schoolId){
			return schoolDao.getAllschool(schoolId);
		}


		/**
		 * 功能描述:将TSchool封装到map中  key：shcoolId  value ：TSchool
		 * @author wsp  2017-1-9 下午07:17:16
		 * @param shcoolIds
		 * @return
		 */
		public Map<String, TSchool> getSchoolBySchoolId(String shcoolIds) {
			if(EmptyUtils.isEmpty(shcoolIds))
				return null;
			return schoolDao.getSchoolBySchoolId(shcoolIds);
		}
}
