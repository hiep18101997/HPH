package com.ltm.client.panel;

import com.ltm.client.model.File;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ListFilePanel extends BaseComps {
    private ArrayList<File> files;
    private JLabel labelServer;

    @Override
    protected void addComps() {

    }

    @Override
    protected void initComps() {
        setBackground(Color.WHITE);

        String title = "Các file hiện có trên server ";
        Border border = BorderFactory.createTitledBorder(title);
        setBorder(border);
        files = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i%3==0){
                files.add(new File("File "+i +".mp3"));
            }else if (i%3==1){
                files.add(new File("File "+i +".txt"));
            }else {
                files.add(new File("File "+i +".png"));
            }
        }
        setPreferredSize(new Dimension(350,files.size()/2 * 110));
        setLayout(new GridLayout(files.size()/2+1,2));
        for (int i=0;i<files.size();i++){
            JLabel lbFile = new JLabel();
            lbFile.setBackground(Color.CYAN);
            if (i%3==0){
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/mp3.png")).getImage()));
            }else if (i%3==1){
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/txt.png")).getImage()));
            }else {
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/png.png")).getImage()));
            }

            lbFile.setText(files.get(i).getName());
            int finalI = i;
            lbFile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    download(files.get(finalI).getName());
                }
            });
            ListFilePanel.this.add(lbFile,SwingConstants.CENTER);
        }
    }

    private void download(String name) {
        Object[] options = { "Đồng ý", "Hủy" };

        int result= JOptionPane.showOptionDialog(null, "Bạn muốn tải file "+name+" ?", "Thông báo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, null);
        if (result==JOptionPane.YES_OPTION){
            MainPanel.addComent("Đang tải file "+name+"...");
            MainPanel.addComent("Đã tải file "+name);
        }
    }

    @Override
    protected void initPanel() {

    }
}
