$(function (){
  
  var $username = $('#Username');
  var $password = $('#Password');

  $('#Submit').on('click', function() {

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
      success: function(response) {
        alert(response.message);
      },
      error: function() {
        alert('Verification error');
      }
    });
  });

});
