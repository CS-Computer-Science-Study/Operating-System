package synchronous;

public class SynchronousMain {

    public static void main(String[] args) {
        TaskA A = new TaskA();
        TaskB B = new TaskB();
        //Strategy synchronous = new Synchronous1(A, B);
        Strategy synchronous = new Synchronous2(A, B);

        Judge.execute(synchronous, A, B);
    }
}
