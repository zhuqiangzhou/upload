<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>上传增量包</title>
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
	<form action="${pageContext.request.contextPath}/upload/UploadZengLiangAction" method="post" enctype="multipart/form-data">
	<table border="1">
		
				上传热更新文件：www_android_uat或www_ios_uat或者www_android_test或者www_ios_test
		
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
			<td align="left">
				<span>版本号</span>
				<input id="version1" name="version" type="text" required="required"/>
			</td>
		</tr>
		<tr>
			<td >
				<span>上传文件：</span>
				<input id="file" type="file" name="file1" required="required"/>
				
			</td>
			
		</tr>
		
		<tr>
			<td>
				<input	type="submit" value="上传" />
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>