package synchronous;

public class Synchronous2 implements Strategy {

    private final TaskA A;
    private final TaskB B;

    public Synchronous2(TaskA A, TaskB B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public void call() {
        while (!A.nioRead()) ;
        A.A();
        A.B();

        B.C();
        while (!B.nioRead()) ;
        B.D();
    }
}
