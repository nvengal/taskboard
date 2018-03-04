package taskboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taskboard.models.Project;
import taskboard.models.ProjectRepository;
import taskboard.models.User;
import taskboard.pojos.ResponsePOJO;

import javax.persistence.EntityManager;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

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

}
