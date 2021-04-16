package vott.config;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ImplicitGrantProperties {
    @SerializedName("grantType")
    private String grantType;
    @SerializedName("clientId")
    private String clientId;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("resource")
    private String resource;
}
