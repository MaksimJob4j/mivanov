function settypes(query) {
    $.ajax(query, {
        method: 'get',
        complete: function (data) {
            var result;
            result = "<tbody id=\"musicTypeList\">";
            var types = JSON.parse(data.responseText);
            for (var i = 0; i != types.userTypes.length; ++i) {
                result += "<tr>\n"
                    + "<td>" + types.userTypes[i].name + "</td>\n";
                result += "<td>\n"
                    + "<div>\n"
                    + "<button onclick=\"deletetype(" + types.userTypes[i].id + ")\" value='DEL' class=\"btn btn-default\">DEL</button>\n"
                    + "</div>\n</td>\n";
                result += "</tr>"
            }
            result += "<tr>\n"
                + "<td >\n"
                + "<select class=\"form-control\" name=\"type-id-new\" id=\"type-id-new\"  onchange=\"addtype()\">\n"
                + "<option value=\"-1\" selected disabled hidden>Choose type</option>\n";
            for (var j = 0; j != types.selectTypes.length; ++j) {
                result += "<option value=" + types.selectTypes[j].id + ">" + types.selectTypes[j].name + "</option>\n";
            }
            result += "<option value=\"-1\">New type</option>\n"
                + "</select>\n"
                + "</td>\n"
                + "<td>\n"
                + "</td>\n"
                + "</tr>\n";
            result += "</tbody>\n";
            document.getElementById("musicTypeList").innerHTML = result;
        }
    })
}