package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

public class ClientHandler extends Thread
{
    Socket serverSocket;
    int port;
    ClientHandler(Socket conn,int port)
    {
      //  System.out.println("ClientHandler constructor");
        this.serverSocket=conn;
        this.port=port;
    }

    public void run()
    {
        System.out.println("In clientHandler run");
        {
            System.out.println("Just connected to "
                    + serverSocket.getRemoteSocketAddress());
            DataInputStream in = null;
            DataOutputStream out = null;
            try {
                in = new DataInputStream(serverSocket.getInputStream());
                String received = in.readUTF();
                String[] arguments = received.split(":");
                System.out.println("recevied msg: " + received);

                out = new DataOutputStream(serverSocket.getOutputStream());

//                switch (arguments[0]) {
//                    case "closestPrecedingFinger":
//                        out.writeUTF(closestPrecedingFinger(new BigInteger(arguments[1])));
//                        break;
//                    case "findPredecessor":
//                        out.writeUTF(findPredecessor(new BigInteger(arguments[1])));
//                        break;
//                    case "findSuccessor":
//                        out.writeUTF(findSuccessor(new BigInteger(arguments[1])));
//                        break;
//                    case "join":
//                        out.writeUTF(join(Integer.parseInt(arguments[1])));
//                        break;
//                    case "getPredecessor":
//                        out.writeUTF(getPredecessor());
//                        break;
//                    case "getSuccessor":
//                        out.writeUTF(getSuccessor());
//                        break;
//                    case "setPredecessor":
//                        out.writeUTF(setPredecessor(new BigInteger(arguments[1]), Integer.parseInt(arguments[2])));
//                        break;
//                    case "setSuccessor":
//                        out.writeUTF(setSuccessor(new BigInteger(arguments[1]), Integer.parseInt(arguments[2])));
//                        break;
//                    case "updateFingerTable":
//                        out.writeUTF(updateFingerTable(new BigInteger(arguments[1]), Integer.parseInt(arguments[2]),
//                                Integer.parseInt(arguments[3])));
//                        break;
//                    case "getIthFinger":
//                        out.writeUTF(getIthFinger(Integer.parseInt(arguments[1])));
//                        break;
//                    case "printFingerTable":
//                        out.writeUTF(printFingerTable());
//                        break;
//                    case "findKeySuccessor":
//                        arguments = received.split(" ");
//                        out.writeUTF(findKeySuccessor(arguments[1],arguments[2],arguments[3]));
//                        break;
//                    case "keyInsert":
//                        arguments = received.split(" ");
//                        out.writeUTF(keyInsert(new BigInteger(arguments[1]),arguments[2], Integer.parseInt(arguments[3])));
//                        break;
//                    case "keyRetrieve":
//                        arguments = received.split(" ");
//                        out.writeUTF(keyRetrieve(new BigInteger(arguments[1])));
//                        break;
//                    case "getKeyValues":
//                        String ress=getKeyValues(out);
//                        out.writeUTF(ress);
//                        break;
//                    case "updateMyReplica":
//                        out.writeUTF(updateMyReplica(arguments[1], Integer.parseInt(arguments[2])));
//                        break;
//                    case "printReplicas":
//                        out.writeUTF(printReplicas());
//                        break;
//                    case "printKeyValues":
//                        out.writeUTF(printKeyValues());
//                        break;
//                    case "getKeyValuesPred":
//                        String res = getKeyValuesPred(out, Integer.parseInt(arguments[1]));
//                        out.writeUTF(res);
//                        break;
//                    case "updateReplicaKeysForReplica2":
//                        out.writeUTF(updateReplicaKeysForReplica2());
//                        break;
//                    case "clearReplica":
//                        out.writeUTF(clearReplica(Integer.parseInt(arguments[1])));
//                        break;
//                    case "dummyFailure":
//                        dummyFailure(out);
//                        break;
//                    case "assignMyReplicas":
//                        out.writeUTF(assignMyReplicas());
//                        break;
//                    case "recoverStageOne":
//                        out.writeUTF(recoverStageOne(out));
//                        break;
//                    case "recoverStageTwo":
//                        out.writeUTF(recoverStageTwo());
//                        break;
//                    case "updateFingerTableInRepair":
//                        out.writeUTF(updateFingerTableInRepair(Integer.parseInt(arguments[1]),arguments[2]));
//                        break;
//                    default: server.close();
//


                        /*
                         * case "moveKeys" : out.writeUTF(moveKeys(new
                         * BigInteger(arguments[1])))); break;
                         */
                //}

                // out.writeUTF("Thank you for connecting to "
                // + server.getLocalSocketAddress() + "\nGoodbye!");
                serverSocket.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
