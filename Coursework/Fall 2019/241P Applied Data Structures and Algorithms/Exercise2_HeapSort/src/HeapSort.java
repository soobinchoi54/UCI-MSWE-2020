import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class HeapSort {

    // 1. Start from a complete binary tree, where a parent node either has 2 children or no child at all
    // fill all nodes by inserting (search and add) strings from Pride and Prejudice

    public static void Heapify(String toSort[], int size, int i) { // i is root
        // initialize the largest root
        int largest = i;
        // any left child element of index i is (2i+1)
        int left = 2 * i + 1;
        // any right child element of index i is (21+2)
        int right = 2 * i + 2;

        // any parent of child i is given by the lower bound of (i-1)/2

        // if left leaf is larger than largest string
        if (left < size && toSort[largest].compareTo(toSort[left]) < 0) {
            largest = left;
        }
        // if right leaf is larger than largest string
        if (right < size && toSort[largest].compareTo(toSort[right]) < 0) {
            largest = right;
        }

        // if current largest is not at the root
        if (largest != i) {
            String temp = toSort[i];
            toSort[i] = toSort[largest];
            toSort[largest] = temp;

            Heapify(toSort, size, largest);
        }

    }

    // 2. Heapify the tree to a Max-Heap or Min-Heap data structure
    // run heapify on all non-leaf elements of the heap (use recursive method)

    public static String[] HeapSort(String[] toSort) {
        int size = toSort.length;

        // Build heap (rearrange array)
        for (int i = size / 2 - 1; i >= 0; i--)
            Heapify(toSort, size, i);

        // Extract one element at a time from the heap
        for (int i = size - 1; i >= 0; i--) {
            // Move current root to end
            String temp = toSort[0];
            toSort[0] = toSort[i];
            toSort[i] = temp;
            // call max heapify on the reduced heap
            Heapify(toSort, i, 0);
        }
        return toSort;
    }

    public static void main(String[] args) throws IOException {
        // pride and prejudice array input
        File pap = new File("pride-and-prejudice_test.txt");
        Scanner textFile = new Scanner(pap);
        int counter = 0;
        String[] array = new String[140000];
        while (textFile.hasNext()) { // hasNext() returns boolean
            String line = textFile.nextLine(); // nextLine() returns string
            //System.out.println(line);
            String[] words = line.split("\\s+|,\\s*|\"|\'|;\\s*|\\.\\s*|\\?\\s*|!\\s*|-\\s*|:\\s*|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/");
            //System.out.println(words.length);
            for (int i = 0; i < words.length; i++) { // for( int i = 0; i < words.length; i++) word = words[i];
                // System.out.println(words[i]);
                array[counter++] = words[i];
            }
        }

        String[] toSort = new String[counter];
        // create another array that only puts non-null elements into array
        for (int i = 0; i < counter; i++) {
            if (array[i] == null) break;
            toSort[i] = array[i];
        }


        long start = System.nanoTime();
        String[] sortedArray = HeapSort(toSort);
        long end = System.nanoTime();
        long elapsed = end - start;
        System.out.println("Time taken for Heap Sort is " + elapsed);

        textFile.close();
    }

}



