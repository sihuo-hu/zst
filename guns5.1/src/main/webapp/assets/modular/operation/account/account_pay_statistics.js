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
    var MgrPaySAccount = {
        tableId: "accountPaySTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrPaySAccount.initColumn = function () {
        return [[
            {field: 'sponsorMoney',  title: '发起支付金额'},
            {field: 'rmbMoney',  title: '实际支付金额'},
            {field: 'successNumber', title: '实际到账充值次数'}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MgrPaySAccount.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MgrPaySAccount.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrPaySAccount.tableId,
        url: Feng.ctxPath + '/account/pay/statistics',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MgrPaySAccount.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrPaySAccount.search();
    });

});
