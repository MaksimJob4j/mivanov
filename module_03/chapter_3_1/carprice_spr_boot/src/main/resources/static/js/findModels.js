function findModels() {
    $.ajax('./models?brand=' + document.getElementById("brand").value, {
        method: 'get',
        complete: function (data) {
            var result;
            result += '<option value="" selected disabled hidden>Choose model</option>';
            var models = JSON.parse(data.responseText);
            for (var i = 0; i !=  models.length; ++i) {
                result += '<option value="' + models[i].id + '">' + models[i].name + '</option>';
            }
            document.getElementById("model").innerHTML = result;
        }
    })
}
