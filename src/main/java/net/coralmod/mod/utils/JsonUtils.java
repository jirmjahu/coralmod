package net.coralmod.mod.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@UtilityClass
public class JsonUtils {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public <T> T loadFromJson(File file, Class<T> clazz) {
        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveToJson(File file, Object object) {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(object, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
