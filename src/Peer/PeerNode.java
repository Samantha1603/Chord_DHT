package Peer;

import Util.Constant;
import Util.Util;

import java.util.HashMap;
import java.util.SortedSet;


public class PeerNode
{
    private FingerTable[] fingerDetails;
    private HashMap<Integer,String> peerMap;
    private SortedSet<Integer> list;
    private int id;
    private String ipAddr;
    private String predecessor;
    private String successor;
    private int keyStart;
    private int keyEnd;
    PeerNode(int randomNo, SortedSet<Integer> peerList, HashMap<Integer,String> map, String ip)
    {
        fingerDetails = new FingerTable[Constant.m];
        id = randomNo;
        list=peerList;
        ipAddr=ip;
        peerMap=map;
        predecessor=ip;
        successor=ip;
        if(randomNo==-1)
        {

        }
        else {
            getPredecessorSuccessor();
            createOrUpdate();
        }


    }
    public void createOrUpdate()
    {
        for(int i=0;i<Constant.m;i++)
        {
            int pos=Util.getPosition(id,i);
            int node=getMin(pos);
            if(node==-1)
            {
                node=id;
            }
            fingerDetails[i]=new FingerTable(i,node,peerMap.get(node));
        }
    }
    public void getPredecessorSuccessor()
    {
        for(Integer i:list)
        {
            if(i<id)
            {
                predecessor=peerMap.get(i);
            }
            else if(i>id)
            {
                successor=peerMap.get(i);
            }
        }
        if(list.size()==1)
        {
            predecessor=peerMap.get(id);
            successor=peerMap.get(id);
        }
        if(predecessor==ipAddr)
        {
            predecessor=peerMap.get(list.last());
        }
        if(successor==ipAddr)
        {
            successor=peerMap.get(list.first());
        }
    }
    public int getMin(int val)
    {
        if(list.size()>1)
        {
            int min=list.first();
            int minDist=(val>list.first()?val:Math.abs(list.first()-val));
            int preDist=minDist;
            for(Integer i:list)
            {
                if(i>val) {
                    int dist = Math.abs(i - val);
                    if (dist < minDist) {
                        min =i;
                        minDist = dist;
                    }
                }
                else if(list.last()==i)
                {
                    min =list.first();
                }
            }
            return min;

        }
        else if(list.size()==0)
        {
            return -1;
        }
        else {

            return list.first();
        }
    }

    public void display()
    {
        System.out.println("---------------------------------");
        System.out.println("Node id:"+id);
        System.out.println("Predecessor:"+predecessor);
        System.out.println("Suuceesor:"+successor);
        for (int i=0;i<fingerDetails.length;i++)
        {
            System.out.println(fingerDetails[i].getId()+"::"+fingerDetails[i].getNodeVal()+"::"+fingerDetails[i].getIpAddr());
        }
        System.out.println("---------------------------------");
    }


    public void displayList()
    {
        System.out.println("---------------------------------");
        System.out.println("List");
        for (Integer i:list)
        {
            System.out.print(i+"-");
        }
        System.out.println();
    }


    public SortedSet<Integer> getList()
    {
        return list;
    }

    public void setList(SortedSet<Integer> newList)
    {
        this.list=list;
    }

    public void remove(int id)
    {
        this.list.remove(id);
        getPredecessorSuccessor();
        createOrUpdate();
    }

    public FingerTable[] getFingerDetails() {
        return fingerDetails;
    }

    public int getId() {
        return id;
    }

    public String getPredecessor() {
        return predecessor;
    }

    public String getSuccessor() {
        return successor;
    }

    public HashMap<Integer, String> getPeerMap() {
        return peerMap;
    }

    public void setPeerMap(HashMap<Integer, String> peerMap) {
        this.peerMap = peerMap;
    }

    public String getIpAddr() {
        return ipAddr;
    }
}
