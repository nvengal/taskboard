package taskboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import taskboard.models.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/register")
    public String register() {
        return "WebOrgRegPage";
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        Optional<Cookie> userIdCookie = Arrays.stream(request.getCookies()).filter(
                cookie -> cookie.getName().equals("user_id")).findFirst();

        if (!userIdCookie.isPresent()) {
            System.out.println("Error: User_id cookie was not set");
            return index();
        }

        User user = userRepository.findOne(Long.valueOf(userIdCookie.get().getValue()));

        if (user == null) {
            System.out.println("Error: No user found with id: " + userIdCookie.get().getValue());
            return index();
        }

        model.addAttribute("user", user);

        Iterable<Project> projects = projectRepository.findAll();

        projects = StreamSupport.stream(projects.spliterator(), false)
                .filter( project -> user.getId() == project.getUser().getId() )
                .collect(Collectors.toList());

        Project defaultProject = StreamSupport.stream(projects.spliterator(), false).findFirst().get();

        model.addAttribute("projects", projects);

        model.addAttribute("taskStatusTypes", Task.Status.values());

        Iterable<Task> tasks = taskRepository.findAll();

        tasks = StreamSupport.stream(tasks.spliterator(), false)
                .filter( task -> defaultProject.getId() == task.getProject().getId() )
                .collect(Collectors.toList());

        model.addAttribute("tasks", tasks);

        return "WebOrgMainPage";
    }

}
