package net.coralmod.mod.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public final class FileUtils {

    private FileUtils() {
    }

    public static List<Path> list(Path directory) {
        if (!Files.exists(directory)) {
            throw new RuntimeException("Directory does not exist: " + directory);
        }

        try (Stream<Path> stream = Files.list(directory)) {
            return stream.toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to list directory: " + directory, e);
        }
    }
}
