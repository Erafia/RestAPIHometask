package models.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Resource {
    @Expose
    int id;
    @Expose
    String name;
    @Expose
    String year;
    @Expose
    String color;
    @SerializedName("pantone_value")
    @Expose
    String pantoneValue;
}
