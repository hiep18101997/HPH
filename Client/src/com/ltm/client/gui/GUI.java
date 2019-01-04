package com.ltm.client.gui;

import com.ltm.client.panel.ListFilePanel;
import com.ltm.client.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    public static final int WFRAME = 700;
    public static final int HFRAME = 400;
    public static final String KEY_PANEL_MAIN= "main";
    public static final String KEY_PANEL_FILE= "listfile";

    private ListFilePanel listFilePanel;
    private MainPanel mainPanel;
    private JScrollPane jScrollPaneList;

    public GUI() {
        initGUI();
        initComps();
        addComps();
    }

    private void addComps() {
        listFilePanel.setAutoscrolls(true);
        jScrollPaneList.setPreferredSize(new Dimension( 350,400));
        add(jScrollPaneList, KEY_PANEL_FILE);
        add(mainPanel, KEY_PANEL_MAIN);

    }

    private void initComps() {
        listFilePanel=new ListFilePanel();
        mainPanel=new MainPanel();

        jScrollPaneList =new JScrollPane(listFilePanel);

    }

    private void initGUI() {
        setTitle("Client - UET - HPH");
        setSize(WFRAME, HFRAME);
        setLayout(new GridLayout(1,2));
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
