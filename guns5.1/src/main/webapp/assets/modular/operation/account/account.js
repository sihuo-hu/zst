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
    var MgrAccount = {
        tableId: "accountTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrAccount.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id',  title: '订单号'},
            {field: 'loginName',  title: '账号'},
            {field: 'amountType',  title: '交易类型'},
            {field: 'orderTime',  title: '下单时间'},
            {field: 'money',  title: '美元'},
            {field: 'rmbMoney',  title: '人民币'},
            {field: 'commission',  title: '手续费'},
            {field: 'bankCard',  title: '银行卡号'},
            {field: 'payStatus',  title: '支付状态'},
            {field: 'payNo',  title: '支付单号'},
            {field: 'payWay', title: '支付渠道'},
            {field: 'payTime', title: '支付时间'},
            {field: 'payMsg',  title: '支付信息'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 50}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MgrAccount.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        queryData['amountType'] = $("#amountType").val();
        table.reload(MgrAccount.tableId, {where: queryData});
    };

    /**
     * 导出excel按钮
     */
    MgrAccount.exportExcel = function () {
        var checkRows = table.checkStatus(MgrAccount.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };


    /**
     * 提现成功
     *
     * @param data 点击按钮时候的行数据
     */
    MgrAccount.withdraw = function (data) {
        Feng.confirm("是否已完成提现?", function () {
            var ajax = new $ax(Feng.ctxPath + "/account/withdraw", function (data) {
                Feng.success("提现成功!");
            }, function (data) {
                Feng.error("提现失败!");
            });
            ajax.set("accountId", data.id);
            ajax.start();
        });
    };
    /**
     * 提现审核
     *
     * @param data 点击按钮时候的行数据
     */
    MgrAccount.withdrawAudit = function (data) {
        Feng.confirm("是否已完成审核?", function () {
            var ajax = new $ax(Feng.ctxPath + "/account/withdrawAudit", function (data) {
                Feng.success("审核成功!");
            }, function (data) {
                Feng.error("审核失败!");
            });
            ajax.set("accountId", data.id);
            ajax.start();
        });
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrAccount.tableId,
        url: Feng.ctxPath + '/account/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MgrAccount.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    layui.use('flow', function(){
        var flow = layui.flow;
        //当你执行这样一个方法时，即对页面中的全部带有lay-src的img元素开启了懒加载（当然你也可以指定相关img）
        flow.lazyimg();
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrAccount.search();
    });

    // 导出excel
    $('#btnExp').click(function () {
        MgrAccount.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + MgrAccount.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'withdraw') {
            MgrAccount.withdraw(data);
        }
        if (layEvent === 'withdrawAudit') {
            MgrAccount.withdrawAudit(data);
        }
    });

});
