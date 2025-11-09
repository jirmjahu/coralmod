package net.coralmod.mod.config.profile;

import net.coralmod.mod.utils.JsonUtils;

import java.io.File;

public class ProfileStorage {

    private static final File PROFILE_DIR = new File("coralmod/profiles/");

    public ProfileStorage() {
        PROFILE_DIR.mkdirs();
    }

    public void saveProfile(Profile profile) {
        JsonUtils.saveToJson(new File(PROFILE_DIR, profile.getName() + ".json"), profile);
    }

    public Profile loadProfile(String name) {
        return JsonUtils.loadFromJson(new File(PROFILE_DIR, name + ".json"), Profile.class);
    }
}
