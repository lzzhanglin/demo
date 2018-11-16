$().ready(function() {
    var articleId = $("#articleIdH").val();
var title = $("#titleH").val();
var content = $("#contentH").val();
var categoryH = $("#categoryH").val();
    if (title != null && title != "" && title != undefined) {
        $("#articleId").val(articleId);
        $("#title").val(title);

        console.log(categoryH);
        editor.txt.html(content);
    }

        $('#category').select2({
            width:"300px",
            placeholder:"请选择分类",
            allowClear:true,
            language: "zh-CN",
            multiple: true,
            maximumSelectionLength:1
            // minimumResultsForSearch: -1
        });

    $.ajax({
        type: "POST",
        url: "/category/getCategory",
        // async:false,
        data: 1,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {

            for (var i = 0; i < data.length; i++) {
                $("#category").append("<option value="+data[i].categoryId+">" + data[i].categoryName + "</option>");
            }
            console.log("隐藏框的值为：" + categoryH);
            $("#category").val(categoryH).trigger('change');



        }
    });
    console.log("选中的值为： " + $("#category").val());
    // $("#category").select2().select2("val", null);
    // $('select').select2().select2('val', $('.select2 option:eq(1)').val());
    // $("#category").val("               ").select2();
    // $("#category").select2('val', "");
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
    var categoryId = $("#category").val().shift();
    var article = {};
    article.userId = userId;
    article.title = title;
    article.content = content;
    article.articleId = articleId;
    article.categoryId = categoryId;
    console.log(article.categoryId);
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

