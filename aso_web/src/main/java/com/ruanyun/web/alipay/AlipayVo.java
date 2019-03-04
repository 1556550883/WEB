package com.ruanyun.web.alipay;

public class AlipayVo {
	private String out_biz_no;//商户转账唯一订单号。发起转账来源方定义的转账单据ID，用于将转账回执通知给来源方。 
	private String payee_type;//收款方账户类型。可取值： ALIPAY_USERID 2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。	
	private String payee_account;//收款方账户。与payee_type配合使用
	private String amount;//转账金额，单位：元。
	private String payee_real_name;//收款方真实姓名
	private String remark;//转账备注（支持200个英文/100个汉字）
	public String getOut_biz_no() {
		return out_biz_no;
	}
	public void setOut_biz_no(String out_biz_no) {
		this.out_biz_no = out_biz_no;
	}
	public String getPayee_type() {
		return payee_type;
	}
	public void setPayee_type(String payee_type) {
		this.payee_type = payee_type;
	}
	public String getPayee_account() {
		return payee_account;
	}
	public void setPayee_account(String payee_account) {
		this.payee_account = payee_account;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayee_real_name() {
		return payee_real_name;
	}
	public void setPayee_real_name(String payee_real_name) {
		this.payee_real_name = payee_real_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
