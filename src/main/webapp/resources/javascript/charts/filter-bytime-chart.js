var filterGraphDataY = [], filterGraphDataX = [];
var maxFilterY, minFilterY;
var datasetFilter;
var filterUpdateInterval = 3000;
var timeZoneOffset;

$(function () {
    var d = new Date();
    timeZoneOffset = d.getTimezoneOffset() / -60;
    $.ajax({
        url: window.location.pathname + "sessions/countSessionWithFilterTime/" + filterType,
        data: {startTime: startTime, stopTime: stopTime, timeZoneOffset: timeZoneOffset},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            maxFilterY = 0, minFilterY = Number.MAX_VALUE;
            var sizeOfResultData = resultData.length;
            if (sizeOfResultData < 30*2) {
                for (var i = 0; i < sizeOfResultData; i++) {
                    if (resultData[i].numOfSessions > maxFilterY) {
                        maxFilterY = resultData[i].numOfSessions;
                    }
                    if (resultData[i].numOfSessions < minFilterY) {
                        minFilterY = resultData[i].numOfSessions;
                    }
                    var temp = [i + 1, resultData[i].numOfSessions];
                    filterGraphDataY.push(temp);
                    temp = [i + 1, resultData[i].timeSeries];
                    filterGraphDataX.push(temp);
                }
            } else {
                var numberRange = Math.floor(sizeOfResultData / 10);
                for (var i = 0; i < sizeOfResultData; i++) {
                    if (resultData[i].numOfSessions > maxFilterY) {
                        maxFilterY = resultData[i].numOfSessions;
                    }
                    if (resultData[i].numOfSessions < minFilterY) {
                        minFilterY = resultData[i].numOfSessions;
                    }
                    var temp = [i + 1, resultData[i].numOfSessions];
                    filterGraphDataY.push(temp);
                    if (i%numberRange){
                        temp = [i + 1,''];
                    } else {
                        temp = [i + 1, resultData[i].timeSeries];
                    }
                    filterGraphDataX.push(temp);
                }
            }
            drawChartFilter();
        }
    });
});
function updateFilterChart() {
    refresh();
    $.ajax({
        url: window.location.pathname + "sessions/countSessionWithFilterTime/" + filterType,
        data: {startTime: startTime, stopTime: stopTime, timeZoneOffset: timeZoneOffset},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            while (filterGraphDataY.length > 0) {
                filterGraphDataY.pop();
                filterGraphDataX.pop();
            }
            maxFilterY = 0, minFilterY = Number.MAX_VALUE;
            var sizeOfResultData = resultData.length;
            if (sizeOfResultData < 60) {
                for (var i = 0; i < resultData.length; i++) {
                    if (resultData[i].numOfSessions > maxFilterY) {
                        maxFilterY = resultData[i].numOfSessions;
                    }
                    if (resultData[i].numOfSessions < minFilterY) {
                        minFilterY = resultData[i].numOfSessions;
                    }
                    var temp = [i + 1, resultData[i].numOfSessions];
                    filterGraphDataY.push(temp);
                    temp = [i + 1, resultData[i].timeSeries];
                    filterGraphDataX.push(temp);
                }
            } else {
                var numberRange = Math.floor(sizeOfResultData / 10);
                for (var i = 0; i < sizeOfResultData; i++) {
                    if (resultData[i].numOfSessions > maxFilterY) {
                        maxFilterY = resultData[i].numOfSessions;
                    }
                    if (resultData[i].numOfSessions < minFilterY) {
                        minFilterY = resultData[i].numOfSessions;
                    }
                    var temp = [i + 1, resultData[i].numOfSessions];
                    filterGraphDataY.push(temp);
                    if (i%numberRange){
                        temp = [i + 1,''];
                    } else {
                        temp = [i + 1,resultData[i].timeSeries];
                    }
                    filterGraphDataX.push(temp);
                }
            }
            var somePlot = $.plot($("#filter-bytime-chart"), datasetFilter, optionFilter);
            var opts = somePlot.getOptions();
            opts.yaxes[0].min = minFilterY;
            if (maxFilterY - minFilterY > 5) {
                opts.yaxes[0].max = maxFilterY + 5;
            }
            else {
                opts.yaxes[0].max = maxFilterY + 1;
            }
            somePlot.setupGrid();
            somePlot.draw();
            updateWorldMap();
        }
    });
}
var optionFilter = {
    series: {
        lines: {
            show: true,
            lineWidth: 1.2,
            fill: true
        },
        color: 'rgba(255,255,255,0.7)',
        shadowSize: 0,
        points: {
            show: false
        }
    },
    xaxis: {
        mode: "time",
        tickSize: 1,
        ticks: filterGraphDataX,
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
        max: maxFilterY,
        tickSize: 1,
        //tickLength: 0,
        font: {
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        tickFormatter: function (v, axis) {
            if (maxFilterY - minFilterY <= 10) {
                if (v % 1 == 0) {
                    return v + "";
                } else {
                    return "";
                }
            }
            else if (maxFilterY - minFilterY > 100) {
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

function drawChartFilter() {
    datasetFilter = [
        {data: filterGraphDataY, label: "Data"}
    ];

    $.plot($("#filter-bytime-chart"), datasetFilter, optionFilter);

    $("#filter-bytime-chart").bind("plothover", function (event, pos, item) {
        if (item) {
            var x = item.series.xaxis.ticks[item.datapoint[0] - 1].label,
                y = item.datapoint[1];
            if (x != "") {
                x = x + " -";
            }
            var totalSessionInInteger = parseInt(y);
            $("#filter-bytime-chart-tooltip").css({'background-color':'#000'});
            if (totalSessionInInteger != 1){
                if (item.pageX < 60){
                    $("#filter-bytime-chart-tooltip").html(x + " " + y + " Total sessions").css({
                        top: item.pageY - 30,
                        left: item.pageX - 40
                    }).fadeIn(200);
                }
                else if(item.pageX > screen.width - 70){
                    $("#filter-bytime-chart-tooltip").html(x + " " + y + " Total sessions").css({
                        top: item.pageY - 30,
                        left: item.pageX - 120
                    }).fadeIn(200);
                }
                else{
                    $("#filter-bytime-chart-tooltip").html(x + " " + y + " Total sessions").css({
                        top: item.pageY - 30,
                        left: item.pageX - 60
                    }).fadeIn(200);
                }
            } else {
                if (item.pageX < 60){
                    $("#filter-bytime-chart-tooltip").html(x + " " + y + " Total session").css({
                        top: item.pageY - 30,
                        left: item.pageX - 40
                    }).fadeIn(200);
                }
                else if(item.pageX > screen.width - 70){
                    $("#filter-bytime-chart-tooltip").html(x + " " + y + " Total session").css({
                        top: item.pageY - 30,
                        left: item.pageX - 120
                    }).fadeIn(200);
                }
                else{
                    $("#filter-bytime-chart-tooltip").html(x + " " + y + " Total session").css({
                        top: item.pageY - 30,
                        left: item.pageX - 60
                    }).fadeIn(200);
                }
            }
        }
        else {
            $("#filter-bytime-chart-tooltip").hide();
        }
    });

    $("<div id='filter-bytime-chart-tooltip' class='chart-tooltip'></div>").appendTo("body");
    setInterval(updateFilterChart, filterUpdateInterval);
}