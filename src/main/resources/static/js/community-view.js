document.addEventListener('DOMContentLoaded', function() {
    const postTitle = document.getElementById('post-title');
    const postAuthor = document.getElementById('post-author');
    const postViewCount = document.getElementById('post-view-count');
    const postCategory = document.getElementById('post-category');
    const postContent = document.getElementById('post-content');
    const commentsList = document.getElementById('comments');
    const commentContent = document.getElementById('comment-content');
    const submitCommentBtn = document.getElementById('submit-comment');
    const editPostBtn = document.getElementById('edit-post');
    const deletePostBtn = document.getElementById('delete-post');
    const backToListBtn = document.getElementById('back-to-list');

    // 초기 데이터 로드
    loadPostDetail();
    loadComments();

    // 이벤트 리스너 설정
    submitCommentBtn.addEventListener('click', submitComment);
    if (editPostBtn) editPostBtn.addEventListener('click', () => window.location.href = `/community/edit/${postId}`);
    if (deletePostBtn) deletePostBtn.addEventListener('click', deletePost);
    if (backToListBtn) backToListBtn.addEventListener('click', () => window.location.href = '/community');

    function loadPostDetail() {
        fetch(`/community/api/${postId}`)
            .then(response => response.json())
            .then(data => {
                const post = data.post;
                postTitle.textContent = post.title;
                postAuthor.textContent = post.author;
                postViewCount.textContent = post.viewCount;
                postCategory.textContent = post.category;
                postContent.textContent = post.content;
            })
            .catch(error => console.error('Error:', error));
    }

    function loadComments() {
        fetch(`/community/api/${postId}/comments`)
            .then(response => response.json())
            .then(comments => {
                commentsList.innerHTML = comments.map(comment => `
                    <div class="card mb-2">
                        <div class="card-body">
                            <p class="card-text">${comment.content}</p>
                            <p class="card-text">
                                <small class="text-muted">작성자: ${comment.author} | 작성일: ${new Date(comment.createdAt).toLocaleString()}</small>
                            </p>
                        </div>
                    </div>
                `).join('');
            })
            .catch(error => console.error('Error:', error));
    }

    function submitComment() {
        const content = commentContent.value.trim();
        if (!content) {
            alert('댓글 내용을 입력해주세요.');
            return;
        }

        fetch(`/community/api/${postId}/comments`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content: content }),
        })
            .then(response => response.json())
            .then(() => {
                commentContent.value = '';
                loadComments();
            })
            .catch(error => console.error('Error:', error));
    }

    function deletePost() {
        if (confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
            fetch(`/community/api/${postId}`, { method: 'DELETE' })
                .then(response => {
                    if (response.ok) {
                        alert('게시글이 삭제되었습니다.');
                        window.location.href = '/community';
                    } else {
                        alert('게시글 삭제에 실패했습니다.');
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    }
});