package asynchronous;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ASynchronousMain {

    private final static String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum accumsan justo non turpis condimentum, nec vulputate erat fringilla. Curabitur vitae mauris eget purus gravida tincidunt.";
    private static boolean cpuIntensive = true;
    private static int numberOfThreads = 20;

    public static void main(String[] args) throws InterruptedException {
        // numberOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = new ThreadPoolExecutor(
                numberOfThreads,
                numberOfThreads,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()
        );

        long startTime = System.nanoTime();

        for (int i = 0; i < 50; i++) {
            if (cpuIntensive) {
                executor.submit(new Thread(new RSA(message)));
            }
            else {
                // Write your file path
                executor.submit(new Thread(new FileCopy("", "")));
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Interrupted!");
        }

        long endTime = System.nanoTime();
        System.out.println("CPU intensive = " + cpuIntensive +
                ". Thread number = " + numberOfThreads +
                ". Time = " + (endTime - startTime) + " nano");
    }
}
