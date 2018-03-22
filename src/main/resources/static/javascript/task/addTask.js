$( () => {

  var $form = $('#new-task-form');
  var $name = $('#name');
  var $description = $('#description');
  var $status = $('#statusBar');

  $form.on('submit', (event) => {
      event.preventDefault();

      var value = "; " + document.cookie;
      var parts = value.split("; project_id=");
      var project_id = parts.pop().split(";").shift();

      var task = {
        'name': $name.val(),
        'description': $description.val(),
        'status': $status.children(":selected").attr("name"),
      };

      $.ajax({
        type: 'PUT',
        datatype: 'json',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        url:'/api/tasks/' + project_id + '/add',
        data: JSON.stringify(task),
        success: (response) => {
          window.location.href = window.location.origin + '/home/' + project_id;
        },
        error: (err) => {
          alert('Failed to save new task');
          console.log('Error: ' + err.responseJSON.message);
        }
      });

  });

});
