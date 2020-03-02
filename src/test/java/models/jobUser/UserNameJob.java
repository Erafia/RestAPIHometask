package models.jobUser;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNameJob {
    @Expose
    String name;
    @Expose
    String job;
}
