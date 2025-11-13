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
  
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  
</head>
<body>
<nav class="top-menu-area">

</nav>

<div class="container">
  <div class="detail-container">
    <div class="detail-descript">
    	<div class="row-box">
    		<h1>소속 프로젝트</h1>
    		<p>이노스케팀</p>
    	</div>
    	<div class="row-box">
    		<h1>등록일</h1>
    		<p>2025-11-08</p>
    	</div>
      <div class="row-box">
        <h1>작성자</h1>
        <p>서주성</p>
      </div>
    </div>

    <div class="detail-content">
    	<div class="detail-header-area">
	      <p class="detail-header" id="detail-update">
	      	업무 설정 △
	      	<span id="detail-update-descript" style="font-size: 1.2rem;" >
	      		설정을 변경하시려면 업무 설정을 누르세요.
	      	</span>
	      </p>
      </div>
      <div id="detail-update-area" class="opacityClose">
	      <div class="detail-content-check">
	        <input type="radio" name="works_hide"><label>공유 (팀 업무)</label>
	        <input type="radio" name="works_hide"><label>비공유 (개인 업무)</label>
	      </div>
	      <div class="detail-alram">
	      	<button id="setAlramBtn">알람 설정</button>
	      	<input id="setAlram" type="datetime-local" class="detail-setAlram close">
	      	<script>
	      		const detailUpdate = document.getElementById('detail-update');
	      		let btnState = false;
	      		detailUpdate.addEventListener('click', () => {
	      			const detailUpdateArea = document.getElementById('detail-update-area');
	      			const state = detailUpdateArea.className;
    					const detailHeaderArea = document.getElementsByClassName('detail-header-area')[0];
    					
	      			if(state === 'opacityClose') {
	      				detailUpdate.innerHTML = `업무 설정 ▽`;
	      				
	      				detailUpdateArea.classList.remove('opacityClose');
	      				detailUpdateArea.classList.add('opacitOpen');
	      				
	      				const updateBtn = document.createElement('button');
	      				updateBtn.addEventListener('click', () => {
	      					// TODO: 업무 설정 변경 로직
	      					
	      				});
	      				updateBtn.classList.add('work-updateBtn');
	      				updateBtn.textContent = '변경사항 저장';
	      				
      					detailHeaderArea.appendChild(updateBtn);
	      				
	      			} else if(state === 'opacitOpen') {
	      				const updateBtn = document.getElementsByClassName('work-updateBtn')[0];
	      				detailHeaderArea.removeChild(updateBtn);
	      				detailUpdate.innerHTML = `
	      				업무 설정 △
	      				<span id="detail-update-descript" style="font-size: 1.2rem;" >
      	      		설정을 변경하시려면 업무 설정을 누르세요.
      	      	</span>
	      				`;
	      				detailUpdateArea.classList.remove('opacitOpen');
	      				detailUpdateArea.classList.add('opacityClose');
	      			}
	      		});
	      	
	      		const setAlramBtn = document.getElementById('setAlramBtn');
	      		setAlramBtn.addEventListener('click', () => {
	      			const setAlram = document.getElementById('setAlram');
	      			const state = setAlram.className.split(' ')[1];
	      			if(state === 'close') {
	      				setAlram.value = undefined;
	      				setAlram.classList.remove('close');
	      				setAlram.classList.add('open');
	      			} else if(state === 'open') {
	      				setAlram.classList.remove('open');
	      				setAlram.classList.add('close');      				
	      			}
	      		});
	      	</script>
	      </div>
      </div>
      <p class="detail-header">업무 내용</p>
            <textarea class="detail-textarea">TODO: 작성자 본인이 들어오면 수정 / 삭제 / 알람 / 등을 설정할 수 있는 BOX 보이게</textarea>

      <!-- 댓글 수정 부분 -->
      <p class="detail-header">업무 메모</p>
        <button id="btnLoadReplies">댓글 새로고침 (GET)</button>
      <div id="replyList" class="work-reply-area">
      </div>
  
      <hr style="margin: 10px 0;">
      
      <h3>새 댓글 작성 (POST)</h3>
      <div class="work-reply">
	      <textarea id="newReplyComment" placeholder="댓글 내용 입력"></textarea> 
	      <button id="btnCreateReply">메모 등록</button>
      </div>

    </div>
  </div>
</div>

