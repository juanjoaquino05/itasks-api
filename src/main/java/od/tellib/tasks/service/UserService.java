package od.tellib.tasks.service;

import od.tellib.tasks.exception.ResourceNotFoundException;
import od.tellib.tasks.model.User;
import od.tellib.tasks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUsers(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) throw new ResourceNotFoundException("User");

        return user.get();
    }
}
