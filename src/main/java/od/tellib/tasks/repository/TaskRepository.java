package od.tellib.tasks.repository;

import od.tellib.tasks.model.Task;
import od.tellib.tasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser(User user);
}
