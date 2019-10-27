import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        // BinaryTreeSet
        BinaryTree bt = new BinaryTree();

        /*
        bt.add("bye");
        bt.add("my name is soobin");
        bt.add("bye");
        bt.add("goodbye");
        bt.add("goodbye");
        bt.add("goodbye");
        */

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


        FileWriter writer = new FileWriter("BinaryTree.csv");
        for (int i = 1; i <= counter; i++) {
            // if size % 10 == 0 and set does not contain the value currently being checked to add
            if (bt.size() % 10 == 0 && !bt.contains(array[i])) {
                long start = System.nanoTime();
                bt.add(array[i]); // check if set contains value and add if false
                long end = System.nanoTime();
                long elapsed = end - start;
                writer.append(Long.toString(elapsed)); // convert long value to string and append string value into LinkedList.csv
                writer.append("\n"); // create new line
            } else {
                bt.add(array[i]);
            }
        }
        writer.flush();
        writer.close();

        // printing unique characters only from pride in prejudice
        System.out.println("set size is: " + bt.size());
        //ll.printList();

        textFile.close();


        // scanning words shuffled text file and taking strings in each line as input
        File shuffle = new File("words-shuffled.txt");
        Scanner wordShuffle = new Scanner(shuffle);

        FileWriter writer3 = new FileWriter("BinaryTree_Shuffle.csv");
        FileWriter writer4 = new FileWriter("BinaryTree_ShuffleWord.csv");

        int counter2 = 0;

        while (wordShuffle.hasNext()) {
            long start2 = System.nanoTime(); // start timer
            String word = wordShuffle.nextLine();
            if (!bt.contains(word)) {
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
