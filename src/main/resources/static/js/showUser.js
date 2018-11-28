$().ready(function () {

    var num = $("#userPageTable").find("tr").length;
    if (num == 1) {
        $("#tip").html('他/她 还没有发表文章呢!  (๑>m<๑)');
    }
})