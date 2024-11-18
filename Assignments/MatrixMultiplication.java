import java.util.*;
class MatrixMultiplication {
    static final int MATRIX_SIZE = 4;

    static int[][] matA = new int[MATRIX_SIZE][MATRIX_SIZE];
 
    static int[][] matB = new int[MATRIX_SIZE][MATRIX_SIZE];
    static int[][] matC = new int[MATRIX_SIZE][MATRIX_SIZE]; // Resultant matrix

    public static void main(String[] args) {
        Thread[] threads = new Thread[MATRIX_SIZE * MATRIX_SIZE];
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the elements of Matrix A:");
        for (int k = 0; k < MATRIX_SIZE; k++) {
            for(int i=0;i<MATRIX_SIZE;i++){
                int element=sc.nextInt();
                matA[k][i]= element;
            }
            }
        System.out.println("Enter the elements of Matrix B:");
         for (int k = 0; k < MATRIX_SIZE; k++) {
            for(int i=0;i<MATRIX_SIZE;i++){
                int element=sc.nextInt();
                matB[k][i]= element;
            }
            }  
        // Create and start threads for each element in the resultant matrix
        int threadCount = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                threads[threadCount] = new Thread(new MultiplyTask(i, j));
                threads[threadCount].start();
                threadCount++;
            }
        }

        // Wait for all threads to complete
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Display the resultant matrix
        System.out.println("Resultant Matrix:");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(matC[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Task to perform multiplication for one element in the resultant matrix
    static class MultiplyTask implements Runnable {
        int row, col;

        MultiplyTask(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void run() {
            matC[row][col] = 0;
            for (int k = 0; k < MATRIX_SIZE; k++) {
                matC[row][col] += matA[row][k] * matB[k][col];
            }
  }
 }
}