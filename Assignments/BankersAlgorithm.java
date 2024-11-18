import java.util.Random;
import java.util.Scanner;
public class BankersAlgorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter number of resources: ");
        int m = sc.nextInt();

        int[][] max = new int[n][m];
        int[][] alloc = new int[n][m];
        int[][] need = new int[n][m];
        int[] avail = new int[m];

        // Set maximum resources for each process randomly
        System.out.println("Max matrix (generated randomly):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max[i][j] = r.nextInt(10)+1; // Random max resources between 0 and 9
                System.out.print(max[i][j] + " ");
            }
            System.out.println();
        }

        // Set allocation randomly; should not exceed max resources
        System.out.println("Allocation matrix (generated randomly):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                alloc[i][j] = r.nextInt(max[i][j] + 1); // Randomly allocate resources
                System.out.print(alloc[i][j] + " ");
            }
            System.out.println();
        }

        // Generate available resources randomly
        System.out.println("Available resources (generated randomly):");
        for (int i = 0; i < m; i++) {
            avail[i] = r.nextInt(10)+1; // Random available resources between 0 and 9
            System.out.print(avail[i] + " ");
        }
        System.out.println();

        // Calculate need matrix
        System.out.println("Need Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - alloc[i][j];
                System.out.print(alloc[i][j] + " ");
            }
            System.out.println();
        }

        boolean[] finish = new boolean[n];
        int[] work = new int[m];
        System.arraycopy(avail, 0, work, 0, m);

        int[] safeSequence = new int[n];
        int count = 0;

        boolean safe = true;
        while (count < n) {
            boolean found = false;
            for (int i = 0; i < n; i++) {
                if (!finish[i]) {
                    int j;
                    for (j = 0; j < m; j++)
                        if (need[i][j] > work[j])
                            break;

                    if (j == m) {
                        for (int k = 0; k < m; k++)
                            work[k] += alloc[i][k];

                        safeSequence[count++] = i;
                        finish[i] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                safe = false;
                break;
            }
        }

        if (safe) {
            System.out.println("System is in a safe state.\nSafe sequence is: ");
            for (int i = 0; i < n; i++)
                System.out.print(safeSequence[i] + " ");
            System.out.println();
        } else {
            System.out.println("System is not in a safe state.");
        }
    }
}