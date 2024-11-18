import java.util.Arrays;
import java.util.Scanner;

public class DiskSchedulingAlgorithms {

    // Function for SSTF (Shortest Seek Time First)
    public static int sstf(int[] requests, int head) {
        Arrays.sort(requests);
        int totalMovement = 0;
        boolean[] visited = new boolean[requests.length];
        int currentHead = head;


        for (int i = 0; i < requests.length; i++) {
            int minDistance = Integer.MAX_VALUE;
            int closestRequestIndex = -1;
    
            // Find the closest unvisited request
            for (int j = 0; j < requests.length; j++) {
                if (!visited[j]) {
                    int distance = Math.abs(requests[j] - currentHead);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestRequestIndex = j;
                    }
                }
            }

            visited[closestRequestIndex] = true;
            totalMovement += minDistance;
            currentHead = requests[closestRequestIndex];
        }

        return totalMovement;
    }

    // Function for SCAN (Elevator Algorithm)
    public static int scan(int[] requests, int head, int diskSize) {
        Arrays.sort(requests);
        int totalMovement = 0;
        int currentHead = head;

        // Find the index of the first request greater than the head
        int index = 0;
        while (index < requests.length && requests[index] < head) {
            index++;
        }

        // Move towards the end of the disk
        for (int i = index; i < requests.length; i++) {
            totalMovement += Math.abs(requests[i] - currentHead);
            currentHead = requests[i];
        }

        // Move to the end of the disk
        totalMovement += Math.abs(diskSize - 1 - currentHead);
        currentHead = diskSize - 1;

        // Move back towards the spindle
        for (int i = index - 1; i >= 0; i--) {
            totalMovement += Math.abs(requests[i] - currentHead);
            currentHead = requests[i];
        }

        return totalMovement;
    }

    // Function for C-LOOK
    public static int cLook(int[] requests, int head) {
        Arrays.sort(requests);
        int totalMovement = 0;
        int currentHead = head;

        // Find the index of the first request greater than the head
        int index = 0;
        while (index < requests.length && requests[index] < head) {
            index++;
        }

        // Move towards the end of the disk (only up to the farthest request)
        for (int i = index; i < requests.length; i++) {
            totalMovement += Math.abs(requests[i] - currentHead);
            currentHead = requests[i];
        }

        // Jump back to the beginning (smallest request)
        if (index > 0) {
            totalMovement += Math.abs(requests[0] - currentHead);
            currentHead = requests[0];

            // Continue servicing requests from the beginning
            for (int i = 1; i < index; i++) {
                totalMovement += Math.abs(requests[i] - currentHead);
                currentHead = requests[i];
            }
        }

        return totalMovement;
    }

    // Main function to run the algorithms
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input the number of disk requests
        System.out.println("Enter the number of disk requests:");
        int n = sc.nextInt();

        int[] requests = new int[n];
        System.out.println("Enter the disk requests:");
        for (int i = 0; i < n; i++) {
            requests[i] = sc.nextInt();
        }

        // Input the initial head position
        System.out.println("Enter the initial head position:");
        int head = sc.nextInt();

        // Input the disk size (for SCAN algorithm)
        System.out.println("Enter the disk size:");
        int diskSize = sc.nextInt();

        // Display results for each algorithm
        System.out.println("Total head movement using SSTF: " + sstf(requests, head));
        System.out.println("Total head movement using SCAN: " + scan(requests, head, diskSize));
        System.out.println("Total head movement using C-LOOK: " + cLook(requests, head));

        sc.close();
    }
}