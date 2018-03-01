package taskboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taskboard.models.User;
import taskboard.models.UserRepository;
import taskboard.pojos.ResponsePOJO;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PutMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponsePOJO<User> addNewUser(@RequestBody User user) {
        ResponsePOJO<User> response = new ResponsePOJO<>();
        try {
            User savedUser = userRepository.save(user);
            response.setSuccess(true);
            response.setMessage("Successfully saved: " + user.getEmail());
            response.setObject(savedUser);
        } catch (DataIntegrityViolationException ex) {
            response.setSuccess(false);
            response.setMessage("Failed to save user. Email id: '" + user.getEmail() + "' is already in use");
        }
        return response;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable(name = "id") long id) {
        User user = userRepository.findOne(id);
        user.setPassword("Not Displayed");
        return user;
    }

    @PostMapping(path = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponsePOJO verifyUser(@RequestBody User user) {
        ResponsePOJO response = new ResponsePOJO();
        User storedUser = userRepository.findUserByEmail(user.getEmail());
        if (storedUser == null) {
            response.setSuccess(false);
            response.setMessage("Could not find user: " + user.getEmail());
        } else if (!storedUser.getPassword().equals(user.getPassword())) {
            response.setSuccess(false);
            response.setMessage("Incorrect Password");
        } else {
            response.setSuccess(true);
            response.setMessage("User successfully verified");
        }
        return response;
    }

}
