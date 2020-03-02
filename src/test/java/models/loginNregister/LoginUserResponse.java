package models.loginNregister;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponse {
    @Expose
    int id;
    @Expose
    String token;
}
