package vott.config;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class VottConfiguration {
    @SerializedName("databaseProperties")
    private DatabaseProperties databaseProperties;
    @SerializedName("oAuthProperties")
    private OAuthProperties oAuthProperties;
    @SerializedName("apiProperties")
    private ApiProperties apiProperties;
    @SerializedName("apiKeys")
    private ApiKeys apiKeys;
}