<script>
    const current_works_idx = ${works_idx};

    // 1. [GET] /api/reply/list (댓글 목록 조회)
    function loadReplies() {
        
        if (!current_works_idx) {
            alert("치명적 오류: works_idx를 찾을 수 없습니다. (페이지가 잘못 로드됨)");
            $('#replyList').html("<span style='color: red;'>페이지 로드 오류: works_idx 없음</span>");
            return;
        }
        
        $.ajax({
            url: "/api/reply/list",
            type: "GET",
            data: {
                works_idx: current_works_idx
            },
            success: function(replies) {
                $('#replyList').empty(); 
                
                if (!replies || replies.length === 0) {
                    $('#replyList').html("댓글이 없습니다."); // 이 부분은 팀원 CSS에 맞게 수정 필요할 수 있음
                    return;
                }
                
                replies.forEach(function(reply) {
                    
                    
                    var html = '<div class="col-box">';
                    
                    html += '<p><span class="reply-name">' + reply.mem_name + '</span> ' + reply.reply_regdate + '</p>';
                    html += '<div class="row-box">';
                    
                    html += '<div class="work-reply-member">';
                    html += '<p id="comment-' + reply.reply_idx + '">' + reply.reply_comment + '</p>';
                    html += '</div>';
                    
                    html += '<div class="work-reply-update">';
                    html += '<button onclick="editReply(' + reply.reply_idx + ')">수정</button>';
                    html += '<button onclick="deleteReply(' + reply.reply_idx + ')">삭제</button>';
                    html += '</div>';
                    
                    html += '</div>';
                    html += '</div>'; 
                    
                    $('#replyList').append(html);
                });
            },
            error: function(xhr) {
                alert("댓글 로딩 실패! (F12 콘솔 확인)");
                console.error("댓글 로딩 오류:", xhr.responseText);
            }
        });
    }

    // 2. [POST] /api/reply (새 댓글 생성) - (로직은 v4와 100% 동일)
    $('#btnCreateReply').on('click', function() {
        
        let comment = $('#newReplyComment').val(); // id="newReplyComment"를 찾아가므로 HTML 구조와 상관없이 작동

        if (!comment) {
            alert("댓글 내용을 입력하세요.");
            return;
        }
        
        $.ajax({
            url: "/api/reply",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                works_idx: current_works_idx,
                reply_comment: comment
            }),
            success: function(response) {
                alert("댓글 생성 성공! (ID: " + response.reply_idx + ")");
                $('#newReplyComment').val(''); 
                loadReplies();
            },
            error: function(xhr) {
                alert("댓글 생성 실패! (F12 콘솔 확인)");
                console.error("댓글 생성 오류:", xhr.responseText);
            }
        });
    });

    // 3. [PUT] /api/reply/{reply_idx} (댓글 수정)
    function editReply(reply_idx) {
        
        if (!reply_idx) {
            alert("수정할 ID가 없습니다! (undefined)");
            return;
        }
        
        let originalComment = $('#comment-' + reply_idx).text(); 
        let newComment = prompt("수정할 내용을 입력하세요:", originalComment);

        if (newComment && newComment !== originalComment) {
            $.ajax({
                url: "/api/reply/" + reply_idx, 
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify({
                    reply_comment: newComment 
                }),
                success: function(response) {
                    alert("댓글 수정 성공!");
                    loadReplies();
                },
                error: function(xhr) {
                    alert("댓글 수정 실패! (F12 콘솔 확인)");
                    console.error("댓글 수정 오류:", xhr.responseText);
                }
            });
        }
    }

    // 4. [DELETE] /api/reply/{reply_idx} (댓글 삭제))
    function deleteReply(reply_idx) { 
        
        if (!reply_idx) {
            alert("삭제할 ID가 없습니다! (undefined)");
            return;
        }

        if (confirm('[' + reply_idx + ']번 댓글을 \'정말로!!\' 삭제하시겠습니까?')) {
            
            $.ajax({
                url: "/api/reply/" + reply_idx, 
                type: "DELETE",
                success: function() {
                    alert("댓글 삭제 성공!");
                    loadReplies(); 
                },
                error: function(xhr) {
                    alert("댓글 삭제 실패! (F12 콘솔 확인)");
                    console.error("댓글 삭제 오류:", xhr.responseText);
                }
            });
        }
    }

    // 페이지 로드 시 '댓글 새로고침' 버튼 자동 클릭
    $('#btnLoadReplies').on('click', loadReplies).click();

</script>

</body>
</html>