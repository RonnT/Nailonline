package com.nailonline.client.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman on 10.12.2016.
 */

public class UserTheme extends RealmObject{

    @PrimaryKey
    private Integer themeId;
    private String themeAC;
    private String themeBC;
    private Integer themeDefault;
    private String themeGC;
    private String themeMC;
    private String themeName;
    private String themeWC;

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getThemeAC() {
        return themeAC;
    }

    public void setThemeAC(String themeAC) {
        this.themeAC = themeAC;
    }

    public String getThemeBC() {
        return themeBC;
    }

    public void setThemeBC(String themeBC) {
        this.themeBC = themeBC;
    }

    public Integer getThemeDefault() {
        return themeDefault;
    }

    public void setThemeDefault(Integer themeDefault) {
        this.themeDefault = themeDefault;
    }

    public String getThemeGC() {
        return themeGC;
    }

    public void setThemeGC(String themeGC) {
        this.themeGC = themeGC;
    }

    public String getThemeMC() {
        return themeMC;
    }

    public void setThemeMC(String themeMC) {
        this.themeMC = themeMC;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeWC() {
        return themeWC;
    }

    public void setThemeWC(String themeWC) {
        this.themeWC = themeWC;
    }

    @Override
    public String toString() {
        return "UserTheme{" +
                "themeId=" + themeId +
                ", themeAC='" + themeAC + '\'' +
                ", themeBC='" + themeBC + '\'' +
                ", themeDefault=" + themeDefault +
                ", themeGC='" + themeGC + '\'' +
                ", themeMC='" + themeMC + '\'' +
                ", themeName='" + themeName + '\'' +
                ", themeWC='" + themeWC + '\'' +
                '}';
    }
}
