package com.ltm.client.panel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainPanel extends BaseComps {
    private static JTextArea jTextArea;
    private  JScrollPane jScrollPane;

    @Override
    protected void addComps() {
        add(jScrollPane);
    }

    @Override
    protected void initComps() {

        jTextArea = new JTextArea();
        jTextArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jTextArea.setEditable(false);
        jScrollPane=new JScrollPane(jTextArea);


    }

    @Override
    protected void initPanel() {
        setLayout(new CardLayout());
        setBackground(Color.WHITE);
        String title = "Thông tin chi tiết";
        Border border = BorderFactory.createTitledBorder(title);
        setBorder(border);
    }
    public static void addComent(String s){
        jTextArea.append(s+"\n");
    }
}
