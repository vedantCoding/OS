import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class schedulingalgorithm{

    public static int[] AtInput(int n){
        int At[] = new int[n];// Arrival time
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert Arrival time for each process:");
        for(int i = 0 ;i < n;i++){
            System.out.println("Insert Arrival time for process no."+(i+1)
            );
            At[i] = sc.nextInt();
        }
        return At;
    }

    public static int[] BtInput(int n){
        int Bt[] = new int[n];// Arrival time
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert Burst time for each process:");
        for(int i = 0 ;i < n;i++){
            System.out.println("Insert Burst time for process no."+(i+1));
            Bt[i] = sc.nextInt();
        }
        return Bt;
    }

    public static void printSoln(int At[],int Bt[],int Ct[],int TAt[],int Wt[]){
        float TotalTAt = 0;
        float TotalWt = 0;
        System.out.println("Final soln is as follows:");
        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < At.length; i++) {
            System.out.println((i+1) + "\t\t" + At[i] + "\t\t" + Bt[i] + "\t\t" + Ct[i] + "\t\t" + TAt[i] + "\t\t" + Wt[i]  );
            TotalTAt += TAt[i];
            TotalWt += Wt[i];
        }   
        System.out.println("Average TurnAround time is: " + (TotalTAt/At.length));
        System.out.println("Average waiting time is: " + (TotalWt/At.length));

    }


    public static  void FCFS(){
        System.out.println("Enter the total no. of processes:");
        Scanner sc = new Scanner(System.in);
        int n =sc.nextInt();
        int At[] = AtInput(n);// Arrival time
        int Bt[] = BtInput(n);//Burst time
        int Ct[] = new int[n];//Completion time
        int TAt[] = new int[n];//TurnAround time
        int Wt[] = new int[n];// waiting time

        //for calculating completion time,turnaround time and waiting time

        Ct[0] = Bt[0];
        TAt[0] = Ct[0] - At[0];
        Wt[0] = TAt[0] - Bt[0];


        for(int i = 1;i < Bt.length;i++){
            Ct[i] = Bt[i] + Ct[i-1];
            TAt[i] = Ct[i] - At[i];
            Wt[i] = TAt[i] - Bt[i];

        }
        printSoln(At, Bt, Ct, TAt, Wt);
    }

    public static void SJFnp(){
        //this function is for Non Premptive Shortest Job First Scheduling algorithm
        System.out.println("Enter the total no. of processes:");
        Scanner sc = new Scanner(System.in);
        int n =sc.nextInt();
        int At[] = AtInput(n);// Arrival time
        int Bt[] = BtInput(n);//Burst time
        int Ct[] = new int[n];//Completion time
        int TAt[] = new int[n];//TurnAround time
        int Wt[] = new int[n];// waiting time
        boolean[] completed = new boolean[n];
        int currentTime = 0;
        int completedProcesses = 0;

        while(completedProcesses < n){
            int minIdx = -1;
            int minBurst = Integer.MAX_VALUE;
            for(int i = 0;i < n;i++){
                if(At[i] <= currentTime && !completed[i] && Bt[i] < minBurst ){  //in this we compare burst time for shortest job
                    minIdx = i;
                    minBurst = Bt[i];
                }
            }

            if(minIdx == -1)
                currentTime++;
            else{
                Ct[minIdx] = currentTime + Bt[minIdx];
                TAt[minIdx] = Ct[minIdx] - At[minIdx];
                Wt[minIdx] = TAt[minIdx] - Bt[minIdx];
                completed[minIdx] = true;
                currentTime += Bt[minIdx];
                completedProcesses++;
            }
        }
        printSoln(At, Bt, Ct, TAt, Wt);
    }
    //what is preemptive?
    //forceful deallocation of memory or not waiting for process to complete
    public static void SJFp(){
        System.out.println("Enter the total no. of processes:");
        Scanner sc = new Scanner(System.in);
        int n =sc.nextInt();
        int At[] = AtInput(n);// Arrival time
        int Bt[] = BtInput(n);//Burst time
        int Ct[] = new int[n];//Completion time
        int TAt[] = new int[n];//TurnAround time
        int Wt[] = new int[n];// waiting time

        //in this we have to add new array to store the remaining time.
        int Rt[] = Arrays.copyOf(Bt,n);
        int currentTime = 0;
        int completedProcesses = 0;
        while(completedProcesses < n){
            int minIdx = -1;
            int minRt = Integer.MAX_VALUE;
            for(int i = 0;i < n;i++){
                if(At[i] <= currentTime && Rt[i] > 0 && Rt[i] < minRt){
                    minIdx = i;
                    minRt = Rt[i];
                }
            }
            if(minIdx == -1)
                currentTime++;
            else{
                Rt[minIdx]--;
                currentTime++;
                if(Rt[minIdx] == 0){
                    Ct[minIdx] = currentTime;
                    TAt[minIdx] = Ct[minIdx] - At[minIdx];
                    Wt[minIdx] = TAt[minIdx] - Bt[minIdx];
                    completedProcesses++;
                }
            }
        }
        printSoln(At, Bt, Ct, TAt, Wt);

    }


    public static int[] priorityInput(int n){
        int priority[] = new int[n];// Arrival time
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert Priority for each process:");
        for(int i = 0 ;i < n;i++){
            System.out.println("Insert Priority for process no."+(i+1));
            priority[i] = sc.nextInt();
        }
        return priority;
    }

    public static void priorityNP_HighestValue(){
        System.out.println("Enter the total no. of processes:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int At[] = AtInput(n); // Arrival time
        int Bt[] = BtInput(n); // Burst time
        int Ct[] = new int[n]; // Completion time
        int TAt[] = new int[n]; // TurnAround time
        int Wt[] = new int[n]; // Waiting time

        int Prior[] = priorityInput(n);
        boolean completed[] = new boolean[n];
        int completedProcesses = 0;
        int currentTime = 0;

        while(n > completedProcesses){
            int idx = -1;
            int highestPriority = Integer.MIN_VALUE;
            for(int i = 0;i < n;i++){
                if((Prior[i] > highestPriority || (Prior[i] == highestPriority && At[i] < At[idx])) && !completed[i] && At[i] <= currentTime){
                    idx = i;
                    highestPriority = Prior[i];
                }
            }

            if(idx == -1)
                currentTime++;
            else{
                currentTime += Bt[idx];
                Ct[idx] = currentTime;
                TAt[idx] = Ct[idx] - At[idx];
                Wt[idx] = TAt[idx] - Bt[idx];
                completed[idx] = true;
                completedProcesses++;
            }
        }
        printSoln(At, Bt, Ct, TAt, Wt);

    }

    public static void priorityNP_LowestValue(){
        System.out.println("Enter the total no. of processes:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int At[] = AtInput(n); // Arrival time
        int Bt[] = BtInput(n); // Burst time
        int Ct[] = new int[n]; // Completion time
        int TAt[] = new int[n]; // TurnAround time
        int Wt[] = new int[n]; // Waiting time

        int Prior[] = priorityInput(n);
        boolean completed[] = new boolean[n];
        int completedProcesses = 0;
        int currentTime = 0;

        while(n > completedProcesses){
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;
            for(int i = 0;i < n;i++){
                if((Prior[i] < highestPriority || (Prior[i] == highestPriority && At[i] < At[idx])) && !completed[i] && At[i] <= currentTime){
                    idx = i;
                    highestPriority = Prior[i];
                }
            }

            if(idx == -1)
                currentTime++;
            else{
                currentTime += Bt[idx];
                Ct[idx] = currentTime;
                TAt[idx] = Ct[idx] - At[idx];
                Wt[idx] = TAt[idx] - Bt[idx];
                completed[idx] = true;
                completedProcesses++;
            }
        }
        printSoln(At, Bt, Ct, TAt, Wt);

    }

    public static void priorityP_HighestValue() {
        System.out.println("Enter the total no. of processes:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int At[] = AtInput(n); // Arrival times
        int Bt[] = BtInput(n); // Burst times
        int Ct[] = new int[n]; // Completion times
        int TAt[] = new int[n]; // Turnaround times
        int Wt[] = new int[n]; // Waiting times
        int prior[] = priorityInput(n); // Priorities
    
        boolean completed[] = new boolean[n]; // Track completion status
        int currentTime = 0; // Current time in the simulation
        int completedProcesses = 0; // Count of completed processes
        int Rt[] = Arrays.copyOf(Bt, n); // Remaining times (initialize with burst times)
    
        while (completedProcesses < n) {
            int Idx = -1;
            int highestPriority = Integer.MIN_VALUE;
    
            // Find the highest priority process that is ready to run
            for (int i = 0; i < n; i++) {
                if (Rt[i] > 0 && At[i] <= currentTime && !completed[i] &&
                    (prior[i] > highestPriority || (prior[i] == highestPriority && (Idx == -1 || At[i] < At[Idx])))) {
                    Idx = i;
                    highestPriority = prior[i];
                }
            }
    
            if (Idx == -1) {
                // No process is ready to run, increment time
                currentTime++;
            } else {
                // Execute the process with the highest priority
                Rt[Idx]--;
                currentTime++;
    
                // If the process is finished
                if (Rt[Idx] == 0) {
                    Ct[Idx] = currentTime;
                    TAt[Idx] = Ct[Idx] - At[Idx];
                    Wt[Idx] = TAt[Idx] - Bt[Idx];
                    completed[Idx] = true;
                    completedProcesses++;
                }
            }
        }
        printSoln(At, Bt, Ct, TAt, Wt);
    }
    
    public static void priorityP_LowestValue() {
        System.out.println("Enter the total no. of processes:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int At[] = AtInput(n); // Arrival times
        int Bt[] = BtInput(n); // Burst times
        int Ct[] = new int[n]; // Completion times
        int TAt[] = new int[n]; // Turnaround times
        int Wt[] = new int[n]; // Waiting times
        int prior[] = priorityInput(n); // Priorities
    
        boolean completed[] = new boolean[n]; // Track completion status
        int currentTime = 0; // Current time in the simulation
        int completedProcesses = 0; // Count of completed processes
        int Rt[] = Arrays.copyOf(Bt, n); // Remaining times (initialize with burst times)
    
        while (completedProcesses < n) {
            int Idx = -1;
            int highestPriority = Integer.MAX_VALUE;
    
            // Find the highest priority process that is ready to run
            for (int i = 0; i < n; i++) {
                if (Rt[i] > 0 && At[i] <= currentTime && !completed[i] &&
                    (prior[i] < highestPriority || (prior[i] == highestPriority && (Idx == -1 || At[i] < At[Idx])))) {
                    Idx = i;
                    highestPriority = prior[i];
                }
            }
    
            if (Idx == -1) {
                // No process is ready to run, increment time
                currentTime++;
            } else {
                // Execute the process with the highest priority
                Rt[Idx]--;
                currentTime++;
    
                // If the process is finished
                if (Rt[Idx] == 0) {
                    Ct[Idx] = currentTime;
                    TAt[Idx] = Ct[Idx] - At[Idx];
                    Wt[Idx] = TAt[Idx] - Bt[Idx];
                    completed[Idx] = true;
                    completedProcesses++;
                }
            }
        }
        printSoln(At, Bt, Ct, TAt, Wt);
    }

    public static void RoundRobin() {
        Scanner sc = new Scanner(System.in);
        int n = 0;
        System.out.println("Enter the no. of processes:");
        n = sc.nextInt();
        System.out.println("Enter the time quantum:");
        int tq = sc.nextInt();
        int At[] = AtInput(n);
        int Bt[] = BtInput(n);
        int Wt[] = new int[n];
        int TAt[] = new int[n];
        int Ct[] = new int[n];

        int currentTime = 0;
        int Rt[] =Arrays.copyOf(Bt, n);
        boolean Completed[] = new boolean[n];
        int completedProcesses= 0;
        Queue<Integer> queue = new LinkedList<>();
        boolean queAdded[] = new boolean[n];

        int minIdx = -1;
        int minAt = 0;
        for(int i = 0;i < n;i++){
            if(At[i] <= currentTime && !Completed[i] ){
                minIdx = i;
                minAt = At[i];
            }

        while(completedProcesses < n){
            int pcurrTime = currentTime;
                if(Rt[minIdx] > tq){
                    Rt[minIdx] -= tq;
                    currentTime += tq;
                }
                else{
                    currentTime += Rt[minIdx];
                    Completed[minIdx] = true;
                    Rt[minIdx] = 0;
                    completedProcesses++;
                    Ct[minIdx] = currentTime;
                    TAt[minIdx] = Ct[minIdx] - At[minIdx];
                    Wt[minIdx] = TAt[minIdx] - Bt[minIdx];
                }
                int Idx = -1;
            for(int k = 0;k < n;k++ ){
                for(int j = 0; j < n;j++){
                    if(pcurrTime < At[j] && currentTime >= At[j] && !Completed[j] && !queAdded[j]){
                        Idx = j;
                    }
                    queue.add(Idx);
                    queAdded[Idx] = true;
                }
            }
            if(Rt[minIdx] > 0){
                queue.add(minIdx);
            }

            minIdx = queue.poll();
            queAdded[minIdx] = false;
            }
        }
        printSoln(At, Bt, Ct, TAt, Wt);

    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        
        
        int k = 1;
        while(k == 1){
        // RoundRobin();
        System.out.println("\n\nChoose below Scheduling Alogrithm: \n1)First Come First Serve\n2)Shortest Job First(Non-preemptive)\n3)Shortest Job First(Preemptive)\n4)Priority (Non-Preemptive,Priority-Highest Value)\n5)Priority (Non-Preemptive,Priority- Lowest Value)\n6)Priority(Preemptive,Priority - Highest Value)\n7)Priority(Preemptive,Priority-Lowest Value)\n8)Round Robin\n9)Exit");
        int n = sc.nextInt();
        switch (n) {
            case 1:
                FCFS();
                break;
            case 2:
            SJFnp();
                break;
                
            case 3:
            SJFp();
                break;

            case 4:
            priorityNP_HighestValue();
                break;

            case 5:
            priorityNP_LowestValue();
                break;

            case 6:
            priorityP_HighestValue();
                break;

            case 7:
            priorityP_LowestValue();
                break;

            case 8:
                RoundRobin();
                break;
            
            default:
                k = 0;
                System.out.println("Exiting......");
                break;
        }
    }
    }
}