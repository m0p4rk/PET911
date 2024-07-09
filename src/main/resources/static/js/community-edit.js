document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('edit-post-form');
    const titleInput = document.getElementById('post-title');
    const categorySelect = document.getElementById('post-category');
    const contentTextarea = document.getElementById('post-content');

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const postData = {
            title: titleInput.value,
            category: categorySelect.value,
            content: contentTextarea.value,
            status: 'PUBLISHED'  // 기본값으로 설정
        };

        fetch(`/community/api/${postId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(postData),
        })
            .then(response => response.json())
            .then(data => {
                alert('게시글이 성공적으로 수정되었습니다.');
                window.location.href = `/community/view/${postId}`;  // 수정된 게시글 상세 페이지로 리다이렉트
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('게시글 수정에 실패했습니다. 다시 시도해주세요.');
            });
    });
});