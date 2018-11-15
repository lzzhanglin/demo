$('#table').bootstrapTable(
    {
    cache: false,
    method: 'post',
        escape:true,
        sortable: false,
    pageList: [5,10,15,20,30],
    pagination: true,
        pageNumber:1,
        pageSize:10,
    sidePagination: 'server',
        sortOrder:'userId',

        //sidePagination: 'client',
    //  queryParamsType: queryParams,
    // queryParams :$("#userId").val(),
        queryParams: function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize: params.limit,   //页面大小
                offset: params.offset,  //偏移量
                // pageSize:10,
                // pageNumber:1,
                userId:$("#userId").val(),
                // length: 6,
                // order:'desc'
            };
            // console.log(temp);
            return temp;

        },
    //queryParamsType: 'limit',
    striped : true,
    // pageNumber:1,
    // pageSize:15,
    //pageSize:9999,
    //limit:20,
    // idField:"userId",
    search: true,
    // undefinedText:"",
    showRefresh: true,
    url: '/article/getArticleList',
    columns: [
        {   field:'username',
            title:'用户名',
            align:'center',
            showSelectTitle:true,

            // visible:false,
            /*               formatter:function(value,row,index){
                              //通过formatter可以自定义列显示的内容
                              //value：当前field的值，即id
                              //row：当前行的数据
                              value='<a href="/testPlan/getCaseIndex?id='+row.id+'"'+">"+value+"</a>";
                              return value
                          } */
        }, {
            field:'title',
            title:'标题',
            align:'center',
            formatter:function (value, row, index) {
                value='<a href="/article/edit?id='+row.articleId+'"'+">"+value+"</a>";
                return value;
                // return [
                //     '<a href="/article/edit?articleId='+'+row.articleId+'+"></a>'
                // ].join("")
                },


                visible:true,
        },  {
            field:'createTime',
            title:'发表时间',
            visible:true,
            align:'center',
        },
              {
                 field: 'operation',
                 title: '操作',
                  align:'center',
                 formatter : function(cell, row, index) {
                     btnEdit = '<a class="btn-success btn-sm"  onclick="viewArticle('+row.articleId+')">查看</a>';
                     btnView = '<a class="btn-primary btn-sm"  onclick="editArticle('+row.articleId+')">编辑</a>';
                     btnDel = '<button type="button" class="btn btn-danger btn-xs" onclick="deleteArticle('+row.articleId+')">删除</button>';
                     cell = btnEdit +" "+ btnView +" "+ btnDel;
                     return cell;
                 },
             }],
});


function viewArticle(articleId) {
    var data = {};
    data.articleId = articleId;
    $.ajax({
        type: "POST",
        url: "/article/view",
        // async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            $("#title").val(data.title);
            $("#content").val(data.content);
            $("#updateArticleBtn").hide();
            $("#title").attr("readonly", "readonly");
            $("#content").attr("readonly", "readonly");
        }
    });
    $('#con-close-modal').modal('show');
    
}


function editArticle(articleId) {
    $("#articleId").val(articleId);
    $("#updateArticleBtn").show();
    $("#title").removeAttr("readonly", "readonly");
    $("#content").removeAttr("readonly", "readonly");


    var data = {};
    data.articleId = articleId;
    $.ajax({
        type: "POST",
        url: "/article/view",
        async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            $("#title").val("");
            $("#content").val("");
            $("#title").val(data.title);
            $("#content").val(data.content);
        }
    });
    $('#con-close-modal').modal('show');

}
$("#updateArticleBtn").click(function () {


    var title = $("#title").val();
    var content = $("#content").val();
    var blog = {};

    blog.articleId = $("#articleId").val();
    blog.title = title;
    blog.content = content;

    $.ajax({
        type: "POST",
        url: "/article/update",
        async:false,
        data: blog,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            delete blog.articleId;
            delete blog.title;
            delete blog.content;
            if (data.status == "failed") {
                swal({title: '保存失败！',
                    type:"error",
                    timer:1000,
                    showConfirmButton:false});

            } else if (data.status == "illegalArgument") {
                swal({
                    title: '标题不能为空！',
                    type: "error",
                    timer: 1000,
                    showConfirmButton: false
                });
            }
            else{
                $("#table").bootstrapTable('refresh',data);
                $("#title").html("");
                $("#content").html("");
                swal({title: '保存成功！',
                    type:"success",
                    timer:1000,
                    showConfirmButton:false});



            }
        }
    });
})



function detailFormatter(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}
function deleteArticle(articleId) {
    var data = {};
    data.articleId = articleId;
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
                url: "/article/delete",
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
                        $("#table").bootstrapTable('refresh',data);
                        swal(
                            '删除！',
                            '该文章已经被删除。',
                            'success'
                        );
                        // swal({title: '删除成功！',
                        //     type:"success",
                        //     timer:1000,
                        //     showConfirmButton:false});


                    }
                }
            });

        }


    })




    }

