package od.tellib.tasks.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import od.tellib.tasks.dto.request.ModifyUserRequest;
import od.tellib.tasks.dto.request.SignupRequest;
import od.tellib.tasks.dto.response.MessageResponse;
import od.tellib.tasks.model.User;
import od.tellib.tasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Validated
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

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest request) throws Exception {
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        User user = service.registerUser(request);
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> modifyUser(@Valid @RequestBody ModifyUserRequest request) throws Exception {
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        service.modifyUser(request);
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity.ok(new MessageResponse("User modified successfully!"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws Exception {
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        var user = service.deleteUser(id);
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity.noContent().build();
    }
}
