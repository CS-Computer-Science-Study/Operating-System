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
        boolean[] M = new boolean[2], N = new boolean[2];

        M[0] = A.nioRead();
        B.C();
        N[0] = B.nioRead();

        while (!(M[1] && N[1])) {
            if (!M[0]) {
                M[0] = A.nioRead();
            }
            else if (!M[1]) {
                A.A();
                A.B();
                M[1] = true;
            }

            if (!N[0]) {
                N[0] = B.nioRead();
            }
            else if (!N[1]) {
                B.D();
                N[1] = true;
            }
        }
    }
}
