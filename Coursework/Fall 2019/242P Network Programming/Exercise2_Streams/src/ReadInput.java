import java.io.BufferedReader;
import java.io.FileReader;

public class ReadInput {

    public static void main(String[] args) {
        // loop prints the name of file
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i] + ":  ");
            // subroutine counts the number of lines
            CountLines(args[i]);
        }
    }

    private static void CountLines(String txtFileName) {

        BufferedReader inputStream; // A stream for reading from the file.
        int lineCount; // Number of lines in the file.

        try {
            inputStream = new BufferedReader(new FileReader(txtFileName));
        } catch (Exception e) { // catch any error in trying to access the text file
            System.out.println("Cannot open text file.");
            return;
        }

        lineCount = 0;

        try {
            String line = inputStream.readLine();
            while (line != null) { // when BufferedReader encounters the end of line, readLine() will return null
                lineCount++;               // Count this line.
                line = inputStream.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error reading from the text file.");
            return;
        }
        System.out.println(lineCount + " lines");
    }
}
