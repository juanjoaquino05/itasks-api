package od.tellib.tasks.service;

import od.tellib.tasks.converter.TaskConverter;
import od.tellib.tasks.converter.UserConverter;
import od.tellib.tasks.dto.request.CreateTaskRequest;
import od.tellib.tasks.dto.request.TaskResponse;
import od.tellib.tasks.exception.ResourceNotFoundException;
import od.tellib.tasks.model.Task;
import od.tellib.tasks.model.User;
import od.tellib.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(!task.isPresent()) throw new ResourceNotFoundException("Task");

        return task.get();
    }
    public List<Task> getTasks(User user) {

        List<Task> tasks = taskRepository.findAllByUser(user);
        return tasks;
    }
    public List<Task> getTasks() {

        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }

    public TaskResponse createTask(CreateTaskRequest request, User user) {
        var newTask = TaskConverter.convertToModel(user, request);
        return TaskConverter.convertToDto(taskRepository.save(newTask));
    }

    public ResponseEntity modifyTask(Long id, CreateTaskRequest request) {
        var task = taskRepository.findById(id);
        if(!task.isPresent()) throw new ResourceNotFoundException("Task");

        Task toUpdate = task.get();

        TaskConverter.updateUserData(toUpdate, request);

        toUpdate = taskRepository.save(toUpdate);

        return ResponseEntity.ok()
                .body(TaskConverter.convertToDto(toUpdate));
    }
}
