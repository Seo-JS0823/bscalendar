<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="/css/reset.css">
  <link rel="stylesheet" href="/css/layout.css">
  <title>부성카렌다</title>
</head>
<body>
<%@ include file="../layout/nav.jsp" %>
<div class="mypage-container">
	<div class="sosocProject">
		<h1>소속 프로젝트</h1>
	</div>
</div>
<script>
	/* 프로젝트 리스트 API */
	const projectListUrl = '/api/project/' + getTokenFromInfo('id');
	fetch(projectListUrl)
	.then(response => {
		const status = response.status;
		if(status === 400) {
			// TODO: 소속된 프로젝트가 없을 경우
			const sosocProject = document.getElementsByClassName('sosocProject')[0];
			const message = document.createElement('h2');
			message.textContent = '소속된 프로젝트가 존재하지 않습니다.';
			sosocProject.appendChild(message);
			return null;
		}
		return response.json();
	})
	.then(data => {
		console.log(data);
	})
</script>
</body>
</html>