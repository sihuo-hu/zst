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
    var MgrOpportunities = {
        tableId: "opportunitiesTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrOpportunities.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '用户id'},
            {field: 'createTime', minWidth:180 , title: '创建时间'},
            {field: 'userImg', templet: '#userImgUrl', title: '专家头像'},
            {field: 'userName', title: '专家名称'},
            {field: 'symbolName', title: '产品名称'},
            {field: 'operatingMode', title: '操作方式'},
            {field: 'title', title: '标题'},
            {field: 'themeText', title: '专家建议'},
            {field: 'risePercentage', title: '买涨百分比'},
            {field: 'foundationAnalysis', title: '基本分析'},
            {field: 'technologicalAnalysis', title: '技术面分析'},
            {field: 'technologicalAnalysisImg', templet: '#technologicalAnalysisUrl', title: '技术面分析图片'},
            {field: 'oStatus', templet: '#oStatusTpl', title: '状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 60}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MgrOpportunities.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MgrOpportunities.tableId, {where: queryData});
    };

    /**
     * 弹出添加用户对话框
     */
    MgrOpportunities.openAddUser = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加交易机会',
            offset: '50px',
            content: Feng.ctxPath + '/opportunities/opportunities_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(MgrOpportunities.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    MgrOpportunities.exportExcel = function () {
        var checkRows = table.checkStatus(MgrOpportunities.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击编辑用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    MgrOpportunities.onEditUser = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑用户',
            content: Feng.ctxPath + '/opportunities/opportunities_edit?userId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(MgrOpportunities.tableId);
            }
        });
    };

    /**
     * 点击删除用户按钮
     *
     * @param data 点击按钮时候的行数据
     */
    MgrOpportunities.onDeleteUser = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/opportunities/delete", function () {
                table.reload(MgrOpportunities.tableId);
                Feng.success("删除成功!");
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("userId", data.userId);
            ajax.start();
        };
        Feng.confirm("是否删除用户" + data.account + "?", operation);
    };

    // 修改交易状态
    form.on('switch(opportuntiesStatusTpl)', function (obj) {

        var id = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        MgrOpportunities.changeUserStatus(id, checked);
    });

    /**
     * 修改代金券状态
     *
     * @param id 用户id
     * @param checked 是否选中（true,false），选中就是正常，未选中就是停用
     */
    MgrOpportunities.changeUserStatus = function (id, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/opportunities/freeze", function (data) {
                Feng.success("开启成功!");
            }, function (data) {
                Feng.error("开启失败!");
                table.reload(MgrOpportunities.tableId);
            });
            ajax.set("id", id);
            ajax.set("opportuntiesStatusTpl",'ENABLE');
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/opportunities/freeze", function (data) {
                Feng.success("停用成功!");
            }, function (data) {
                Feng.error("停用失败!" + data.responseJSON.message + "!");
                table.reload(MgrOpportunities.tableId);
            });
            ajax.set("id", id);
            ajax.set("opportuntiesStatusTpl",'DISABLE');
            ajax.start();
        }
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrOpportunities.tableId,
        url: Feng.ctxPath + '/opportunities/list',
        page: true,
        height: "full-158",
        cellMinWidth: 40,
        cols: MgrOpportunities.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    layui.use('flow', function () {
        var flow = layui.flow;
        //当你执行这样一个方法时，即对页面中的全部带有lay-src的img元素开启了懒加载（当然你也可以指定相关img）
        flow.lazyimg();
    });

    //初始化左侧部门树
    // var ztree = new $ZTree("deptTree", "/dept/tree");
    // ztree.bindOnClick(MgrOpportunities.onClickDept);
    // ztree.init();

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrOpportunities.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MgrOpportunities.openAddUser();
    });

    // 导出excel
    $('#btnExp').click(function () {
        MgrOpportunities.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + MgrOpportunities.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MgrOpportunities.onEditUser(data);
        } else if (layEvent === 'delete') {
            MgrOpportunities.onDeleteUser(data);
        } else if (layEvent === 'roleAssign') {
            MgrOpportunities.roleAssign(data);
        } else if (layEvent === 'reset') {
            MgrOpportunities.resetPassword(data);
        } else if (layEvent === 'audit') {
            MgrOpportunities.onAuditUser(data);
        }
    });

    // 修改user状态
    form.on('switch(oStatus)', function (obj) {

        var userId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        MgrOpportunities.changeUserStatus(userId, checked);
    });

});
