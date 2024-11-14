package synchronous;

public class Synchronous1 implements Strategy {

    private final TaskA A;
    private final TaskB B;

    public Synchronous1(TaskA A, TaskB B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public void call() {
        // write your code!
        // TaskA: nioRead() -> A() -> B()
        // TaskB: C() -> nioRead() -> D()
    }
}
