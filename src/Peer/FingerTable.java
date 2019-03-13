package Peer;

import java.io.Serializable;

public class FingerTable implements Serializable
{
    private int id;
    private String ipAddr;
    private int nodeVal;

    public FingerTable(int id,int val,String ip) {

        this.id=id;
        this.nodeVal=val;
        this.ipAddr=ip;
    }

    public int getId() {
        return id;
    }

    public int getNodeVal() {
        return nodeVal;
    }

    public String getIpAddr() {
        return ipAddr;
    }

}
