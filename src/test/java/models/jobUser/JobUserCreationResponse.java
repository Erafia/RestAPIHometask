package models.jobUser;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobUserCreationResponse {
    @Expose
    String name;
    @Expose
    String job;
    @Expose
    String id;
    @Expose
    String createdAt;
}
