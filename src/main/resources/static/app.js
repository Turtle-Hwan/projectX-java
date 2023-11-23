//const writeBtn = document.getElementById('writeBtn');
//const writeForm = document.getElementById('writeForm');
//const overlay = document.getElementById('overlay');
//const btnCloseModal = document.getElementById('closeModal');
//let editBtn = document.querySelectorAll('#editBtn');
//const deleteBtn = document.querySelectorAll('#deleteBtn');
//const saveBtn = document.getElementById('saveBtn');
//const readForm = document.getElementById('readForm');
//const btnCloseRead = document.getElementById('closeRead');
//let check = -1;
//
///* open modal to write new post */
//const openModal = () => {
//    writeForm.classList.remove('hidden');
//    overlay.classList.remove('hidden');
//}
//
//const closeModal = () => {
//    writeForm.classList.add('hidden');
//    overlay.classList.add('hidden');
//    readForm.classList.add('hidden');
//    resetForm();
//};
//
//const openRead = () => {
//    readForm.classList.remove('hidden');
//    overlay.classList.remove('hidden');
//}
//
//const closeRead = () => {
//    overlay.classList.add('hidden');
//    readForm.classList.add('hidden');
//}
//
//const createLi = (title, writer, text, i) => {
//    const tr = document.createElement("tr");
//    tr.setAttribute('tr','textItem');
//    tr.setAttribute('class',"text-item");
//    let time = new Date();
//
//    tr.innerHTML=`<tr><td class="title" onclick="readPost(this)">${title}</td>
//    <td class="writer">${writer}</td> <td class="text" hidden>${text}</td> <td>${time.getHours()}:${time.getMinutes()}</td></tr>
//    `
//    const btns = document.createElement("td");
//    btns.innerHTML = `<button class="btn edit" id="editBtn" onClick="onEdit(this)">EDIT</button>
//                    <button class="btn delete" id="deleteBtn" onClick="onDelete(this)">DELETE</button>`
//
//    if(i!=-1){
//        onDelete(i);
//        check=-1;
//    }
//
//    tr.appendChild(btns);
//    document.getElementById('textList').appendChild(tr);
//}
//
//const onDelete = (i) => {
//    document.getElementById('textList').removeChild(i.parentElement.parentElement);
//}

const onEdit = (i) => {
    window.history.pushState({urlPath:'/article/list'},"",'/article/list');
//    window.onpageshow = function(event) {
//    // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
//        if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
//        	//페이지 내용이 다시 계산 될 수 있도록 이벤트를 호출 한다.
//        	location.reload();
//        }
//    }

    let template = document.getElementById("template");
    template.hidden = true;

    console.log(document.getElementById('content_hidden').innerText)
    const index = document.getElementById('index').innerText;
    const subject = document.getElementById('subject').innerText;
    const userName = document.getElementById('userName').innerText;
    const content_h = document.getElementById('content_hidden').innerText

    let new_template = document.createElement("div");
    new_template.innerHTML = `<div layout:fragment="content" class="container">
                            <h5 class="my-3 border-bottom pb-2">게시글 작성</h5>
                            <form th:action="@{/article/detail/${index}}" method="post">
                              <input type="hidden" name="_method" value="put" />
                              <div th:replace="~{form_errors :: formErrorsFragment}"></div>
                              <div class="mb-3">
                                <label for="subject" class="form-label">제목</label>
                                <input type="text" th:field="*{subject}" name="subject" class="form-control" placeHolder="제목" id="postTitle"
                                       maxlength="200" required oninput="countLength(200, this)" value=${subject}>
                              </div>
                              <div class="mb-3">
                                <label for="subject" class="form-label">작성자</label>
                                <input type="text" th:field="*{userName}" name="userName" class="form-control" placeHolder="작성자" id="postWriter"
                                       maxlength="20" required oninput="countLength(20, this)" value=${userName}>
                              </div>
                              <div class="mb-3">
                                <label for="subject" class="form-label">비밀번호</label>
                                <input type="password" th:field="*{password}" name="password" class="password" placeHolder="비밀번호" id="postPassword"
                                    maxlength="10" required oninput="countLength(10, this)" value=${userName}>
                              </div>
                              <div class="mb-3">
                                <label for="content" class="form-label">내용</label>
                                <textarea th:field="*{content}" name="content" class="form-control" placeHolder="내용을 입력해 주세요"  rows="10">${content_h}</textarea>
                              </div>
                              <button type="submit" onClick="" class="write">수정하기</button>
                            </form>
                          </div>`;
    document.body.appendChild(new_template);


}

