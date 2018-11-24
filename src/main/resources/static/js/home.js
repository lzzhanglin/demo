$().ready(function () {

    console.log($("#homeTable").find("tr").length);
    var num = $("#homeTable").find("tr").length;
    if (num == 1) {
        $("#tip").html('你还没有发表文章哦，快去写文章吧!');
    }
})