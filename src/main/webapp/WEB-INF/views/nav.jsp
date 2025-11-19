<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="top-menu-area">
	<div class="menuItem">
		<button onclick="homeLocation()">Project Home</button>
	</div>
	<div class="menuItem">
		<button onclick="myPageLocation()">마이페이지</button>
	</div>
</nav>
<script>
	function homeLocation() {
		const url = '/project/list';
		window.location.href = url;
	}
	function myPageLocation() {
		const url = '/member/mypage';
		window.location.href = url;
	}

	function getTokenFromInfo(info) {
		if(!info) {
			console.err('getTokenFromInfo Function Parameter Not Found');
			return;
		}
		
		const token = localStorage.getItem('token');
		if(!token) {
			console.err('Token Not Found in localStorage');
			return;
		}
		const base64Url = token.split('.')[1];
		const infomation = decodeJwt(base64Url); 
		
		return infomation[info];
	}
	
	function decodeJwt(base64Url) {
		if(!base64Url) {
			console.error('Token Payload Not Found');
			return;
		}
		
		const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
		
		const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
	  	return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
	  }).join(''));
		
		return JSON.parse(jsonPayload);
	}
</script>