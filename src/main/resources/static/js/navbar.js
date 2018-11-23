
$("#searchBtn").click(function () {
    window.location.href = '/search/result';
    $("#table").bootstrapTable('refresh',data);
    console.log("hello");

})