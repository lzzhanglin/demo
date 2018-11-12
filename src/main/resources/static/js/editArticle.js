$().ready(function() {
    var articleId = $("#articleIdH").val();
var title = $("#titleH").val();
var content = $("#contentH").val();
    console.log("title is: " + title + "***" + content);
    if (title != null && title != "" && title != undefined) {
        $("#articleId").val(articleId);
        $("#title").val(title);
        editor.txt.html(content);
    }
});
var E = window.wangEditor
var editor = new E('#editor')
// 或者 var editor = new E( document.getElementById('editor') )
editor.create()

$("#editArticleBtn").click(function () {
    $("#articleForm #title").removeAttr("readonly", "readonly");
    editor.$textElem.attr('contenteditable', true)
})

$("#saveArticleBtn").click(function () {

    var articleId = $("#articleForm #articleId").val();
    var userId = $("#articleForm #userId").val();
    var title = $("#title").val();
    var content = editor.txt.text();
    var article = {};
    article.userId = userId;
    article.title = title;
    article.content = content;
    article.articleId = articleId;
    console.log("文章id为：" + articleId);
    if (articleId == "" || articleId == null || articleId == undefined) {
        //articleId 为空 保存操作
        $.ajax({
            type: "POST",
            url: "/article/save",
            // async:false,
            data: article,

            //type、contentType必填,指明传参方式
            dataType: "json",

            // contentType: "application/x-www-form-urlencoded",
            // contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data.status == "failed") {
                    swal({
                        title: '保存失败！',
                        type: "error",
                        timer: 1000,
                        showConfirmButton: false
                    });

                    console.log("save failed")
                } else if (data.status == "illegalArgument") {
                    swal({
                        title: '标题不能为空！',
                        type: "error",
                        timer: 1000,
                        showConfirmButton: false
                    });
                }
                else
                {
                    swal({
                        title: '保存成功！',
                        type: "success",
                        timer: 1000,
                        showConfirmButton: false
                    });
                    $("#articleForm #articleId").val(data.data);
                    $("#articleForm #title").attr("readonly", "readonly");


                    editor.$textElem.attr('contenteditable', false)

                    console.log("save success");

                }
            }
        });

    } else {
        //articleId不为空 更新操作
        $.ajax({
            type: "POST",
            url: "/article/update",
            // async:false,
            data: article,

            //type、contentType必填,指明传参方式
            dataType: "json",

            // contentType: "application/x-www-form-urlencoded",
            // contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data.status == "failed") {
                    swal({title: '保存失败！',
                        type:"error",
                        timer:1000,
                        showConfirmButton:false});

                    console.log("save failed")
                } else if (data.status == "illegalArgument") {
                    swal({
                        title: '标题不能为空！',
                        type: "error",
                        timer: 1000,
                        showConfirmButton: false
                    });
                }
                else{
                    swal({title: '保存成功！',
                        type:"success",
                        timer:1000,
                        showConfirmButton:false});
                    // $("#articleForm #articleId").val(data.data);
                    $("#articleForm #title").attr("readonly", "readonly");

                    editor.$textElem.attr('contenteditable', false)

                    console.log("save success");

                }
            }
        });

    }


})

