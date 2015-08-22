/**
 * Created by hvlien on 6/25/2015.
 */
var date = new Date();
var startTime = minusMinutes(date, date.getHours()*60 + date.getMinutes()).toISOString();
var stopTime;
var filterType = "today";
var timeZoneOffset;
var updateInterval = 20000;

var dataColor = [
    "#428bca",
    "#f0ad4e",
    "#5bc0de",
    "#d9534f",
    "#2BBDA8",
    "#FD6722",
    "#0411BD",
    "#8325BD",
    "#D95C49",
    "#F04EE0",
    "#56E2CF",
    "#56AEE2",
    "#5668E2",
    "#8A56E2",
    "#CF56E2",
    "#E256AE",
    "#E25668",
    "#E28956",
    "#E2CF56",
    "#AEE256",
    "#68E256",
    "#56E289"];


var options = {
    series: {
        pie: {
            show: true,
            combine: {
                color: '#999',
                threshold: 0.05
            },
            radius: 'auto',
            innerRadius: 0.4,
            label: {
                show: true,
                radio: 180,
                radius: 0.58,
                formatter: function (label, series) {
                    return '<div style="font-size:8pt;text-align:center;padding:5px;color:white">' +
                            //label + ' : ' +
                        Math.round(series.percent) +
                        '%</div>';
                },
                background: {
                    //opacity: 0.5
                }
            },
            offset: {
                top: 5,
                left: 5
            },
            highlight: {
                opacity : 0.3
            }
        }
    },
    legend: {
        show: true,
        opacity: 0
    },
    grid: {
        hoverable: true
    }
};

function updateFilterChart(){
    timeZoneOffset = date.getTimezoneOffset() / -60;
    if (!$('#filter-time-customize').hasClass('active')){
        stopTime = new Date().toISOString();
    }
    $.ajax({
        url: window.location.pathname + "/fetchScreenSizeBySession",
        data: {startTime: startTime, stopTime: stopTime},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
            var lengthOfScreenList = resultData.length;
            var dataSetScreen = [];
            if (lengthOfScreenList > 0) {
                for (var i = 0; i < lengthOfScreenList; i++) {
                    dataSetScreen[i] = {
                        "label": resultData[i].name,
                        "data": resultData[i].total,
                            "color": dataColor[i % dataColor.length]
                    }
                }
            }
            if (dataSetScreen.length > 0) {
                $.plot($("#donut-chart-device-screen"), dataSetScreen, options);
                $('#donut-chart-device-screen').find(".flot-overlay")[0].style['z-index'] = 2;
                $("#donut-chart-device-screen").showMemo();
                $(".legend").addClass("hidden-xs");
                $(".legendColorBox").css("padding","2px");
            } else {
                showNoData("#donut-chart-device-screen");
            }
        }
    });
}
$(document).ready(function () {
    updateFilterChart();

    /*js for custom date range in _filtertime.html*/
    $('input[id="filter-time-customize"]').daterangepicker({
        maxDate: moment()
    });

    function setValueForDateRangePicker(){
        var valueOfInputDateRangePicker = $('#filter-time-customize').val();
        if (valueOfInputDateRangePicker != "Custom") {
            var startDateValue  = valueOfInputDateRangePicker.substring(0,10);
            var stopDateValue   = valueOfInputDateRangePicker.substring(13,24);
            $('#filter-time-customize').data('daterangepicker').setStartDate(startDateValue);
            $('#filter-time-customize').data('daterangepicker').setEndDate(stopDateValue);
        }
    }
    setValueForDateRangePicker();

    /*
     *
     * filter time: today
     * format: YYYY-MM-DDT00:00:00.000Z
     *
     * */
    $('button[id="filter-time-today"]').click(function(){
        $('.btn-filter').removeClass('active');
        $(this).addClass('active');
        var date = new Date();
        startTime = minusMinutes(date, date.getHours()*60 + date.getMinutes()).toISOString();
        stopTime = new Date().toISOString();
        filterType = "today";
        updateFilterChart();
    });

    /*
     *
     * filter time: 24 Hours
     * format: YYYY-MM-DDT00:00:00.000Z
     *
     * */
    $('button[id="filter-time-24hours"]').click(function(){
        $('.btn-filter').removeClass('active');
        $(this).addClass('active');
        var date = new Date();
        startTime = minusMinutes(date,24*60 ).toISOString();
        stopTime = new Date().toISOString();
        filterType = "24hours";
        updateFilterChart();
    });

    /*
     *
     * filter time: 7 Days
     * format: YYYY-MM-DDT00:00:00.000Z
     *
     * */
    $('button[id="filter-time-7days"]').click(function(){
        $('.btn-filter').removeClass('active');
        $(this).addClass('active');
        var date = new Date();
        startTime = minusMinutes(date,7*24*60 ).toISOString();
        stopTime = new Date().toISOString();
        filterType = "7days";
        updateFilterChart();
    });

    /*
     *
     * filter time: Month
     * format: YYYY-MM-DDT00:00:00.000Z
     *
     * */
    $('button[id="filter-time-month"]').click(function(){
        $('.btn-filter').removeClass('active');
        $(this).addClass('active');
        var date = new Date();
        startTime = minusMinutes(date,31*24*60 ).toISOString();
        stopTime = new Date().toISOString();
        filterType = "month";
        updateFilterChart();
    });

});

$.fn.showMemo = function () {
    $(this).bind("plothover", function (event, pos, item) {
        var html = [];
        var id_flot_memo = "#flot-memo-" + this.id ;
        $(id_flot_memo).html("");
        if (!item) {return;}
        var percent = parseFloat(item.series.percent).toFixed(2);

        html.push("<div style=\"border:1px solid grey;background-color:",
            item.series.color,
            "\">",
            "<span style=\"color:white\">",
            item.series.label,
            " : ",
            $.formatNumber(item.series.data[0][1], { format: "#,###", locale: "us" }),
            " (", percent, "%)",
            "</span>",
            "</div>");
        $(id_flot_memo).html(html.join(''));
    });
}

function minusMinutes(date, minutes) {
    return new Date(date.getTime() - minutes*60000);
}

function showNoData(id){
    var somePlot = $.plot($(id), [{"data":0}], {
        series: {
            pie: {
                show: true
            }
        }
    });
    if (isNaN(somePlot.getData()[0].percent)){
        var canvas = somePlot.getCanvas();
        var ctx = canvas.getContext("2d");
        var x = canvas.width / 2;
        var y = canvas.height / 2;
        ctx.font = '30pt Calibri';
        ctx.textAlign = 'center';
        ctx.fillStyle = 'white';
        ctx.fillText('No Data', x, y);
    }
}
setInterval(updateFilterChart, updateInterval);