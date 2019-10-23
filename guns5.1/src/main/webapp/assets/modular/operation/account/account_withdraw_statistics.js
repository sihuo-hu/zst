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
    var MgrWithdrawSAccount = {
        tableId: "accountWithdrawSTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrWithdrawSAccount.initColumn = function () {
        return [[
            {field: 'withdrawUserNumber', title: '提现申请人次'},
            {field: 'withdrawDoneNumber',  title: '提现完成人次'},
            {field: 'sumSponsorMoney',  title: '提现申请金额'},
            {field: 'sumRmbMoney',  title: '提现完成打款金额'}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MgrWithdrawSAccount.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MgrWithdrawSAccount.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrWithdrawSAccount.tableId,
        url: Feng.ctxPath + '/account/withdraw/statistics',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MgrWithdrawSAccount.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrWithdrawSAccount.search();
    });

});
