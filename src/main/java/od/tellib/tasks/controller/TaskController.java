package od.tellib.tasks.controller;

import lombok.extern.slf4j.Slf4j;
import od.tellib.tasks.model.Task;
import od.tellib.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@Slf4j
public class TaskController {
    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        log.info("{} request received.", Thread.currentThread().getStackTrace()[1].getMethodName());
        Task task = service.getTasks(id);
        log.info("{} request completed.", Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseEntity.ok().body(task);
    }
}
