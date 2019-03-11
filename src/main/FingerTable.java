package main;

import Util.Constant;
import Util.Util;

public class FingerTable
{
    private int id;
    private int nodeVal;

    public FingerTable(int id,int val) {

        this.id=id;
        this.nodeVal=val;
    }

    public int getId() {
        return id;
    }

    public int getNodeVal() {
        return nodeVal;
    }

    @Override
    public String toString() {
        return "FingerTable{" +
                "id=" + id +
                ", nodeVal=" + nodeVal +
                '}';
    }
}
