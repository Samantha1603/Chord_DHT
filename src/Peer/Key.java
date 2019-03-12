package Peer;

public class Key
{
    int start;
    int end;
    public Key(int s,int e)
    {
        start=s;
        end=s;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
