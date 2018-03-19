$( () => {

  var $form = $('#edit-task-form');
  var $taskId = $('#taskId');
  var $name = $('#name');
  var $description = $('#description');
  var $status = $('#statusBar');

  $form.on('submit', (event) => {
      event.preventDefault();

      var value = "; " + document.cookie;
      var parts = value.split("; project_id=");
      var project_id = parts.pop().split(";").shift();

      var task = {
        'id': $taskId.val(),
        'name': $name.val(),
        'description': $description.val(),
        'status': $status.children(":selected").attr("name"),
      };

      $.ajax({
        type: 'POST',
        datatype: 'json',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        url:'/api/tasks/' + project_id + '/update',
        data: JSON.stringify(task),
        success: (response) => {
          if (response.success) {
            window.location.href = window.location.origin + '/home/' + project_id;
          } else {
            alert('Failed to save new task');
            console.log('Error: ' + response.message);
          }
        },
        error: (err) => {
          alert('Failed to save new task');
          console.log('Error: ' + err.responseJSON.message);
        }
      });

  });

});
