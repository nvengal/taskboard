$( () => {

  var $form = $('#new-project-form');
  var $name = $('#nameP');
  var $description = $('#descriptionP');

  $form.on('submit', (event) => {
      event.preventDefault();

      var value = "; " + document.cookie;
      var parts = value.split("; user_id=");
      var user_id = parts.pop().split(";").shift();

      var project = {
        'name': $name.val(),
        'description': $description.val(),
      };

      $.ajax({
        type: 'PUT',
        datatype: 'json',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        url:'/api/projects/' + user_id + '/add',
        data: JSON.stringify(project),
        success: (response) => {
          window.location.href = window.location.origin + '/home/' + response.id;
        },
        error: (err) => {
          alert('Failed to save new project');
          console.log('Error: ' + err.responseJSON.message);
        }
      });

  });

});
