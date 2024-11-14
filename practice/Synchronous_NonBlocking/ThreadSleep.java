package synchronous;

public class ThreadSleep {

    public static void sleep(int nanoTime) {
        try {
            Thread.sleep(0, nanoTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
