<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원가입</title>
  <link rel="stylesheet" href="/css/member.css" />
  <script src="/js/member.js"></script>
</head>
<body onload="init()">
   <div class="wrap">
     <div class="head">회원가입</div>
     <div class="box"><input type="text" name="mem_name" placeholder="이름" /></div><div></div>
     <div class="box"><input type="text" name="mem_position" placeholder="직책" /></div><div></div>
     <div class="box"><input type="text" name="mem_depart" placeholder="부서명" /></div><div></div>
     <div class="box"><input type="text" name="mem_id" placeholder="아이디" /></div><div></div>
     <div class="box"><input type="password" name="mem_pwd" placeholder="비밀번호" /></div><div></div>
     <div class="box"><input type="password" name="mem_repwd" placeholder="비밀번호 확인" /></div><div></div>
     <button type="button" id="btnsend">회원가입</button>
   </div>
</body>
</html>