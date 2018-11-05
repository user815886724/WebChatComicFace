var curr = 1;
var size = 10;
var count = 0;
layui.use(['table','jquery','laypage'], function(){
    var table = layui.table;
    var laypage = layui.laypage;
    table.render({
        elem: '#demo',
        height: 312
    });
    init();
})

function initTable(curr,size) {
    $.ajax({
        url : "/shares/getSharesList",
        type : "GET",
        dataType:"JSON",
        data : {
            page : curr - 1,
            size : size
        },
        success : function (resp) {
            $(".layui-table tbody").html("");
            count = resp.total;
            if(resp.numberOfElements > 0){
                $(resp.datas).each(function (i,item) {
                    var html = "<tr>" +
                        "<td>"+item.code.replace("cn_","")+"</td>" +
                        "<td>"+item.codeName+"</td>" +
                        "<td>"+item.timeDesc+"</td>" +
                        "<td><button class='layui-btn layui-btn-primary layui-btn-sm' onclick='getDetail(\""+item.id+"\")'><i class='layui-icon'>&#xe615;</i></button></td>" +
                        "</tr>";
                    $(".layui-table tbody").append(html);
                })
            }
        }
    })
}
function renderLaypage(curr,size){
    layui.use('laypage', function(){
        var laypage = layui.laypage;//高版本建议把括号去掉，有的低版本，需要加()
        laypage.render({
            elem: 'laypage',
            count: count, //数据总数，从服务端得到
            limit : size,
            curr : curr,
            jump : function (object,first) {
                curr = object.curr;
                initTable(curr,size);
            }
        });
    });
}
function init() {
    $.ajax({
        type : "GET",
        url : "/shares/getSharesCount",
        success: function (resp) {
            count = resp;
            curr = 1;
            initTable(curr,size);
            renderLaypage(curr,size);
        }
    })
}

function getDetail(id) {
    $(window).attr('location','/shares_detail?id='+id);
}

