package com.ltm.client.panel;

import com.ltm.client.model.FileTranfer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListFilePanel extends BaseComps {
    private ArrayList<FileTranfer> fileTranfers;
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
        fileTranfers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i%3==0){
                fileTranfers.add(new FileTranfer("File "+i +".mp3"));
            }else if (i%3==1){
                fileTranfers.add(new FileTranfer("File "+i +".txt"));
            }else {
                fileTranfers.add(new FileTranfer("File "+i +".png"));
            }
        }
        setPreferredSize(new Dimension(350, fileTranfers.size()/3 * 110));
        setLayout(new GridLayout(fileTranfers.size()/3+1,3));
        for (int i = 0; i< fileTranfers.size(); i++){
            JLabel lbFile = new JLabel();
            lbFile.setBackground(Color.CYAN);
            if (i%3==0){
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/mp3.png")).getImage()));
            }else if (i%3==1){
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/txt.png")).getImage()));
            }else {
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/png.png")).getImage()));
            }

            lbFile.setText(fileTranfers.get(i).getName());
            lbFile.setHorizontalTextPosition(JLabel.CENTER);
            lbFile.setVerticalTextPosition(JLabel.BOTTOM);
            int finalI = i;
            lbFile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    download(fileTranfers.get(finalI).getName());
                }
            });
            ListFilePanel.this.add(lbFile,SwingConstants.CENTER);
        }
    }

    private void download(String name) {
        Object[] options = { "Tải", "Hủy" };
        File file = new File(String.valueOf(getClass().getResource("/resource/png.png")));
        DateFormat FORMATTER = new SimpleDateFormat(
                "hh:mm dd/MM/yyyy ");
        String s="";
        s+="Name : " + file.getName()+"\n";
        s+="Size : " + (double)file.length()+"\n";
        s+="Last modified : " + FORMATTER.format(new Date(file.lastModified()))+"\n";
        int result= JOptionPane.showOptionDialog(null, s, "Thông tin file",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, null);
        if (result==JOptionPane.YES_OPTION){

            MainPanel.addComent(s);
            MainPanel.addComent("Đang tải file "+name+"...");
            MainPanel.addComent("Đã tải file "+name);
        }
    }

    @Override
    protected void initPanel() {

    }
}
