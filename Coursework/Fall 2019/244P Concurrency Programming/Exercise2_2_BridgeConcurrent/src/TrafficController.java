public class TrafficController {
    public volatile boolean onBridge = false;

    public void enterLeft() throws InterruptedException {
        synchronized (this) {
            while (onBridge) {
                wait();
            }
            onBridge = true;
        }
    }

    public void enterRight() throws InterruptedException {
        synchronized (this) {
            while (onBridge) {
                wait();
            }
            onBridge = true;
        }
    }

    public void leaveLeft() throws InterruptedException {
        synchronized (this) {
            notifyAll();
            onBridge = false;
        }
    }

    public void leaveRight() throws InterruptedException {
        synchronized (this) {
            notifyAll();
            onBridge = false;
        }
    }

}
