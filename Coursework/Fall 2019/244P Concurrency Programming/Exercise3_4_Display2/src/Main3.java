import java.util.concurrent.Semaphore;

public class Main3 {

    private static void nap(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void addProc(HighLevelDisplay d) {

        // Add a sequence of addRow operations with short random naps.
        for (int i = 0; i < 20; i++) {
            d.addRow("FLIGHT  " + i);
            nap(1000);
        }

    }

    private static void deleteProc(HighLevelDisplay d) {

        // Add a sequence of deletions of row 0 with short random naps.
        for (int i = 0; i < 20; i++) {
            d.deleteRow(0);
            nap(4000);
        }
    }

    public static void main(String[] args) {
        final HighLevelDisplay d = new JDisplay2();
        Semaphore sem = new Semaphore(2); // assigned 2 permits, so add and delete can be accessed at the same time

        new Thread() {
            public synchronized void run() {
                try {
                    sem.acquire();
                    addProc(d);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sem.release();
            }
        }.start();


        new Thread() {
            public synchronized void run() {
                try {
                    sem.acquire();
                    deleteProc(d);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sem.release();
            }
        }.start();

    }
}
