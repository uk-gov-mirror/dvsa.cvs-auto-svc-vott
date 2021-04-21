package vott.config;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ClientCredentialsGrantProperties {
    @SerializedName("grantType")
    private String grantType;
    @SerializedName("clientId")
    private String clientId;
    @SerializedName("scope")
    private String scope;
    @SerializedName("clientSecret")
    private String clientSecret;
}
