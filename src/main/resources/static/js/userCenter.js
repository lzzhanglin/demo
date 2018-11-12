$().ready(function(){

    // var validate =
    //     $("#userProfileForm").validate({
    //     rules: {
    //         nickname: {
    //             required: true,
    //             number:true
    //         },
    //         phone:{
    //             required: true,
    //             rangelength: [11]
    //
    //         },
    //         describe: {
    //             required: true
    //         },
    //         homeAddr: {
    //             required: true
    //         },
    //         school: {
    //             required: true
    //         },
    //         birthday: {
    //             required:true
    //
    //         }
    //     },
    //     messages: {
    //         nickname: {
    //             required: "不能为空"
    //         },
    //         homeAddr:{
    //             required: "不能为空"
    //         },
    //         school: {
    //             required: "不能为空"
    //         },
    //
    //         phone: {
    //             required: "不能为空",
    //             rangelength: "电话号码长度必须为{0}位"
    //         },
    //         describe: {
    //             required: "不能为空"
    //         },
    //         birthday: {
    //             required: "不能为空"
    //         }
    //     }
    //
    //
    // });
    // validate.resetForm();

    //
    $("#nickname").attr("readonly","readonly");
    $("#phone").attr("readonly","readonly");
    $("#birthday").attr("readonly","readonly");
    $("#homeAddr").attr("readonly","readonly");
    $("#school").attr("readonly","readonly");
    $("#tag").attr("readonly","readonly");
    $("#describe").attr("readonly","readonly");
});
$('#birthday').datetimepicker({
    // inline: true,
    // sideBySide: true,
    // locale: 'cn'
    format: 'L'

});


$("#editProfileBtn").click(function () {
    $("#nickname").removeAttr("readonly");
    $("#phone").removeAttr("readonly");
    $("#birthday").removeAttr("readonly");
    $("#homeAddr").removeAttr("readonly");
    $("#school").removeAttr("readonly");
    $("#tag").removeAttr("readonly");
    $("#describe").removeAttr("readonly");


})
$("#saveProfileBtn").click(function () {


    var username = $("#username").val();
    var nickname = $("#nickname").val();
    var phone = $("#phone").val();
    var birthday = $("#birthday").val();
    var homeAddr = $("#homeAddr").val();
    var school = $("#school").val();
    var tag = $("#tag").val();
    var describe = $("#describe").val();
    var user = {};
    user.username = username;
    user.nickname = nickname;
    user.phone = phone;
    user.birthday = birthday;
    user.homeAddr = homeAddr;
    user.school = school;
    user.tag = tag;
    user.describe =describe;
    $.ajax({
        type: "POST",
        url: "/user/saveProfile",
        async:false,
        data: user,

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
            } else {
                swal({title: '保存成功！',
                    type:"success",
                    timer:1000,
                    showConfirmButton:false});
                $("#nickname").attr("readonly","readonly");
                $("#phone").attr("readonly","readonly");
                $("#birthday").attr("readonly","readonly");
                $("#homeAddr").attr("readonly","readonly");
                $("#school").attr("readonly","readonly");
                $("#tag").attr("readonly","readonly");
                $("#describe").attr("readonly","readonly");

            }
        }
    });




})