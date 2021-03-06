import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        // LinkedListSet

        HashTableSet ht = new HashTableSet();

        /*ll.add("word2");
        ll.add("word3");
        ll.add("word4");
        ll.add("word2");
        */

        // System.out.println("LinkedList size is " + ll.size());
        // System.out.println("Does 'word3' exist?  " + ll.contains("word3"));

        // -----------
        // Scan pride and prejudice text file and turn into an array
        File pap = new File("pride-and-prejudice.txt");
        Scanner textFile = new Scanner(pap);
        String[] array = new String[140000];
        int counter = 0;
        while (textFile.hasNext()) { // hasNext() returns boolean
            String line = textFile.nextLine(); // nextLine() returns string
            //System.out.println(line);
            String[] words = line.split("\\s+|,\\s*|\"|\'|;\\s*|\\.\\s*|\\?\\s*|!\\s*|-\\s*|:\\s*|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/");
            //System.out.println(words.length);
            for (int i = 0; i < words.length; i++) { // for( int i = 0; i < words.length; i++) word = words[i];
                // System.out.println(words[i]);
                array[++counter] = words[i];
            }
        }
        // print total words counted in pride in prejudice
        System.out.println("total number of words in Pride and Prejudice: " + counter);


        FileWriter writer = new FileWriter("HashTable.csv");
        for (int i = 1; i <= counter; i++) {
            // if size % 10 == 0 and set does not contain the value currently being checked to add
            if (ht.size() % 10 == 0 && !ht.contains(array[i])) {
                long start = System.nanoTime();
                ht.add(array[i]); // check if set contains value and add if false
                long end = System.nanoTime();
                long elapsed = end - start;
                writer.append(Long.toString(elapsed)); // convert long value to string and append string value into LinkedList.csv
                writer.append("\n"); // create new line
            } else {
                ht.add(array[i]);
            }
        }
        writer.flush();
        writer.close();


        System.out.println("set size is: " + ht.size());
        //ll.printList();

        textFile.close();

        // scanning words shuffled text file and taking strings in each line as input
        File shuffle = new File("words-shuffled.txt");
        Scanner wordShuffle = new Scanner(shuffle);

        FileWriter writer3 = new FileWriter("HashTable_Shuffle.csv");
        FileWriter writer4 = new FileWriter("HashTable_ShuffleWord.csv");

        int counter2 = 0;

        while (wordShuffle.hasNext()) {
            long start2 = System.nanoTime(); // start timer
            String word = wordShuffle.nextLine();
            if (!ht.contains(word)) {
                counter2++;
            }
            long end2 = System.nanoTime(); // end timer
            long elapsed2 = end2 - start2; // calculate elapsed time
            writer3.append(Long.toString(elapsed2)); // convert long value to string and append string value into LinkedList.csv
            writer3.append("\n"); // create new line
            writer4.append(word);
            writer4.append("\n");

        }

        writer3.flush();
        writer3.close();
        writer4.flush();
        writer4.close();

        // printing words that don't exist
        System.out.println("number of words that don't exist is :" + counter2);


    }
}
