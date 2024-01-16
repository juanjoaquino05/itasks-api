package od.tellib.tasks.converter;

import od.tellib.tasks.dto.request.CreateTaskRequest;
import od.tellib.tasks.dto.request.ModifyUserRequest;
import od.tellib.tasks.dto.request.TaskResponse;
import od.tellib.tasks.model.Task;
import od.tellib.tasks.model.User;
import od.tellib.tasks.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static od.tellib.tasks.constant.Tasks.DATE_FORMAT_PATTERN;

public class TaskConverter {
    public static Task convertToModel(User user, CreateTaskRequest request){
        Task newTask = new Task();
        newTask.setDescription(request.getDescription());
        newTask.setName(request.getName());

        newTask.setDueDate(DateUtil.parseDate(request.getDueDate(), DATE_FORMAT_PATTERN));
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

    public static void updateUserData(Task toUpdate, CreateTaskRequest request) {
        toUpdate.setName(request.getName());
        toUpdate.setDescription(request.getDescription());
        toUpdate.setDueDate(DateUtil.parseDate(request.getDueDate(), DATE_FORMAT_PATTERN));
    }
}
