/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-26
 */
package com.ruanyun.web.model;

/**
 *@author feiyang
 *@date 2016-1-26
 */
public class AppGlobalParameterModel {


	private String danWei;// 单位
	private String useSim;// 是否检测SIM卡
	private String packageName;// 包名

	public AppGlobalParameterModel() {

	}

	public AppGlobalParameterModel( String danWei, String useSim,String packageName) {
		this.danWei = danWei;
		this.useSim = useSim;
		this.packageName = packageName;
	}


	public String getDanWei() {
		return danWei;
	}

	public void setDanWei(String danWei) {
		this.danWei = danWei;
	}

	public String getUseSim() {
		return useSim;
	}

	public void setUseSim(String useSim) {
		this.useSim = useSim;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
