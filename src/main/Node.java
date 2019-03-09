package main;

import java.util.Scanner;

public class Node
{
    String ipAddress;

    Node(String ipAddress)
    {
        this.ipAddress=ipAddress;

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

    public void run() {

        Scanner scanner = new Scanner(System.in);

        boolean isNodeRunning = true;

        while (isNodeRunning) {

            //Print options menu on command line
            printOptionsMenu();


            int switchInt = scanner.nextInt();

            switch (switchInt) {

                case 1: //Finger table details
                    System.out.println("Finger table details");
                    break;
                case 2: //Leave Chord Zone
                    System.out.println("Leave Chord Zone");
                    break;
                case 3: //Upload Files to Zone
                    System.out.println("Upload Files to Zone");
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

        scanner.close();

    }
}
