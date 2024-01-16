package od.tellib.tasks.controller;

import lombok.extern.slf4j.Slf4j;
import od.tellib.tasks.model.User;
import od.tellib.tasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        User user = service.getUsers(id);
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity.ok().body(user);
    }
}
