// $(function() {
//     console.log("hello");
//
//     $("#loginBtn").click(function () {
//         var username = $("#loginForm #username").val();
//         var password = $("#loginForm #password").val();
//         var param = {};
//         param.username = username;
//         param.password = password;
//         $.ajax({
//                 type: "POST",
//                 url: "/loginPost",
//                 // data: $("#loginForm").serialize(),
//                 data:param,
//                 async: false,
//                 xhrFields: {
//                     withCredentials: true
//                 },
//                 crossDomain: true,
//                 //type、contentType必填,指明传参方式
//                 dataType: "json",
//
//             // contentType: "application/x-www-form-urlencoded",
//             contentType: "application/json;charset=utf-8",
//             success: function (data) {
//                     // debugger;
//                     var jsonData = JSON.parse(data);
//                 //前端调用成功后，可以处理后端传回的json格式数据。
//                 if (data.status == 302) {
//                     location.href = data.location;
//                     console.log("denglu success");
//                     alert("登录成功");
//                 }
//                 console.log("login success");
//             }
//         });
//         // debugger;
//     })
//
//
// });