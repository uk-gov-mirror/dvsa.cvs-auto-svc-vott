package vott.config;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class OAuthProperties {
    @SerializedName("implicit")
    private ImplicitGrantProperties implicit;
    @SerializedName("clientCredentials")
    private ClientCredentialsGrantProperties clientCredentials;
}
