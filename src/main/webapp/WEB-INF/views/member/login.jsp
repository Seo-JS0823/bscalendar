<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/reset.css">
	<title>BS Calendar</title>
<style>
	.container {
		width: 100%;
		height: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.login {
		width: 50rem;
		height: 40rem;
		background: #FFFAFA;
		border-radius: 1rem;
		border: 1px solid #ccc;
		box-shadow: 0 0 3px #ccc;
		overflow: hidden;
	}
	.login-header {
		width: 100%;
		height: 5rem;
		background: #6495ed;
		text-align: center;
		align-content: center;
		color: white;
	}
	.login-input {
		width: 100%;
		height: calc(100% - 10rem);
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 0 1rem;
	}
	.login-logo {
		width: 8rem;
		height: 8rem;
		padding-left: 1rem;
		background-image: linear-gradient(135deg, #6495ed, #6445edcc);
		border: 1px solid #ccc;
		border-radius: 2rem;
		margin: 1rem 0;
		text-align: left;
		text-shadow: 0 0 3px #fff;
		align-content: center;
		font-size: 1.5rem;
		color: white;
	}
	.input-box {
		width: 100%;
		height: 6rem;
		display: flex;
		align-items: center;
		gap: 1rem;
		margin: 1rem 0;
	}
	.input-box label {
		display: inline-block;
		width: 12rem;
		height: 4.5rem;
		font-size: 1.5rem;
		text-align: center;
		align-content: center;
		border: 1px solid #ccc;
		border-radius: 1rem;
		background: white;
	}
	.input-box input[type=text],
	.input-box input[type=password] {
		width: calc(100% - 13rem);
		height: 4.5rem;
		border-radius: 1rem;
		border: 1px solid #ccc;
	}
	.input-box input[type=text]:focus,
	.input-box input[type=password]:focus {
		border: 1px solid #6495ed;
		box-shadow: 0 0 3px #ccc;
	}
	.login-btns {
		width: 100%;
		height: 5rem;
		display: flex;
		align-items: center;
		justify-content: center;
		gap: 1rem;
	}
	.login-btns button {
		width: 40%;
		height: 4rem;
		font-size: 2rem;
		border-radius: 1rem;
	}
	.login-btns button:hover {
		transform: scale(1.03);
		box-shadow: 0 0 3px #666;
	}
	.login-btns button:nth-of-type(1) { background: #6495ed; color: white; }
	.login-btns button:nth-of-type(2) { background: #6465ed; color: white; }
</style>
</head>
<body>
	<div class="container">
		<div class="login">
			<div class="login-header">
				<h1>BooSeong Calendar Login</h1>
			</div>
			<div class="login-input">
				<div class="login-logo">Boo<br>Seong</div>
				<div class="input-box">
					<label for="memberId">아이디</label><input type="text" id="memberId">
				</div>
				<div class="input-box">
					<label for="memberPwd">비밀번호</label><input type="password" id="memberPwd">
				</div>
			</div>
			<div class="login-btns">
				<button onclick="joinLocation()">회원가입</button>
				<button onclick="login()">로그인</button>
			</div>
		</div>
	</div>
<script>
	
	function login() {
		const memberId = document.getElementById('memberId').value;
		const memberPwd = document.getElementById('memberPwd').value;
		
		if(!memberId || !memberPwd) {
			alert('아이디와 비밀번호를 입력하세요.')
			return;
		}
		
		const member = {
				'mem_id':memberId,
				'mem_pwd':memberPwd
		}
		console.log(member);
		
		const url = '/api/member/login';
		fetch(url, {
			method: 'post',
			headers: {
				'Content-Type':'application/json'
			},
			body: JSON.stringify(member)
		})
		.catch(err => console.err(err))
		.then(response => response.json())
		.then(data => {
			let token = data.token;
			let refreshToken = data.refresh_token;
			localStorage.setItem('calendarToken', token);
			localStorage.setItem('refresh_token', refreshToken);
			
			const url = data.locationUrl;
			window.location.href = url;
		})
		
	}
	
	function joinLocation() {
		const url = '/join';
		window.location.href = url;
	}
	
</script>
</body>
</html>