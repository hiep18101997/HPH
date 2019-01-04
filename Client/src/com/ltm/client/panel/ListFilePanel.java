package com.ltm.client.panel;

import com.ltm.client.model.FileHadSent;
import com.ltm.client.model.FileTranfer;
import com.ltm.client.thread.SocketThread;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ListFilePanel extends BaseComps {
    private ArrayList<FileTranfer> fileTranfers;
    private JLabel labelServer;
    private Socket socket;

    @Override
    protected void addComps() {

    }

    @Override
    protected void initComps() {
        Random random=new Random();
        int port = random.nextInt(10000);
        try {
            socket = new Socket("192.168.43.72", 8080);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            String msg = port + "," + getListFile();
            out.write(msg.getBytes());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String s = bufferedReader.readLine();
            fileTranfers = showListFile(s);
            in.close();
            out.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(Color.WHITE);
        String title = "Các file hiện có trên server ";
        Border border = BorderFactory.createTitledBorder(title);
        setBorder(border);


        setPreferredSize(new Dimension(350, fileTranfers.size() * 90));
        setLayout(new GridLayout(fileTranfers.size() + 1, 1));
        for (int i = 0; i < fileTranfers.size(); i++) {
            JLabel lbFile = new JLabel();
            lbFile.setBackground(Color.CYAN);
            if (fileTranfers.get(i).getIconType().equals("mp3")) {
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/mp3.png")).getImage()));
            } else if (fileTranfers.get(i).getIconType().equals("txt")) {
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/txt.png")).getImage()));
            } else if (fileTranfers.get(i).getIconType().equals("png")) {
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/png.png")).getImage()));
            } else if (fileTranfers.get(i).getIconType().equals("jpg")) {
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/jpg.png")).getImage()));
            } else {
                lbFile.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/resource/file.png")).getImage()));
            }

            lbFile.setText(fileTranfers.get(i).getName().substring(fileTranfers.get(i).getName().lastIndexOf(":") + 1));
            lbFile.setToolTipText(fileTranfers.get(i).getName());
            lbFile.setHorizontalTextPosition(JLabel.RIGHT);
            lbFile.setVerticalTextPosition(JLabel.CENTER);
            String name = fileTranfers.get(i).getName();
            lbFile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showInfo(name);
                }
            });
            ListFilePanel.this.add(lbFile, SwingConstants.CENTER);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    createListener(port);
                }
            }).start();
        }
    }

    private void createListener(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            FileHadSent numberFileHasSent = new FileHadSent();
            while (true) {
                Socket connection = null;
                try {
                    connection = serverSocket.accept();
                    DataInputStream dis = new DataInputStream(connection.getInputStream());
                    DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                    Thread handleThread = new SocketThread(connection, dis, dos, numberFileHasSent);
                    handleThread.start();
                } catch (Exception e) {
                    connection.close();
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<FileTranfer> showListFile(String s) {
        String[] splitString = s.split(",");
        ArrayList<FileTranfer> fileTranfers = new ArrayList<>();
        for (String str : splitString) {
            String name = str;
            String type = name.substring(name.lastIndexOf(".") + 1);
            fileTranfers.add(new FileTranfer(name, type));
        }
        fileTranfers.toString();
        return fileTranfers;
    }

    private String getListFile() {
        File folder = new File("data");
        File[] listOfFiles = folder.listFiles();
        String listFiles = "";
        ArrayList<FileTranfer> fileTranfers = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (i < listOfFiles.length - 1) {
                    listFiles = listFiles + listOfFiles[i].getName() + ",";
                } else {
                    listFiles = listFiles + listOfFiles[i].getName();
                }
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        return listFiles;
    }

    private void showInfo(String name) {
        System.out.println(name);
        Object[] options = {"Tải", "Hủy"};
        String fileName = name.substring(name.lastIndexOf(":") + 1);
        String fromIP = name.substring(name.indexOf(":"), name.lastIndexOf(":"));
        String port = name.substring(0, name.indexOf(":"));
        String s = "";
        s += "Name : " + fileName + "\n";
        s += "FromIP : " + fromIP + "\n";
        s += "Port : " + port + "\n";
        int result = JOptionPane.showOptionDialog(null, s, "Thông tin file",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, null);
        if (result == JOptionPane.YES_OPTION) {
            download(fromIP, port, fileName);
            MainPanel.addComent(s);
            MainPanel.addComent("Đang tải file " + name + "...");
            MainPanel.addComent("Đã tải file " + name + "\n");
        }
    }

    private void download(String fromIP, String port, String fileName) {
        try {
            Socket connSocket = new Socket(fromIP, Integer.valueOf(port));
            DataInputStream dis = new DataInputStream(connSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(connSocket.getOutputStream());
            while (true) {
                dos.writeUTF(fileName);
                String received = dis.readUTF();
                System.out.println(received);
                if (received.equals("found")) {
                    int size = dis.readInt();
                    System.out.println("The file has: " + size);
                    byte[] contents = new byte[size];
                    dis.readFully(contents);
                    DataOutputStream dataOutput = new DataOutputStream(new FileOutputStream("data/" + fileName));
                    dataOutput.write(contents);
                    dataOutput.flush();
                    System.out.println("File saved");
                    dataOutput.close();
                } else {
                    System.out.println("File not exits");
                }
                dis.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initPanel() {

    }
}
