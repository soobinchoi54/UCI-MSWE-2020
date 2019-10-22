import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HelloRunnable implements Runnable {


    private static volatile boolean running = true;

    public void run() {
        int counter = 0;
        while (running) {
            try {
                LocalTime time = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                ++counter;
                System.out.println("Hello World! I'm thread " + counter + ". The time is " + time.format(formatter) + ".");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                if (!running) {
                    break;
                }
            }
        }
    }

    public static void stopThread() {
        running = false;
    }


    public static void main(String[] args) {

        // give user options
        System.out.println("Here are your options");
        System.out.println("a - Create a new thread \nb - Stop a given thread (e.g. \"b 2\" kills thread 2)\nc - Stop all threads and exit this program");

        Scanner input = new Scanner(System.in);
        String option = input.nextLine();

        while (!option.equals("c")) {
            switch (option) {
                case "a":
                    System.out.println("Creating a new thread");
                    (new Thread(new HelloRunnable())).start();
                    // equivalent to:
                    // HelloRunnable w = new HelloRunnable();
                    // Thread t = new Thread(w);
                    // t.start();
                    break;
                case "b":
                    System.out.println("Stopping thread");
                    HelloRunnable.stopThread();
                    break;
                case "c":
                    System.out.println("Stopping and exiting program");
                    HelloRunnable.stopThread();
                    input.close();
                    break;
                default:
                    System.out.println("Please pick from a, b, and c.");

            }
            option = input.nextLine();
        }
        if (option.equals("c")) {
            System.out.println("Stop all threads and exit the program");
        }
    }

}
