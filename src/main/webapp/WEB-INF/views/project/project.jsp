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
<script src="/js/index.global.min.js"></script>
<script src="/js/calendar.js"></script>
</head>
<body>
<nav class="top-menu-area">

</nav>

<div class="container">
  <div class="content-area">

    <!-- 달력 , 날씨 -->
    <div class="content col">
      <!-- 달력 Layer -->
      <div class="calendar-container">
        <div class="calendar-descript">
          <div>
            <span class="cal-descript green">● 팀 업무</span>
            <span class="cal-descript orange">● 개인 업무</span>
          </div>
        </div>
        <!-- calendar.js -->
        <div id="calendar"></div>
      </div>
      <!-- 날씨 Layer -->
      <div class="weather-container">

      </div>

    </div>

    <!-- 업무 리스트 AJAX -->
    <div class="content col">

      <!-- 업무 리스트 Layer -->
      <div class="worklist-container">
        <div class="worklist-header">
          <div class="worklist-header-project-name">
            <p>TEAM: 귀멸의칼날</p>
            <h1>Work List</h1>
          </div>
          <div class="worklist-header-day">
            <p>2025-11-05 (수)</p>
          </div>
        </div>

        <div class="worklist-content-container">
          <!-- AM -->
          <div class="worklist-content">
            <div class="worklist-content-header">
              <p>미완료 업무 : 1 / 완료 업무 : 1</p>
              <a href="#">업무 등록</a>
            </div>
            <div class="worklist-content-main">
              <table id="worklist" class="worklist-content-table">
                <colgroup>
                  <col style="width: 10%;">
                  <col style="width: 15%;">
                  <col style="width: 15%;">
                  <col style="width: 30%;">
                  <col style="width: 20%;">
                  <col style="width: 10%;">
                </colgroup>
                <tr>
                  <th>작성자</th>
                  <th>수행 시작일</th>
                  <th>수행 종료일</th>
                  <th>업무 내용</th>
                  <th>알람 시간</th>
                  <th>비고</th>
                </tr>
                <tr>
                  <td>서주성</td>
                  <td>25/11/05</td>
                  <td>25/11/07</td>
                  <td>HTML 작성</td>
                  <td>25-11-05 09:10</td>
                  <td class="worklist-complete">완료</td>
                </tr>
                <tr>
                  <td>서주성</td>
                  <td>25/11/07</td>
                  <td>25/11/09</td>
                  <td>JS 작성</td>
                  <td>미설정</td>
                  <td class="worklist-notComplete">미완료</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>

    </div>

  </div>
</div>
</body>
</html>