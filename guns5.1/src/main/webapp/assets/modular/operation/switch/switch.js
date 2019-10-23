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
    var MarSwitch = {
        tableId: "switchTable",    //表格id
        condition: {
            ditch: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MarSwitch.initColumn = function () {
        return [[
            {field: 'id', hide: true, title: '优惠券ID'},
            {field: 'create_time',  title: '创建时间'},
            {field: 'platform',  title: '平台'},
            {field: 'ditch',  title: '渠道'},
            {field: 'versions',  title: '版本号'},
            {field: 'transaction', templet: '#transactionTpl', title: '交易'},
            {field: 'capital', templet: '#capitalTpl', title: '充值'}

        ]];
    };


    /**
     * 点击查询按钮
     */
    MarSwitch.search = function () {
        var queryData = {};
        queryData['ditch'] = $("#ditch").val();
        table.reload(MarSwitch.tableId, {where: queryData});
    };

    /**
     * 弹出添加用户对话框
     */
    MarSwitch.openAddUser = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加版本',
            area: '400px',
            offset: '50px',
            content: Feng.ctxPath + '/switch/switch_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(MarSwitch.tableId);
            }
        });
    };

    // 修改交易状态
    form.on('switch(transaction)', function (obj) {

        var id = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        MarSwitch.changeUserStatus(id, checked,"transaction");
    });

    // 修改交易状态
    form.on('switch(capital)', function (obj) {

        var id = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        MarSwitch.changeUserStatus(id, checked,"capital");
    });

    /**
     * 修改代金券状态
     *
     * @param id 用户id
     * @param checked 是否选中（true,false），选中就是正常，未选中就是停用
     */
    MarSwitch.changeUserStatus = function (id, checked,switchType) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/switch/freeze", function (data) {
                Feng.success("开启成功!");
            }, function (data) {
                Feng.error("开启失败!");
                table.reload(MarSwitch.tableId);
            });
            ajax.set("id", id);
            ajax.set("checked",'OPEN');
            ajax.set("switchType",switchType);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/switch/freeze", function (data) {
                Feng.success("停用成功!");
            }, function (data) {
                Feng.error("停用失败!" + data.responseJSON.message + "!");
                table.reload(MarSwitch.tableId);
            });
            ajax.set("id", id);
            ajax.set("checked",'CLOSE');
            ajax.set("switchType",switchType);
            ajax.start();
        }
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MarSwitch.tableId,
        url: Feng.ctxPath + '/switch/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MarSwitch.initColumn()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MarSwitch.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MarSwitch.openAddUser();
    });
});
