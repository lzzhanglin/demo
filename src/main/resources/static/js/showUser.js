$().ready(function () {

    var num = $("#userPageTable").find("tr").length;
    if (num == 1) {
        if ($("#isMyPage").val() == 1) {
            $("#tip").html('你还没有发表文章呢,快去写文章吧!  (๑>m<๑)');
        } else {
            $("#tip").html('他/她 还没有发表文章呢!  (๑>m<๑)');

        }
    }
})