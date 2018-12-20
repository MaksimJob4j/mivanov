function carValidate() {
    var currentYear = (new Date()).getFullYear();
    if (document.getElementsByName("brand")[0].value == '') {
        alert("Please choose brand");
        document.getElementsByName("brand")[0].focus();
        return false;
    } else if (document.getElementsByName("model")[0].value == '') {
        alert("Please choose model");
        document.getElementsByName("model")[0].focus();
        return false;
    } else {
        var year = document.getElementsByName("year")[0].value;
        if (year == '') {
            alert("Please insert year");
            document.getElementsByName("year")[0].focus();
            return false;
        } else if (isNaN(year) || year < 1900 || year > currentYear ) {
            alert("Please insert correct year: 1900 < YYYY < " + (currentYear + 1));
            document.getElementsByName("year")[0].focus();
            return false;
        }
        var price = document.getElementsByName("price")[0].value;
        if (price == '') {
            alert("Please insert price");
            document.getElementsByName("price")[0].focus();
            return false;
        } else if (isNaN(price) || price < 0) {
            alert("Please insert correct price");
            document.getElementsByName("price")[0].focus();
            return false;
        }
    }
}