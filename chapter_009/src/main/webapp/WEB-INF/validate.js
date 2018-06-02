function validate() {
    var result = true;
    var emailPattern = /^([a-z0-9_\.-])+@[a-z0-9-]+\.([a-z]{2,4}\.)?[a-z]{2,4}$/i;
    var emailRegExp = new RegExp(emailPattern);
    if (document.getElementsByName("login")[0].value == '') {
        result = false;
        alert("Please insert login");
        document.getElementsByName("login")[0].focus();
    } else if (document.getElementsByName("password")[0].value == '') {
        result = false;
        alert("Please insert password");
        document.getElementsByName("password")[0].focus();
    } else if (document.getElementsByName("role")[0].value == '') {
        result = false;
        alert("Please choose role");
        document.getElementsByName("role")[0].focus();
    } else {
        var email = document.getElementsByName("email")[0].value;
        if (email == '') {
            result = false;
            alert("Please insert email");
            document.getElementsByName("email")[0].focus();
        } else if (!emailRegExp.test(email)) {
            result = false;
            alert("Please insert correct email");
            document.getElementsByName("email")[0].focus();
        }
    }
    return result;
}