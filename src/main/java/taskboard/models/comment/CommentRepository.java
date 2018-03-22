package taskboard.models.comment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import taskboard.models.task.Task;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.task = :task")
    List<Comment> findCommentsForTask(@Param("task") Task task);

}
