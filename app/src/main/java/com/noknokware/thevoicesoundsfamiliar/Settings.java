package com.noknokware.thevoicesoundsfamiliar;

public class Settings {

    private int settingsId;
    private int soundLevel;
    private String theme;

    public Settings() {
    }

    public Settings(int settingsId, int soundLevel, String theme) {
        this.settingsId = settingsId;
        this.soundLevel = soundLevel;
        this.theme = theme;
    }

    public int getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(int settingsId) {
        this.settingsId = settingsId;
    }

    public int getSoundLevel() {
        return soundLevel;
    }

    public void setSoundLevel(int soundLevel) {
        this.soundLevel = soundLevel;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }


}
