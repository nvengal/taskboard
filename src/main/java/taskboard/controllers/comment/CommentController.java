package taskboard.controllers.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import taskboard.models.comment.Comment;
import taskboard.models.comment.CommentRepository;
import taskboard.models.task.Task;

import javax.persistence.EntityManager;

@RestController
@RequestMapping(path = "/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;

    @PutMapping(path = "/{taskId}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment addComment(@RequestBody Comment comment, @PathVariable(name = "taskId") long taskId) {
        Task task = entityManager.getReference(Task.class, taskId);
        comment.setTask(task);
        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

}
