<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>마이페이지</title>
  <link rel="stylesheet" href="/css/member.css" />
</head>
<body onload = "init()">
   <div class="wrap">
     <div class="head">마이페이지</div>
     <div class="box"><input type="text" name="mem_name"></div><div></div>
     <div class="box"><input type="text" name="mem_position" ></div><div></div>
     <div class="box"><input type="text" name="mem_depart"></div><div></div>
     <div class="box"><input type="text" name="mem_id" readonly></div><div>* 아이디는 수정불가</div>
     <button type="button" id="btnsend">수정</button>
     <div class="main-link"><a href="/index">메인으로 가기</a></div>
   </div>
   
   <script>
       let result = null;
       let btnsend = document.getElementById("btnsend");
       
	   let init = () => {
		   getData();
		   
		   let els = document.getElementsByClassName("wrap")[0];
           
		   el = els.getElementsByClassName("box")[0].children[0];
           el.onblur = e => {
             let el = e.target;
             let msg = e.target.parentElement.nextElementSibling;
             
             if( el.value.length == 0 ) {
               msg.innerText = '이름을 입력해주세요.';
               btnsend.disabled = true;
             } else {
                 msg.innerText = '';
                 btnsend.disabled = false; }
           }
           
           el = els.getElementsByClassName("box")[1].children[0];
           el.onblur = e => {
             let el = e.target;
             let msg = e.target.parentElement.nextElementSibling;
             
             if( el.value.length == 0 ) {
               msg.innerText = '직급을 입력해주세요.';
               btnsend.disabled = true;
             } else {
                 msg.innerText = '';
                 btnsend.disabled = false; }
           }
           
           el = els.getElementsByClassName("box")[2].children[0];
           el.onblur = e => {
             let el = e.target;
             let msg = e.target.parentElement.nextElementSibling;
             
             if( el.value.length == 0 ) {
               msg.innerText = '소속을 입력해주세요.';
               btnsend.disabled = true;
             } else {
                 msg.innerText = '';
                 btnsend.disabled = false; }
           }
	    } // End of init() */
	   
	   let getData = () => {
		   let url = "/api/member/getMember";
           fetch(url, {
               method: 'GET'
           })
           .catch(error => console.error('Error:', error))
           .then(response => response.json())
           .then(data => { 
               result = data;
               //console.log(result);
               draw();
           })
	   } // End of getData() */
	   
	   let draw = () => {
		   let el, els = document.getElementsByClassName("wrap")[0];
		   let key = ["mem_name", "mem_position", "mem_depart", "mem_id"];
		   
		   for(let i = 0; i < key.length; i++){
			   el = els.getElementsByClassName("box")[i].children[0];
			   //el.textContent = result[key[i]];
			   el.value = result[key[i]];
		   }
	   } // End of draw() */
	   
	   let doEdit = () => {
		    let data = {}, el = {}, els = document.querySelectorAll('.box');
		    el.mem_name  = els[0].children[0]
		    el.mem_position  = els[1].children[0]
	        el.mem_depart  = els[2].children[0]
		    el.mem_id = els[3].children[0]
	       
		    data.mem_name = el.mem_name.value.trim();
	        data.mem_position = el.mem_position.value.trim();
	        data.mem_depart = el.mem_depart.value.trim();
	        data.mem_id = el.mem_id.value.trim();
	        
	        params = {
	            method: "POST",
	            headers: {
	                "Content-Type" : "application/json",
	            },
	            body:JSON.stringify(data)
	        }
	        fetch("/api/member/update", params)
	        .catch(error => console.dir(error))
	        .then(response => response.json())
	        .then(data => {
	            if(data.result === "ok") location.reload();
	            else {
	              console.log(data.result);
	            }
	        })
	   } // End of doEdit() */
	   
	   btnsend.addEventListener('click', doEdit);
   </script>
</body>
</html>