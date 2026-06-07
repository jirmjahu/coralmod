package net.coralmod.mod.config;

public class Config {

    public static final int VERSION = 1;

    private int version;
    private String currentProfile;
    private String selectedTheme;

    public Config() {
        resetToDefaults();
    }

    public void resetToDefaults() {
        this.version = VERSION;
        this.currentProfile = "Default";
        this.selectedTheme = "TUBE";
    }

    public int version() {
        return version;
    }

    public String currentProfile() {
        return currentProfile;
    }

    public String selectedTheme() {
        return selectedTheme;
    }

    public void version(int version) {
        this.version = version;
    }

    public void currentProfile(String currentProfile) {
        this.currentProfile = currentProfile;
    }

    public void selectedTheme(String selectedTheme) {
        this.selectedTheme = selectedTheme;
    }
}
