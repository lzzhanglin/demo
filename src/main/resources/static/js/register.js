

$().ready(function() {
  var validate =  $("#registerForm").validate({
        rules: {
            username: {
                required: true,
                isExistUsername:true,
                rangelength: [1, 15]
            },
            email:{
                required: true,
                isExistEmail:true
            },
            password: {
                required: true,
                rangelength: [1, 15]
            },
            passwordR: {
                required:true,
                rangelength: [1, 15],
                equalTo:"[name='password']"
            }
        },
        messages: {
            username: {
                required: "不能为空",
                rangelength: "长度{0}-{1}之间"
            },
            email:{
              required: "不能为空"
            },
            password: {
                required: "不能为空",
                rangelength: "长度{0}-{1}之间"
            },
            passwordR: {
                required: "不能为空",
                rangelength: "长度{0}-{1}之间",
                equalTo:"两次密码不一致"
            }
        }


    });
    validate.resetForm();

});
// $("#registerForm").validate().resetForm();


//
// $("#password").change(function(){
//     var password = $("#registerForm #password").val();
//     var passwordR = $("#registerForm #passwordR").val();
//     if (passwordR != undefined && passwordR != null &&passwordR != "") {
//         if (password != passwordR) {
//             $("#registerForm #passwordTip").text("两次密码输入不一致");
//         } else {
//             $("#registerForm #passwordTip").text("");
//
//         }
//     }
//
// });

// $("#passwordR").change(function(){
//     var password = $("#registerForm #password").val();
//     var passwordR = $("#registerForm #passwordR").val();
//     if (password != undefined && password != null && password != "") {
//         if (password != passwordR) {
//             $("#registerForm #passwordTip").text("两次密码输入不一致");
//
//         } else {
//             $("#registerForm #passwordTip").text("");
//
//         }
//     }
//
// });


jQuery.validator.addMethod("isExistUsername", function(value, element) {
    var username = $("#registerForm #username").val();
    var param = {};
    var canRegister = false;
    param.username = username;
    $.ajax({
        type: "POST",
        url: "/isExistUsername",
        async:false,
        data:param,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data.status == "failed") {
                canRegister = false;
            } else {
                canRegister = true;


            }
        }
    });
    return canRegister;

}, "用户名已存在");

jQuery.validator.addMethod("isExistEmail", function(value, element) {
    var email = $("#registerForm #email").val();
    var param = {};
    param.email = email;
    var canRegister = false;
    $.ajax({
        type: "POST",
        url: "/isExistEmail",
        async:false,
        data:param,

        //type、contentType必填,指明传参方式
        dataType: "json",

        // contentType: "application/x-www-form-urlencoded",
        // contentType: "application/json;charset=utf-8",
        success: function (data) {
            if (data.status == "failed") {
                canRegister = false;
            } else {
                canRegister = true;


            }
        }
    });
    return canRegister;

}, "邮箱已被占用");
