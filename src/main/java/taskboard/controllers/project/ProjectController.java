package taskboard.controllers.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taskboard.controllers.task.TaskController;
import taskboard.models.project.Project;
import taskboard.models.project.ProjectRepository;
import taskboard.models.task.TaskRepository;
import taskboard.models.user.User;
import taskboard.pojos.ResponsePOJO;

import javax.persistence.EntityManager;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskController taskController;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EntityManager entityManager;

    @PutMapping(path = "/{userId}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Project addProject(@RequestBody Project project, @PathVariable(name = "userId") long userId) {
        User user = entityManager.getReference(User.class, userId);
        project.setUser(user);
        project.setStartDate(new Date());
        return projectRepository.save(project);
    }

    @GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Project getProject(@PathVariable(name = "id") long id) {
        return projectRepository.findOne(id);
    }

    @PostMapping(path = "/{userId}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponsePOJO<Project> updateProject(@RequestBody Project project, @PathVariable(name = "userId") long userId) {
        Project foundProject = projectRepository.findOne(project.getId());
        if (foundProject == null) {
            return new ResponsePOJO<>(false, "There is no project with id: " + project.getId());
        } else {
            if (foundProject.getUser().getId() != userId) {
                foundProject.setUser(entityManager.getReference(User.class, userId));
            }
            foundProject.setName(project.getName());
            foundProject.setDescription(project.getDescription());
            foundProject.setDueDate(project.getDueDate());
            Project savedProject = projectRepository.save(foundProject);
            return new ResponsePOJO<>(true, "Successfully updated project: " + project.getName(), savedProject);
        }
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponsePOJO deleteProject(@PathVariable(name = "id") long id) {
        Project project = projectRepository.findOne(id);
        if (project == null) {
            return new ResponsePOJO(false, "Unable to delete project. " +
                    "There is no project with id=" + id);
        } else if (projectRepository.findProjects(project.getUser()).size() <= 1) {
            return new ResponsePOJO(false, "Sorry, you can't delete your last project.\n" +
                    "Every account must have at least one project.");
        }
        taskRepository.findTasks(project).stream().forEach(task -> taskController.deleteTask(task.getId()));
        projectRepository.delete(project);
        return new ResponsePOJO(true, "Deleted project #" + id);
    }

}
