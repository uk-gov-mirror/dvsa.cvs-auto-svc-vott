package vott.config;

import vott.json.GsonInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigurationProvider {
    private static VottConfiguration local;

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
