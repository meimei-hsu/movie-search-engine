<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>
<style type="text/css">
body {
	background-image: url('images/bg0.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
}

#padding {
	padding: 0px 0px 15px 15px;
}

a {
	color: #fff;
	font-size: 30px;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

.border-style {
	border-radius: 90px/90px;
}
</style>
</head>
<body>
<body>
	<!-- style='background-color: #0d3640' -->
	<form action='${requestUri}' method='get'>

		<br> <br> <br> <br> <br> <br>
		<div style='position: absolute; margin-top: 190px; margin-left: 50px;'>
			<%
			String[] info = (String[]) request.getAttribute("info");
			String ps = "關於 [" + info[0] + "] 有 " + info[1] + "筆資料 (類型: " + info[2] + ")";
			%>
			<h4 style="color: #D3D3D3;"><%=ps%></h4> <br> <br>

			<%
			String[][] orderList = (String[][]) request.getAttribute("query");
			for (int i = 0; i < orderList.length; i++) {
				String s = orderList[i][1];
			%>

			<a href='<%=s%>'><%=orderList[i][0]%> </a> <br> <br> <br>
			<%
			}
			%>
		</div>
		<div>
			<a href='http://localhost:8080/Fin/TestProject'><img
				src="images/logo.gif"
				style='position: absolute; width: 116px; height: 100px; left: 50%; top: 50%; margin-top: -300px; margin-left: -530px'></a>
		</div>
		<div>
			<input type='text' class="border-style" id="padding" name='keyword'
				style='font-size: 120%; position: absolute; left: 50%; top: 48%; margin-top: -250px; margin-left: -400px; width: 800px; height: 25px'
				placeholder='請輸入關鍵字' value='<%=request.getParameter("keyword")%>' />
		</div>

	</form>
</body>
</html>