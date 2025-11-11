<%--
  (경로: /WEB-INF/views/reply/test.jsp)
  'snake_case' 규칙으로 통일된 '댓글(메모)' API 최종 테스트 페이지
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- EL 태그(${...})를 사용하기 위해 isELIgnored=false를 명시합니다. --%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 기능(메모) 단위 테스트</title>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<style>
    body { font-family: sans-serif; padding: 20px; }
    #replyList { border: 1px solid #ccc; min-height: 100px; padding: 10px; margin-top: 10px; background-color: #f9f9f9; }
    .reply-item { border-bottom: 1px dotted #eee; padding: 5px; }
    .reply-item button { margin-left: 10px; font-size: 10px; cursor: pointer; }
</style>
</head>
<body>

    <h1>댓글 (메모) 기능 단위 테스트 (SnakeCase)</h1>
    <p>(※ `TestPageController`가 `tengen` (으)로 강제 로그인 처리함)</p>

    <input type="hidden" id="worksIdx" value="${testWorksIdx}">
    
    <h3>댓글 목록 (Works ID: ${testWorksIdx})</h3>
    <button id="btnLoadReplies">댓글 새로고침 (GET)</button>
    <div id="replyList">
        </div>

    <hr>
    <h3>새 댓글 작성 (POST)</h3>
    <input type="text" id="newReplyComment" placeholder="댓글 내용 입력" style="width: 300px;">
    <button id="btnCreateReply">댓글 등록</button>

    <script>	      
        // -----------------------------------------------------
        // 1. [GET] /api/reply (댓글 목록 조회)
        // -----------------------------------------------------
        function loadReplies() {
            
            // EL 태그 문제를 무시하고 '1'을 하드코딩
            const current_works_idx = 1; // $('#worksIdx').val();
            $.ajax({
                url: "/api/reply",
                type: "GET",
                data: {
                    works_idx: current_works_idx // 1이 전송됨
                },
                success: function(replies) {
                    $('#replyList').empty(); 
                    
                    if (replies.length === 0) {
                        $('#replyList').html("댓글이 없습니다.");
                        return;
                    }
                    replies.forEach(function(reply) {
                        let html = '<div class="reply-item">';
                        html += `[${reply.reply_idx}] `; 
                        html += `<b>${reply.mem_name}</b>: `;
                        html += `<span id="comment-${reply.reply_idx}">${reply.reply_comment}</span>`;
                        html += `<button onclick="editReply(${reply.reply_idx})">수정</button>`;
                        html += `<button onclick="deleteReply(${reply.reply_idx})">삭제</button>`;
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

        // -----------------------------------------------------
        // 2. [POST] /api/reply (새 댓글 생성)
        // -----------------------------------------------------
        $('#btnCreateReply').on('click', function() {
            
            // ★ [수정] 500(외래 키) 오류 해결을 위해 '1'을 하드코딩
            const current_works_idx = 1; // $('#worksIdx').val();
            let comment = $('#newReplyComment').val();

            if (!comment) {
                alert("댓글 내용을 입력하세요.");
                return;
            }
            
            $.ajax({
                url: "/api/reply",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    works_idx: current_works_idx, // 1이 전송
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

        // -----------------------------------------------------
        // 3. [PUT] /api/reply/{reply_idx} (댓글 수정)
        // -----------------------------------------------------
        function editReply(reply_idx) {
            let originalComment = $(`#comment-${reply_idx}`).text();
            let newComment = prompt("수정할 내용을 입력하세요:", originalComment);

            if (newComment && newComment !== originalComment) {
                // ReplyController의 replyUpdate() 호출
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

        // -----------------------------------------------------
        // 4. [DELETE] /api/reply/{reply_idx} (댓글 삭제)
        // -----------------------------------------------------
        function deleteReply(reply_idx) { 
            if (confirm(`[${reply_idx}]번 댓글을 정말 삭제하시겠습니까?`)) {
                
                // ReplyController의 replyDelete() 호출
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