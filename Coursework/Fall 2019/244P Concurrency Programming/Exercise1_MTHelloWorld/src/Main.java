import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class MyThread implements Runnable {
    private final int i;
    int[] arr;

    public MyThread(int i) {
        this.i = i;
    }

    static boolean ended = false;

    public void run() {
        int counter = 0;
        while (!ended) {
            try {
                LocalTime time = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                counter++;
                System.out.println("Hello World! I'm thread " + counter + ". The time is " + time.format(formatter) + ".");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        //            List q = Collections.synchronizedList(new LinkedList<String>());
//        ArrayList<Thread> q = new ArrayList<>();
//        ArrayList<Thread> synclist = (ArrayList<Thread>) Collections.synchronizedList(q);
        Thread[] t = new Thread[10000];
        Scanner sc = new Scanner(System.in);
        boolean ended = false;
        printOps();
        int i = 1;
        try {
            while (!ended) {
                String input = sc.nextLine();
                char option = input.charAt(0);
                switch (option) {
                    case 'a':
                        t[i] = new Thread(new MyThread(i));
                        t[i].start();
                        System.out.println("            Starting thread " + i);
                        i++;
                        // System.out.println(q.size());
                        break;
                    case 'b':
                        char tn = input.charAt(1);
                        int thread = Character.getNumericValue(tn);
                        if (thread <= i) {
                            // System.out.println(t[thread]);
                            t[thread].stop();
                            System.out.printf("             %s Kills thread %d", input, thread);
                            System.out.println();
                        } else {
                            System.out.println("Thread does not exist");
                        }

                        break;
                    case 'c':
                        stopThread();
                        ended = true;
                        System.out.println("             Exit all threads");
                        break;
                    default:
                        System.out.println("Select from options");
                }
            }
        } catch (Exception e) {
        }
    }

    private static void endThread(Thread kt) throws InterruptedException {
        kt.interrupt();
        kt.wait(100000);
    }

    public static void stop() {
        MyThread.ended = true;
    }

    private static void stopThread() {
        MyThread.ended = true;
    }

    public static void printOps() {
        System.out.printf("Here are your options:%n");
        System.out.printf("a- Create a new thread%n");
        System.out.printf("b- Stop a given thread (e.g. 'b 2' kills thread 2%n");
        System.out.printf("c- Stop all threads and exit the program%n");
    }
}
