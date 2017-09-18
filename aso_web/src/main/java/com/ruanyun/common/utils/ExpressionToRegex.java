package com.ruanyun.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 工程名称  :baseweb <br/>
 *
 * 版本说明  : $id:$ <br/>
 *
 * 功能说明: 用于URL通配符转正则表达式过滤
 *
 * <br/>创建说明: 2013-6-7 下午5:08:24 fangcong 创建文件
 *
 * <br/>修改历史:
 *
 * <br/>
 *
 * @author fangcong<br/>
 */
public class ExpressionToRegex {

	/**
	 * 源码取自{@link org.springframework.util.AntPathMatcher.AntPathStringMatcher.GLOB_PATTERN}
	 * 
	 * <br/>原表达式：\/\*\*|\/\*|\?|\*|\{((?:\{[^/]+?\}|[^/{}]|\\[{}])+?)\}<br/>
	 *  
	 * 分析
	 * \/\*\*				匹配/**		2013.6.18新增
	 * \/\*					匹配 /*
	 * |					或者
	 * \?					匹配 ?
	 * |					或者
	 * \*					匹配 *
	 * |					或者
	 * \{					匹配{
	 *   (					分组 \1开始
	 *     (?:				匹配下方两个表达式,不捕获匹配的文本，也不给此分组分配组号
	 *       \{[^/]+?\}		{不等于"/"的字符大于0任意长度懒惰匹配}
	 *       |				或者
	 *       [^/{}]			不等于"/{}"的字符
	 *       |				或者
	 *       \\[{}]			\{}
	 *     )				没组号的匹配分组
	 *     +?				大于0任意长度懒惰匹配
	 *   )					分组\1结束
	 * \}					匹配}
	 */
	private static final Pattern GLOB_PATTERN = Pattern
			.compile("\\/\\*\\*|\\/\\*|\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}");

	private static final String DEFAULT_VARIABLE_PATTERN = "(.*)";

	private Pattern pattern;

	/**
	 * 功能描述: 将通配符转换成正则表达式<br/>
	 * 
	 * 源码取自
	 * {@link org.springframework.util.AntPathMatcher.AntPathStringMatcher.AntPathStringMatcher}
	 * 
	 * @author fangcong 2013-6-7 下午4:55:12
	 * 
	 * @param pattern
	 *            URL通配符 例如：/user/login* 可以匹配
	 *            <ul>
	 *            <li>/user/login</li>
	 *            <li>/user/login/</li>
	 *            <li>/user/loginadmin</li>
	 *            <li>/user/loginadmin/</li>
	 * 
	 * @return pattern
	 */
	public Pattern getRegexPattern(String pattern) {
		if (pattern.charAt(pattern.length() - 1) == '/')
			pattern = pattern.substring(0, pattern.length() - 1);
		StringBuilder patternBuilder = new StringBuilder();
		Matcher m = GLOB_PATTERN.matcher(pattern);
		int end = 0;
		while (m.find()) {
			patternBuilder.append(quote(pattern, end, m.start()));
			String match = m.group();
			if ("/**".equals(match)){
				patternBuilder.append("\\Q/\\E(.+)");
			} else if ("/*".equals(match)) {
				patternBuilder.append("\\Q/\\E([^/]+)");
			} else if ("?".equals(match)) {
				patternBuilder.append('.');
			} else if ("*".equals(match)) {
				patternBuilder.append("([^/]*)");
			} else if (match.startsWith("{") && match.endsWith("}")) {
				int colonIdx = match.indexOf(':');
				if (colonIdx == -1) {
					patternBuilder.append(DEFAULT_VARIABLE_PATTERN);
				} else {
					String variablePattern = match.substring(colonIdx + 1,
							match.length() - 1);
					patternBuilder.append('(');
					patternBuilder.append(variablePattern);
					patternBuilder.append(')');
				}
			}
			end = m.end();
		}
		patternBuilder.append(quote(pattern, end, pattern.length()));
		patternBuilder.append("(/{0,1})");
		this.pattern = Pattern.compile(patternBuilder.toString());
		return this.pattern;
	}

	public boolean match(String pattern, String path) {
		getRegexPattern(pattern);
		return this.pattern.matcher(path).matches();
	}

	public boolean match(String path) {
		return this.pattern.matcher(path).matches();
	}

	private String quote(String s, int start, int end) {
		if (start == end) {
			return "";
		}
		return Pattern.quote(s.substring(start, end));
	}
}
