$("#postCommentBtn").click(function () {
    var data = {};
    var userId = $("#userId").val();
    var comment = $("#comment").val();
    var articleId = $("#articleId").val();
    data.userId = userId;
    data.comment = comment;
    data.articleId = articleId;
    $.ajax({
        type: "POST",
        url: "/comment/save",
        // async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data.status == "success") {
                swal({
                    title: '发表成功！',
                    type: "success",
                    timer: 1000,
                    showConfirmButton: false
                });
                $("#comment").val('');
            } else if (data.status == "userIdError") {
                swal({
                    title: 'userId为空！',
                    type: "error",
                    timer: 1000,
                    showConfirmButton: false
                });
            }
            else if (data.status == "articleIdError") {
                swal({
                    title: 'article为空！',
                    type: "error",
                    timer: 1000,
                    showConfirmButton: false
                });
            } else {
                swal({
                    title: '发表失败！',
                    type: "error",
                    timer: 1000,
                    showConfirmButton: false
                });
            }




        }
    });

})