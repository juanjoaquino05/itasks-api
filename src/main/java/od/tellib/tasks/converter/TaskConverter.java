package od.tellib.tasks.converter;

import od.tellib.tasks.dto.request.CreateTaskRequest;
import od.tellib.tasks.dto.request.ModifyUserRequest;
import od.tellib.tasks.dto.request.TaskResponse;
import od.tellib.tasks.model.Task;
import od.tellib.tasks.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static od.tellib.tasks.constant.Tasks.DATE_FORMAT_PATTERN;

public class TaskConverter {
    public static Task convertToModel(User user, CreateTaskRequest request){
        Task newTask = new Task();
        newTask.setDescription(request.getDescription());
        newTask.setName(request.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        LocalDate dueDate = LocalDate.parse(request.getDueDate(), formatter);

        newTask.setDueDate(dueDate);
        newTask.setCreatedDate(LocalDate.now());
        newTask.setUser(user);

        return newTask;
    }
    public static TaskResponse convertToDto(Task task){
        TaskResponse response = new TaskResponse();
        response.setDescription(task.getDescription());
        response.setName(task.getName());
        response.setDueDate(task.getDueDate().toString());
        response.setCreatedDate(task.getCreatedDate().toString());

        return response;
    }
}
