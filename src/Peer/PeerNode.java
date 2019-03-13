package Peer;

import Util.Constant;
import Util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedSet;


public class PeerNode
{
    private FingerTable[] fingerDetails;
    private HashMap<Integer,String> peerMap1;
    private SortedSet<Integer> list1;
    private HashSet<Key> peerKeyList;
    private int id;
    private String ipAddr;
    private String predecessor;
    private String successor;
    private int keyStart;
    private int keyEnd;
    PeerNode(int randomNo, SortedSet<Integer> peerList, HashMap<Integer,String> map, HashSet<Key> keyList, String ip)
    {
        fingerDetails = new FingerTable[Constant.m];
        id = randomNo;
        list1=peerList;
        ipAddr=ip;
        peerMap1=map;
        peerKeyList=keyList;
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
            fingerDetails[i]=new FingerTable(i,node,peerMap1.get(node));
        }
    }
    public void getPredecessorSuccessor()
    {
        for(Integer i:list1)
        {
            if(i<id)
            {
                predecessor=peerMap1.get(i);
            }
            else if(i>id)
            {
                successor=peerMap1.get(i);
            }
        }
        if(list1.size()==1)
        {
            predecessor=peerMap1.get(id);
            successor=peerMap1.get(id);
        }
        if(predecessor==ipAddr)
        {
            predecessor=peerMap1.get(list1.last());
        }
        if(successor==ipAddr)
        {
            successor=peerMap1.get(list1.first());
        }
    }
    public int getMin(int val)
    {
        if(list1.size()>1)
        {
            int min=list1.first();
            int minDist=(val>list1.first()?val:Math.abs(list1.first()-val));
            int preDist=minDist;
            for(Integer i:list1)
            {
                if(i>val) {
                    int dist = Math.abs(i - val);
                    if (dist < minDist) {
                        min =i;
                        minDist = dist;
                    }
                }
                else if(list1.last()==i)
                {
                    min =list1.first();
                }
            }
            return min;

        }
        else if(list1.size()==0)
        {
            return -1;
        }
        else {

            return list1.first();
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
        for (Integer i:list1)
        {
            System.out.print(i+"-");
        }
        System.out.println();
    }


    public SortedSet<Integer> getList()
    {
        return list1;
    }

    public void setList(SortedSet<Integer> newList)
    {
        this.list1=list1;
    }

    public void remove(int id)
    {
        this.list1.remove(id);
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
        return peerMap1;
    }

    public void setPeerMap(HashMap<Integer, String> peerMap) {
        this.peerMap1 = peerMap;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public HashSet<Key> getPeerKeyList() {
        return peerKeyList;
    }

    public void setPeerKeyList(HashSet<Key> peerSet) {
        this.peerKeyList = peerSet;
    }

}
