package vott.config;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import vott.json.GsonInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Data
public class VottConfiguration {
    private static VottConfiguration local;

    @SerializedName("databaseProperties")
    private DatabaseProperties databaseProperties;
    @SerializedName("oAuthProperties")
    private OAuthProperties oAuthProperties;
    @SerializedName("apiProperties")
    private ApiProperties apiProperties;
    @SerializedName("apiKeys")
    private ApiKeys apiKeys;

    public static VottConfiguration local() {
        if (local == null) {
            BufferedReader reader;

            try {
                reader = Files.newBufferedReader(Paths.get("src/main/resources/config.json"));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            local = GsonInstance.get().fromJson(reader, VottConfiguration.class);
        }

        return local;
    }
}
