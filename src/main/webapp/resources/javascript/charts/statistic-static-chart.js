var graphDataY = [], graphDataX = [];
var maxStaticY, minStaticY;
var datasetStatic;
var staticUpdateInterval = 3000;
$(function () {
    $.ajax({
        url: window.location.pathname + "sessions/countSessionWithRangeDate",
        data: JSON.stringify(""),
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            maxStaticY = 0, minStaticY = Number.MAX_VALUE;
            for (i = 0; i < resultData.length; i++) {
                if (resultData[i].numOfSessions > maxStaticY) {
                    maxStaticY = resultData[i].numOfSessions;
                }
                if (resultData[i].numOfSessions < minStaticY) {
                    minStaticY = resultData[i].numOfSessions;
                }
                var temp = [i + 1, resultData[i].numOfSessions];
                graphDataY.push(temp);
                temp = [i + 1, resultData[i].timeSeries];
                graphDataX.push(temp);
            }
            drawChart();
        }
    });
});
function getDataStaticChart() {
    $.ajax({
        url: window.location.pathname + "sessions/countSessionWithRangeDate",
        data: JSON.stringify(""),
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            while (graphDataY.length > 0) {
                graphDataY.pop();
                graphDataX.pop();
            }
            maxStaticY = 0, minStaticY = Number.MAX_VALUE;
            for (i = 0; i < resultData.length; i++) {
                if (resultData[i].numOfSessions > maxStaticY) {
                    maxStaticY = resultData[i].numOfSessions;
                }
                if (Number(resultData[i].numOfSessions) < minStaticY) {
                    minStaticY = resultData[i].numOfSessions;
                }
                var temp = [i + 1, resultData[i].numOfSessions];
                graphDataY.push(temp);
                temp = [i + 1, resultData[i].timeSeries];
                graphDataX.push(temp);
            }
        }
    });
}
var optionStatic = {
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
        tickSize: 1,
        ticks: graphDataX,
        font: {
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        axisLabel: "Time",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 1
    },
    yaxis: {
        min: 0,
        max: maxStaticY,
        tickSize: 1,
        font: {
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        tickFormatter: function (v, axis) {
            if (maxStaticY - minStaticY <= 10) {
                if (v % 1 == 0) {
                    return v + "";
                } else {
                    return "";
                }
            }
            else if (maxStaticY - minStaticY > 100) {
                if (v % 50 == 0) {
                    return v + "";
                } else {
                    return "";
                }
            }
            else {
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
        labelMargin: 10,
        hoverable: true,
        clickable: true,
        mouseActiveRadius: 6
    },
    legend: {
        show: false
    }
};
function updateStaticChart() {
    setInterval(function () {
        getDataStaticChart();
        var somePlot = $.plot($("#line-chart-static"), datasetStatic, optionStatic);
        var opts = somePlot.getOptions();
        opts.yaxes[0].min = minStaticY;
        if (maxStaticY - minStaticY > 5) {
            opts.yaxes[0].max = maxStaticY + 5;
        }
        else {
            opts.yaxes[0].max = maxStaticY + 1;
        }
        somePlot.setupGrid();
        somePlot.draw();
    }, staticUpdateInterval);
}
function drawChart() {
    datasetStatic = [
        {data: graphDataY, label: "Data"}
    ];

    $.plot($("#line-chart-static"), datasetStatic, optionStatic);

    $("#line-chart-static").bind("plothover", function (event, pos, item) {
        if (item) {
            var x = item.series.xaxis.ticks[item.datapoint[0] - 1].label,
                y = item.datapoint[1];
            $("#linechart-tooltip-static").css({'background-color': '#000', 'word-wrap': 'break-word'});
            if (item.pageX < 60) {
                $("#linechart-tooltip-static").html("Total <br> sessions: " + y).css({
                    top: item.pageY - 30,
                    left: item.pageX - 30
                }).fadeIn(200);
            }
            else if (item.pageX > screen.width - 60) {
                $("#linechart-tooltip-static").html("Total <br> sessions: " + y).css({
                    top: item.pageY - 30,
                    left: item.pageX - 70
                }).fadeIn(200);
            }
            else {
                $("#linechart-tooltip-static").html("Total sessions: " + y).css({
                    top: item.pageY - 30,
                    left: item.pageX - 60
                }).fadeIn(200);
            }
        }
        else {
            $("#linechart-tooltip-static").hide();
        }
    });

    $("<div id='linechart-tooltip-static' class='chart-tooltip'></div>").appendTo("body");
    updateStaticChart();
}