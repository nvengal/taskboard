$( () => {
    $deleteTaskButton = $("#deleteTaskButton");

    $deleteTaskButton.on('click', () => {
        var id = $('#taskId').val();

        $.ajax({
              type: 'DELETE',
              datatype: 'json',
              headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              },
              url:'/api/tasks/delete/' + id,
              success: (response) => {
                console.log(response.message);
                window.location.href = window.location.href;
              },
              error: () => {
                alert('Error deleting task');
              }
        });
    });

});