function showAllCars() {
	$('.activity-item').removeClass('hidden');
}

function showAllEvents() {
	$('.item').removeClass('hidden');
}


$(document).ready(function() {
	getCountVisitors();
    setInterval(getCountVisitors, 100000);
});

function getCountVisitors() {
    $.ajax({
        url: "/countVisitor",
        data: {},
        type: "GET",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (resultData) {
        	$("#countVisitors").text(resultData);
        }
    })
};