package taskboard.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.project = :project")
    List<Task> findTasks(@Param("project")Project project);

}
