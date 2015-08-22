
var delete_row;

$('#shortDescriptionEvent').redactor();
$('#fullDescriptionEvent').redactor();

$('#titleImageEvent').redactor();
$('#descriptionImageEvent').redactor();

$('#btn-create').click(function() {
    $('#exampleModalLabel').text("Create New Event");
    $('.btn-submit').text("Create Event");
/*    $('#userStatus').val(1);
    $('#roleUser').val(1);
    $('#emailUser').val("");
    $('#error-message-mail').addClass("hidden");
    $('#check-frontend-mail').hide();
    $('#activeUser').hide();*/
});

$('.btn-edit').click(function(){

    var row = $(this).closest("tr");
    var td = row.find("td");
    $('#idEvent').val(td.eq(0).children().val());

    $('#eventType').val(td.eq(2).text());

    $('#titleEvent').val(td.eq(3).text());
    //$('#shortDescriptionEvent').val(td.eq(4).text());
    $('#shortDescriptionEvent').parent().children('.redactor_form-control').find('p').text(td.eq(4).text());
    
    //$('#fullDescriptionEvent').val(td.eq(5).text());
    $('#fullDescriptionEvent').parent().children('.redactor_form-control').find('p').text(td.eq(5).text());
    
    $('#exampleModalLabel').text("Update Event");
    $('.btn-submit').text("Update Event");
    //$('#error-message-mail').addClass("hidden");
    //$('#check-frontend-mail').hide();

    //$('#activeUser').show();
});

$('.btn-add-paras').click(function(){
    var row = $(this).closest("tr");
    var td = row.find("td");
    $('#eventImageId').val(td.eq(0).children().val());

});

$('.btn-delete').click(function(){
    var id = $(this).attr("id");
    var userName = $(this).attr("content");
    $('#idUserDelete').attr("href", "/admin/event/deleteEvent/?eventId=" + id);
    $('#selectedUser').text(userName);
});
/*
$('#btn-dialog-delete').click(function(){
    var eventId = $('#idUserDelete').val();
    $.ajax({
        url: '/admin/event/deleteEvent',
        type: 'GET',
        data: {eventId: eventId},
        success: function(result){
            alert(result);
            if(result === "ok"){
                delete_row.remove();
                $('#modalDeleteUser').modal('toggle');
            }
            else{
                alert("Cant delete it now! Error Message: " + result);
            }
        },
        error: function(result){
            alert("Cant delete it now! Error Message: " + result);
            $('#modalDeleteUser').modal('toggle');
        }
    });
});
*/
$('#createDialogForm').submit(function(event) {
/*    event.preventDefault();
    var user_email = $('#emailUser').val();
    var markA = user_email.indexOf('@');
    var markDot = user_email.lastIndexOf('.');
    if (user_email == "" || user_email == null ||
        markA < 1 || markDot <= markA+2 || markDot >= user_email.length-2){
    } else {
        var user_id = $('#idUser').val();
        if(user_id === ""){
            $.ajax({
                url: window.location.pathname + '/validateEmailWithoutUserId',
                type: 'GET',
                data: {userEmail: user_email},
                success: function (result) {
                    if (result === "true") {
                        $('#error-message-mail').removeClass("hidden");
                    }
                    else {
                        $('#createDialogForm').unbind().submit();
                    }
                }
            });
        } else{
            $.ajax({
                url: window.location.pathname + '/validateEmailWithUserId',
                type: 'GET',
                data: {userEmail: user_email, userId: user_id},
                success: function (result) {
                    if (result === "true") {
                        $('#error-message-mail').removeClass("hidden");
                    }
                    else {
                        $('#createDialogForm').unbind().submit();
                    }
                }
            });
        }
    }*/
});