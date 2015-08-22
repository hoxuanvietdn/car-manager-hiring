/* --------------------------------------------------------
 Map
 -----------------------------------------------------------*/
var worldMap;
var otherSession = 0;
function sortJSON(data, key) {
    return data.sort(function (a, b) {
        var x = a[key];
        var y = b[key];
        return ((x > y) ? -1 : ((x < y) ? 1 : 0));
    });
}
function updateWorldMap() {
    //World Map
    if ($('#world-map')[0]) {
        $.ajax({
            url: window.location.pathname + "sessions/countSessionAndLocation",
            data: {startTime: startTime, stopTime: stopTime, timeZoneOffset: timeZoneOffset},
            type: "GET",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (resultData) {
                $.each(sessionMapData, function(key, value) {
                    sessionMapData[key] = 0;
                });
                for (var i = 0; i < resultData.length; i++) {
                    if (resultData[i].countryCode == null) {
                        otherSession = resultData[i].numberOfSessions;
                        sessionMapData["UNDEFINED"] = 0;
                    }
                    else {
                        sessionMapData[resultData[i].countryCode] = resultData[i].numberOfSessions;
                    }
                }
                if (worldMap != null) {
                    worldMap = $('#world-map').vectorMap('get', 'mapObject');
                    worldMap.series.regions[0].clear();
                    var arr = Object.keys( sessionMapData ).map(function ( key ) { return sessionMapData[key]; });
                    if (Math.max.apply( null, arr ) == 0) {
                        sessionMapData["UNDEFINED"] = 1;
                    }
                    arr = Object.keys( sessionMapData ).map(function ( key ) { return sessionMapData[key]; });
                    worldMap.series.regions[0].scale.setMin(Math.min.apply( null, arr ));
                    worldMap.series.regions[0].scale.setMax(Math.max.apply( null, arr ));
                    worldMap.series.regions[0].setValues(sessionMapData);
                }
                var arraySort = [];
                $.each(sessionMapData, function(key, value) {
                    arraySort.push({ "k": key, "v":value});
                });
                var result = sortJSON(arraySort, "v");
                $("#most-session-table > tbody > tr").remove();
                if (result[0].k === "UNDEFINED" && otherSession == 0) {
                    $('.map-float-table').addClass("hidden");
                } else {
                    if ($('.map-float-table').hasClass("hidden")) {
                        $('.map-float-table').removeClass("hidden");
                    }
                    for (var i = 0; i < 5; i++) {
                        if (result[i].v > 0 && result[i].k != "UNDEFINED") {
                            var newRowContent = "<tr><td class='f16'><i class='flag " + result[i].k.toLowerCase() + "'></i> " + mapCountryName[result[i].k] + "</td><td style='text-align: center'>" + result[i].v + "</td></tr>";
                            $(newRowContent).appendTo($("#most-session-table"));
                        }
                    }
                    if (otherSession > 0) {
                        var newRowContent = "<tr><td class='f16'><i class='flag undefine'></i> Unknown</td><td style='text-align: center'>" + otherSession + "</td></tr>";
                        $(newRowContent).appendTo($("#most-session-table"));
                    }
                    else {
                        var newRowContent = "<tr><td class='f16'><i class='flag " + result[5].k.toLowerCase() + "'></i> " + mapCountryName[result[5].k] + "</td><td style='text-align: center'>" + result[i].v + "</td></tr>";
                        $(newRowContent).appendTo($("#most-session-table"));
                    }
                }
            }
        });
    }
}
$(function(){
    worldMap = $('#world-map').vectorMap({
        map: 'world_mill_en',
        backgroundColor: 'rgba(0,0,0,0)',
        series: {
            regions: [{
                values: sessionMapData,
                scale: ['#d8f4ff', '#0084bc'],
                normalizeFunction: 'polynomial',
                attribute: 'fill'
            }]
        },
        regionStyle: {
            initial: {
                fill: 'rgba(255,255,255,0.7)'
            },
            hover: {
                fill: '#A6A6A6'
            }
        },
        onRegionClick: function (e, code) {
            console.log(sessionMapData[code]);
        },
        onRegionLabelShow: function (event, label, code) {
            label.html(label.html() + ' (' + code.toString() +
                ') <br>Total: ' + sessionMapData[code] + ' session(s)');
        }
    });
    setInterval(updateWorldMap, interval);
});