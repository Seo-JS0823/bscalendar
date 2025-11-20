<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <div id="member" onclick="gomypage()">마이페이지 가기</div>
    
    <script>
	    let gomypage = () => {
	        window.location.href = '/member/mypage';
	    }
    </script>
</body>
</html>