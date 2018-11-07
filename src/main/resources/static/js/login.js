

$().ready(function() {
    var validate =  $("#loginForm").validate({
        rules: {
            username: {
                required: true,
            },

            password: {
                required: true,
            }
        },
        messages: {
            username: {
                required: "不能为空"
            },

            password: {
                required: "不能为空",
            }
        }


    });
    validate.resetForm();

});
console.log("hello");


