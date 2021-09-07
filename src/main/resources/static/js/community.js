function post() {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    let userId = $("#user_id").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": questionId,
            "content" : content,
            "userId" : userId,
            "type" : 1
        }),
        success : function (response) {
            if (response.code === 200) {
                $("#comment_section").hide();
            } else {
                if (response.code === 2003) {
                    let isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=6816431ab51981c79b94&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType : "json"
    });
}