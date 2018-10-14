layui.use(['layer', 'form','upload'], function(){
    var layer = layui.layer;
    var form = layui.form;
    form.on('submit(formDemo)', function(data){
        $.ajax({
            url : "/face/addFaceShape",
            type : "POST",
            data : data.field,
            success:function (resp) {
                if(resp.success){
                    var index = parent.layer.getFrameIndex(window.name)
                    parent.layer.close(index);
                }else{
                    layer.msg(resp.message);
                }
            }
        });
        return false;
    });
});