/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.dao.sys.background;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TUserScore;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Repository("userScoreDao")
public class UserScoreDao extends BaseDaoImpl<TUserScore>{
	@Override
	protected String queryPageSql(TUserScore t, Map<String, Object> params) {
		StringBuffer sql=new StringBuffer(" from TUserScore where 1=1 ");
		return sql.toString();
	}
	/**
	 * 
	* 手机端接口:获取排行榜
	 * @param page
	 * @param info
	 * @param type 1/2/3  收益/收徒数量/累计中奖
	 * @return
	 *@author feiyang
	 *@date 2016-1-20
	 */
	public Page<TUserScore> pageSql(Page<TUserScore>page,TUserScore info,Integer type){
		StringBuffer sql=new StringBuffer(" SELECT tus.*,tua.user_nick ,tua.head_img ,tul.level_name");
		sql.append(" from t_user_score  tus INNER JOIN t_user_app tua ON tus.user_num=tua.user_num");
		sql.append(" INNER JOIN t_user_level tul ON tus.user_level_num=tul.level_num");
		if(type==1){
			sql.append(" ORDER BY tus.score_sum DESC");
		}else if (type==2) {			
			sql.append(" ORDER BY tus.apprentice_count DESC");	
		}else if(type==3) {
			sql.append("  ORDER BY tus.red_package_score_count DESC");	
		}
		return sqlDao.queryPage(page, TUserScore.class, sql.toString());
	}
	
	/**
	 * 
	 * 功能描述:根据用户名获取用户积分信息
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-11
	 */
	public TUserScore getScore(String userNum){
		StringBuffer sql=new StringBuffer(" SELECT * FROM t_user_score WHERE user_num='"+userNum+"'");
		return sqlDao.get(TUserScore.class, sql.toString());
	}
	
	/**
	 * 功能描述: 根据用户编号 获取用户分数账号信息  格式 为 'US_0001','US_0002';
	 *
	 * @author yangliu  2016年1月18日 下午8:45:05
	 * 
	 * @param userNums 'US_0001','US_0002';
	 * @return
	 */
	public List<TUserScore> getScoreListByUserNums(String userNums){
		StringBuffer sql=new StringBuffer(" SELECT * FROM t_user_score WHERE user_num in ("+userNums+")");
		return sqlDao.getAll(TUserScore.class, sql.toString());
	}
	
	/**
	 * 功能描述:清楚积分
	 *
	 * @author yangliu  2016年4月7日 下午3:52:51
	 * 
	 * @return
	 */
	public int clearUserScoreDay(){
		String sql="UPDATE t_user_score SET score_day=0,apprentice_count_day=0,apprentice_score_day=0";
		return sqlDao.execute(sql);
	}
	
	
	public int updateApprentice(String usernum, int count)
	{
		String sql="UPDATE t_user_score SET apprentice_count= "+count+" where user_num = '"+usernum+ "'";
		return sqlDao.execute(sql);
	}
	/**
	 * 功能描述: 清楚红包数量
	 *
	 * @author yangliu  2016年4月7日 下午3:53:03
	 * 
	 * @return
	 */
	public int clearUserRedPackageCount(){
		String sql="UPDATE t_user_score SET red_package_score_day=0";
		return sqlDao.execute(sql);
	}
	
	
	public TUserScore getUserScoreByUserAppId(String userAppIds) 
	{
		final TUserScore user = new TUserScore();
		String sql ="select * from t_user_app where user_app_id in ("+SQLUtils.sqlForIn(userAppIds)+")";
		jdbcTemplate.query(sql, new RowCallbackHandler()
		{
			@Override
			public void processRow(ResultSet rs) throws SQLException 
			{
				user.setUserScoreId(rs.getInt("user_score_id"));
				user.setUserNum(rs.getString("user_num"));
				user.setScoreDay(rs.getFloat("score_day"));
				user.setScore(rs.getFloat("score"));
				user.setScoreSum(rs.getFloat("score_sum"));
			}
		});
		
		return user;
	}
	
}
