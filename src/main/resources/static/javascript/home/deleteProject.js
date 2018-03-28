$( () => {
    $deleteProjectButton = $("#deleteProjectButton");

    $deleteProjectButton.on('click', () => {
        var id = $('#projectBar').children(":selected").attr("id").split('_').pop();

        $.ajax({
              type: 'DELETE',
              datatype: 'json',
              headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              },
              url:'/api/projects/delete/' + id,
              success: (response) => {
                console.log(response.message);
                window.location.href = window.location.origin + '/home';
              },
              error: () => {
                alert('Error deleting project');
              }
        });
    });

});