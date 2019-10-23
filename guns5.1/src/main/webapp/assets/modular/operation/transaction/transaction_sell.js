layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 持仓管理-持仓列表
     */
    var MgrTransactionSell = {
        tableId: "transactionSellTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrTransactionSell.initColumn = function () {
        return [[
            {field: 'id',  title: '订单号'},
            {field: 'createTime', minWidth:180 , title: '建仓时间'},
            {field: 'endTime',  minWidth:180 , title: '平仓时间'},
            {field: 'loginName', minWidth:140, title: '手机号'},
            {field: 'symbolName', title: '产品名称'},
            {field: 'sellStatus', title: '平仓原因'},
            {field: 'unitPrice',  title: '单价'},
            {field: 'ransactionType', title: '方向'},
            {field: 'money', title: '建仓成本'},
            {field: 'commissionCharges',  title: '手续费'},
            {field: 'isOvernight', title: '是否递延'},
            {field: 'overnightFee', title: '过夜费'},
            {field: 'userCashCouponId', title: '是否代金券'},
            {field: 'ditch', title: '渠道'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    MgrTransactionSell.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MgrTransactionSell.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrTransactionSell.tableId,
        url: Feng.ctxPath + '/transaction/sell/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MgrTransactionSell.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrTransactionSell.search();
    });



});
