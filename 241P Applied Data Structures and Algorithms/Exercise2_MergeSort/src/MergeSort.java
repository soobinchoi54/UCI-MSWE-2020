import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MergeSort {

    public static String[] doMergeSort(String toSort[], int low, int high) {
        int middle;

        if (low < high) {
            // find the middle
            middle = (low + high) / 2;
            // sort the first half
            doMergeSort(toSort, low, middle);
            // sort the second half
            doMergeSort(toSort, middle + 1, high);
            // merge the two sorted halves
            merge(toSort, low, middle + 1, high);
        }
        return toSort;
    }

    public static void merge(String toSort[], int low, int middle, int high) {
        // initialize sizes of the first and second halves

        // System.out.println(low + " " + middle + " " + high);

        int array1Size = middle - low;
        int array2Size = high - middle + 1;

        // create arrays
        String[] array1 = new String[array1Size];
        String[] array2 = new String[array2Size];

        // System.out.println(array1Size + " " + array2Size);

        // copy strings into arrays
        for (int i = 0; i < array1Size; i++) {
            array1[i] = toSort[low + i];
        }
        for (int j = 0; j < array2Size; j++) {
            array2[j] = toSort[middle + j];
        }

        // initialize first indexes of first and second arrays in order to merge two arrays
        int i = 0, j = 0;
        // initialize index of the subarray
        int k = low;

        while (i < array1Size && j < array2Size) {
            if (array1[i].compareTo(array2[j]) <= 0) {
                toSort[k] = array1[i];
                i++;
            } else {
                toSort[k] = array2[j];
                j++;
            }
            k++;
        }

        // copy remaining string from array1
        while (i < array1Size) {
            toSort[k] = array1[i];
            i++;
            k++;
        }
        // copy remaining string from array2
        while (j < array2Size) {
            toSort[k] = array2[j];
            j++;
            k++;
        }

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
        String[] sortedArray = doMergeSort(toSort, 0, toSort.length - 1);
        long end = System.nanoTime();
        long elapsed = end - start;
        System.out.println("Time taken for Merge Sort is " + elapsed);

        textFile.close();


    }

}
