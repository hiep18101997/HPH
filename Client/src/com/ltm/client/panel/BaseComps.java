package com.ltm.client.panel;

import javax.swing.*;

public abstract class BaseComps extends JPanel {
    public BaseComps() {
        initPanel();
        initComps();
        addComps();
    }

    protected abstract void addComps();

    protected abstract void initComps();

    protected abstract void initPanel();
}