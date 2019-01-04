package com.ltm.client.thread;

import com.ltm.client.model.FileHadSent;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class SocketThread extends Thread {
    private Socket connSock;
    DataInputStream dis;
    DataOutputStream dos;
    FileHadSent counter;

    public SocketThread(Socket connSock, DataInputStream dis, DataOutputStream dos, FileHadSent numberFileHasSent) {
        this.connSock = connSock;
        this.dis = dis;
        this.dos = dos;
        this.counter = numberFileHasSent;
    }

    @Override
    public void run() {
        System.out.println("\n-----------------------Thread start----------------------\n");
        String received;
        boolean isCheck = true;

        while (isCheck) {
            try {
                received = dis.readUTF();
                try {
                    File fileToSend = new File("data/"+received);
                    DataInputStream dataInput = new DataInputStream(new FileInputStream(fileToSend));
                    int fileLength = (int) fileToSend.length();
                    dos.writeInt(fileLength);
                    byte[] contents = new byte[(int) fileToSend.length()];

                    dataInput.readFully(contents);
                    dos.write(contents);
                    dos.flush();
                    dataInput.close();
                    System.out.println("File sent success");
                    counter.add();
                    System.out.println("Total files had sent: " + counter.getValue());
                } catch (FileNotFoundException e) {
                    System.err.println(e.toString());
                }
            } catch (SocketException ex) {
                System.err.println(ex.toString());
                isCheck = false;
            } catch (IOException e) {
                e.printStackTrace();
                isCheck = false;
            }

        }

        try {
            //closing resource
            this.dis.close();
            this.dos.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }
}