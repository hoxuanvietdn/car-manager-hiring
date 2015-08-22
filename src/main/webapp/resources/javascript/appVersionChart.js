/*

var dataTest1 = [
    [gd(2012, 0, 1), 1652.21], [gd(2012, 1, 1), 1742.14], [gd(2012, 2, 1), 1673.77], [gd(2012, 3, 1), 1649.69],
    [gd(2012, 4, 1), 1591.19], [gd(2012, 5, 1), 1598.76], [gd(2012, 6, 1), 1589.90], [gd(2012, 7, 1), 1630.31],
    [gd(2012, 8, 1), 1744.81], [gd(2012, 9, 1), 1746.58], [gd(2012, 10, 1), 1721.64], [gd(2012, 11, 2), 1684.76]
];

var dataTest2 = [
    [gd(2012, 0, 1), 0.63], [gd(2012, 1, 1), 5.44], [gd(2012, 2, 1), -3.92], [gd(2012, 3, 1), -1.44],
    [gd(2012, 4, 1), -3.55], [gd(2012, 5, 1), 0.48], [gd(2012, 6, 1), -0.55], [gd(2012, 7, 1), 2.54],
    [gd(2012, 8, 1), 7.02], [gd(2012, 9, 1), 0.10], [gd(2012, 10, 1), -1.43], [gd(2012, 11, 2), -2.14]
];
*/

/*
 var dataset = [
 { label: "Version 1.0", data: dataTest1},
 { label: "Vesion 2.0", data: dataTest2 }
 ];
 */
