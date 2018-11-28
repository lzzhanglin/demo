$('#userTable').bootstrapTable(
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
        search: false,

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
                keyWord:$("#searchForm #keyWord").val(),
                // length: 6,
                // order:'desc'
            };
            console.log("111传输的参数为："+$("#searchForm #keyWord").val());
            return temp;

        },
        //queryParamsType: 'limit',
        striped : true,
        // pageNumber:1,
        // pageSize:15,
        //pageSize:9999,
        //limit:20,
        // idField:"userId",
        search: false,
        // undefinedText:"",
        showRefresh: false,
        url: '/search/userList',
        columns: [
            {   field:'username',
                title:'用户名',
                align:'center',
                showSelectTitle:true,
                formatter:function (value, row, index) {
                    value='<a href="/user/show?userId='+row.userId+'"'+">"+value+"</a>";
                    return value;
                    // return [
                    //     '<a href="/article/edit?articleId='+'+row.articleId+'+"></a>'
                    // ].join("")
                },

                // visible:false,
                /*               formatter:function(value,row,index){
                                  //通过formatter可以自定义列显示的内容
                                  //value：当前field的值，即id
                                  //row：当前行的数据
                                  value='<a href="/testPlan/getCaseIndex?id='+row.id+'"'+">"+value+"</a>";
                                  return value
                              } */
            },
            {   field:'articleNum',
                title:'文章',
                align:'center',
                showSelectTitle:true,

            },
            {
                field: 'operation',
                title: '操作',
                align:'center',
                formatter : function(cell, row, index) {
                    btnEdit = '<a class="btn-success btn-sm"  onclick="viewUser('+row.userId+')">查看</a>';
                    if (row.isFollowCurrentUser == 1) {
                        btnView = '<a class="btn-primary btn-sm"  onclick="cancelFollow('+row.userId+')">已关注</a>';


                    } else {
                        btnView = '<a class="btn-info btn-sm"  onclick="addFollow('+row.userId+')">关&nbsp;&nbsp;&nbsp;&nbsp;注</a>';

                    }
                    // btnDel = '<button type="button" class="btn btn-danger btn-xs" onclick="deleteArticle('+row.articleId+')">删除</button>';
                    cell = btnEdit +" " + btnView;
                    return cell;
                },
            }],
    });

$("#searchBtn").click(function () {
    console.log("点击了搜索按钮");
    var data = {};
    data.keyWord = $("#keyWord").val();
    $.ajax({
        type: "POST",
        url: "/search/userList",
        async:false,
        data: JSON.stringify(data),

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            $("#userTable").bootstrapTable('refresh', data);

            console.log("成功接收服务端返回的数据");
        }
    });



})


function viewUser(userId) {
    var data = {};
    data.userId = userId;
    $.ajax({
        type: "POST",
        url: "/user/view",
        // async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            $("#username").val(data.username);
            $("#nickname").val(data.nickname);
            $("#phone").val(data.phone);
            $("#homeAddr").val(data.homeAddr);
            $("#school").val(data.school);
            $("#describe").val(data.describe);
            $("#birthday").val(data.birthday);


            $("#title").attr("readonly", "readonly");
            $("#username").attr("readonly", "readonly");
            $("#nickname").attr("readonly", "readonly");
            $("#phone").attr("readonly", "readonly");
            $("#homeAddr").attr("readonly", "readonly");
            $("#birthday").attr("readonly", "readonly");
            $("#school").attr("readonly", "readonly");
            $("#describe").attr("readonly", "readonly");
        }
    });
    $('#userModal').modal('show');

}
//
//这里传入的userId是想关注的人的用户id 不是当前登录的用户id 后台会把userId转换为followUserId
function addFollow(userId) {


    var data = {};
    data.userId = userId;
    $.ajax({
        type: "POST",
        url: "/follow/add",
        async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data.status == "success") {
                swal({
                    title: '关注成功！',
                    type: "success",
                    timer: 1000,
                    showConfirmButton: false
                });
                $("#userTable").bootstrapTable('refresh', data);
            } else {
                swal({title: '关注失败！',
                    type:"error",
                    timer:1000,
                    showConfirmButton:false});
            }

        }
    });

}

function cancelFollow(userId) {


    var data = {};
    data.userId = userId;
    $.ajax({
        type: "POST",
        url: "/follow/cancel",
        async:false,
        data: data,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data.status == "success") {
                swal({
                    title: '取消关注成功！',
                    type: "success",
                    timer: 1000,
                    showConfirmButton: false
                });
                $("#userTable").bootstrapTable('refresh', data);
            } else {
                swal({title: '取消关注失败！',
                    type:"error",
                    timer:1000,
                    showConfirmButton:false});
            }


        }
    });

}

