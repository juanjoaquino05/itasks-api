package od.tellib.tasks.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
//import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
//@ToString
public class LoginRequest {
//    @NotNull(message = "room may not be null")
    private String username;

//    @NotNull(message = "startDate may not be null")
    private String password;
}
