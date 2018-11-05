layui.use(['element','jquery'], function(){
    var element = layui.element;
    element.on('nav(menu)',function (e) {
        $("iframe").attr("src",$(e).attr("data_url"));
    })
});