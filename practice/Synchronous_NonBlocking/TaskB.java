package synchronous;

public class TaskB {

    private long readStartTime = -1;
    private final long requiredReadTime = 2000000000;
    private boolean isReadDone, isCDone, isDDone;

    public void C() {
        if (isCDone) {
            throw new RuntimeException("C() is already done");
        }
        ThreadSleep.sleep(80000);
        isCDone = true;
    }

    public boolean nioRead() {
        if (isReadDone) {
            throw new RuntimeException("TaskB read() is already done");
        }
        if (!isCDone) {
            throw new RuntimeException("C() is not done yet");
        }

        if (readStartTime == -1) {
            readStartTime = System.nanoTime();
        }

        if (System.nanoTime() - readStartTime >= requiredReadTime) {
            return isReadDone = true;
        }
        return false;
    }

    public void D() {
        if (isDDone) {
            throw new RuntimeException("D() is already done");
        }
        if (!isReadDone) {
            throw new RuntimeException("TaskB read() is not done yet");
        }
        ThreadSleep.sleep(20000);
        isDDone = true;
    }

    private boolean isDone() {
        return isReadDone && isCDone && isDDone;
    }
}
