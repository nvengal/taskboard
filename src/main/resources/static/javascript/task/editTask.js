$( () => {

  var $form = $('#edit-task-form');
  var $taskId = $('#taskId');
  var $name = $('#nameT');
  var $description = $('#descriptionT');
  var $status = $('#statusBarT');
  var $comment = $('#newComment');

  $form.on('submit', (event) => {
    event.preventDefault();

    console.log("in the edit task javascript")

    if ($comment.val().trim().length > 0) {
      var comment = {
        'text': $comment.val().trim(),
      };

      $.ajax({
        type: 'PUT',
        datatype: 'json',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        url:'/api/comments/' + $taskId.val() + '/add',
        data: JSON.stringify(comment),
        success: () => {
          console.log('Comment saved for task ' + $taskId.val());
        },
        error: (err) => {
          console.log(err);
        }
      });
    }

    var task = {
      'id': $taskId.val(),
      'name': $name.val(),
      'description': $description.val(),
      'status': $status.children(":selected").attr("name"),
    };

    updateTask(task);

  });


  $( ".card" ).draggable({ revert: "invalid" });

  $(".droppable").droppable({

    drop: function(event, ui) {


      $(ui.draggable).detach().css({top: 0,left: 0}).appendTo(this);

      var task ={
        'id':$(ui.draggable).attr('id').split("task_").pop(),
        'status': $($(this).closest('div[name]')).attr('name')
      };

      updateTask(task);
    }
    ,

    over: function(event, ui) {


      $('.droppable').droppable('enable');


      if($(this).has('.card').length) {
        $(this).droppable('disable');
      }



    }


  });

  function updateTask(task) {
    var value = "; " + document.cookie;
    var parts = value.split("; project_id=");
    var project_id = parts.pop().split(";").shift();

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
  }

});
