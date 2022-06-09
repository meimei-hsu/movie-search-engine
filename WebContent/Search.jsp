<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>
<style type="text/css">
body {
	background-image: url('images/bg.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
}

.button {
	position: absolute;
	width: 45px;
	height: 25px;
	font-size: 15px;
	left: 50%;
	top: 50%;
}

.border-style {
	border-radius: 75px/90px;
}

#padding {
	padding: 0px 0px 0px 15px;
}

.circle {
	height: 45px;
	width: 45px;
	background-color: #fff;
	border-radius: 50%;
	display: inline-block;
}
</style>
</head>
<body>
	<form action='${requestUri}' method='get'>
		<div>
			<select class="circle" name="category"
				style='position: absolute; left: 50%; top: 48%; margin-top: -47px; margin-left: -465px;'>
				<option value="" disabled>類別</option>
				<option value=""></option>
				<option value="romance">愛情</option>
				<option value="fantasy">奇幻</option>
				<option value="drama">劇情</option>
				<option value="action">動作</option>
				<option value="comedy">喜劇</option>
				<option value="thriller">驚悚</option>
			</select>
		</div>
		<div>
			<input type='text' class="border-style" id="padding"
				style='font-size: 120%; position: absolute; left: 50%; top: 48%; margin-top: -47px; margin-left: -400px; width: 800px; height: 45px'
				name='keyword' placeholder='請輸入關鍵字' onfocus="placeholder= '' "
				onblur="placeholder='請輸入關鍵字'" />
		</div>
		<div>
			<!-- Notice: 照片來源裡名字後的附檔名要正確 -->
			<input type='image' src="images/loupe-2.png"
				style='position: absolute; width: 37px; height: 37px; left: 50%; top: 50%; margin-top: -55px; margin-left: 368px' />
		</div>
		<div>
			<a href='http://localhost:8080/Fin/TestProject'><img
				src="images/logo.gif"
				style='position: absolute; width: 210px; height: 180px; left: 50%; top: 50%; margin-top: -265px; margin-left: -120px'></a>
		</div>
	</form>
</body>
</html>