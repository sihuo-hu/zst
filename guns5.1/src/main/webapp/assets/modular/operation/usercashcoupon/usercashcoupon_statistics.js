layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 代金券管理--代金券管理
     */
    var MarUserCashStatisticsCoupon = {
        tableId: "userCashCouponStatisticsTable",    //表格id
        condition: {
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MarUserCashStatisticsCoupon.initColumn = function () {
        return [[
            {field: 'cashCouponPNumber',  title: '送券人数'},
            {field: 'allNumber', title: '送券张数'},
            {field: 'used',  title: '使用张数'},
            {field: 'pastDueNumber',  title: '过期张数'},
            {field: 'unused',  title: '未使用张数'},
            {field: 'profit',  title: '盈利'},
            {field: 'loss',  title: '亏损'}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MarUserCashStatisticsCoupon.search = function () {
        var queryData = {};
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MarUserCashStatisticsCoupon.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MarUserCashStatisticsCoupon.tableId,
        url: Feng.ctxPath + '/user/cashcoupon/statistics/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MarUserCashStatisticsCoupon.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MarUserCashStatisticsCoupon.search();
    });


});
