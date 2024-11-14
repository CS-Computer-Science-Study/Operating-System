package synchronous;

import java.lang.reflect.Method;

public class Judge {

    private static final String methodName = "isDone";

    public static void execute(Strategy strategy, TaskA taskA, TaskB taskB) {
        long startTime = System.nanoTime();
        strategy.call();
        long endTime = System.nanoTime();

        try {
            Method methodA = TaskA.class.getDeclaredMethod(methodName);
            methodA.setAccessible(true);
            boolean resultA = (boolean) methodA.invoke(taskA);

            Method methodB = TaskB.class.getDeclaredMethod(methodName);
            methodB.setAccessible(true);
            boolean resultB = (boolean) methodB.invoke(taskB);

            if (!(resultA && resultB)) {
                System.err.println("Not complete Task");
                return;
            }
            System.out.println("Time = " + (endTime - startTime) + " ns");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
