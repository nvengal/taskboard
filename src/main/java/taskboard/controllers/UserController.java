package taskboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taskboard.models.User;
import taskboard.models.UserRepository;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User addNewUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable(name = "id") long id) {
        User user = userRepository.findOne(id);
        user.setPassword(null);
        return user;
    }

    @PostMapping(path = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean verifyUser(@RequestBody User user) {
        User storedUser = userRepository.findUserByEmail(user.getEmail());
        return (storedUser == null) ? false : storedUser.getPassword().equals(user.getPassword());
    }

}
