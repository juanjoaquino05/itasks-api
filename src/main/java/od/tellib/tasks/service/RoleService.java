package od.tellib.tasks.service;

import od.tellib.tasks.model.ERole;
import od.tellib.tasks.model.Role;
import od.tellib.tasks.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService( RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRole(ERole eRole) {
        return roleRepository.findByName(eRole.name());
    }
}
