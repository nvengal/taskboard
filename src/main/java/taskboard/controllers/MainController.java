package taskboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import taskboard.models.comment.Comment;
import taskboard.models.comment.CommentRepository;
import taskboard.models.project.Project;
import taskboard.models.project.ProjectRepository;
import taskboard.models.task.Task;
import taskboard.models.task.TaskRepository;
import taskboard.models.user.User;
import taskboard.models.user.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping("/")
    public String index(HttpServletResponse response) {
        Cookie cookie = new Cookie("user_id", "-1");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "index";
    }

    @RequestMapping("/register")
    public String register() {
        return "WebOrgRegPage";
    }

    @RequestMapping("/addTask")
    public String addTask(Model model) {
        model.addAttribute("taskStatusTypes", Task.Status.values());

        return "WebOrgTaskPage";
    }

    @RequestMapping("/editTask/{taskId}")
    public String editTask(Model model, @PathVariable(name = "taskId") long taskId) {
        Task task = taskRepository.findOne(taskId);
        List<Comment> comments = commentRepository.findCommentsForTask(task);
        model.addAttribute("task", task);
        model.addAttribute("taskStatusTypes", Task.Status.values());
        model.addAttribute("comments", comments);
        return "WebOrgMainPage :: taskContent";
    }

    @RequestMapping("/addProject")
    public String addProject() {
        return "WebOrgProjectPage";
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
        return projectHome(request, response, model, -1L);
    }

    @RequestMapping("/home/{projectId}")
    public String projectHome(HttpServletRequest request, HttpServletResponse response, Model model,
                              @PathVariable(name = "projectId") long projectId) {
        Optional<Cookie> userIdCookie = Arrays.stream(
                request.getCookies() != null ? request.getCookies() : new Cookie[0])
                .filter(cookie -> cookie.getName().equals("user_id")).findFirst();

        if (!userIdCookie.isPresent()) {
            System.out.println("Error: User_id cookie was not set");
            return index(response);
        }

        User user = userRepository.findOne(Long.valueOf(userIdCookie.get().getValue()));

        if (user == null) {
            System.out.println("Error: No user found with id: " + userIdCookie.get().getValue());
            return index(response);
        }

        model.addAttribute("user", user);

        Iterable<Project> projects = projectRepository.findProjects(user);

        projects = StreamSupport.stream(projects.spliterator(), false)
                .filter( project -> user.getId() == project.getUser().getId() )
                .sorted( (p1, p2) -> projectId == p1.getId() ? -1 : projectId == p2.getId() ? 1 : 0 )
                .collect(Collectors.toList());

        Project defaultProject = StreamSupport.stream(projects.spliterator(), false).findFirst().get();

        createProjectCookie(response, defaultProject);

        model.addAttribute("project", defaultProject);

        model.addAttribute("projects", projects);

        model.addAttribute("taskStatusTypes", Task.Status.values());

        Iterable<Task> tasks = taskRepository.findTasks(defaultProject);

        model.addAttribute("tasks", tasks);

        return "WebOrgMainPage";
    }

    private void createProjectCookie(HttpServletResponse response, Project project) {
        Cookie cookie = new Cookie("project_id", String.valueOf(project.getId()));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
