import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException; // Import the InputMismatchException class

class Process {
    int AT, BT, FT, WT, TAT, pos;
    int[] ST = new int[20];

    public Process() {
        Arrays.fill(ST, -1);
    }
}

public class SJF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = 0;

        // Taking Input
        System.out.print("Enter the number of processes: ");
        while (true) {
            try {
                n = sc.nextInt();
                if (n > 0) break;
                else System.out.print("Please enter a positive integer: ");
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter an integer: ");
                sc.next(); // clear the invalid input
            }
        }
        Process[] p = new Process[n];

        System.out.println("Enter the process numbers: ");
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            while (true) {
                try {
                    p[i].pos = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter an integer: ");
                    sc.next(); // clear the invalid input
                }
            }
        }

        System.out.println("Enter the Arrival time of processes: ");
        for (int i = 0; i < n; i++) {
            while (true) {
                try {
                    p[i].AT = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter an integer: ");
                    sc.next(); // clear the invalid input
                }
            }
        }

        System.out.println("Enter the Burst time of processes: ");
        for (int i = 0; i < n; i++) {
            while (true) {
                try {
                    p[i].BT = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter an integer: ");
                    sc.next(); // clear the invalid input
                }
            }
        }

        // Declaring variables
        int c = n;
        float time = 0;
        float[] b = new float[n];
        float[] a = new float[n];
        int[][] s = new int[n][20];

        // Initializing burst and arrival time arrays
        int index = -1;
        for (int i = 0; i < n; i++) {
            b[i] = p[i].BT;
            a[i] = p[i].AT;
            Arrays.fill(s[i], -1);
        }

        int tot_wt = 0, tot_tat = 0;
        boolean flag = false;

        while (c != 0) {
            int shortest = -1;
            float min_bt = Float.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (a[i] <= time && b[i] < min_bt && b[i] > 0) {
                    min_bt = b[i];
                    shortest = i;
                    flag = true;
                }
            }

            // If no process is ready to be executed
            if (!flag) {
                time++;
                continue;
            }

            // Execute the shortest process
            time += b[shortest];
            b[shortest] = 0;
            c--;

            // Calculating final, wait, and turnaround times
            p[shortest].FT = (int) time;
            p[shortest].WT = p[shortest].FT - p[shortest].AT - p[shortest].BT;
            tot_wt += p[shortest].WT;
            p[shortest].TAT = p[shortest].BT + p[shortest].WT;
            tot_tat += p[shortest].TAT;
        } // end of while loop

        // Printing output
        System.out.println("Process number  Arrival time  Burst time  Final time  Wait Time  TurnAround Time");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.printf("%14d%13d%11d%11d%11d%17d%n", p[i].pos, p[i].AT, p[i].BT, p[i].FT, p[i].WT, p[i].TAT);
        }

        // Calculating average wait time and turnaround time
        double avg_wt = tot_wt / (double) n;
        double avg_tat = tot_tat / (double) n;

        // Printing average wait time and turnaround time
        System.out.println("The average wait time is: " + avg_wt);
        System.out.println("The average TurnAround time is: " + avg_tat);

        sc.close();
    }
}
