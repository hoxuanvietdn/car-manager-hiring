$(function(){
    drawAllMiniChart();
    setInterval(drawAllMiniChart, 3000);
});
function drawAllMiniChart() {
    var xaxisInfor1 = [], dataValues1 = [];
    var d = new Date();
    timeZoneOffset = d.getTimezoneOffset() / -60;
    /* Total session mini chart */
    $.ajax({
        url: window.location.pathname + "sessions/countSessionWithFilterTime/7days",
        data: {startTime: stopTime, stopTime: stopTime, timeZoneOffset: timeZoneOffset},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            for (var i = 0; i < resultData.length; i++) {
                xaxisInfor1.push(resultData[i].timeSeries);
                dataValues1.push(resultData[i].numOfSessions);
            }
            drawMiniTotalSessionChart();
        }
    });
    function drawMiniTotalSessionChart() {
        $("#total-session-chart").sparkline(dataValues1, {
            type: 'line',
            width: $('.quick-stats').width(),
            height: '65',
            SpotColor: '#f80',
            borderWidth: 7,
            borderColor: '#f5f5f5',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            valueSpots: {'0:': '#f80'},
            tooltipFormatter: function (sparkline, options, fields) {
                return xaxisInfor1[fields.x] + ": " + fields.y + " session(s)";
            }
        });
    }

    /* Current session mini chart */
    var date = new Date();
    var startTimeMiniChart = minusMinutes(date, 24 * 60).toISOString();
    var stopTimeMiniChart = new Date().toISOString();
    //var xaxisInfor2 = [], dataValues2 = [];
    //$.ajax({
    //    url: window.location.pathname + "sessions/countCurrentSession",
    //    data: {startTime: startTimeMiniChart, stopTime: stopTimeMiniChart, timeZoneOffset: timeZoneOffset},
    //    type: "GET",
    //    beforeSend: function (xhr) {
    //        xhr.setRequestHeader("Accept", "application/json");
    //        xhr.setRequestHeader("Content-Type", "application/json");
    //    },
    //    success: function (resultData) {
    //        for (var i = 0; i < resultData.length; i++) {
    //            xaxisInfor2.push(resultData[i].timeSeries);
    //            dataValues2.push(resultData[i].numOfSessions);
    //        }
    //        drawMiniTotalCurrentSessionChart();
    //    }
    //});
    //function drawMiniTotalCurrentSessionChart() {
    //    $("#total-current-session-chart").sparkline(dataValues2, {
    //        type: 'line',
    //        width: $('.quick-stats').width(),
    //        height: '65',
    //        SpotColor: '#f80',
    //        borderWidth: 7,
    //        borderColor: '#f5f5f5',
    //        lineColor: 'rgba(255,255,255,0.4)',
    //        fillColor: 'rgba(0,0,0,0.2)',
    //        lineWidth: 1.25,
    //        valueSpots: {'0:': '#f80'},
    //        tooltipFormatter: function (sparkline, options, fields) {
    //            return xaxisInfor2[fields.x] + ": " + fields.y + " session(s)";
    //        }
    //    });
    //}

    /* Total session time spent mini chart */
    var xaxisInfor3 = [], dataValues3 = [];
    $.ajax({
        url: window.location.pathname + "sessions/countSessionTimeSpent",
        data: {stopTime: stopTimeMiniChart, timeZoneOffset: timeZoneOffset},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            for (var i = 0; i < resultData.length; i++) {
                xaxisInfor3.push(resultData[i].timeSeries);
                dataValues3.push(resultData[i].timeSpents);
            }
            drawMiniTotalTimeSpentChart();
        }
    });
    function drawMiniTotalTimeSpentChart() {
        $("#total-time-spent-chart").sparkline(dataValues3, {
            type: 'line',
            width: $('.quick-stats').width(),
            height: '65',
            SpotColor: '#f80',
            borderWidth: 7,
            borderColor: '#f5f5f5',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            valueSpots: {'0:': '#f80'},
            tooltipFormatter: function (sparkline, options, fields) {
                return xaxisInfor3[fields.x] + ": " + fields.y + " minute(s)";
            }
        });
    }

    /* Total session time average mini chart*/
    var xaxisInfor4 = [], dataValues4 = [];
    $.ajax({
        url: window.location.pathname + "sessions/countSessionTimeSpentAverage",
        data: {stopTime: stopTimeMiniChart, timeZoneOffset: timeZoneOffset},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            for (var i = 0; i < resultData.length; i++) {
                xaxisInfor4.push(resultData[i].timeSeries);
                dataValues4.push(resultData[i].timeSpents);
            }
            drawMiniTotalTimeSpentAverageChart();
        }
    });
    function drawMiniTotalTimeSpentAverageChart() {
        $("#total-time-spent-average-chart").sparkline(dataValues4, {
            type: 'line',
            width: $('.quick-stats').width(),
            height: '65',
            SpotColor: '#f80',
            borderWidth: 7,
            borderColor: '#f5f5f5',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            valueSpots: {'0:': '#f80'},
            tooltipFormatter: function (sparkline, options, fields) {
                return xaxisInfor4[fields.x] + ": " + fields.y + " minute(s)";
            }
        });
    }

    /* Total total user mini chart*/
    var xaxisInfor5 = [], dataValues5 = [];
    $.ajax({
        url: window.location.pathname + "sessions/countTotalUserWithFilterTime",
        data: {stopTime: stopTimeMiniChart, timeZoneOffset: timeZoneOffset},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            for (var i = 0; i < resultData.length; i++) {
                xaxisInfor5.push(resultData[i].timeSeries);
                dataValues5.push(resultData[i].numOfSessions);
            }
            drawMiniTotalUserChart();
        }
    });
    function drawMiniTotalUserChart() {
        $("#total-user-chart").sparkline(dataValues5, {
            type: 'line',
            width: $('.quick-stats').width(),
            height: '65',
            SpotColor: '#f80',
            borderWidth: 7,
            borderColor: '#f5f5f5',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            valueSpots: {'0:': '#f80'},
            tooltipFormatter: function (sparkline, options, fields) {
                return xaxisInfor5[fields.x] + ": " + fields.y + " user(s)";
            }
        });
    }

    /* Total total new user mini chart*/
    var xaxisInfor6 = [], dataValues6 = [];
    $.ajax({
        url: window.location.pathname + "sessions/countNewUserWithFilterTime",
        data: {stopTime: stopTimeMiniChart, timeZoneOffset: timeZoneOffset},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            for (var i = 0; i < resultData.length; i++) {
                xaxisInfor6.push(resultData[i].timeSeries);
                dataValues6.push(resultData[i].numOfSessions);
            }
            drawMiniTotalNewUserChart();
        }
    });
    function drawMiniTotalNewUserChart() {
        $("#total-new-user-chart").sparkline(dataValues6, {
            type: 'line',
            width: $('.quick-stats').width(),
            height: '65',
            SpotColor: '#f80',
            borderWidth: 7,
            borderColor: '#f5f5f5',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            valueSpots: {'0:': '#f80'},
            tooltipFormatter: function (sparkline, options, fields) {
                return xaxisInfor6[fields.x] + ": " + fields.y + " user(s)";
            }
        });
    }

    /* Total total new user mini chart*/
    var xaxisInfor7 = [], dataValues7 = [];
    $.ajax({
        url: window.location.pathname + "sessions/countUserOnlineWithFilterTime",
        data: {startTime: startTimeMiniChart, stopTime: stopTimeMiniChart, timeZoneOffset: timeZoneOffset},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            for (var i = 0; i < resultData.length; i++) {
                xaxisInfor7.push(resultData[i].timeSeries);
                dataValues7.push(resultData[i].numOfSessions);
            }
            drawMiniUserOnlineChart();
        }
    });
    function drawMiniUserOnlineChart() {
        $("#total-user-online-chart").sparkline(dataValues7, {
            type: 'line',
            width: $('.quick-stats').width(),
            height: '65',
            SpotColor: '#f80',
            borderWidth: 7,
            borderColor: '#f5f5f5',
            lineColor: 'rgba(255,255,255,0.4)',
            fillColor: 'rgba(0,0,0,0.2)',
            lineWidth: 1.25,
            valueSpots: {'0:': '#f80'},
            tooltipFormatter: function (sparkline, options, fields) {
                return xaxisInfor7[fields.x] + ": " + fields.y + " user(s) online";
            }
        });
    }

    $(window).resize(function (e) {
        drawMiniTotalSessionChart();
        drawMiniTotalTimeSpentChart();
        drawMiniTotalTimeSpentAverageChart();
        drawMiniTotalUserChart();
    });
}