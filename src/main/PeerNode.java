package main;

import Util.Constant;
import Util.Util;
import java.util.SortedSet;


public class PeerNode
{
    private FingerTable[] fingerDetails;
    private SortedSet<Integer> list;
    private int id;
    private int predecessor=-1;
    private int successor=-1;
    PeerNode(int randomNo, SortedSet<Integer> peerList)
    {
        fingerDetails = new FingerTable[Constant.m];
        id = randomNo;
        list=peerList;
        System.out.println("list="+list.size());
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
            fingerDetails[i]=new FingerTable(i,node);
            // fingerDetails[i].toString();
        }
    }
    public void getPredecessorSuccessor()
    {
        for(Integer i:list)
        {
            if(i<id)
            {
                predecessor=i;
            }
            else if(i>id)
            {
                successor=i;
            }
        }
        if(list.size()==1)
        {
            predecessor=id;
            successor=id;
        }
        if(predecessor==-1)
        {
            predecessor=list.last();
        }
        if(successor==-1)
        {
            successor=list.first();
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
            System.out.println(fingerDetails[i].getId()+"::"+fingerDetails[i].getNodeVal());
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

    public int getPredecessor() {
        return predecessor;
    }

    public int getSuccessor() {
        return successor;
    }
}
