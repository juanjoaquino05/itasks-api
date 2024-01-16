package od.tellib.tasks.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static od.tellib.tasks.constant.Tasks.DATE_FORMAT_PATTERN;

@Data
@NoArgsConstructor
public class CreateTaskRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(max = 150)
    private String description;

    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    @JsonProperty("dueDate")
    private String dueDate;
}
