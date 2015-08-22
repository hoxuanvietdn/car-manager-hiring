var interval = 3000;
var timeOut;
var date = new Date();
date.setHours(0, 0, 0, 0);
var startTimeIndex = date.toISOString();
var stopTimeIndex;
var filterType = "today";
var tickSize = [1, "hour"];
$(document).ready(function() {
    if (!$('#filter-time-customize').hasClass('active')){
        stopTimeIndex = new Date().toISOString();
    }

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
        $('#filter-time-customize').val("Custom");
        $('.btn-filter').removeClass('active');
        $(this).addClass('active');
        var date = new Date();
        date.setHours(0, 0, 0, 0);
        startTimeIndex = date.toISOString();
        stopTimeIndex = new Date().toISOString();
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
        //$('#filter-time-customize').val("Custom");
        $('.btn-filter').removeClass('active');
        $(this).addClass('active');
        var date = new Date();
        date.setHours(date.getHours() - 23, 0, 0, 0);
        //date.setMinutes(0, 0);
        startTimeIndex = date.toISOString();
        stopTimeIndex = new Date().toISOString();

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
        //$('#filter-time-customize').val("Custom");
        $('.btn-filter').removeClass('active');
        $(this).addClass('active');
        var date = new Date();
        date.setDate(date.getDate() - 7);
        date.setHours(0, 0, 0, 0);
        startTimeIndex = date.toISOString();
        stopTimeIndex = new Date().toISOString();
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
        //$('#filter-time-customize').val("Custom");
        $('.btn-filter').removeClass('active');
        $(this).addClass('active');
        tickSize = [1, "day"];
        var date = new Date();
        date.setMonth(date.getMonth() - 1);
        date.setHours(0, 0, 0, 0);
        startTimeIndex = date.toISOString();
        stopTimeIndex = new Date().toISOString();
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
