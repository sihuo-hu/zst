layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 系统管理--用户管理
     */
    var MgrUser = {
        tableId: "userTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrUser.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true,  title: '用户id'},
            {field: 'createTime',  minWidth:180 , title: '注册时间'},
            {field: 'loginName', minWidth:140, title: '账号'},
            {field: 'userName',  title: '姓名'},
            {field: 'gender',  title: '性别'},
            {field: 'nickname',  title: '昵称'},
            {field: 'userImg',  templet: '#userImgUrl', title: '头像'},
            {field: 'birthdate', minWidth:120 ,  title: '出生日期'},
            {field: 'idNumber',  title: '身份证号'},
            {field: 'cardFront',  templet: '#cardFrontUrl', title: '身份证正面'},
            {field: 'cardReverse',  templet: '#cardReverseUrl', title: '身份证反面'},
            {field: 'auditStatus',  title: '审核状态'},
            {field: 'bankCard',  title: '银行卡号'},
            {field: 'bankOfDeposit',  title: '开户行'},
            {field: 'userStatus', templet: '#capitalTpl', title: '状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 130}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MgrUser.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MgrUser.tableId, {where: queryData});
    };

    /**
     * 导出excel按钮
     */
    MgrUser.exportExcel = function () {
        var checkRows = table.checkStatus(MgrUser.tableId);
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
    MgrUser.onEditUser = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑用户',
            content: Feng.ctxPath + '/user/user_edit?userId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(MgrUser.tableId);
            }
        });
    };

    /**
     * 点击审核用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    MgrUser.onAuditUser = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: '420px',
            title: '审核用户',
            content: Feng.ctxPath + '/user/user_audit?userId=' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(MgrUser.tableId);
            }
        });
    };
    // 修改用户状态
    form.on('switch(userStatus)', function (obj) {

        var id = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        MgrUser.changeUserStatus(id, checked);
    });

    MgrUser.changeUserStatus = function (id, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/user/editStatus", function (data) {
                Feng.success("开启成功!");
            }, function (data) {
                Feng.error("开启失败!");
                table.reload(MgrUser.tableId);
            });
            ajax.set("id", id);
            ajax.set("userStatus",'ENABLE');
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/user/editStatus", function (data) {
                Feng.success("停用成功!");
            }, function (data) {
                Feng.error("停用失败!" + data.responseJSON.message + "!");
                table.reload(MgrUser.tableId);
            });
            ajax.set("id", id);
            ajax.set("userStatus",'FREEZE');
            ajax.start();
        }
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrUser.tableId,
        url: Feng.ctxPath + '/user/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MgrUser.initColumn()
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

    //初始化左侧部门树
    // var ztree = new $ZTree("deptTree", "/dept/tree");
    // ztree.bindOnClick(MgrUser.onClickDept);
    // ztree.init();

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrUser.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MgrUser.openAddUser();
    });

    // 导出excel
    $('#btnExp').click(function () {
        MgrUser.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + MgrUser.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MgrUser.onEditUser(data);
        }else if (layEvent === 'audit') {
            MgrUser.onAuditUser(data);
        }
    });

});
