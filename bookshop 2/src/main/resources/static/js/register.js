$(function () {
    $("form-data").submit(function (event) {
        event.preventDefault();
        ajax_submit();

    })
})

function ajax_submit() {
    var user = {};
    user["email"] = $("#email").val();
    user['username'] = $("#username").val();
    user["password"] = $("#password").val();
    $("#btn-submit").prop("disabled",true);
    window.alert(user);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/register",
        data: JSON.stringify(user),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

        },
        error: function (e) {

        }
    });

}