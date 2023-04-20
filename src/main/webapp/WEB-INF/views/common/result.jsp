<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>result</title>
</head>
<body>
<!-- 이 화면은 출력목적이 아니라 result로 온 값을 보고 성공/실패창을 띄우는 용도로만 사용 -->
<!-- 여기에 필요한 데이터는 메시지(성공실패 확인), url -->
	<script type="text/javascript">
		let message ='${result}';
		if(message != ''){
			alert(message);
		}
		location.href='${url}';
	</script>
</body>
</html>