layui.use(['form','jquery','laydate','layer'],function () {
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;

    lay(".date").each(function (i,item) {
        laydate.render({
            elem : this,
            trigger: 'click'
        });
    })

    $("#save").click(function () {
        save();
    })
    // $("input[name='code']").bind("input propertychange",function () {
    //     getCodeTip($("input[name='code']").val());
    // })
})

// function getCodeTip(param) {
//     $.ajax({
//         type : "POST",
//         dataType:'jsonp',
//         jsonp:'callback',
//         url : "http://www.yz21.org/stock/info/nc_q.php",
//         data : {
//             q : param
//         },
//         success : function (resp) {
//             console.log(resp);
//         },
//         error : function (resp) {
//             console.log(resp)
//         }
//     })
// }

function save(){
    layer.load();
    var param = {};
    var code = $("input[name='code']").val();
    param["code"] = code;
    param["startTime"] = $("#startTime").val();
    param["endTime"] = $("#endTime").val();
    $.ajax({
        type : "POST",
        dataType:'json',
        url : "/shares/save",
        data : param,
        success : function (resp) {
            layer.closeAll('loading');
            if(resp.success){
                layer.alert("加入"+code+"成功",{icon : 1});
            }else{
                layer.alert(resp.message,{icon: 5});
            }
        }
    })
}