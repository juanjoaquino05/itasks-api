package od.tellib.tasks.service;

import od.tellib.tasks.exception.ResourceNotFoundException;
import od.tellib.tasks.model.Task;
import od.tellib.tasks.model.User;
import od.tellib.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
