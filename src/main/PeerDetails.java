package main;

import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class PeerDetails extends Thread
{
    PeerNode nodeDetails=new PeerNode(-1, new TreeSet<>());

    PeerDetails(int randomNumber){

        PeerLookUp peerLookUp=new PeerLookUp(randomNumber,nodeDetails);
        peerLookUp.start();
    }

    public void run()
    {
        boolean isNodeRunning = true;
        Scanner sc=new Scanner(System.in);
        while (isNodeRunning) {
            printOptionsMenu();
            int switchInt = sc.nextInt();

            switch (switchInt) {

                case 1: //Finger table details
                    break;

                case 2: //Leave Chord Zone
                    break;

                case 3: //Upload Files to Zone
                    break;
                case 4: //Download Files from Zone

                    System.out.println("Download files from zone");
                    break;
                case 5: //Files at the Zone
                    System.out.println("Files at the zone");
                    break;


                default:
                    System.out.println("Enter the correct number");

            }

        }
    }

    public static void printOptionsMenu()
    {
        System.out.println("Choose an option:");
        System.out.println("1 - Finger Table Details ");
        System.out.println("2 - Leave chord zone ");
        System.out.println("3 - Upload File to zone ");
        System.out.println("4 - Download File from zone ");
        System.out.println("5 - Files present at this node ");

        System.out.print("Your input - ");
    }

}
