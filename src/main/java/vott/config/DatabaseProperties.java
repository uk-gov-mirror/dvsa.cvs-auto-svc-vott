package vott.config;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DatabaseProperties {
    @SerializedName("engine")
    private String engine;
    @SerializedName("host")
    private String host;
    @SerializedName("port")
    private int port;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("databaseName")
    private String databaseName;
    @SerializedName("databaseClusterIdentifier")
    private String databaseClusterIdentifier;
}
