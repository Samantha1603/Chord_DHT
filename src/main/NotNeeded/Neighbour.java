package main.NotNeeded;

public class Neighbour
{
    String ipAddress;
    int zoneStart;
    int zoneEnd;

    // constructor
    public Neighbour(String ip, int zoneSrt, int zoneEnd)
    {
        this.ipAddress = ip;
        this.zoneStart = zoneSrt;
        this.zoneEnd = zoneEnd;
    }

    public String getIP() {
        return ipAddress;
    }

    // getter for start zone
    public int getZoneSrt() {
        return zoneStart;
    }

    // geter for end zone
    public int getZoneEnd() {
        return zoneEnd;
    }

}

