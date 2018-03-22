$( () => {

  var modal = document.getElementById('menu-modal');

  var btn = document.getElementById('optionsToggle');

  btn.onclick = function() {
    modal.style.display = 'block';
  }

  $('.card').on('click', (event) => {

      var taskId = $(event.target.closest('.card')).attr('id').split("task_").pop();

      window.location.href = window.location.origin + '/editTask/' + taskId;

  });

});
