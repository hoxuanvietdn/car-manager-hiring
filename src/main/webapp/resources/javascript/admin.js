
var delete_row;

$('#btn-create').click(function() {
    $('#exampleModalLabel').text("Create New User");
    $('.btn-submit').text("Create User");
    $('#userStatus').val(1);
    $('#roleUser').val(1);
    $('#emailUser').val("");
    $('#error-message-mail').addClass("hidden");
    $('#check-frontend-mail').hide();
    $('#activeUser').hide();
});

$('.btn-edit').click(function(){
    var row = $(this).closest("tr");
    var td = row.find("td");
    $('#idUser').val(td.eq(0).children().val());
    $('#userStatus').val(td.eq(1).children().val() === "true" ? 1 : 0);
    var roleId = td.eq(2).children().val();
    if (roleId > 0) {
        $('#roleUser').val(roleId);
    }
    $('#emailUser').val(td.eq(5).text());
    $('#exampleModalLabel').text("Update User");
    $('.btn-submit').text("Update User");
    $('#error-message-mail').addClass("hidden");
    $('#check-frontend-mail').hide();

    $('#activeUser').show();
});

$('.btn-delete').click(function(){
    var id = $(this).attr("id");
    var userName = $(this).attr("content");
    $('#idUserDelete').attr("href", "/admin/deleteUser/?userId=" + id);
    $('#selectedUser').text(userName);
});

/*$('#btn-dialog-delete').click(function(){
    var user_id = $('#idUserDelete').val();
    $.ajax({
        url: '/admin/deleteUser',
        type: 'GET',
        data: {userId: user_id},
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
});*/

$('#createDialogForm').submit(function(event) {
    event.preventDefault();
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
    }
});