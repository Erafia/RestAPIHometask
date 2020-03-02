package models.loginNregister;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginUserRequest {
    @Expose
    @NonNull
    String email;
    @Expose
    String password;
}
