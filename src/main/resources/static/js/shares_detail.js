var curr = 1;
var size = 10;
var count = 0;
layui.use(['jquery','element','laypage','table'],function () {
    document.getElementById('openPriceMain').style.width = 0.9*window.innerWidth+'px';
    document.getElementById('closePriceMain').style.width = 0.9*window.innerWidth+'px';
    document.getElementById('riseFallAmountMain').style.width = 0.9*window.innerWidth+'px';
    document.getElementById('riseFallRangeMain').style.width = 0.9*window.innerWidth+'px';
    document.getElementById('VolumeMain').style.width = 0.9*window.innerWidth+'px';
    document.getElementById('TurnoverMain').style.width = 0.9*window.innerWidth+'px';
    document.getElementById('TurnoverRateMain').style.width = 0.9*window.innerWidth+'px';
    init()
    var element = layui.element;
    var table = layui.table;
    var laypage = layui.laypage;
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
    initCharts();
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


function initCharts(){
    var times = [];
    var openPrices = [];
    var closePrices = [];
    var riseFallAmounts = [];
    var riseFallRanges = [];
    var volumes = [];
    var turnovers = [];
    var turnoverRates = [];



    var openPriceChart = echarts.init(document.getElementById('openPriceMain'));
    var closePriceChart = echarts.init(document.getElementById('closePriceMain'));
    var riseFallAmountChart = echarts.init(document.getElementById('riseFallAmountMain'));
    var riseFallRangeChart = echarts.init(document.getElementById('riseFallRangeMain'));
    var volumeChart = echarts.init(document.getElementById('VolumeMain'));
    var turnoverChart = echarts.init(document.getElementById('TurnoverMain'));
    var turnoverRateChart = echarts.init(document.getElementById('TurnoverRateMain'));
    $.ajax({
        url : "/shares/getChartOption",
        type : "GET",
        dataType:"JSON",
        data : {
            id : getUrlParam("id")
        },
        success : function (resp) {
            if(resp.success){
                times = resp.details.times;
                openPrices = resp.details.openPrices;
                closePrices = resp.details.closePrices;
                riseFallAmounts = resp.details.riseFallAmounts;
                riseFallRanges = resp.details.riseFallRanges;
                volumes = resp.details.volumes;
                turnovers = resp.details.turnovers;
                turnoverRates = resp.details.turnoverRates

                initLineChart(openPriceChart,times,openPrices);
                initLineChart(closePriceChart,times,closePrices);
                initLineChart(riseFallAmountChart,times,riseFallAmounts);
                initHistogram(riseFallRangeChart,times,riseFallRanges);
                initLineChart(volumeChart,times,volumes);
                initLineChart(turnoverChart,times,turnovers);
                initHistogram(turnoverRateChart,times,turnoverRates)
            }
        }
    })
}


function initLineChart(chart,times,data){
    var option = {
        xAxis: {
            type: 'category',
            data: times
        },
        yAxis: {
            type: 'value'
        },
        dataZoom : [{
            type : 'inside',
            start : 0
        },{
            start: 0,
            end: 10,
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '80%',
            handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
            }
        }],
        tooltip:{
            trigger:'axis'
        },
        series: [{
            data: data,
            type: 'line'
        }]
    };
    chart.setOption(option);
}

function initHistogram(chart,times,data) {
    var option = {
        xAxis : {
            data : times,
            silent: false,
            splitLine: {
                show: false
            }
        },
        yAxis : {
        },
        series : [{
            type : 'bar',
            data : data
        }],
        dataZoom : [{
            type : 'inside',
            start : 0
        },{
            start: 0,
            end: 10,
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '80%',
            handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
            }
        }],
        tooltip:{
            trigger:'axis'
        }
    }

    chart.setOption(option)
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}