function validate() {
    var result = true;
    if ($('#login').val() == '') {
        result = false;
        alert("Please insert login");
        $('#login').focus()
    } else if ($('#password').val() == '') {
        result = false;
        alert("Please insert password");
        $('#password').focus()
    } else if (document.getElementsByName("role")[0].value == '' || document.getElementsByName("role")[0].value == null) {
        result = false;
        alert("Please choose role");
        document.getElementsByName("role")[0].focus()
    }
    return result;
}