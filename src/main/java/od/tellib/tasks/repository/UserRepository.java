package od.tellib.tasks.repository;

import od.tellib.tasks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
