$( () => {

   $('.card').on('click', (event) => {

        var taskId = $(event.target.closest('.card')).attr('id').split("task_").pop();

        console.log(taskId)
        getTaskInfo(taskId)
   });



});