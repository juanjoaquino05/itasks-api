package od.tellib.tasks.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import od.tellib.tasks.dto.request.CreateTaskRequest;
import od.tellib.tasks.dto.request.ModifyUserRequest;
import od.tellib.tasks.dto.request.SignupRequest;
import od.tellib.tasks.dto.response.MessageResponse;
import od.tellib.tasks.model.Task;
import od.tellib.tasks.model.User;
import od.tellib.tasks.service.TaskService;
import od.tellib.tasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Slf4j
public class TaskController {
    private final TaskService service;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List> getCurrentUserTasks(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        var tasks = service.getTasks(userService.getUser(auth.getName()));
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity.ok().body(tasks);
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List> getAllUsersTasks(){
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        var tasks = service.getTasks();
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity.ok().body(tasks);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        Task task = service.getTask(id);
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity.ok().body(task);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request) throws Exception {
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var task = service.createTask(request, userService.getUser(auth.getName()));
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> modifyTask(@PathVariable Long id, @Valid @RequestBody CreateTaskRequest request) throws Exception {
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        var response = service.modifyTask(id, request);
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) throws Exception {
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        var task = service.deleteTask(id);
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity.noContent().build();
    }
}
