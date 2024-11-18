import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class PageReplacementAlgorithms {

    // Function for FIFO Page Replacement
    public static int fifoPageFaults(int[] pages, int frames) {
        Set<Integer> set = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!set.contains(page)) {
                // Page fault occurs
                pageFaults++;
                if (set.size() == frames) {
                    // Remove the oldest page
                    int removed = queue.poll();
                    set.remove(removed);
                }
                set.add(page);
                queue.add(page);
            }
        }
        return pageFaults;
    }

    // Function for LRU Page Replacement
    public static int lruPageFaults(int[] pages, int frames) {
        Set<Integer> set = new HashSet<>();
        Map<Integer, Integer> lruMap = new HashMap<>();
        int pageFaults = 0;
        int time = 0;

        for (int page : pages) {
            if (!set.contains(page)) {
                // Page fault occurs
                pageFaults++;
                if (set.size() == frames) {
                    // Find the least recently used page
                    int lru = Collections.min(lruMap.entrySet(), Map.Entry.comparingByValue()).getKey();
                    set.remove(lru);
                    lruMap.remove(lru);
                }
                set.add(page);
            }
            lruMap.put(page, time++);
        }
        return pageFaults;
    }

    // Function for Optimal Page Replacement
    public static int optimalPageFaults(int[] pages, int frames) {
        Set<Integer> set = new HashSet<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];

            if (!set.contains(page)) {
                // Page fault occurs
                pageFaults++;
                if (set.size() == frames) {
                    // Find the page that won't be used for the longest time in the future
                    int farthest = i;
                    int victim = -1;
                    for (int s : set) {
                        int nextUse = findNextUse(pages, i, s);
                        if (nextUse == -1) {
                            victim = s;
                            break;
                        } else if (nextUse > farthest) {
                            farthest = nextUse;
                            victim = s;
                        }
                    }
                    set.remove(victim);
                }
                set.add(page);
            }
        }
        return pageFaults;
    }

    // Function to find the next use of a page (for Optimal Algorithm)
    private static int findNextUse(int[] pages, int currentIndex, int page) {
        for (int i = currentIndex + 1; i < pages.length; i++) {
            if (pages[i] == page) {
                return i;
            }
        }
        return -1;
    }

    // Main function to run the algorithms
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of pages in the reference string:");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter the reference string (space-separated integers):");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        System.out.println("Enter the number of frames:");
        int frames = sc.nextInt();

        System.out.println("FIFO Page Faults: " + fifoPageFaults(pages, frames));
        System.out.println("LRU Page Faults: " + lruPageFaults(pages, frames));
        System.out.println("Optimal Page Faults: " + optimalPageFaults(pages, frames));

        sc.close();
    }
}