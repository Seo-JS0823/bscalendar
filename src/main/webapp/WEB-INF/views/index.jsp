<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body onload="getData()">
    <div onclick="gomypage()"><div id="memId"></div>마이페이지 가기</div>
    <div onclick="logout()">로그아웃</div>
    
    <script>
        let getData = () => {
        	let el = document.getElementById("memId");
            let token = localStorage.getItem("token");
            let url = "/api/member/getMember";
            
            fetch(url, {
                method: 'POST',
                headers: {
                    "Content-Type" : "application/json",
                    "Authorization": `Bearer \${token}`
                }
            })
            .catch(error => console.error('Error:', error))
            .then(response => response.json())
            .then(data => { 
                result = data;
                el.textContent = result["mem_id"];
               
            })
        } // End of getData() */
        
        let gomypage = () => {
	        window.location.href = '/member/mypage';
	    }
	    
	    let logout = () => {
	    	localStorage.removeItem('token');
	    }
    </script>
</body>
</html>