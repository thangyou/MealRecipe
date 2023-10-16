// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('recipe-id').value;
        fetch(`/api/recipes/id=${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/recipes');
            });
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/recipes/id=${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                rcp_seq: document.getElementById('rcp_seq').value,
                rcp_nm: document.getElementById('rcp_nm').value,
                rcp_way2: document.getElementById('rcp_way2').value,
                rcp_pat2: document.getElementById('rcp_pat2').value,
                manual01: document.getElementById('manual01').value,
                manual_img01: document.getElementById('manual_img01').value
            })
        })
            .then(() => {
                alert('수정이 완료되었습니다.');
                location.replace(`/recipes/id=${id}`);
            });
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        fetch('/api/recipes', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                rcp_seq: document.getElementById('rcp_seq').value,
                rcp_nm: document.getElementById('rcp_nm').value,
                rcp_way2: document.getElementById('rcp_way2').value,
                rcp_pat2: document.getElementById('rcp_pat2').value,
                manual01: document.getElementById('manual01').value,
                manual_img01: document.getElementById('manual_img01').value
            })
        })
            .then(() => {
                alert('등록 완료되었습니다.');
                location.replace('/recipes');
            });
    });
}
