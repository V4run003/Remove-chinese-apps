package com.sandscape.rmvchinese.Models;

import android.graphics.drawable.Drawable;

public class Apps {

    private String name;
    private String packageName;
    private Drawable icon;
    private String appSize;

    public Apps() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Apps(String name, String packageName, Drawable icon, String appSize) {
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
        this.appSize = appSize;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }
}
