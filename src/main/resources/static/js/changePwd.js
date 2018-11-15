$("#changePwdBtn").click(function () {

    var oldPwd = $("#oldPwd").val();
    var newPwd = $("#newPwd").val();
    var newPwdR = $("#newPwdR").val();

    var password = {};
    password.oldPwd = oldPwd;
    password.newPwd = newPwd;
    password.newPwdR = newPwdR;
    $.ajax({
        type: "POST",
        url: "/user/changePwdPost",
        async:false,
        data: password,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data.status == "not_same") {
                swal({title: '两次密码输入不一致！',
                    type:"error",
                    timer:1000,
                    showConfirmButton:false});

                console.log("save failed")
            } else if (data.status == "failed") {
                swal({
                    title: '密码错误！',
                    type: "error",
                    timer: 1000,
                    showConfirmButton: false
                });




            } else {
                swal({
                    title: '修改成功！',
                    type: "success",
                    timer: 1000,
                    showConfirmButton: false
                });
            }
        }
    });




})