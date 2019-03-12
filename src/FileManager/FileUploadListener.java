package FileManager;

import Util.Constant;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FileUploadListener extends Thread {


    private ServerSocket serverSocket;
    private boolean isServerRunning;
    Socket socket;
    private byte fileInPackets[][];
    /**
     * constructor to handle the server request
     */
//    public FileUploadListener() {
//        System.out.println("In file upload listener");
//        try {
//            serverSocket = new ServerSocket(Constant.FILE_UPLOAD_PORT);
//            isServerRunning = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    public static void main(String args[])
    {
        FileUploadListener uploadListener=new FileUploadListener();
        System.out.println("In file upload listener");
        try {
            uploadListener.serverSocket = new ServerSocket(Constant.FILE_UPLOAD_PORT);
            uploadListener.isServerRunning = true;
            uploadListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while( isServerRunning ) {

            try {

                System.out.println("SERVER SOCKET RUNNING");
                socket = serverSocket.accept();

                new FileUploadHandler(socket).start();

            } catch (IOException e) {

            }

        }


    }
    /**
     * method stop the server
     */
    public void stopServer()
    {
        try {
            serverSocket.close();
        } catch (IOException e) {

        }
        isServerRunning = false;
    }

    private class FileUploadHandler extends Thread {
        //data members of the class
        Socket socket;

        byte sendData[] = new byte[Constant.FILE_MSG_SIZE];
        byte recvData[] = new byte[Constant.FILE_MSG_SIZE];

        public FileUploadHandler(Socket socket) {

            this.socket = socket;

        }
        public void run() {

            try {

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                dataInputStream.read(recvData, 0, recvData.length);

                System.out.println("Download Started.");

                String data=new String(recvData);
                String[] fileDetails=data.split(" ");

                int totalPackets=Integer.parseInt(fileDetails[1]);
                String ipAddress=fileDetails[3];
                String node=fileDetails[2];
                ArrayList<String> val=new ArrayList<>();

               fileInPackets=new byte[totalPackets][Constant.FILE_MSG_SIZE];
                for(int i =0;i<totalPackets;i++)
                {
                    dataInputStream.read(recvData,0,recvData.length);
                    System.arraycopy(recvData, 0 ,fileInPackets[i], 0, Constant.FILE_MSG_SIZE);
                }
                fileWrite(fileDetails[0],totalPackets,ipAddress,node);
                System.out.println("Download Complete.");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void fileWrite(String filepath,int totalPackets,String ipAddr,String sendToZone) throws IOException
        {
            System.out.println("filePath="+filepath);
            System.out.println("zozne="+sendToZone);
            System.out.println("ipaddress="+ipAddr);
            File file = new File(ipAddr);
            if(!file.exists())
                file.mkdirs();


            FileOutputStream fos = new FileOutputStream(ipAddr + "/" + sendToZone);

            byte[] finalFile = extractFileContents(fileInPackets);
            fos.write(finalFile);

            fos.close();

        }

        private byte[] extractFileContents(byte[][] fileArray) {
            List<byte[]> data = new ArrayList<>();

            for (int i = 0; i <= fileArray.length - 1; i++) {
                if(i == fileArray.length - 1) {
                    data.add(new String(fileArray[i]).trim().getBytes());
                    break;
                }
                data.add(fileArray[i]);
            }

            byte[] finalFile = new byte[0];
            for (byte[] b : data) {
                byte[] temp = new byte[finalFile.length + b.length];
                System.arraycopy(finalFile, 0, temp, 0, finalFile.length);
                System.arraycopy(b, 0, temp, finalFile.length, b.length);
                finalFile = temp;
            }
            return finalFile;
        }

    }

}

