$( () => {

  $('.card').on('click', (event) => {

      var taskId = $(event.target.closest('.card')).attr('id').split("task_").pop();

      window.location.href = window.location.origin + '/editTask/' + taskId;

  });

});
