package taskboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taskboard.models.Project;
import taskboard.models.Task;
import taskboard.models.TaskRepository;
import taskboard.pojos.ResponsePOJO;

import javax.persistence.EntityManager;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EntityManager entityManager;

    @PutMapping(path = "/{projectId}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Task addNewTask(@RequestBody Task task, @PathVariable(name = "projectId") long projectId) {
        Project project = entityManager.getReference(Project.class, projectId);
        task.setProject(project);
        task.setStartDate(new Date());
        Task savedTask = taskRepository.save(task);
        return savedTask;
    }

    @PostMapping(path = "/{projectId}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponsePOJO<Task> updateTask(@RequestBody Task task, @PathVariable(name = "projectId") long projectId) {
        Task foundTask = taskRepository.findOne(task.getId());
        if (foundTask == null) {
            return new ResponsePOJO<>(false, "There is no task with id: " + task.getId());
        } else {
            if (foundTask.getProject().getId() != projectId) {
                Project project = entityManager.getReference(Project.class, projectId);
                task.setProject(project);
            }
            foundTask.setName(task.getName());
            foundTask.setProject(task.getProject());
            foundTask.setDescription(task.getDescription());
            foundTask.setDueDate(task.getDueDate());
            Task savedTask = taskRepository.save(foundTask);
            return new ResponsePOJO<>(true, "Successfully updated task: " + task.getName(), savedTask);
        }
    }

    @GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getTask(@PathVariable(name = "id") long id) {
        Task task = taskRepository.findOne(id);
        return task;
    }

}
