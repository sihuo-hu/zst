/**
 * 用户详情对话框
 */
var UserInfoDlg = {
    data: {
        deptId: "",
        deptName: ""
    }
};

var uploadUrl = 'http://www.zhangstz.com/royal/file/upload';

layui.use(['layer', 'form', 'admin', 'laydate', 'ax','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;
    var upload = layui.upload;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/user/getUserInfo?userId=" + Feng.getUrlParam("userId"));
    var result = ajax.start();
    form.val('appUserForm', result.data);
    $('#uploadImg1').attr('src', result.data.userImg);


    // 添加表单验证方法
    form.verify({
        psw: [/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'],
        repsw: function (value) {
            if (value !== $('#appUserForm input[name=password]').val()) {
                return '两次密码输入不一致';
            }
        }
    });

    // 渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/user/edit", function (data) {
            Feng.success("修改成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改成功！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });

    //普通图片上传
    var uploadInst1 = upload.render({
        elem: '#upload1'
        ,url: uploadUrl
        ,data: {'typeName':'head'}
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#uploadImg1').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.msgCode != 0){
                var demoText = $('#uploadImgText1');
                demoText.html('<span style="color: #FF5722;">上传失败</span>');
                return layer.msg(res.msg);
            }
            //上传成功
            $('#userImg').attr('value', res.data);
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#uploadImgText1');
            demoText.html('<span style="color: #FF5722;">上传失败</span>');

        }
    });



});