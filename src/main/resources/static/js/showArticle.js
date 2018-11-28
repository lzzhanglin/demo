$().ready(function () {
    var num = $("#commentTable").find("tr").length;
    if (num == 1) {
        $("#tip").html('还没有评论哦,快留下第一条评论吧！');
    }
})

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
                var tableId = 'commentTable';
                $("#tip").html('');

                var tableRowData = {};
                tableRowData.username = data.msg;
                tableRowData.userId = data.data.userId;
                tableRowData.comment = comment;
                tableRowData.createTime = data.data.createTime;
                tableRowData.commentId = data.data.commentId;
                var insertTr = $('#' + tableId + ' tr:last').clone(true);
                var tableLength = $("#" + tableId).find("tr").length;
                // 将json数据循环追加到表的每一列
                    insertTr.children('td').eq(0).html("<a href='/user/show?userId="+tableRowData.userId+"'>"+tableRowData.username+"</a>");
                    insertTr.children('td').eq(1).html(tableRowData.comment);
                    insertTr.children('td').eq(2).html(tableRowData.createTime);
                    insertTr.children('td').eq(3).html("<button class='btn btn-info' type='button' onclick='reply("+tableRowData.commentId+")'>Reply</button>"
                    +" "+"<button class='btn btn-warning' type='button' onclick='deleteComment("+tableRowData.commentId+")'>Delete</button>");
                $('#' + tableId + ' tr:last').after(insertTr);
//                 window.location.reload();
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
            } else  if (data.status = "commentNull") {
                swal({
                    title: '评论不能为空！',
                    type: "error",
                    timer: 1000,
                    showConfirmButton: false
                });
            }else {
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

var tableIndex = -1;
$("#commentTable tr").click(function() {
    tableIndex = $(this).parent().find("tr").index($(this)[0]); //所获取的当前行的行号

});
function deleteComment(commentId) {
    var data = {};
    data.commentId = commentId;
    swal({
        title: '确定删除吗？',
        text: '你将无法恢复它！',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '确定删除！',
    }).then(function(isConfirm){
        if (isConfirm.dismiss != 'cancel') {
            $.ajax({
                type: "POST",
                url: "/comment/delete",
                // async:false,
                data: data,

                //type、contentType必填,指明传参方式
                // dataType: "json",

                contentType: "application/x-www-form-urlencoded",
                // contentType: "application/json;charset=utf-8",
                success: function (data) {
                    if (data.status == "failed") {
                        swal({title: '删除失败！',
                            type:"error",
                            timer:1000,
                            showConfirmButton:false});

                    } else {
                        swal({

                            title:'评论已被删除！',
                            type: "success",
                            timer: 1000,
                            showConfirmButton: false});
                        document.getElementById('commentTable').deleteRow(tableIndex)



                    }
                }
            });

        }


    })

}

function reply(commentId) {
    // var commentId=$(this).parents("tr").find("td").eq(3).text();
    $("#commentIdH").val(commentId);
    $("#replyModal").modal('show');

}



$("#replyBtn").click(function () {
    var data = {};
    var replyCommentId = $("#commentIdH").val();
    var userId = $("#userId").val();
    var comment = $("#replyComment").val();
    var articleId = $("#articleId").val();
    data.userId = userId;
    data.comment = comment;
    data.articleId = articleId;
    data.replyCommentId = replyCommentId;

    $.ajax({
        type: "POST",
        url: "/comment/saveReply",
        // async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {

            if (data.status == "success") {
                swal({
                    title: '回复成功！',
                    type: "success",
                    timer: 1000,
                    showConfirmButton: false
                });
                $("#replyModal").modal('hide');
                $("#replyComment").val('');

                var tableId = 'commentTable';
                var tableRowData = {};
                tableRowData.userId = data.data.userId;
                tableRowData.username = data.msg;
                tableRowData.comment = data.data.comment;
                tableRowData.createTime = data.data.createTime;
                tableRowData.commentId = data.data.commentId;
                var insertTr = $('#' + tableId + ' tr:last').clone(true);
                // 将json数据循环追加到表的每一列
                insertTr.children('td').eq(0).html("<a href='/user/show?userId="+tableRowData.userId+"'>"+tableRowData.username+"</a>");
                insertTr.children('td').eq(1).html(tableRowData.comment);
                insertTr.children('td').eq(2).html(tableRowData.createTime);
                insertTr.children('td').eq(3).html("<button class='btn btn-info' type='button' onclick='reply("+tableRowData.commentId+")'>Reply</button>"
                +" "+"<button class='btn btn-warning' type='button' onclick='deleteComment("+tableRowData.commentId+")'>Delete</button>");
                $('#' + tableId + ' tr:last').after(insertTr);
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
            } else  if (data.status = "commentNull") {
                swal({
                    title: '回复不能为空！',
                    type: "error",
                    timer: 1000,
                    showConfirmButton: false
                });
            }else{
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


