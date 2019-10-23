

layui.use(['layer', 'form', 'admin', 'laydate', 'ax','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取提现信息
    var ajax = new $ax(Feng.ctxPath + "/account/withdraw/accountInfo?loginName=" + Feng.getUrlParam("loginName"));
    var result = ajax.start();
    form.val('accountInfoForm', result.data);


});