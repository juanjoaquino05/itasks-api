package od.tellib.tasks.converter;

import od.tellib.tasks.dto.request.ModifyUserRequest;
import od.tellib.tasks.model.User;

public class UserConverter {

    public static void updateUserData(User userToUpdate, ModifyUserRequest request){
        userToUpdate.setUsername(request.getUsername());
        userToUpdate.setEmail(request.getEmail());
        userToUpdate.setEnabled(request.getEnabled());
        userToUpdate.setPassword(request.getPassword());
    }
}
