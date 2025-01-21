document.addEventListener("DOMContentLoaded", function () {
    // 챗봇 열기
    document.getElementById('chatbot-open').addEventListener('click', function () {
        document.getElementById('chatbot').style.display = 'block';
        document.getElementById('chatbot-open').style.display = 'none';
    });

    // 챗봇 닫기
    document.getElementById('chatbot-close').addEventListener('click', function () {
        document.getElementById('chatbot').style.display = 'none';
        document.getElementById('chatbot-open').style.display = 'block';
    });

    // 보내기 버튼 클릭 이벤트
    document.getElementById('chatbot-send').addEventListener('click', function () {
        let message = document.getElementById('chatbot-input').value;
        addUserMessage(message);
        document.getElementById('chatbot-input').value = '';
        sendMessage(message, addBotMessage); // 챗봇 비동기 통신
    });

    // input칸 엔터 이벤트
    document.getElementById('chatbot-input').addEventListener('keypress', function (e) {
        if (e.code === 'Enter') {
            let message = e.target.value;
            addUserMessage(message);
            e.target.value = '';
            sendMessage(message, addBotMessage); // 챗봇 비동기 통신
        }
    });
});

// 유저 메세지 추가
function addUserMessage(message) {
    let htmlCode = `<div class="user-message message">
          <div class="message-text">${message}</div>
        </div>`;

    document.querySelector('.chatbot-body').insertAdjacentHTML('beforeend', htmlCode);
    let chatbotBody = document.querySelector('.chatbot-body');
    chatbotBody.scrollTop = chatbotBody.scrollHeight; // 스크롤 하단으로 이동
}

// 챗봇 메세지 추가
function addBotMessage(message) {
    let htmlCode = `<div class="bot-message message">
          <div class="message-text">${message}</div>
        </div>`;

    document.querySelector('.chatbot-body').insertAdjacentHTML('beforeend', htmlCode);
    let chatbotBody = document.querySelector('.chatbot-body');
    chatbotBody.scrollTop = chatbotBody.scrollHeight; // 스크롤 하단으로 이동
}


function sendMessage(content, callback) {
    fetch("/chat2", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
        body: JSON.stringify({ message: content })
    })
        .then(response => {
            if (!response.ok) {
                // 서버 응답이 실패한 경우 처리
                throw new Error('Network response was not ok');
            }
            return response.text(); // JSON 형태로 응답 본문을 파싱
        })
        .then(result => {
            console.log(result);
            let message = result;
            callback(message); // 콜백 호출
        })
        .catch(error => {
            console.log('There was a problem with the fetch operation:', error);
        });
}
