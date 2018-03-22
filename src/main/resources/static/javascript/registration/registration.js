$( () => {


  var $form = $('#regInfo');
  var $email = $('#emailAddress');
  var $password = $('#password');
  var $confirmedPassword = $('#confirmPassword');
  var $firstname = $('#firstName');
  var $lastname = $('#lastName');

  $form.on('submit', (event) => {
      event.preventDefault();

      var user = {
        'firstname': $firstname.val(),
        'lastname': $lastname.val(),
        'email': $email.val(),
        'password': $password.val()
      };
  
      $.ajax({
        type: 'PUT',
        datatype: 'json',
        headers: { 
          'Accept': 'application/json',
          'Content-Type': 'application/json' 
        },
        url:'/api/users/add',
        data: JSON.stringify(user),
        success: (response) => {
          if (response.success) {
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
                window.location.href = window.location.origin + '/home';
              },
              error: () => {
                alert('Verification error');
              }
            });
          } else {
            alert(response.message);
          }
        },
        error: (err) => {
          alert('Failed to save new user');
          console.log('Error: ' + err.responseJSON.message);
        }
      });

  });

});
