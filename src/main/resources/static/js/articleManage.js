$('#table').bootstrapTable(
    {
    cache: false,
    method: 'post',
    pageList: [10,15,20,30,50],
    pagination: true,
    sidePagination: 'server',
    //sidePagination: 'client',
    //  queryParamsType: queryParams,
    queryParams :$("#userId").val(),
    //queryParamsType: 'limit',
    striped : true,
    pageNumber:1,
    pageSize:15,
    //pageSize:9999,
    //limit:20,
    idField:"id",
    search: true,
    undefinedText:"",
    showRefresh: true,
    url: '/article/getArticleList',
    columns: [
        {   field:'userId',
            title:'用户id',
            visible:true,
            /*               formatter:function(value,row,index){
                              //通过formatter可以自定义列显示的内容
                              //value：当前field的值，即id
                              //row：当前行的数据
                              value='<a href="/testPlan/getCaseIndex?id='+row.id+'"'+">"+value+"</a>";
                              return value
                          } */
        }, {
            field:'title',
            title:'标题',
            visible:true,
        },  {
            field:'createTime',
            title:'发表时间',
            visible:true,
        }
        /*       {
                 field: 'operation',
                 title: '操作',
                 formatter : function(cell, row, index) {
                     btnEdit = '<a class="btn-warning btn-sm"   href="/testPlan/editSkip?id='+row.id+'">编辑</a>';
                     btnDetail = '<a class="btn-primary btn-sm"   href="/testPlan/getRecordById?id='+row.id+'">查看</a>';
                     btnDel = '<button type="button" class="btn btn-danger btn-xs" onclick="deleteCase('+row.id+')">删除</button>';
                     btnStatistics= '<a class="btn-primary btn-sm" href="/testPlan/getTestPlanReport?id='+row.id+'">结果统计</a>';
                     btnCaseAssociation= '<button type="button" class="btn-success  btn-xs" onclick="caseAssociation('+row.id+')">关联用例</button>';
                     cell =btnEdit+btnDetail+btnDel+btnCaseAssociation+btnStatistics;
                     return cell;
                 },
             } */],
});
console.log("hello");