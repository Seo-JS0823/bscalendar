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
<%@ include file="../nav.jsp" %>

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
	      		const ptags = document.querySelectorAll('.row-box > :nth-child(2)');
	      		const works_idx = ${works_idx};
	      		const mem_id = getTokenFromInfo('username');
	      		
	      		fetch(`/api/work/detail/\${works_idx}`)
	      			.then( response => response.json() )
	      			.then( data => {
	      				console.log(data)
	      				ptags[0].textContent = data.team_name;
	      				ptags[1].textContent = data.works_sdate.substring(0,10);
	      				ptags[2].textContent = data.mem_name;
	      				
	      				
	      				
	      			})
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
      
      <h3>새 댓글 작성</h3>
      <div class="work-reply">
	      <textarea id="newReplyComment" placeholder="댓글 내용 입력"></textarea> 
	      <button id="btnCreateReply">메모 등록</button>
      </div>

    </div>
  </div>
</div>

<script>
    const current_works_idx = ${works_idx};

    //DOM 요소 미리 찾아두기
    const replyListEl = document.getElementById('replyList');
    const btnLoadRepliesEl = document.getElementById('btnLoadReplies');
    const btnCreateReplyEl = document.getElementById('btnCreateReply');
    const newReplyCommentEl = document.getElementById('newReplyComment');

    // 1. GET /api/reply/list (댓글 목록 조회)
    function loadReplies() {
        
        if (!current_works_idx) {
            alert("치명적 오류: works_idx를 찾을 수 없습니다.");
            replyListEl.innerHTML = "<span style='color: red;'>페이지 로드 오류: works_idx 없음</span>";
            return;
        }
        
        const url = new URL("/api/reply/list", window.location.origin);
        url.searchParams.append('works_idx', current_works_idx);

        fetch(url, {
            method: "GET",
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("HTTP " + response.status);
            }
            return response.json();
        })
        .then(replies => {
            replyListEl.innerHTML = ''; 
            
            if (!replies || replies.length === 0) {
                replyListEl.innerHTML = "댓글이 없습니다.";
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
                
                replyListEl.insertAdjacentHTML('beforeend', html);
            });
        })
        .catch(err => {
            alert("댓글 로딩 실패! (F12 콘솔 확인)");
            console.error("댓글 로딩 오류:", err.message);
        });
    }

    // 2. POST /api/reply (새 댓글 생성)
    btnCreateReplyEl.addEventListener('click', function() {
        
        let comment = newReplyCommentEl.value; 

        if (!comment) {
            alert("댓글 내용을 입력하세요.");
            return;
        }
        
        fetch("/api/reply", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify({
                works_idx: current_works_idx,
                reply_comment: comment
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("HTTP " + response.status);
            }
            return response.json();
        })
        .then(data => {
            alert("댓글 생성 성공! (ID: " + data.reply_idx + ")");
            newReplyCommentEl.value = ''; 
            loadReplies();
        })
        .catch(err => {
            alert("댓글 생성 실패! (F12 콘솔 확인)");
            console.error("댓글 생성 오류:", err.message);
        });
    });

     //3. PUT /api/reply/{reply_idx} (댓글 수정)
    function editReply(reply_idx) {
        
        if (!reply_idx) {
            alert("수정할 ID가 없습니다! (undefined)");
            return;
        }
       
        let originalCommentEl = document.getElementById('comment-' + reply_idx);
        if (!originalCommentEl) {
            alert("오류: 원본 댓글 엘리먼트를 찾을 수 없습니다. (ID: comment-" + reply_idx + ")");
            return;
        }
        let originalComment = originalCommentEl.textContent; 
        
        let newComment = prompt("수정할 내용을 입력하세요:", originalComment);

        if (newComment && newComment !== originalComment) {
            fetch("/api/reply/" + reply_idx, {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                },
                body: JSON.stringify({
                    reply_comment: newComment 
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("HTTP " + response.status);
                }
            })
            .then(() => {
                alert("댓글 수정 성공!");
                loadReplies();
            })
            .catch(err => {
                alert("댓글 수정 실패! (F12 콘솔 확인)");
                console.error("댓글 수정 오류:", err.message);
            });
        }
    }

     // 4. DELETE /api/reply/{reply_idx} (댓글 삭제)
function deleteReply(reply_idx) { 
        
        if (!reply_idx) {
            alert("삭제할 ID가 없습니다! (undefined)");
            return;
        }

        if (confirm('[' + reply_idx + ']번 댓글을 삭제하시겠습니까?')) {
            
            fetch("/api/reply/" + reply_idx, {
                method: "DELETE",
                headers: { 
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                } 
            }) 
            .then(response => {
                if (!response.ok) {
                    if (response.status === 401 || response.status === 403) {
                        alert("댓글 삭제 권한이 없습니다. (본인 댓글만 가능)");
                    }
                    throw new Error("HTTP " + response.status);
                }
            })
            .then(() => {
                alert("댓글 삭제 성공!");
                loadReplies(); 
            })
            .catch(err => {
                alert("댓글 삭제 실패! (F12 콘솔 확인)");
                console.error("댓글 삭제 오류:", err.message);
            });
        }
    }

    //페이지 로드 시 
    btnLoadRepliesEl.addEventListener('click', loadReplies);
    loadReplies(); // 페이지 로드 시 바로 실행

</script>

</body>
</html>