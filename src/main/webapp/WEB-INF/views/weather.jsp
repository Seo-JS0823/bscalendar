<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>날씨API</title>
    <link rel="stylesheet" href="/css/weather.css" />
    <script src="/js/weather.js"></script>
    <script src="/js/conv_weather.js"></script>
  </head>
  <body onload="init()">
    <div class="wrap">
        <div class="weather">
            <div>
                <div>오늘의 날씨</div>
                <div></div><!--주간 날씨보기 공공포털오류-->
            </div>
            <div>
               <div class="weather-icon"><img></div>
               <div class="weather-detail">
                    <div class="item"></div>
                    <div class="item"></div>
                    <div class="item"></div>
                    <div class="item"></div>
                    <div class="item"></div>
                    <div class="item"></div>
                    <div class="item"></div>
                    <div class="item"></div>
               </div>
            </div>
        </div>
    </div>
  </body>
</html>