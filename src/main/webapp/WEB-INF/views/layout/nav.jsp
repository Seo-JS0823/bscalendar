<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="top-menu-area">
	<div class="menu-item">
		<button onclick="homeLocation()">BS Calendar</button>
	</div>
	<div class="menu-item">
		<button onclick="myPageLocation()">마이페이지</button>
	</div>
</nav>
<script>
	/* nav functions */
	function homeLocation() {
		const url = "/project/list";
		window.location.href = url;
	}
	function myPageLocation() {
		const url = "/mypage";
		window.location.href = url;
	}
	
	/* JWT Token */
	function getToken() {
		const token = localStorage.getItem('calendarToken');
		return token;
	}
	function getReToken() {
		const reToken = localStorage.getItem('refresh_token');
		return reToken;
	}
	
	/* JWT Token From Member_ID getter */
	function getTokenFromInfo(info) {
		if(!info) {
			console.err('getTokenFromInfo Function Parameter Not Found');
			return;
		}
		
		const token = localStorage.getItem('calendarToken');
		if(!token) {
			console.err('Token Not Found in localStorage');
			return;
		}
		const base64Url = token.split('.')[1];
		const infomation = decodeJwt(base64Url); 
		
		if(info === 'id') {
			info = 'sub';
		}
		
		return infomation[info];
	}
	
	function decodeJwt(base64Url) {
		if(!base64Url) {
			console.error('Token Payload Not Found in getTokenFromInfo Function');
			return;
		}
		
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
		
    return JSON.parse(jsonPayload);
	}
	
	/* 바디에 넣어서 GET 요청 보내는 함수 */
	function authFetchToBody(url, bodyObject, callback, method) {
		console.log("authFetchToBody" + url);
		fetch(url, {
			method: method,
			headers: {
				'Authorization': `Bearer \${getToken()}`,
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(bodyObject)
		})
		.then(response => {
			const status = response.status;
			
			if(status === 401) {
				fetch('/api/member/auth/re', {
					method: 'get',
					headers: {
						'Authorization': `Bearer \${getReToken()}`
					}
				})
				.then(response => {
					const status = response.status;
					if(status === 401) {
						const message = response.json().message;
						alert(message);
						localStorage.removeItem('calendarToken');
						localStorage.removeItem('refresh_token');
						window.location.href = '/';
					} else if(status === 500) {
						const message = response.json().message;
						alert(message);
						window.location.href = '/';
					}
					return response.json();
				})
				.then(data => {
					localStorage.setItem('calendarToken', data.token);
					localStorage.setItem('refresh_token', data.refresh_token);
					callback(data);
				});
			}
			return response.json();
		})
		.then(data => {
			callback(data);
		});
	}
	
	async function authFetch(url, callback, method) {
		const originalRequest = () => fetch(url, {
			method: method,
			headers: {
				'Authorization': `Bearer \${getToken()}`
			}
		});
		
		let response = await originalRequest();
		let status = response.status;
		if(status === 401 || status === 403) {
			const refreshSuccess = await reAccessToken();
			if(refreshSuccess) {
				response = await originalRequest();
				status = response.status;
				
				if(status === 401 || status === 403) {
					window.location.href = '/';
					return;
				}
			}
		} else if (status === 200) {
			const data = await response.json();
			callback(data);
		}
		/*
		fetch(url, {
			method: method,
			headers: {
				'Authorization': `Bearer \${getToken()}`
			}
		})
		.then(response => {
			const status = response.status;
			if(status === 401) {
				
			} else if(status === 403) {
				if(reAccessToken()) {
					
				}
			}
			return response.json();
		})
		.then(data => {
			callback(data);
		})
		*/
	}
	
	function reAccessToken() {
		return fetch('/api/member/auth/re', {
			method: 'get',
			headers: {
				'Authorization': `Bearer \${getReToken()}`
			}
		})
		.then(response => {
			if(!response.ok) {
				window.location.href = '/';
			}
			return response.json();
		})
		.then(data => {
			const newToken = data.token;
			const newReToken = data.refresh_token;
			localStorage.setItem('calendarToken', newToken);
			localStorage.setItem('refresh_token', newReToken);
			return true;
		})
		.catch(error => {
			console.error('Token Refresh 갱신 실패');
			window.location.href = '/';
			return false;
		})
	}
</script>