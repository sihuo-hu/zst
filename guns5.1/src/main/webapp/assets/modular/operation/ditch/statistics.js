layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 渠道管理--渠道统计管理
     */
    var DitchCoupon = {
        tableId: "ditchStatisticsTable",    //表格id
        condition: {
            timeLimit: "",
            loginName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    DitchCoupon.initColumn = function () {
        return [[
            {field: 'ditch',  title: '渠道'},
            {field: 'ditchUserNumber',  title: '注册量'},
            {field: 'moneyUserNumber',  title: '现金做单客户量'},
            {field: 'moneyNumber',  title: '现金做单量'},
            {field: 'moneySum',  title: '现金建仓总额'},
            {field: 'cashUserNumber',  title: '代金券客户量'},
            {field: 'cashNumber',  title: '代金券做单量'},
            {field: 'cashMoneySum',  title: '代金券建仓总额'},
            {field: 'payAllNumber',  title: '充值申请量'},
            {field: 'paySuccessNumber',  title: '充值成功量'},
            {field: 'payClosedNumber',  title: '充值失败量'},
            {field: 'payMoney',  title: '充值成功金额'}
        ]];
    };


    /**
     * 点击查询按钮
     */
    DitchCoupon.search = function () {
        var queryData = {};
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(DitchCoupon.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + DitchCoupon.tableId,
        url: Feng.ctxPath + '/ditch/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: DitchCoupon.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        DitchCoupon.search();
    });


});
