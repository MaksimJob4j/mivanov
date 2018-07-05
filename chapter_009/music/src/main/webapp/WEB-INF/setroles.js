function setroles() {
    $.ajax('./roles', {
        method: 'get',
        complete: function (data) {
            var str = "";
            // str += "<select class=\"form-control\" name=\"role\" id=\"role\">\n";
            var roles = JSON.parse(data.responseText);
            for (var i = 0; i < roles.roles.length; ++i) {
                var role = roles.roles[i] && roles.roles[i].name ;
                str += "<option";
                if ("${user.role.name}" === role) {
                    str += " selected";
                }
                str += " value=\""+role+"\">"+role+"</option>\n";
            }
            // str += "</select>\n"
            console.log(str);
            document.getElementById("role").innerHTML = str;
        }
    });
}