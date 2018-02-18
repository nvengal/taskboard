$( () => {
  
  var $username = $('#Username');
  var $password = $('#Password');

  $('#Submit').on('click', () => {

    var user = {
      'email': $username.val(),
      'password': $password.val()
    };

    $.ajax({
      type: 'POST',
      datatype: 'json',
      headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
      },
      url:'/api/users/verify',
      data: JSON.stringify(user),
      success: (response) => {
        alert(response.message);
      },
      error: () => {
        alert('Verification error');
      }
    });
  });

});
