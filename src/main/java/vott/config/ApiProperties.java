package vott.config;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ApiProperties {
    @SerializedName("baseUrl")
    private String baseUrl;
    @SerializedName("branch")
    private String branch;
}