const onDelete = (i) => {
    window.history.pushState({urlPath:'/article/list'},"",'/article/list');

    let template = document.getElementById("template");
    template.hidden = true;

    console.log(document.getElementById('content_hidden').innerText)
    const index = document.getElementById('index').innerText;
    const subject = document.getElementById('subject').innerText;
    const userName = document.getElementById('userName').innerText;
    const content_h = document.getElementById('content_hidden').innerText

    let new_template = document.createElement("div");
    new_template.innerHTML = `<div layout:fragment="content" class="container">
                            <h5 class="my-3 border-bottom pb-2">게시글 작성</h5>
                            <form th:action="@{/article/detail/${index}}" method="post">
                              <input type="hidden" name="_method" value="delete" />
                              <div th:replace="~{form_errors :: formErrorsFragment}"></div>
                              <div class="mb-3">
                                <label for="subject" class="form-label">제목</label>
                                <input type="text" th:field="*{subject}" name="subject" class="form-control" placeHolder="제목" id="postTitle"
                                       maxlength="200" required oninput="countLength(200, this)" value=${subject} disabled>
                              </div>
                              <div class="mb-3">
                                <label for="subject" class="form-label">작성자</label>
                                <input type="text" th:field="*{userName}" name="userName" class="form-control" placeHolder="작성자" id="postWriter"
                                       maxlength="20" required oninput="countLength(20, this)" value=${userName} disabled>
                              </div>
                              <div class="mb-3">
                                <label for="subject" class="form-label">비밀번호</label>
                                <input type="password" th:field="*{password}" name="password" class="password" placeHolder="비밀번호" id="postPassword" maxlength="10" required oninput="countLength(10, this)">
                              </div>
                              <div class="mb-3">
                                <label for="content" class="form-label">내용</label>
                                <textarea th:field="*{content}" name="content" class="form-control" placeHolder="내용을 입력해 주세요"  rows="10" disabled>${content_h}</textarea>
                              </div>
                              <button type="submit" onClick="" class="write">수정하기</button>
                            </form>
                          </div>`;
    document.body.appendChild(new_template);
}

//const savePost = () => {
//	//location.reload();
//	const title = document.getElementById('postTitle').value;
//    const writer = document.getElementById('postWriter').value;
//    const text = document.getElementById('postText').value;
//
//    createLi(title, writer, text, check);
//    closeModal();
//}

//const readPost = (i) => {
//    document.getElementById('readTitle').textContent = i.parentElement.children[0].textContent;
//    document.getElementById('readWriter').textContent = i.parentElement.children[1].textContent;
//    document.getElementById('readText').textContent = i.parentElement.children[2].textContent;
//    openRead();
//}
//
//const resetForm = () => {
//    document.getElementById('postTitle').value = "";
//    document.getElementById('postWriter').value = "";
//    document.getElementById('postText').value = "";
//}


const countLength = (cnt, i) =>{
    if(i.value.length>cnt){
        alert(`길이는 최대 ${cnt}자 입니다!`);
    }
}

//writeBtn.addEventListener('click', openModal);
//
///* close modal (X button/overlay) */
//btnCloseModal.addEventListener('click', closeModal);
//overlay.addEventListener('click', closeModal);
//
//btnCloseRead.addEventListener('click', closeRead);
