package Peer;

import Util.Constant;
import Util.Util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.SortedSet;

public class Node implements Serializable
{
    int id;
    String ipAddress;
    String successor;
    String predecessor;
    int startZone;
    int endZone;
    FingerTable[] fingerDetails;
    HashMap<Integer,String> peerMap;
    SortedSet<Integer> peerList;
    public Node(int id, String ip,String succ,String pred, SortedSet<Integer> list,HashMap<Integer,String> map,int start,int end)
    {
        fingerDetails = new FingerTable[Constant.m];
        this.id=id;
        this.ipAddress=ip;
        this.successor=succ;
        this.predecessor=pred;
        this.peerList=list;
        this.peerMap=map;
        this.startZone=start;
        this.endZone=end;
        createOrUpdate();

    }
    public void createOrUpdate()
    {
        for(int i = 0; i< Constant.m; i++)
        {
            int pos= Util.getPosition(id,i);
            int node=getMin(pos);
            if(node==-1)
            {
                node=id;
            }
            fingerDetails[i]=new FingerTable(i,node,peerMap.get(node));
        }
    }

    public int getMin(int val)
    {
        if(peerList.size()>1)
        {
            int min=peerList.first();
            int minDist=(val>peerList.first()?val:Math.abs(peerList.first()-val));
            int preDist=minDist;
            for(Integer i:peerList)
            {
                if(i>val) {
                    int dist = Math.abs(i - val);
                    if (dist < minDist) {
                        min =i;
                        minDist = dist;
                    }
                }
                else if(peerList.last()==i)
                {
                    min =peerList.first();
                }
            }
            return min;

        }
        else if(peerList.size()==0)
        {
            return -1;
        }
        else {

            return peerList.first();
        }
    }

    public void display()
    {
        System.out.println("---------------------------------");
        System.out.println("Node id:"+id);
        System.out.println("Predecessor:"+predecessor);
        System.out.println("Suuceesor:"+successor);
        System.out.println("Start zone:"+startZone);
        System.out.println("End zone:"+endZone);
        for (int i=0;i<fingerDetails.length;i++)
        {
            System.out.println(fingerDetails[i].getId()+"::"+fingerDetails[i].getNodeVal()+"::"+fingerDetails[i].getIpAddr());
        }
        System.out.println("---------------------------------");
    }

}
