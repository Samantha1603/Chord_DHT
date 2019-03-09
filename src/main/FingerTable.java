package main;

public class FingerTable
{
    private int start;
    private int intervalBegin;
    private int intervalEnd;
    private PeerNode successor;

    //Setters
    public void setStart(int newStart){
        this.start = newStart;
    }

    public void setInterval(int begin, int end){
        this.intervalBegin = begin;
        this.intervalEnd = end;
    }

    public void setSuccessor(PeerNode newSuccessor){
        this.successor = newSuccessor;
    }

    //Getters
    public int getStart(){
        return this.start;
    }

    public int getIntervalBegin(){
        return this.intervalBegin;
    }

    public int getIntervalEnd(){
        return this.intervalEnd;
    }

    public PeerNode getSuccessor(){
        return this.successor;
    }

    public FingerTable(){
    }

    public FingerTable(int startID, PeerNode succ) {
        start = startID;
        successor = succ;
    }
}
