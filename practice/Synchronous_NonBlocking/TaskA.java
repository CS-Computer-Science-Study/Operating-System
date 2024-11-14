package synchronous;

public class TaskA {

    private long readStartTime = -1;
    private final long requiredReadTime = 1000000000;
    private boolean isReadDone, isADone, isBDone;

    public boolean nioRead() {
        if (isReadDone) {
            throw new RuntimeException("TaskA read() is already done");
        }
        if (readStartTime == -1) {
            readStartTime = System.nanoTime();
        }

        if (System.nanoTime() - readStartTime >= requiredReadTime) {
            return isReadDone = true;
        }
        return false;
    }

    public void A() {
        if (isADone) {
            throw new RuntimeException("A() is already done");
        }
        if (!isReadDone) {
            throw new RuntimeException("TaskA read() is not done yet");
        }

        ThreadSleep.sleep(10000);
        isADone = true;
    }

    public void B() {
        if (isBDone) {
            throw new RuntimeException("B() is already done");
        }
        if (!isADone) {
            throw new RuntimeException("A() is not done yet");
        }

        ThreadSleep.sleep(30000);
        isBDone = true;
    }

    private boolean isDone() {
        return isADone && isBDone && isReadDone;
    }
}
