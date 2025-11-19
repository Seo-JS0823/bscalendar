<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인</title>
	<link rel="stylesheet" href="/css/member.css" />
</head>
<body onload = "init()">
    <div class="wrap">
        <div class="head">Log In</div>
        <div class="box"><input type="text" name="mem_id" placeholder="아이디" /></div><div></div>
        <div class="box"><input type="password" name="mem_pwd" placeholder="패스워드" /></div><div></div>
        <button type="button" id="btnLogin" onclick="loginCheck()">로그인</button>
    </div>
    
    <script>
    let init = () => {
    	let el, els = document.querySelectorAll('.box');
    	let btnLogin = document.getElementById('btnLogin');
    	
    	el = els[0].children[0];
    	el.onkeyup = e => {
    		let el = e.target;
    		
    		if(el.value.length != 0){
                let msg = el.parentElement.nextElementSibling;
                msg.innerText = ''; 
                btnLogin.disable = false
            }
    		
    	}
    	
    	el = els[1].children[0];
    	el.onkeyup = e => {
    		let el = e.target;
    		
    		if(el.value.length != 0){
                let msg = el.parentElement.nextElementSibling;
                msg.innerText = ''; 
                btnLogin.disable = false
            }
    	}
    }
    
    let loginCheck = () => {
   	   let el, els = document.querySelectorAll('.box');
   	   let btnLogin = document.getElementById('btnLogin'); // 로그인 버튼 활성 or 비활성화
   	    
   	   el = els[0].children[0];
   	   if (el.value.length == 0) {
   	      let msg = el.parentElement.nextElementSibling;
   	      msg.innerText = '아이디를 입력해주세요.'; 
   	      btnLogin.disable = true;
   	       
   	      return false;
   	   }
   	   
   	   el = els[1].children[0];
   	   if (el.value.length == 0) {
   	       let msg = el.parentElement.nextElementSibling;
   	       msg.innerText = '패스워드를 입력해주세요.'; 
   	       btnLogin.disable = true;
   	       
   	       return false;
   	   }

   	  doLogin();
   	  return true;
   	} // End of loginCheck()

   	let doLogin = () => {
   	    let data = {}, el = {}, els = document.querySelectorAll('.box');
   	    el.mem_id  = els[0].children[0]
   	    el.mem_pwd  = els[1].children[0]
   	   
   	    data.mem_id = el.mem_id.value.trim();
   	    data.mem_pwd = el.mem_pwd.value.trim();
   	    
   	    params = {
   	        method: "POST",
   	        headers: {
   	            "Content-Type" : "application/json",
   	        },
   	        body:JSON.stringify(data)
   	    }
   	    fetch("/api/member/login", params)
   	    .catch(error => console.dir(error))
   	    .then(response => response.json())
   	    .then(data => {
   	    	console.log(data.token)
   	    	
   	        if(data.result === "ok") {
   	        	localStorage.setItem('token', data.token);
   	        	location.href="/project/list"; //** ===== 로그인 후 메인페이지로 이동해야 함 ** //
   	        }
   	        else {
   	        	el =  els[1].children[0];
   	        	let msg = el.parentElement.nextElementSibling;
   	        	msg.innerText = '로그인 정보가 일치하지 않습니다.'; 
   	        }
   	    })
   	} // End of goLogin()
    </script>
</body>
</html>