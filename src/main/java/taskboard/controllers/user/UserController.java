package taskboard.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taskboard.controllers.project.ProjectController;
import taskboard.models.project.Project;
import taskboard.models.user.User;
import taskboard.models.user.UserRepository;
import taskboard.pojos.ResponsePOJO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectController projectController;

    @PutMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponsePOJO<User> addNewUser(@RequestBody User user) {
        ResponsePOJO<User> response = new ResponsePOJO<>();
        User savedUser = null;
        try {
            savedUser = userRepository.save(user);
            response.setSuccess(true);
            response.setMessage("Successfully saved: " + user.getEmail());
            response.setObject(savedUser);
        } catch (DataIntegrityViolationException ex) {
            response.setSuccess(false);
            response.setMessage("Failed to save user. Email id: '" + user.getEmail() + "' is already in use");
        }
        if (response.isSuccess()) {
            Project defaultProject = new Project();
            defaultProject.setName("Default Project");
            defaultProject.setDescription(((savedUser.getFirstname() != null) ?
                    savedUser.getFirstname() : savedUser.getEmail()) + "'s initial project");
            projectController.addProject(defaultProject, savedUser.getId());
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
    public ResponsePOJO verifyUser(@RequestBody User user, HttpServletResponse httpResponse) {
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
            createUserIdCookie(httpResponse, storedUser);
        }
        return response;
    }

    private void createUserIdCookie(HttpServletResponse response, User user) {
        Cookie cookie = new Cookie("user_id", String.valueOf(user.getId()));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
