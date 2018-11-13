package com.ltm.client.gui;

import com.ltm.client.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    public static final int WFRAME = 300;
    public static final int HFRAME = 200;
    public static final String KEY_PANEL_MAIN= "main";

    private MainPanel mainPanel;

    public GUI() {
        initGUI();
        initComps();
        addComps();
    }

    private void addComps() {
        add(mainPanel, KEY_PANEL_MAIN);
    }

    private void initComps() {
        mainPanel=new MainPanel();
    }

    private void initGUI() {
        setTitle("Client - UET - HPH");
        setSize(WFRAME, HFRAME);
        setLayout(new CardLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showMessage();
            }
        };
        addWindowListener(windowAdapter);
    }
    private void showMessage() {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát không ?");
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
