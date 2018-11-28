package com.ltm.client.model;

import javax.swing.*;
import java.awt.*;

public class File {
    private String iconType;
    private String name;

    public File(String name) {

        this.iconType = iconType;
        this.name = name;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
