function getItems() {
    $.ajax('./list', {
        method: 'get',
        complete: function (data) {
            var result;
            result = "<tbody id='items'>";
            var task_items = JSON.parse(data.responseText);
            if (task_items != null) {
                for (var i = 0; i != task_items.length; ++i) {
                    var created = new Date (Date.parse(task_items[i].created));
                    result += "<tr>"
                        + "<td class='id' name='id' volume='" + task_items[i].id + "'>" + task_items[i].id + "</td>"
                        + "<td>" + task_items[i].task + "</td>"
                        + "<td>" + created.toLocaleDateString() + " " + created.toLocaleTimeString() + "</td>"
                        + "<td>"
                        + "<div class='checkbox'>"
                        + "<label><input type='checkbox' name ='done'";
                    if (task_items[i].done != "0") {
                        result += " checked"
                    }
                    result += " onchange='changeDone(" + task_items[i].id + ")'/>"
                        + "</div>"
                        + "</td>"
                        + "</tr>";
                }
            }
            result += "</tbody>";
            document.getElementById("items").innerHTML = result;
            setVisibility();
        }
    })
}