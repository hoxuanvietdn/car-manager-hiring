var interval = 3000;
var date = new Date();
var startTime = minusMinutes(date, date.getHours()*60 + date.getMinutes()).toISOString();
var stopTime;
var filterType = "today";

var refresh = function() {
    if (!$('#filter-time-customize').hasClass('active')){
        stopTime = new Date().toISOString();
    }
    $.ajax({
        url: window.location.pathname + "sessions/getQuickStats",
        data: {startTime: startTime, stopTime: stopTime},
        type: "GET",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(resultData) {
            $('#totalSessions').html(commaSeparateNumber(resultData.session));
            $('#totalSessionTime').html(commaSeparateNumber(resultData.sessionTime));
            $('#totalAverageSessionTime').html(commaSeparateNumber(resultData.avgSessionTime));

            $('#totalUser').html(commaSeparateNumber(resultData.totalUser));
            $('#totalNewUser').html(commaSeparateNumber(resultData.totalNewUser));
            $('#totalUserOnline').html(commaSeparateNumber(resultData.totalUserOnline));
            $('#totalNewUserOnline').html(commaSeparateNumber(resultData.totalNewUserOnline));
        }
    });
}

$(document).ready(function() {
    refresh();
    updateWorldMap();

    setInterval(function(){
        refresh();
    }, interval);

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
        $("#filterByTimeChartName").html("STATISTICS BY TODAY");
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
        $("#filterByTimeChartName").html("STATISTICS BY 24 HOURS");
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
        $("#filterByTimeChartName").html("STATISTICS BY 7 DAYS");
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
        $("#filterByTimeChartName").html("STATISTICS BY MONTH");
        updateFilterChart();
    });
});

function minusMinutes(date, minutes) {
    return new Date(date.getTime() - minutes*60000);
}

$(function(){
    $('.alert-message-fadable').delay(3000).fadeOut(1000);
});
