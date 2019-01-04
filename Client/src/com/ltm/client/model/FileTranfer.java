package com.ltm.client.model;

import javax.swing.*;
import java.awt.*;

public class FileTranfer {
    private String iconType;
    private String name;
    private String fromIP;

    public String getFromIP() {
        return fromIP;
    }

    public void setFromIP(String fromIP) {
        this.fromIP = fromIP;
    }

    public FileTranfer(String name, String iconType) {

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
