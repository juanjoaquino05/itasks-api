package od.tellib.tasks.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskResponse {
    private String name;
    private String description;
    private String dueDate;
    private String createdDate;
}
