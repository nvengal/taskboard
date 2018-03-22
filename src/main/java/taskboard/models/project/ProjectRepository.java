package taskboard.models.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    @Query("SELECT p FROM Project t WHERE t.project = :project")
    List<Task> findTasks(@Param("project")Project project);

}
