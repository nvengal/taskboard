$( () => {

   $('.card').on('click', (event) => {

        var taskId = $(event.target.closest('.card')).attr('id').split("task_").pop();

        getTaskInfo(taskId);
   });



});