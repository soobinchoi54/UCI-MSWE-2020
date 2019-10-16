import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class InsertionSort {

    public static String[] doInsertionSort(String[] toSort) {
        // both i and j start at 1
        // store key as i and j as i - 1

        // from i = 1 to n, compare key to j
        // while key is less than j, iterate j-- to check again
        for (int i = 1; i <= toSort.length - 1; i++) {
            int key = i;
            int j = i - 1;
            while (j >= 0 && toSort[key].compareTo(toSort[j]) < 0) {
                String temp = toSort [j];
                toSort[j] = toSort[key];
                toSort[key] = temp;
                j--;
                key--;
            }
        }
        return toSort;
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

        // pride and prejudice array input
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
        String[] sortedArray = doInsertionSort(toSort);
        long end = System.nanoTime();
        long elapsed = end - start;
        System.out.println("Time taken for Insertion Sort is " + elapsed);

        textFile.close();

    }
}
