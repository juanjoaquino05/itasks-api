package od.tellib.tasks.repository;

import java.util.Optional;

import od.tellib.tasks.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}