function applyCarFilter() {
    var brand = document.getElementById("brand_filter").value;
    var date = document.getElementById("date_filter").checked;
    var photo = document.getElementById("photo_filter").checked;
    var login_id = document.getElementById("login_id").value;
    var result;
    var query = './carFilter?'
        + (brand == null ? "" : 'brand=' + brand)
        + '&date=' + date
        + '&photo=' + photo;
        // + '&json=' + true;
    $.ajax(query, {
        method: 'get',
        complete: function (data) {
            result = '<tbody id="tbody_cars">';
            var cars = JSON.parse(data.responseText);
            if (cars != 0) {
                for (var i = 0; i != cars.length; ++i) {
                    result += '<tr';
                    if (cars[i].owner.id == login_id) {
                        result += ' class="users_car"';
                    }
                    result += '>';
                    result += '<td class="id" name="id" value="' + cars[i].id + '">'+ cars[i].id + '</td>';
                    result += '<td>'
                        + cars[i].model.brand.name + ' '
                        + cars[i].model.name + ' '
                        + (cars[i].engine == null ? '' : (cars[i].engine.name + ' '))
                        + (cars[i].volume == null ? '' : (cars[i].volume + ' '))
                        + (cars[i].year == null ? '' : (cars[i].year + ' '))
                        + (cars[i].color == null ? '' : (cars[i].color.name + ' '));
                    if (cars[i].broken) {
                        result += 'broken'
                    }
                    result += '</td>';
                    result += '<td>' + cars[i].price + ' RUB</td>';
                    var cr_date = new Date(Date.parse(cars[i].dateCreated));
                    result += '<td>' + (new Date(Date.parse(cars[i].dateCreated))).toLocaleString() + '</td>';
                    result += '<td>';
                    result += '<input class="check-box" type="checkbox" name="sold"';
                    if (cars[i].sold) {
                        result += ' checked';
                    }
                    if (cars[i].owner.id != login_id) {
                        result += ' disabled';
                    }
                    result += ' onclick="changeSold(' + cars[i].id + ')"/>';
                    result += '</td>';
                    result += '<td>' + cars[i].owner.login + '</td>';
                    result += '<td>';
                    result += '<form action="' + window.location.toString().split('?')[0] + 'car">';
                    result += '<input name="id" type="hidden" value="' + cars[i].id + '">';
                    result += '<input type="submit" value="INFO" class="btn btn-default">';
                    result += '</form>';
                    result += '</td>';
                    result += '</tr>';
                }
            }
            result += '</tbody>';
            document.getElementById("tbody_cars").innerHTML = result;
        }
    });
}