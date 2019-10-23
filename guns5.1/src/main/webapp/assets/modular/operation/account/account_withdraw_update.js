

layui.use(['layer', 'form', 'admin', 'ax','jquery'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    var MgrWithdrawAccount = {
        tableId: "accountWithdrawTable",    //表格id
        condition: {
            name: "",
            timeLimit: "",
            batchId: ""
        }
    };

    MgrWithdrawAccount.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        queryData['batchId'] = $("#batchId").val();
        table.reload(MgrWithdrawAccount.tableId, {where: queryData});
    };

    // 让当前iframe弹层高度适应
    admin.iframeAuto();
    form.on('radio(payStatusRadio)', function (data) {
        if (data.value == "FAILURE") {
            $('#errorMsgDiv').css("display", "block");
            admin.iframeAuto();
        } else {
            $('#errorMsgDiv').css("display", "none");
            admin.iframeAuto();
        }
    });
    //获取提现信息
    var ajax = new $ax(Feng.ctxPath + "/account/withdraw/getInfo?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('withdrawUpdateForm', result.data);


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        layer.load();
        var ajax = new $ax(Feng.ctxPath + "/account/withdraw/update", function (data) {
            layer.closeAll('loading'); //关闭loading
            Feng.success("修改成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
            MgrWithdrawAccount.search();
        }, function (data) {
            layer.closeAll('loading'); //关闭loading
            Feng.error("修改失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });

});