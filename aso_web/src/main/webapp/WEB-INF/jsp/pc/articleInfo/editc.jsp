<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/WEB-INF/jsp/inc/appbase.jsp"%>
<%@include file="/WEB-INF/jsp/inc/pression.jsp"%>
		<title>本地新闻</title>
	</head>
	<body>
		<section style="background: #FFFFFF;">
			<article class="auto article_content">
				<p>${bean.articleContent}
				</p>
			</article>
		</section>
	</body>
</html>