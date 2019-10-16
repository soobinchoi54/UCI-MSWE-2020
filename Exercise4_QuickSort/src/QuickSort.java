import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class QuickSort {

    public static String[] doQuickSort(String[] toSort, int low, int high) {

        int p; // pivot
        if ((high - low) > 0) {

            p = partition(toSort, low, high);
            doQuickSort(toSort, low, p - 1); // sort to the left of pivot element
            doQuickSort(toSort, p + 1, high); // sort unexplored to the right of pivot element

        }
        return toSort;
    }


    public static int partition(String[] toSort, int low, int high) {
        int p;
        int firsthigh;

        p = high;
        firsthigh = low;

        for (int i = low; i < high; i++) {
            // If current element in index i is smaller than the pivot
            if (toSort[i].compareTo(toSort[p]) < 0) {
                String temp = toSort[i];
                toSort[i] = toSort[firsthigh];
                toSort[firsthigh] = temp;
                firsthigh++;
            } else {
                String temp = toSort[p];
                toSort[p] = toSort[firsthigh];
                toSort[firsthigh] = temp;
            }
        }
        return (firsthigh);

    }

    public static String[] printString(String[] toSort) {
        for (int i = 0; i < toSort.length; i++) {
            if (toSort[i] != null) {
                System.out.print(toSort[i] + " ");
            }
        }
        return toSort;
    }

    public static void main(String[] args) throws IOException {

        File pap = new File("pride-and-prejudice.txt");
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
        String[] sortedArray = doQuickSort(toSort, 0, toSort.length - 1);
        long end = System.nanoTime();
        long elapsed = end - start;
        System.out.println("Time taken for Quick Sort " + elapsed);

        textFile.close();
    }
}
