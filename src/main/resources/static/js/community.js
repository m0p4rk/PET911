document.addEventListener('DOMContentLoaded', function() {
    const postList = document.getElementById('post-list');
    const pagination = document.getElementById('pagination');
    const categorySelect = document.getElementById('category-select');
    const writePostBtn = document.getElementById('write-post');

    let currentPage = 1;
    let currentCategory = 'ALL';

    // 초기 게시글 로드
    loadPosts();

    // 이벤트 리스너 설정
    categorySelect.addEventListener('change', handleCategoryChange);
    writePostBtn.addEventListener('click', () => window.location.href = '/community/write');

    function handleCategoryChange() {
        currentCategory = this.value;
        currentPage = 1;
        loadPosts();
    }

    function loadPosts() {
        let url = `/community/api?page=${currentPage}&size=10`;
        if (currentCategory !== 'ALL') {
            url = `/community/api/category/${currentCategory}?page=${currentPage}&size=10`;
        }

        fetch(url)
            .then(response => response.json())
            .then(data => {
                displayPosts(data.posts);
                updatePagination(data.currentPage, data.totalPages);
            })
            .catch(error => console.error('Error:', error));
    }

    function displayPosts(posts) {
        postList.innerHTML = posts.map(post => `
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">${post.title}</h5>
                    <p class="card-text">${post.content.substring(0, 100)}...</p>
                    <p class="card-text">
                        <small class="text-muted">조회수: ${post.viewCount} | 카테고리: ${post.category}</small>
                        <div class="float-right">
                            <a href="/community/view/${post.id}" class="btn btn-primary btn-sm">보기</a>
                            <a href="/community/edit/${post.id}" class="btn btn-warning btn-sm">수정</a>
                            <button class="btn btn-danger btn-sm delete-post" data-id="${post.id}">삭제</button>
                        </div>
                    </p>
                </div>
            </div>
        `).join('');

        // 삭제 버튼에 이벤트 리스너 추가
        document.querySelectorAll('.delete-post').forEach(btn => {
            btn.addEventListener('click', () => deletePost(btn.getAttribute('data-id')));
        });
    }

    function updatePagination(currentPage, totalPages) {
        pagination.innerHTML = Array.from({length: totalPages}, (_, i) => i + 1)
            .map(i => `
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i}</a>
                </li>
            `).join('');

        pagination.querySelectorAll('.page-link').forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                currentPage = parseInt(link.getAttribute('data-page'));
                loadPosts();
            });
        });
    }

    function deletePost(id) {
        if (confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
            fetch(`/community/api/${id}`, { method: 'DELETE' })
                .then(response => {
                    if (response.ok) {
                        loadPosts();
                    } else {
                        alert('게시글 삭제에 실패했습니다.');
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    }
});