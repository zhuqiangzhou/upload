<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style type="text/css">
.divForm {
	position: absolute; 
	width: 800px;
	height: 300px;
	
	text-align: center; 
	top: 50%;
	left: 50%;
	margin-top: -100px;
	margin-left: -350px;
}
.divForm table {border:1px solid #C0C0C0} 
.divForm table td {border:1px solid #C0C0C0} 
</style>
</head>
<body>
<div class="divForm">
<table border="1">
	<tr>
		<td>
			${message}
		</td>
	</tr>
	<tr>
		<td align="left">
			文件路径：${realSavePath}
		</td>
	</tr>
	<tr>
		<td align="left">
		release路径:
		<c:forEach var="list" items="${list}">
		${contentPath}${list}&nbsp;&nbsp;
		</c:forEach>
		</td>
	</tr>
	<tr>
		<td>
			<a href="${pageContext.request.contextPath}/jsonIndex.do">返回更新App版本信息页面</a>
		</td>
		</tr>
		<tr>
		<td>
			<a href="${pageContext.request.contextPath}/index.do">返回上传移动App安装包页面</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="${pageContext.request.contextPath}/zengliangIndex.do">返回上传App热更新包页面</a>
		</td>
		</tr>
		
	</table>
</div>
</body>
</html>