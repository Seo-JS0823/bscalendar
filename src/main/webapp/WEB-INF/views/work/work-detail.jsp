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
<nav class="top-menu-area">

</nav>

<div class="container">
  <div class="detail-container">
    <div class="detail-descript">
      <div class="row-box">
        <h1>작성자</h1>
        <p>서주성</p>
      </div>
    </div>

    <div class="detail-content">
      <p class="detail-header">업무 내용</p>
      <textarea class="detail-textarea">TODO: 작성자 본인이 들어오면 수정 / 삭제 / 알람 / 등을 설정할 수 있는 BOX 보이게</textarea>
      <div class="detail-content-check">
        <input type="radio" name="WORKS_HIDE"><label>공유 (팀 업무)</label>
        <input type="radio" name="WORKS_HIDE"><label>비공유 (개인 업무)</label>
        <!--
        <script>
          // TODO: DB에서 공유, 비공유 데이터 가져와서
          // document.getElementById('값').setAttribute('checked', 'true') 설정
        </script>
        -->
        
      </div>
    </div>
  </div>
</div>
</body>
</html>