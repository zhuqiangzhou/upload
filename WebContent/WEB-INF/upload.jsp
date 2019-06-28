<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>上传移动App安装包</title>
<style type="text/css">
.divForm {
	position: absolute; 
	width: 400px;
	height: 300px;
	
	text-align: center; 
	top: 50%;
	left: 50%;
	margin-top: -100px;
	margin-left: -190px;
}
.divForm table {border:1px solid #C0C0C0} 
.divForm table td {border:1px solid #C0C0C0} 
</style>
</head>

<body>
<div class="divForm">
	<form action="${pageContext.request.contextPath}/upload/UploadAction" method="post" enctype="multipart/form-data">
	<table border="1">
		
		
		<span>上传移动app包：.apk或者.ipa</span>
	
		<tr>
			<td align="left">
				<span>客户：</span>
				<select name="customer" id="customer">
					<c:forEach var="kehulist" items="${kehulist}">
						<option value="${kehulist.code}">[${kehulist.code}]${kehulist.name}</option>
					</c:forEach>
				</select>
			</td> 
		</tr>
		<tr>
			<td align="left">
				<span>类型：</span>
				<input name="type" type="radio" value="test" checked="checked" />本部测试
				<input name="type" type="radio" value="uat" />现场测试<br />
			</td>
		</tr>
		<tr>
			<td >
				<span>安装包：</span>
				<input id="file" type="file" name="file" required="required" />
			</td>
			<td>
				<input	type="submit" value="上传" />
			</td>
		</tr>
		<tr>
			<td align="left">
				<a target="_blank" href="${pageContext.request.contextPath}/jsonIndex.do">更新App版本信息</a>
			</td>
		</tr>
		<tr>
			<td align="left">
				<a target="_blank" href="${pageContext.request.contextPath}/zengliangIndex.do">上传App热更新包</a>
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>