package com.ruanyun.common.tags;
import java.io.IOException;
import java.util.Date;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.common.utils.TimeUtil;

/**
 * 日期时间显示标签<br>
 * 支持时间字符串 + 格式方式进行转换<br>
 * 默认是时间字符串时 yyyyMMddHHmmss 转换为 yyyy-MM-dd HH:mm:ss<br>
 * @author qs
 * @version $Id: ShowDateStrTag.java 16562 2010-03-22 11:13:26Z linsun $
 */
public class ShowDateTag extends TagSupport {
	private static final long serialVersionUID = -5554013594331864055L;
	/**
	 * 格式化的值
	 */
	private Object date;
	/**
	 * 值的类型 Date，String
	 */
	private String dateType="Date";
	/**
	 * String 类型的格式
	 */
	private String fromFmt = SysCode.DATE_FORMAT_STR_L;
	/**
	 * 格式化后的格式
	 */
	private String toFmt = SysCode.DATE_FORMAT_STR_L;

	public int doStartTag() throws JspException {
		JspWriter out = (JspWriter) pageContext.getOut();
		try {
			if (EmptyUtils.isNotEmpty(date)) {
				String sb = "";
				if ("Date".equals(dateType)) {

					sb = TimeUtil.doFormatDate((Date) date, toFmt);
				} else if ("String".equals(dateType)) {
					sb = TimeUtil.doFormatDate(TimeUtil.doFormatDate(date
							.toString(), fromFmt), toFmt);
				}
				out.print(sb);
			}

		} catch (IOException e) {
		}
		return SKIP_BODY;
	}

	
	public Object getDate() {
		return date;
	}

	public void setDate(Object date) {
		this.date = date;
	}


	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getFromFmt() {
		return fromFmt;
	}

	public void setFromFmt(String fromFmt) {
		this.fromFmt = fromFmt;
	}

	public String getToFmt() {
		return toFmt;
	}

	public void setToFmt(String toFmt) {
		this.toFmt = toFmt;
	}
	
	
}
