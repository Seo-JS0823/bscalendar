<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 기능(메모) 테스트</title>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<style>
    body { font-family: sans-serif; padding: 20px; }
    #replyList { border: 1px solid #ccc; min-height: 100px; padding: 10px; margin-top: 10px; background-color: #f9f9f9; }
    .reply-item { border-bottom: 1px dotted #eee; padding: 5px; }
    .reply-item button { margin-left: 10px; font-size: 10px; cursor: pointer; }
</style>
</head>
<body>

    <h1>댓글 (메모) 기능 테스트</h1>
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
        // 1. [GET] /api/reply/list (댓글 목록 조회)
        // -----------------------------------------------------
        function loadReplies() {
            
            const current_works_idx = 1; // $('#works_idx').val();
            
            $.ajax({
                url: "/api/reply/list",
                type: "GET",
                data: {
                    works_idx: current_works_idx 
                },
                success: function(replies) {
                    $('#replyList').empty(); 
                    
                    if (!replies || replies.length === 0) {
                        $('#replyList').html("댓글이 없습니다.");
                        return;
                    }
                    
                    // 반복문 시작
                    replies.forEach(function(reply) {
                        
                        console.log("도착한 데이터:", reply);
                        console.log("reply.reply_idx 값:", reply.reply_idx); 
                        
                        // '+' 연산자로만 HTML 생성
                        var html = '<div class="reply-item">';
                        html += '[' + reply.reply_idx + '] '; 
                        html += '<b>' + reply.mem_name + '</b>: ';
                        html += '<span id="comment-' + reply.reply_idx + '">' + reply.reply_comment + '</span>';
                        html += '<button onclick="editReply(' + reply.reply_idx + ')">수정</button>';
                        html += '<button onclick="deleteReply(' + reply.reply_idx + ')">삭제</button>';
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
            
            const current_works_idx = 1; // $('#works_idx').val();
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

        // -----------------------------------------------------
        // 3. [PUT] /api/reply/{reply_idx} (댓글 수정)
        // -----------------------------------------------------
        function editReply(reply_idx) {
            
            console.log("수정할 ID:", reply_idx);
            
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

        // -----------------------------------------------------
        // 4. [DELETE] /api/reply/{reply_idx} (댓글 삭제)
        // -----------------------------------------------------
        function deleteReply(reply_idx) { 
            
            console.log("삭제할 ID:", reply_idx);

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

        $('#btnLoadReplies').on('click', loadReplies).click();

    </script>

</body>
</html>