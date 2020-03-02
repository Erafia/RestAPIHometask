package models.loginNregister;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnsuccessLoginUserResponse {
    @Expose
    String error;
}
