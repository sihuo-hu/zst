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
    var MgrTransactionBuyS = {
        tableId: "transactionBuyStatisticsTable",    //表格id
        condition: {
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrTransactionBuyS.initColumn = function () {
        return [[
            {field: 'commissionCharges',  title: '未平仓手续费'},
            {field: 'buyNumber',  title: '持仓数量'},
            {field: 'lotNumber', title: '持仓手数'},
            {field: 'userNumber',title: '交易用户数'},
            {field: 'overnightFee',  title: '过夜费总额'},
            {field: 'moneyNumber', title: '建仓总成本'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    MgrTransactionBuyS.search = function () {
        var queryData = {};
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MgrTransactionBuyS.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrTransactionBuyS.tableId,
        url: Feng.ctxPath + '/transaction/buy/statistics',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MgrTransactionBuyS.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrTransactionBuyS.search();
    });


});
