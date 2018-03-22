package taskboard.models.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import taskboard.models.user.User;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.user = :user")
    List<Project> findProjects(@Param("user")User user);

}
