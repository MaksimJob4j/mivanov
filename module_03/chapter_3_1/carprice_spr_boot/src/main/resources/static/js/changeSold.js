function changeSold(car_id) {
    var car_tr = document.querySelector('tr td.id[ value=\"' + car_id + '\"]').parentNode;
    var sold_checkbox = car_tr.getElementsByClassName("check-box")[0];
    $.ajax('./changeSold', {
        method: 'post',
        data: {car_id: car_id, sold: sold_checkbox.checked},
        complete: function (data) {
        }
    });
}