package net.coralmod.mod.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.coralmod.mod.CoralMod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class JsonUtils {

    private static final Gson DEFAULT_GSON = new GsonBuilder().setPrettyPrinting().create();

    private JsonUtils() {
    }

    public static <T> T loadFromJson(Path path, Class<T> clazz) {
        return loadFromJson(DEFAULT_GSON, path, clazz);
    }

    public static <T> T loadFromJson(Gson gson, Path path, Class<T> clazz) {
        if (!Files.exists(path)) {
            return null;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            CoralMod.LOGGER.error("Failed to load json from {}", path.getFileName(), e);
        }

        return null;
    }

    public static void saveToJson(Path path, Object object) {
        saveToJson(DEFAULT_GSON, path, object);
    }

    public static void saveToJson(Gson gson, Path path, Object object) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException e) {
            CoralMod.LOGGER.error("Failed to create directories for {}", path, e);
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            CoralMod.LOGGER.error("Failed to save json to {}", path.getFileName(), e);
        }
    }
}
