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
    var MarUserCashCoupon = {
        tableId: "userCashCouponTable",    //表格id
        condition: {
            timeLimit: "",
            loginName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MarUserCashCoupon.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true,  title: '用户优惠券ID'},
            {field: 'userCreateTime', minWidth:180 , title: '注册时间'},
            {field: 'pushTime', minWidth:180 , title: '赠送时间'},
            {field: 'startTime', minWidth:180 , title: '开始时间'},
            {field: 'pastDueTime', minWidth:180 , title: '过期时间'},
            {field: 'loginName', title: '用户手机号'},
            {field: 'ccMoney',  title: '券类型'},
            {field: 'ccName',  title: '送券原因详情'},
            {field: 'useStatus',  title: '是否使用'},
            {field: 'profit',  title: '盈亏'},
            {field: 'ditch',  title: '渠道'}

        ]];
    };


    /**
     * 点击查询按钮
     */
    MarUserCashCoupon.search = function () {
        var queryData = {};
        queryData['timeLimit'] = $("#timeLimit").val();
        queryData['loginName'] = $("#loginName").val();
        table.reload(MarUserCashCoupon.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MarUserCashCoupon.tableId,
        url: Feng.ctxPath + '/user/cashcoupon/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MarUserCashCoupon.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MarUserCashCoupon.search();
    });


});
