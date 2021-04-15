package vott.config;

import com.google.gson.annotations.SerializedName;

public class VottConfiguration {
    @SerializedName("databaseProperties")
    private DatabaseProperties databaseProperties;
    @SerializedName("oAuthProperties")
    private OAuthProperties oAuthProperties;
    @SerializedName("apiKeys")
    private ApiKeys apiKeys;
}
