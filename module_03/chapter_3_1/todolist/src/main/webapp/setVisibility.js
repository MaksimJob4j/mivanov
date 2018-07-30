function setVisibility() {
    var show = document.getElementById("show_done").checked;
    var items = document.getElementById("items").getElementsByTagName("tr");
    for (var i = 0; i != items.length; ++i) {
        if (show) {
            items[i].removeAttribute('hidden');
        } else {
            if (items[i].getElementsByTagName("input")[0].checked) {
                items[i].setAttribute('hidden', true);
            }
        }
    }
}