/*
var dataX = [];*/
var d = new Date();
var optionsHour = {
    series: {
        lines: {
            show: true,
            lineWidth: 1.2,
            fill: true
        },
        font: {
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        points: {
            radius: 3,
            fill: true,
            show: true
        },
    },
    xaxis: {
        mode: "time",
        tickSize: [1, "hour"],
        tickLength: 0,
        font: {
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        axisLabel: "Time",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 10
    },
    yaxes: [{
        min:0,
        tickDecimals:0,
        axisLabel: "Total Sessions",
        axisLabelUseCanvas: true,
        font: {
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 3
/*        tickFormatter: function (v, axis) {
            return $.formatNumber(v, { format: "####", locale : "number"});
        }*/
    }, {
        position: "right",
        axisLabel: "Change(%)",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 3
    }
    ],
    legend: {
        noColumns: 0,
        labelBoxBorderColor: "#000000",
        position: "nw"
    },
    grid: {
        hoverable: true,
        borderWidth: 2,
        borderColor: "#633200",
        /*        backgroundColor: { colors: ["#ffffff", "#EDF5FF"]
         colors:["#2980B9","#D35400","#F39C12","#7F8CFF","#C0392B","#7F8C8D"]
         }*/
    },
    colors: ["#428bca",
        "#f0ad4e",
        "#5bc0de",
        "#d9534f",
        "#999",
        "#8325BD",
        "#D95C49",
        "#D99400",
        "#2BBDA8",
        "#FD6722",
        "#0411BD",
        "#F04EE0"],
};

var optionsMonth = {
    series: {
        lines: {
            show: true,
            lineWidth: 1.2,
            fill: true
        },
        font: {
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        points: {
            radius: 3,
            fill: true,
            show: true
        },
    },
    xaxis: {
        mode: "time",
        tickSize: [1, "day"],
        font: {
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        tickLength: 0,
        axisLabel: "Time",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 10
    },
    yaxes: [{
        min:0,
        tickDecimals:0,
        axisLabel: "Total Sessions",
        font: {
            lineHeight: 13,
            style: "normal",
            color: "rgba(255,255,255,0.8)"
        },
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 3
/*        tickFormatter: function (v, axis) {
            return $.formatNumber(v, { format: "#,###", locale: "us" });
        }*/
    }, {
        position: "right",
        axisLabel: "Change(%)",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 12,
        axisLabelFontFamily: 'Verdana, Arial',
        axisLabelPadding: 3
    }
    ],
    legend: {
        noColumns: 0,
        labelBoxBorderColor: "#000000",
        position: "nw"
    },
    grid: {
        hoverable: true,
        borderWidth: 2,
        borderColor: "#633200",
        /*        backgroundColor: { colors: ["#ffffff", "#EDF5FF"]
         colors:["#2980B9","#D35400","#F39C12","#7F8CFF","#C0392B","#7F8C8D"]
         }*/
    },
    colors: ["#428bca",
        "#f0ad4e",
        "#5bc0de",
        "#d9534f",
        "#999",
        "#8325BD",
        "#D95C49",
        "#D99400",
        "#2BBDA8",
        "#FD6722",
        "#0411BD",
        "#F04EE0"],
};

$(document).ready(function () {
    updateFilterChart();
    setInterval(updateFilterChart, interval);
});

function drawChart() {
    if (filterType === "24hours" || filterType === "today") {
        $.plot($("#filter-bytime-chart"), dataset, optionsHour);

    } else {
        $.plot($("#filter-bytime-chart"), dataset, optionsMonth);
    }

    $("#filter-bytime-chart").UseTooltip();
}

function updateFilterChart() {
    var d = new Date();
    timeZoneOffset = d.getTimezoneOffset() / -60;

    $.ajax({
        url: '/app/appVersion?',
        data: {filterType: filterType, startTime: startTimeIndex, stopTime: stopTimeIndex, timeZoneOffset: timeZoneOffset},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            data1 = [];
            data2 = [];
            data3 = [];
            data4 = [];
            dataOther = [];
            dataTotal = [];
            dataX = [];

            switch (resultData.appVersions.length) {
                case 0:
                    break;
                case 1:
                    $.each(resultData.sessionAppVersionLevelses, function(index, obj) {
                        var time = (new Date(obj.timeSeries - d.getTimezoneOffset()*60000));
                        data1.push([time, obj.totalSessionVersionLevel1]);
                        dataOther.push([time, obj.totalSessionVersionLevelOther]);
                        dataTotal.push([time, obj.totalSessionVersionLevelAll]);
                    });

                    dataset = [
                        { label: resultData.appVersions[0].appVersion, data: data1},
                        { label: "Other versions", data: dataOther},
                        { label: "All versions", data: dataTotal}
                    ];
                    break;
                case 2:
                    $.each(resultData.sessionAppVersionLevelses, function(index, obj) {
                        var time = (new Date(obj.timeSeries - d.getTimezoneOffset()*60000));
                        data1.push([time, obj.totalSessionVersionLevel1]);
                        data2.push([time, obj.totalSessionVersionLevel2]);
                        dataOther.push([time, obj.totalSessionVersionLevelOther]);
                        dataTotal.push([time, obj.totalSessionVersionLevelAll]);
                    });

                    dataset = [
                        { label: resultData.appVersions[0].appVersion, data: data1},
                        { label: resultData.appVersions[1].appVersion, data: data2},
                        { label: "Other versions", data: dataOther},
                        { label: "All versions", data: dataTotal}
                    ];
                    break;
                case 3:
                    $.each(resultData.sessionAppVersionLevelses, function(index, obj) {
                        var time = (new Date(obj.timeSeries - d.getTimezoneOffset()*60000));
                        data1.push([time, obj.totalSessionVersionLevel1]);
                        data2.push([time, obj.totalSessionVersionLevel2]);
                        data3.push([time, obj.totalSessionVersionLevel3]);
                        dataOther.push([time, obj.totalSessionVersionLevelOther]);
                        dataTotal.push([time, obj.totalSessionVersionLevelAll]);
                    });

                    dataset = [
                        { label: resultData.appVersions[0].appVersion, data: data1},
                        { label: resultData.appVersions[1].appVersion, data: data2},
                        { label: resultData.appVersions[2].appVersion, data: data3},
                        { label: "Other versions", data: dataOther},
                        { label: "All versions", data: dataTotal}
                    ];
                    break;
                default :
                    $.each(resultData.sessionAppVersionLevelses, function(index, obj) {
                        var time = (new Date(obj.timeSeries - d.getTimezoneOffset()*60000));
                        data1.push([time, obj.totalSessionVersionLevel1]);
                        data2.push([time, obj.totalSessionVersionLevel2]);
                        data3.push([time, obj.totalSessionVersionLevel3]);
                        data4.push([time, obj.totalSessionVersionLevel4]);
                        dataOther.push([time, obj.totalSessionVersionLevelOther]);
                        dataTotal.push([time, obj.totalSessionVersionLevelAll]);
                    });

                    dataset = [
                        { label: resultData.appVersions[0].appVersion, data: data1},
                        { label: resultData.appVersions[1].appVersion, data: data2},
                        { label: resultData.appVersions[2].appVersion, data: data3},
                        { label: resultData.appVersions[3].appVersion, data: data4},
                        { label: "Other versions", data: dataOther},
                        { label: "All versions", data: dataTotal}
                    ];
                    break;
            }

/*
            if (resultData.appVersions.length > 1) {
                dataset = [
                    { label: resultData.appVersions[0].appVersion, data: data1},
                    { label: resultData.appVersions[1].appVersion, data: data2},
                    { label: "Other versions", data: dataOther},
                    { label: "All versions", data: dataTotal}
                ];
            } else {
                dataset = [
                    { label: resultData.appVersions[0].appVersion, data: data1},
                    { label: "Other versions", data: dataOther},
                    { label: "All versions", data: dataTotal}
                ];
            }
*/

            drawChart();
        }
    });
}


function gd(year, month, day) {
    return new Date(year, month, day).getTime();
}


var previousPoint = null, previousLabel = null;
var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

$.fn.UseTooltip = function () {
    $(this).bind("plothover", function (event, pos, item) {
        if (item) {
            if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                previousPoint = item.dataIndex;
                previousLabel = item.series.label;
                $("#tooltip").remove();

                var x = item.datapoint[0];
                var y = item.datapoint[1];

                var color = item.series.color;
                var month = new Date(x).getMonth();

                if (item.seriesIndex == 0) {
                    showTooltip(item.pageX,
                        item.pageY,
                        color,
                        "</strong><strong>" + formatTime(new Date(x + d.getTimezoneOffset()*60000)) + "</strong><br><strong>" + previousLabel + "</strong><br><strong>Sessions: " + y + "</strong>");
                } else {
                    showTooltip(item.pageX,
                        item.pageY,
                        color,
                        "</strong><strong>" + formatTime(new Date(x + d.getTimezoneOffset()*60000)) + "</strong><br><strong>" + previousLabel + "</strong><br><strong>Sessions: " + y + "</strong>");
                }
            }
        } else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });
};

function showTooltip(x, y, color, contents) {
    $('<div id="tooltip">' + contents + '</div>').css({
        position: 'absolute',
        display: 'none',
        top: y - 40,
        left: x - 80,
        border: '2px solid ' + color,
        padding: '3px',
        'font-size': '9px',
        'border-radius': '5px',
        // 'background-color': '#fff',
        'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
        opacity: 0.9
    }).appendTo("body").fadeIn(200);
}

function formatTime(date) {
    if (date.getMinutes() > 0) {
        return date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDate() + "/" + (date.getYear() + 1900);
    }

    return  date.getMonth() + "/" + date.getDate() + "/" + (date.getYear() + 1900);
}