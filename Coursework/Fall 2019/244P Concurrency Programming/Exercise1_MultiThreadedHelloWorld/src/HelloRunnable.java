import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class HelloRunnable implements Runnable {
    private static volatile boolean running = true;

    public void run() {
        int counter = 0;
        int i = 0;
        Thread[] myThreads = new Thread[1000];
        myThreads[i] = new Thread(new HelloRunnable());

        while (running) {
            try {
                LocalTime time = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                counter++;
                System.out.println("Hello World! I'm thread " + " " + counter + ". The time is " + time.format(formatter) + ".");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println();
//                if (!running) {
//                    break;
//                }
            }
        }
    }

    public static void stopThread() {
        running = false;
    }

    public static void main(String[] args) {

        System.out.println("Here are your options");
        System.out.println("a - Create a new thread \nb - Stop a given thread \nc - Stop all threads and exit this program");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();

        while (!option.equals("c")) {
            int i = 0;
            Thread[] myThreads = new Thread[1000];
            myThreads[i] = new Thread(new HelloRunnable());
            switch (option) {
                case "a":
                    System.out.println("Creating a new thread");
                    myThreads[i].start();
                    i++;
                    break;
                case "b":
                    System.out.println("Stopping thread " + (char) (i + 65));
                    stopThread();
                    break;
                default:
                    System.out.println("Please pick from a, b, and c.");
            }
            option = input.nextLine();
        }
        System.out.println("Stopping all threads and exiting the program");
        stopThread();
        input.close();
    }
}
