layui.use(['layer', 'form','upload'], function(){
    getOptions();
    var layer = layui.layer;
    var form = layui.form;
    var upload = layui.upload;
    //执行实例
    var json
    window.uploadInst = upload.render({
        elem : "#faceImage",
        url : "/face/addData",
        auto : false,//关闭自动上传
        field : "files",
        multiple : "false",
        size : "10240",
        bindAction : "#submit",//指向一个按钮触发上传
        choose : function(obj){
            var files = obj.pushFile();
            obj.preview(function (index,file,result) {
                if(file.size > 0){
                    $("#faceShow").attr("src",result);
                    $("#faceShow").attr("alt",file.name);
                }
            })
        },
        before: function(obj){
            this.data = $("#faceShape").serializeJSON();
            layer.load(); //上传loading
        },
        done : function (res, index, upload) {
            layer.closeAll('loading'); //关闭loading
            var index = parent.layer.getFrameIndex(window.name)
            window.parent.init();
            parent.layer.close(index);
            window.parent.layer.msg("添加成功");
        },
        error: function(index, upload){
            layer.closeAll('loading'); //关闭loading
        }
    });
});

function getOptions() {
    $.ajax({
        url : "/face/getFaceShapeList",
        type : "GET",
        success : function (resp) {
            if(resp.success){
                $(resp.details).each(function (i,item) {
                   $("#faceShape").append("<option value='"+item.id+"'>"+item.shapeName+"</option>");
                });
                renderForm();
            }
        }
    })
}
function renderForm(){
    layui.use('form', function(){
        var form = layui.form;//高版本建议把括号去掉，有的低版本，需要加()
        form.render();
    });
}
function clickSubmit() {
    uploadInst.upload();
}