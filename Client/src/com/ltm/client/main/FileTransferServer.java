package com.ltm.client.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransferServer {

    public static void main(String[] args) throws Exception {
        //Initialize Sockets
        ServerSocket ssock = new ServerSocket(8080);
        Socket socket = ssock.accept();

        //The InetAddress specification  IA.getHostAddress()
        InetAddress IA = InetAddress.getByName("localhost");
        System.out.println(  IA.getHostAddress());

        //Specify the file
        File file = new File("cat.jpg");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        //Get socket's output stream
        OutputStream os = socket.getOutputStream();

        //Read FileTranfer Contents into contents array
        byte[] contents;
        long fileLength = file.length();
        long current = 0;

        long start = System.nanoTime();
        while(current!=fileLength){
            int size = 10000;
            if(fileLength - current >= size)
                current += size;
            else{
                size = (int)(fileLength - current);
                current = fileLength;
            }
            contents = new byte[size];
            bis.read(contents, 0, size);
            os.write(2);
            System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
        }

        os.flush();
        //FileTranfer transfer done. Close the socket connection!
        socket.close();
        ssock.close();
        System.out.println("FileTranfer sent succesfully!");
    }
}