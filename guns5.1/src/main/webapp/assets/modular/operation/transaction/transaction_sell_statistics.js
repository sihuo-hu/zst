layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 系统管理--用户管理
     */
    var MgrTransactionSellS = {
        tableId: "transactionSellStatisticsTable",    //表格id
        condition: {
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrTransactionSellS.initColumn = function () {
        return [[
            {field: 'userNumber', title: '平仓人数'},
            {field: 'sellNumber', title: '平仓单数'},
            {field: 'profitOrderNumber', title: '盈利平仓单数'},
            {field: 'equalityNumber',title: '盈亏相等单数'},
            {field: 'profitLossNumber',  title: '客户盈亏金额'},
            {field: 'moneyNumber',  title: '建仓总成本'},
            {field: 'profitNumber',  title: '客户盈利金额'},
            {field: 'lossNumber',  title: '客户亏损金额'},
            {field: 'commissionCharges',  title: '手续费总金额'},
            {field: 'lotNumber',  title: '平仓总手数'},
            {field: 'overnightFee', title: '过夜费总额'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    MgrTransactionSellS.search = function () {
        var queryData = {};
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MgrTransactionSellS.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrTransactionSellS.tableId,
        url: Feng.ctxPath + '/transaction/sell/statistics',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MgrTransactionSellS.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrTransactionSellS.search();
    });


});
