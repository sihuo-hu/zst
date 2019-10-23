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
    var MgrWithdrawAccount = {
        tableId: "accountWithdrawTable",    //表格id
        condition: {
            name: "",
            timeLimit: "",
            batchId: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrWithdrawAccount.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', title: '订单号'},
            {field: 'orderTime', minWidth:180 , title: '提现申请时间'},
            {field: 'payTime', minWidth:180 , title: '提现处理时间'},
            {field: 'createTime', minWidth:180 , title: '开户时间'},
            {field: 'loginName',  title: '手机号码'},
            {field: 'rechargeAmount',  title: '总充值'},
            {field: 'commission', title: '手续费'},
            {field: 'ditch',  title: '渠道'},
            {field: 'sponsorMoney',  title: '提现金额'},
            {field: 'exchangeRate',  title: '汇率'},
            {field: 'rmbMoney',  title: '人民币汇款金额'},
            {field: 'payStatus',  title: '状态'},
            {field: 'batchId',  title: '批次ID'},
            {field: 'bankCard', hide: true, title: '银行卡号'},
            {field: 'bankOfDeposit', hide: true,  title: '开户行'},
            {field: 'branch', hide: true, title: '分行'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 150}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MgrWithdrawAccount.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        queryData['batchId'] = $("#batchId").val();
        table.reload(MgrWithdrawAccount.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrWithdrawAccount.tableId,
        url: Feng.ctxPath + '/account/withdraw/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MgrWithdrawAccount.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrWithdrawAccount.search();
    });

    /**
     * 导出excel按钮
     */
    MgrWithdrawAccount.exportExcel = function () {

        var checkRows = table.checkStatus(MgrWithdrawAccount.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            var ids = '';
            for(var i = 0;i<checkRows.data.length;i++){
                console.log(checkRows.data[i].id);
                ids = ids+checkRows.data[i].id+",";
            }
            layer.load();
            window.location.href=Feng.ctxPath + "/account/withdraw/exportExcel?ids="+ids;
            setTimeout(function(){
                MgrWithdrawAccount.search()
            },2000)
        }
        layer.closeAll('loading'); //关闭loading
    };

    // 导出excel
    $('#btnExp').click(function () {
        MgrWithdrawAccount.exportExcel();
    });

    /**
     * 提现完成按钮
     */
    MgrWithdrawAccount.withdrawDone = function () {
        var checkRows = table.checkStatus(MgrWithdrawAccount.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要完成提现的数据");
        } else {
            var ids = '';
            for (var i = 0; i < checkRows.data.length; i++) {
                console.log(checkRows.data[i].id);
                ids = ids + checkRows.data[i].id + ",";
            }
            Feng.confirm("是否已完成提现?", function () {
                layer.load();
                var ajax = new $ax(Feng.ctxPath + "/account/withdraw/done", function (data) {
                    Feng.success("提现成功!");
                }, function (data) {
                    Feng.error("提现失败!");
                });
                ajax.set("ids", ids);
                ajax.start();
                layer.closeAll('loading'); //关闭loading
                MgrWithdrawAccount.search();
            });
        }
    };

    // 设置为已打款
    $('#btnDone').click(function () {
        MgrWithdrawAccount.withdrawDone();
    });


    /**
     * 修改提现状态
     *
     * @param data 点击按钮时候的行数据
     */
    MgrWithdrawAccount.onEdit = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑提现',
            content: Feng.ctxPath + '/account/withdraw/toUpdate?id=' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(MgrWithdrawAccount.tableId);
            }
        });
    };

    /**
     * 查看账户详情
     *
     * @param data 点击按钮时候的行数据
     */
    MgrWithdrawAccount.onInfo = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '账户详情',
            content: Feng.ctxPath + '/account/withdraw/toAccountInfo?loginName=' + data.loginName,
            end: function () {
                admin.getTempData('formOk') && table.reload(MgrWithdrawAccount.tableId);
            }
        });
    };

    // 工具条点击事件
    table.on('tool(' + MgrWithdrawAccount.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'info') {
            MgrWithdrawAccount.onInfo(data);
        } else if (layEvent === 'edit') {
            MgrWithdrawAccount.onEdit(data);
        }
    });

});
