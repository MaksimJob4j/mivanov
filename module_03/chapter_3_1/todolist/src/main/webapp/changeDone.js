function changeDone(id) {
    $.ajax('./changeDone', {
        method: 'post',
        data: { id: id},
        complete: function (data) {
        }
    });
    var show = document.getElementById("show_done").checked;
    var tr = document.querySelector('tr td.id[ volume=\"' + id + '\"]').parentNode;
    if (!show && tr.getElementsByTagName("input")[0].checked) {
        tr.setAttribute('hidden', true);
    } else {
        tr.removeAttribute('hidden');
    }
}