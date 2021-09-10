/**
 * 提交回复
 */
function post() {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    let userId = $("#user_id").val();
    if (!content) {
        alert("输入回复不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "userId": userId,
            "type": type
        }),
        success: function (response) {
            if (response.code === 200) {
                window.location.reload();
            } else {
                if (response.code === 2003) {
                    let isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=6816431ab51981c79b94&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    let commentId = e.getAttribute("data-id");
    let content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);

}

/**
 * 展开二级评论
 * @param e
 */
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);

    $(".glyphicon-comment").toggleClass("active")
    $.ajax({
        type: "GET",
        url: "/comment/" + id,
        contentType: 'application/json',
        success: function (response) {
            const subCommentContainer = $("#comment-" + id);

            if (e.getAttribute("data-collapse")) {
                comments.removeClass("in");
                e.removeAttribute("data-collapse");
                e.classList.remove("active");
            } else {
                if (subCommentContainer.children().length !== 1) {
                    //展开二级评论
                    comments.addClass("in");
                    // 标记二级评论展开状态
                    e.setAttribute("data-collapse", "in");
                    e.classList.add("active");
                } else {
                    $.each(response.data.reverse(), function (index, comments) {
                        console.log(comments);
                        let mediaLeftElement = $("<div/>", {
                            "class": "media-left"
                        }).append($("<img/>", {
                            "class": "media-object img-rounded",
                            "src": comments.user.avatarUrl
                        }));

                        let mediaBodyElement = $("<div/>", {
                            "class": "media-body"
                        }).append($("<h5/>", {
                            "class": "media-heading",
                            "html": comments.user.name
                        })).append($("<div/>", {
                            "style": "color: #c0c3c6",
                            "html": comments.comment.content
                        })).append($("<div/>", {
                            "class": "menu"
                        }).append($("<span/>", {
                            "class": "pull-right text-desc",
                            "html": moment(comments.comment.gmtCreate).format('YYYY年MM月DD日')
                        })));

                        let mediaElement = $("<div/>", {
                            "class": "media"
                        }).append(mediaLeftElement).append(mediaBodyElement);

                        let commentElement = $("<div/>", {
                            "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                        }).append(mediaElement);

                        subCommentContainer.prepend(commentElement);
                    });
                    comments.addClass("in");
                    // 标记二级评论展开状态
                    e.setAttribute("data-collapse", "in");
                    e.classList.add("active");
                }
            }
        },
        dataType: "json"
    });
}

/**
 * 展示选择标签
 */
function showSelectTag() {
    $("#select-tag").show();
}
function hiddenSelectTag() {
    $("#select-tag").hide();
}

/**
 * 添加标签
 * @param e
 */
function selectTag(e) {
    let value = e.getAttribute("data-tag");
    let previous = $("#tag").val();
    if (previous.indexOf(value) === -1) {
        if (previous) {
            $("#tag").val(previous+','+value);
        } else {
            $("#tag").val(value);
        }
    }
}

function clearTag() {
    $("#tag").val("");
}