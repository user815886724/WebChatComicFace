var curr = 1;
var size = 10;
var count = 0;
layui.use(['jquery','laypage','table'],function () {
    var table = layui.table;
    var laypage = layui.laypage;
    table.render({
        elem: '#demo',
        height: 312
    });
    init()
})

function init(){
    $.ajax({
        type : "GET",
        url : "/shares/getShareCount",
        data : {
          id : getUrlParam("id")
        },
        success: function (resp) {
            count = resp;
            curr = 1;
            initTable(curr,size);
            renderLaypage(curr,size);
        }
    })
}

function initTable(curr,size) {
    $.ajax({
        url : "/shares/getShareList",
        type : "GET",
        dataType:"JSON",
        data : {
            id : getUrlParam("id"),
            page : curr - 1,
            size : size
        },
        success : function (resp) {
            $(".layui-table tbody").html("");
            count = resp.total;
            if(resp.numberOfElements > 0){
                $(resp.datas).each(function (i,item) {
                    var html = "<tr>" +
                        "<td>"+item.time+"</td>" +
                        "<td>"+item.openPrice+"</td>" +
                        "<td>"+item.closePrice+"</td>" +
                        "<td>"+item.riseFallAmount+"</td>" +
                        "<td>"+item.riseFallRange+"</td>" +
                        "<td>"+item.lowest+"</td>" +
                        "<td>"+item.highest+"</td>" +
                        "<td>"+item.volume+"</td>" +
                        "<td>"+item.turnover+"</td>" +
                        "<td>"+item.turnoverRate+"</td>" +
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

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}