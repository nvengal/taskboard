$( () => {
  $('#projectBar').change( () => {
    var id = $('#projectBar').children(":selected").attr("id").split('_').pop();
    window.location.href = window.location.origin + '/home/' + id;
  });
});
