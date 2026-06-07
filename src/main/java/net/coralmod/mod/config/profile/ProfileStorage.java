package net.coralmod.mod.config.profile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.coralmod.mod.CoralMod;
import net.coralmod.mod.module.ModuleManager;
import net.coralmod.mod.utils.FileUtils;
import net.coralmod.mod.utils.JsonUtils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProfileStorage {

    private static final Path PROFILE_DIR = Path.of("coralmod", "profiles");

    private final Gson gson;

    public ProfileStorage(ModuleManager moduleManager) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Profile.class, new ProfileTypeAdapter(moduleManager))
                .setPrettyPrinting()
                .create();

        try {
            Files.createDirectories(PROFILE_DIR);
        } catch (IOException e) {
            CoralMod.LOGGER.error("Failed to create profiles directory", e);
        }
    }

    public List<Profile> loadProfiles() {
        final List<Profile> profileList = new ArrayList<>();

        FileUtils.list(PROFILE_DIR).stream()
                .filter(path -> path.getFileName().toString().endsWith(".json"))
                .forEach(path -> {
                    final Profile profile = load(path.getFileName().toString().substring(0, path.getFileName().toString().length() - 5));

                    profileList.add(profile);
                });

        return profileList;
    }

    public void save(Profile profile) {
        JsonUtils.saveToJson(gson, PROFILE_DIR.resolve(profile.name() + ".json"), profile);
    }

    public Profile load(String name) {
        return JsonUtils.loadFromJson(gson, PROFILE_DIR.resolve(name + ".json"), Profile.class);
    }
}
