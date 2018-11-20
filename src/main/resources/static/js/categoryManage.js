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
        url: '/category/getCategoryList',
        columns: [
            {   field:'categoryName',
                title:'分类名称',
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
            },
            {
                field: 'operation',
                title: '操作',
                align:'center',
                formatter : function(cell, row, index) {
                    // btnEdit = '<a class="btn-success btn-sm"  onclick="viewArticle('+row.articleId+')">查看</a>';
                    btnView = '<a class="btn-primary btn-sm"  onclick="editCategory('+row.categoryId+')">编辑</a>';
                    btnDel = '<button type="button" class="btn btn-danger btn-xs" onclick="deleteCategory('+row.categoryId+')">删除</button>';
                    cell =  btnView +" "+ btnDel;
                    return cell;
                },
            }],
    });


$("#newCategoryBtn").click(function () {
    $("#categoryName").val("");
    $("#categoryId").val("");

    $('#con-close-modal').modal('show');



})

function editCategory(categoryId) {
    $("#categoryId").val(categoryId);


    var data = {};
    data.categoryId = categoryId;
    $.ajax({
        type: "POST",
        url: "/category/view",
        async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            $("#categoryName").val(data.categoryName);
            $("#content").val(data.content);
        }
    });
    $('#con-close-modal').modal('show');

}
$("#updateCategoryBtn").click(function () {



    var c = {};

    c.categoryId = $("#categoryId").val();
    c.categoryName = $("#categoryName").val();
    if (c.categoryId == "" || c.categoryId == undefined || c.categoryId == null) {
        $.ajax({
            type: "POST",
            url: "/category/add",
            async:false,
            data: c,

            //type、contentType必填,指明传参方式
            dataType: "json",

            // contentType: "application/x-www-form-urlencoded",
            // contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data.status == "success") {

                    $("#table").bootstrapTable('refresh',data);
                    $("#categoryId").html("");
                    $("#categoryName").html("");
                    $('#con-close-modal').modal('hide');
                    swal({title: '添加成功！',
                        type:"success",
                        timer:1000,
                        showConfirmButton:false});


                }
                else{
                    swal({title: '添加失败！',
                        type:"error",
                        timer:1000,
                        showConfirmButton:false});



                }
            }
        });
    } else {



    // $.ajax({
    //     type: "POST",
    //     url: "/category/update",
    //     async:false,
    //     data: c,
    //
    //     //type、contentType必填,指明传参方式
    //     dataType: "json",
    //
    //     // contentType: "application/x-www-form-urlencoded",
    //     // contentType: "application/json;charset=utf-8",
    //     success: function (data) {
    //         if (data.status == "success") {
    //             console.log($("#category").val());
    //             $("#category").select2('destroy').empty();
    //             console.log("修改分类成功");
    //
    //             $("#table").bootstrapTable('refresh',data);
    //             $("#categoryId").html("");
    //             $("#categoryName").html("");
    //             swal({title: '保存成功！',
    //                 type:"success",
    //                 timer:1000,
    //                 showConfirmButton:false});
    //
    //
    //         }
    //         else{
    //             swal({title: '保存失败！',
    //                 type:"error",
    //                 timer:1000,
    //                 showConfirmButton:false});
    //
    //
    //
    //         }
    //     }
    // });
    }
})



function detailFormatter(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}
function deleteCategory(categoryId) {
    var data = {};
    data.categoryId = categoryId;
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
                url: "/category/delete",
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
                            '该分类已经被删除。',
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

