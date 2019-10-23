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
    var MgrPayAccount = {
        tableId: "accountPayTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrPayAccount.initColumn = function () {
        return [[
            {field: 'id',  title: '订单号'},
            {field: 'orderTime', minWidth:180 , title: '发起充值时间'},
            {field: 'payTime', minWidth:180 , title: '充值到账时间'},
            {field: 'createTime', minWidth:180 , title: '开户时间'},
            {field: 'loginName',  title: '手机号码'},
            {field: 'ditch',  title: '渠道'},
            {field: 'sponsorMoney',  title: '发起支付金额'},
            {field: 'rmbMoney',  title: '实际支付金额'},
            {field: 'payWay',  title: '支付通道'},
            {field: 'payStatus',  title: '状态'}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MgrPayAccount.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MgrPayAccount.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrPayAccount.tableId,
        url: Feng.ctxPath + '/account/pay/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MgrPayAccount.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrPayAccount.search();
    });

});
