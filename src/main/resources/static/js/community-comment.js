document.addEventListener('DOMContentLoaded', function() {
    const commentsList = document.getElementById('comments-list');
    const commentContent = document.getElementById('comment-content');
    const submitCommentBtn = document.getElementById('submit-comment');

    let currentUserId = null;

    // 페이지 로드 시 현재 사용자 ID를 가져오고 댓글을 로드합니다.
    getCurrentUserId().then(userId => {
        currentUserId = userId;
        loadComments();
    });

    submitCommentBtn.addEventListener('click', submitComment);

    function loadComments() {
        fetch(`/api/community/comments/community/${postId}`)
            .then(response => response.json())
            .then(comments => {
                commentsList.innerHTML = comments.map(comment => `
                    <div class="card mb-2" data-id="${comment.id}">
                        <div class="card-body">
                            <p class="card-text comment-content">${comment.content}</p>
                            <p class="card-text">
                                <small class="text-muted">
                                    작성자 ID: ${comment.user_id} | 
                                    작성일: ${new Date(comment.createdAt).toLocaleString()}
                                </small>
                            </p>
                            ${comment.user_id === currentUserId ? `
                                <button class="btn btn-sm btn-warning edit-comment">수정</button>
                                <button class="btn btn-sm btn-danger delete-comment">삭제</button>
                            ` : ''}
                        </div>
                    </div>
                `).join('');

                // 수정 및 삭제 버튼에 이벤트 리스너 추가
                document.querySelectorAll('.edit-comment').forEach(btn => {
                    btn.addEventListener('click', function() {
                        const commentCard = this.closest('.card');
                        editComment(commentCard);
                    });
                });
                document.querySelectorAll('.delete-comment').forEach(btn => {
                    btn.addEventListener('click', function() {
                        const commentCard = this.closest('.card');
                        deleteComment(commentCard.getAttribute('data-id'));
                    });
                });
            })
            .catch(error => console.error('Error:', error));
    }

    function submitComment() {
        const content = commentContent.value.trim();
        if (!content) {
            alert('댓글 내용을 입력해주세요.');
            return;
        }

        fetch('/api/community/comments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                communityId: postId,
                content: content
            }),
        })
            .then(response => response.json())
            .then(() => {
                commentContent.value = '';
                loadComments();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 작성에 실패했습니다. 다시 시도해주세요.');
            });
    }

    function editComment(commentCard) {
        const contentElement = commentCard.querySelector('.comment-content');
        const currentContent = contentElement.textContent;
        const textarea = document.createElement('textarea');
        textarea.className = 'form-control mb-2';
        textarea.value = currentContent;

        const saveBtn = document.createElement('button');
        saveBtn.textContent = '저장';
        saveBtn.className = 'btn btn-sm btn-primary mr-2';

        const cancelBtn = document.createElement('button');
        cancelBtn.textContent = '취소';
        cancelBtn.className = 'btn btn-sm btn-secondary';

        contentElement.replaceWith(textarea);
        commentCard.querySelector('.card-body').appendChild(saveBtn);
        commentCard.querySelector('.card-body').appendChild(cancelBtn);

        saveBtn.addEventListener('click', function() {
            const newContent = textarea.value.trim();
            if (newContent && newContent !== currentContent) {
                updateCommentOnServer(commentCard.getAttribute('data-id'), newContent);
            } else {
                restoreCommentCard();
            }
        });

        cancelBtn.addEventListener('click', restoreCommentCard);

        function restoreCommentCard() {
            textarea.replaceWith(contentElement);
            saveBtn.remove();
            cancelBtn.remove();
        }
    }

    function updateCommentOnServer(commentId, newContent) {
        fetch(`/api/community/comments/${commentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ content: newContent }),
        })
            .then(response => response.json())
            .then(() => loadComments())
            .catch(error => {
                console.error('Error:', error);
                alert('댓글 수정에 실패했습니다. 다시 시도해주세요.');
            });
    }

    function deleteComment(commentId) {
        if (confirm('정말로 이 댓글을 삭제하시겠습니까?')) {
            fetch(`/api/community/comments/${commentId}`, {
                method: 'DELETE',
            })
                .then(response => {
                    if (response.ok) {
                        loadComments();
                    } else {
                        throw new Error('댓글 삭제에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('댓글 삭제에 실패했습니다. 다시 시도해주세요.');
                });
        }
    }

    async function getCurrentUserId() {
        try {
            const response = await fetch('/api/session/user');
            const data = await response.json();
            return data.id; // UserSessionDTO의 id 필드를 반환
        } catch (error) {
            console.error('Error fetching current user:', error);
            return null;
        }
    }
});