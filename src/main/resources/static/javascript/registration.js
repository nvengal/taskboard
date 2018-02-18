$( () => {
  //TODO: Add jquery selectors for the vars and submit button once the regitsration
  //page is completed
  var $email;
  var $password;
  var $confirmedPassword;
  var $firstname;
  var $lastname;

  $('#submit-button').on('click', () => {

    if ($password !== $confirmedPassword) {
      alert("Passwords don't match");
    } else {

      var user = {
        'firstname': $firstname.val(),
        'lastname': $lastname.val(),
        'email': $email.val();
        'password': $password.val();
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
        success: (savedUser) => {
          console.log('Saved new user: ', savedUser);
        },
        error: () => {
          alert('Failed to save new user');
        }
      });

    }

  });

});
