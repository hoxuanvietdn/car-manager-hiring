var data = [];
var dataset;
var totalPoints = 30;
var updateInterval = 10000;
var now = new Date().getTime();
var maxY, minY;

function GetData() {
    while (data.length < totalPoints) {
        var y = Math.floor(Math.random() * 6 + 15);
        var temp = [now += updateInterval, y];

        data.push(temp);
    }
}

function GetDataRealtime() {
    data.shift();

    var y = 0;
    $.ajax({
        url: window.location.pathname + "sessions/count",
        data: JSON.stringify(""),
        type: "GET",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(resultData) {
            var y = resultData;
            var temp = [now += updateInterval, y];
            data.push(temp);
        }
    });
}

var options = {
    series: {
        lines: {
            show: true,
            lineWidth: 1.2,
            fill: true
        },
        color: 'rgba(255,255,255,0.7)',
        shadowSize: 0,
        points: {
            show: true
        }
    },
    xaxis: {
        mode: "time",
        tickSize: [2, "second"],
        font :{
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        tickFormatter: function (v, axis) {
            var date = new Date(v);

            if (date.getSeconds() % 30 == 0) {
                var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
                var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

                return hours + ":" + minutes + ":" + seconds;
            } else {
                return "";
            }
        },
        axisLabel: "Time",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 10
    },
    yaxis: {
        min: 0,
        max: maxY,
        tickSize: 1,
        font :{
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        tickFormatter: function (v, axis) {
            if(maxY - minY <= 10) {
                if (v % 1 == 0) {
                    return v + "";
                } else {
                    return "";
                }
            }
            else if (maxY - minY > 100) {
                if (v % 50 == 0) {
                    return v + "";
                } else {
                    return "";
                }
            }
            else{
                if (v % 10 == 0) {
                    return v + "";
                } else {
                    return "";
                }
            }
        },
        axisLabel: "Session",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 6
    },
    grid: {
        borderWidth: 1,
        borderColor: 'rgba(255,255,255,0.25)',
        labelMargin:10,
        hoverable: true,
        clickable: true,
        mouseActiveRadius:6
    },
    legend: {
        show: false
    }
};

$(document).ready(function () {
    GetData();

    dataset = [
        {data: data, label: "Data"}
    ];

    $.plot($("#line-chart"), dataset, options);

    $("#line-chart").bind("plothover", function (event, pos, item) {
        if (item) {
            var x = item.datapoint[0],
                y = item.datapoint[1];
            $("#linechart-tooltip").html("Total sessions: " + y).css({top: item.pageY+5, left: item.pageX+5}).fadeIn(200);
        }
        else {
            $("#linechart-tooltip").hide();
        }
    });

    $("<div id='linechart-tooltip' class='chart-tooltip'></div>").appendTo("body");

    function update() {
        GetDataRealtime();

        var somePlot = $.plot($("#line-chart"), dataset, options);
        var opts = somePlot.getOptions();
        maxY = 0, minY = Number.MAX_VALUE;
        for(i = 0; i < data.length; i++){
            if (data[i][1] > maxY){
                maxY = data[i][1];
            }
            if (data[i][1] < minY){
                minY = data[i][1];
            }
        }
        if (maxY - minY == 0) {
            opts.yaxes[0].min = minY - 5;
        }
        else{
            opts.yaxes[0].min = minY;
        }
        opts.yaxes[0].max = maxY + 5;
        somePlot.setupGrid();
        somePlot.draw();
        setTimeout(update, updateInterval);
    }

    update();
});