function addItem() {
    if ($('#new_task').val() == '') {
        alert("Task is empty!");
        $('#new_task').focus()
    } else {
        $.ajax('./add', {
            method: 'post',
            data: { task: $('#new_task').val()},
            complete: function (data) {
                document.newTask.reset();
                getItems();
            }
        })
    }
}