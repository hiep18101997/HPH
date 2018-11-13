package com.ltm.client.panel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends BaseComps {
    private JLabel lbServerIp , lbFile;
    private JTextField lbServerIpName,lbFileName ;
    @Override
    protected void addComps() {
        add(lbServerIp);
        add(lbServerIpName);
        add(lbFile);
        add(lbFileName);

    }

    @Override
    protected void initComps() {
        lbServerIp=new JLabel("Nhập ip server ");
        lbServerIp.setFont(new Font("Tahoma",Font.BOLD,12));
        lbServerIp.setLocation(30,30);
        lbServerIp.setSize(100,30);
        lbServerIp.setForeground(Color.black);

        lbServerIpName=new JTextField();
        lbServerIpName.setFont(new Font("Tahoma",Font.BOLD,12));
        lbServerIpName.setForeground(Color.black);
        lbServerIpName.setSize(100,30);
        lbServerIpName.setLocation(lbServerIp.getX()+110,lbServerIp.getY());

        lbFile=new JLabel("Nhập tên file");
        lbFile.setFont(new Font("Tahoma",Font.BOLD,12));
        lbFile.setLocation(30,lbServerIp.getY()+50 );
        lbFile.setSize(100,30);
        lbFile.setForeground(Color.black);

        lbFileName=new JTextField();
        lbFileName.setFont(new Font("Tahoma",Font.BOLD,12));
        lbFileName.setForeground(Color.black);
        lbFileName.setSize(100,30);
        lbFileName.setLocation(lbFile.getX()+110,lbFile.getY());
    }

    @Override
    protected void initPanel() {
        setLayout(null);
    }

}
