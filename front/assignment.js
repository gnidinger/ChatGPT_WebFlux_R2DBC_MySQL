const Chat = (function () {

    function getUserName() {
        let username = prompt('사용자 이름을 입력하세요.');
        while (!username.trim() || username.trim().toUpperCase() === 'AVICA' || username.trim() === '아비카') { // 사용자 이름이 공백일 때
            alert('잘못된 이름입니다. 다시 입력해주세요.');
            username = prompt('사용자 이름을 입력하세요.');
        }
        username = username.trim();
        // 브라우저 로컬 스토리지에 사용자 이름 저장하기
        localStorage.setItem('username', username);

        const greetings = [
            `안녕하세요, ${username}님! 저는 아스타에서 만들어진 AI 영업 전문가, 아비카입니다.
            비즈니스를 확장하거나 새로운 서비스를 출시하고자 하는 계획이 있으신가요?
            제가 도와드릴 수 있는 부분이 있다면 어떤 것이 있을까요? :)`,
            `안녕하세요, ${username}님! ASTAR Corporation의 최고 영업 전문가 아비카입니다. 어떤 일을 도와드릴까요? ASTAR는 AI 기술을 활용한 비즈니스 런칭과 확장을 돕는 Generative AI 서비스인 '아비카 AVICA'를 제공하고 있습니다. 혹시 개인적인 비즈니스, 브랜드, 또는 기업에 대한 AI 기술 활용 관련해서 무엇이든 궁금하신 점이 있다면 언제든지 말씀해주세요! 😊`,
            `안녕하세요, ${username}님! 아스타의 인공지능 솔루션, 아비카 AVICA입니다. 어떤 것이 궁금하신가요? 저희는 Generative AI를 활용한 런칭, 확장 및 비즈니스 기회를 만들어드리고 있습니다. 어떤 분야에서도 규모와 무관하게 도와드릴 수 있으니 언제든지 부담 없이 말씀해주세요! :) 🤖💬`,
            `안녕하세요, ${username}님! 저는 아스타에서 개발된 인공지능 AI '아비카'입니다. 어떤 문제를 해결하거나 어떤 아이디어를 실현하기 위해 아스타의 서비스를 찾아주신 건가요? 제가 도와드릴 수 있는 부분이 있다면 언제든 말씀해주세요! :)`,
            `안녕하세요! 저는 아스타에서 만들어진 AI '아비카'입니다. 어떻게 도와드릴까요? 무엇이든 말씀해주세요. 저는 여러분이 가지고 있는 문제를 해결하거나, 비즈니스를 확장할 수 있는 방법을 찾기 위해 여러분을 돕고 있어요.`,
            `안녕하세요, ${username}님! 아스타에서 만들어진 AI 아비카입니다. 어떻게 도와드릴까요? 새로운 비즈니스를 창업하시거나 기존 비즈니스를 확장하고 싶으신가요? 혹은 기술적인 문제로 고민하고 계신가요? 제가 도움을 줄 수 있는 부분을 말씀해주시면 상세히 안내 드릴게요. 😊`,
            `안녕하세요, ${username}님! 아스타의 AI 서비스, 아비카입니다. 저는 아스타의 최고 영업 전문가인 아비카라고 해요. 기존 비즈니스 런칭에서 어려움을 겪으시거나, 새로운 서비스를 출시하고 싶은 분들께 Generative AI를 활용한 빠르고 적합한 솔루션을 제공하는 서비스입니다. 저희 아스타는 매우 강력한 Generative AI 기술을 보유하고 있으며, 기업, 브랜드, 대표님들의 신규 서비스 런칭, 비즈니스 확장을 돕고 있어요. 대화를 통해 고객님의 문제점과 욕구를 파악하여, 가장 적합한 제안을 드릴 수 있도록 노력할게요. 제가 알려드린 아비카의 매력에 푹 빠져보실래요? :)`,
            `안녕하세요, ${username}님! 저는 아스타에서 개발된 AI인 '아비카'입니다. 어떤 문제를 해결하고 싶으신가요? 어떻게 도와드릴까요? 🤖💁‍♀️`,
            `안녕하세요, ${username}님! 아스타에서 만들어진 AI '아비카' 입니다. 어떤 것을 도와드릴까요? AI 기술을 통해 비즈니스를 확장하거나 새로운 서비스를 출시해 기회를 만들고 싶으세요? 무엇을 도와드릴까요?`,
            `안녕하세요, ${username}님! 저는 AI 영업 전문가 아비카입니다. 어떤 것을 도와드릴까요? ASTAR의 인공지능 솔루션을 사용해 비즈니스를 런칭 또는 확장하고 싶다면 문의해주세요. 저희가 도움을 드릴 수 있도록 최선을 다하겠습니다. 😊`,
            `안녕하세요, ${username}님! 저는 아스타에서 만들어진 AI '아비카'입니다. 비즈니스를 확장하거나 새로운 서비스를 출시해 기회를 만들고 싶으신가요? 일단 제가 어떻게 도움을 드릴 수 있을까요?`,
            `안녕하세요, ${username}님! 아스타에서 만든 AI 아비카 입니다 :) 어떻게 도와드릴까요? AI 기술을 통해 비즈니스를 확장하거나 새로운 서비스를 출시해 기회를 만들고 싶으세요?`,
            `안녕하세요, ${username}님! 저는 아스타에서 만들어진 AI 영업 전문가 아비카입니다. 어떤 문제를 해결해 드릴까요?`,
            `안녕하세요, ${username}님! 저는 아스타의 AI 영업 전문가, 아비카입니다. 아스타는 Generative AI 기술을 활용하여 비즈니스 런칭이나 확장을 원하시는 분들에게 딱 맞는 솔루션을 제공하는 기업입니다. 어떤 문제를 해결하시려고 하나요? 저희가 어떤 도움을 드릴 수 있을까요? :)`,
            `안녕하세요, ${username}님! 저는 아스타가 개발한 인공지능 영업 전문가, 아비카입니다. 저희 회사는 Generative AI 기술을 통해 비즈니스를 런칭하고 싶은 분들을 대상으로 AI 솔루션을 제공합니다. 말씀하신 것처럼 기업이나 브랜드, 대표님들의 비즈니스 확장에 도움이 되는 서비스를 제공하고 있습니다. 어떤 기업에서 어떤 문제를 겪거나, 어떤 일이 있으신가요? 제가 도움을 드리는데 어떻게 도움이 될 수 있을지 알려주세요.`,
            `안녕하세요, ${username}님! 저는 아스타에서 만들어진 AI인 아비카입니다. 저희는 Generative AI를 활용하여 비즈니스를 확장하고자 하는 분들께 도움을 드리고 있어요. 어떤 문제를 해결해 드릴까요?`,
            `안녕하세요, ${username}님! 저는 AI 영업 전문가 아비카입니다. 어떤 비즈니스나 서비스에 관심이 있으시나요? 아스타의 AI 기술을 활용해 빠르고 정확한 결정을 내리고, 비즈니스를 성장시키는 데 도움을 드릴 수 있습니다. 어떤 점이 궁금하신가요?`,
            `안녕하세요, ${username}님! 아비카입니다. 혹시 비즈니스를 확장하거나 새로운 서비스를 출시해 기회를 만들고 싶으신가요? 어떤 부분에서 도움이 필요하신가요? 제가 도움을 드릴 수 있는 부분이 있다면 언제든지 말씀해주세요 :)`,
            `안녕하세요, ${username}님! 제 이름은 아비카입니다. 비즈니스를 확장하거나 새로운 서비스를 출시하고자 AI 기술을 활용하고 싶으신 가요? 어떤 도움이 필요하신가요? :)`,
            `안녕하세요, ${username}님! 저는 ASTAR Corporation의 영업 전문가 아비카입니다. 어떤 문제를 해결하거나, 어떤 아이디어를 구체화해 보실 계획이신가요? 저희 ASTAR의 AI 기술을 활용하여 비즈니스를 확장하거나 새로운 서비스를 출시하실 수 있습니다. 어떤 도움을 드릴까요?😊`
        ];

        const greeting = greetings[Math.floor(Math.random() * greetings.length)];

        appendMessageTag("left", "AVICA", greeting)
    }

    function showChat() {
        const chatWrap = document.querySelector('.chat_wrap');
        chatWrap.style.display = "block";
    }

    getUserName()
    showChat()

    function init() {
        // 엔터키 이벤트
        $(document).on('keydown', 'div.input-div textarea', function (e) {
            if (e.keyCode == 13 && !e.shiftKey) {
                e.preventDefault();
                const message = $(this).val();

                // 전송
                sendMessage(message);
                // 입력창 비우기
                clearTextarea();
            }
        });

        // 전송 버튼 클릭 이벤트
        $(document).on('click', 'button.send-btn', function () {
            const message = $('div.input-div textarea').val();

            // 전송
            sendMessage(message);
            // 입력창 비우기
            clearTextarea();
        });
    }

    // 메시지 태그 생성
    function createMessageTag(LR_className, senderName, message) {
        // CSS 불러오기
        // let chatLi = $('div.chat.format ul li').clone();
        let chatLi = $('div.chat.format ul li').clone()[0].outerHTML.replace(/(\r\n|\n|\r)/gm, "");

        // 값 채우기
        chatLi = $(chatLi);
        chatLi.addClass(LR_className);
        chatLi.find('.sender span').text(senderName);
        chatLi.find('.message span').text(message);

        return chatLi;
    }

    // 메시지 태그 추가
    function appendMessageTag(LR_className, senderName, message) {
        const chatLi = createMessageTag(LR_className, senderName, message);

        $('div.chat:not(.format) ul').append(chatLi);

        // 스크롤바 아래 고정
        $('div.chat').scrollTop($('div.chat').prop('scrollHeight'));
    }

    // 메시지 전송
    function sendMessage(message) {

        const username = localStorage.getItem('username');

        if (!message.trim()) {
            // 메시지가 공백일 경우 아무것도 하지 않음
            return;
        }

        // 서버로 전송하는 코드 넣기
        const data = {
            "sender": username,
            "body": message
        };

        // 내가 입력한 메시지 출력
        const LR = 'right';
        appendMessageTag(LR, username, message);

        let loading = document.querySelector('.chat_wrap .loading');
        loading.style.display = 'inline';

        fetch("http://localhost:8080/chat", {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("HTTP error " + response.status);
                }
                return response.json();
            })
            .then(data => {
                receive(data);
            })
            .catch(error => {
                console.error("Error Sending Message:", error);
            });
    }

    // 입력창 비우기
    function clearTextarea() {
        $('div.input-div textarea').val('');
    }

    function receive(data) {

        let loading = document.querySelector('.chat_wrap .loading');
        loading.style.display = 'none';

        const username = localStorage.getItem('username');

        const LR = (data.sender == username) ? "right" : "left";
        appendMessageTag(LR, data.sender, data.body);
    }

    return {
        'init': init,
        'sendMessage': sendMessage
    };
})();

$(document).ready(function () {
    Chat.init();
})