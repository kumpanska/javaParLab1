import java.util.Random;

public class Main {

    static final int numberThreads = 10;
    static int[] steps;
    static int[] durations;

    public static void main(String[] args) throws InterruptedException {

        generateStepsDuration();
        ControllerThread controller = new ControllerThread(durations);
        WorkerThread[] workers = new WorkerThread[numberThreads];
        for (int i = 0; i < numberThreads; i++)
        {
            workers[i] = new WorkerThread(i + 1, steps[i], controller);
        }
        new Thread(controller).start();
        for (WorkerThread w : workers)
        {
            w.start();
        }
        for (WorkerThread w : workers)
        {
            w.join();
        }
    }
    static void generateStepsDuration(){
        Random random = new Random();
        steps = new int[numberThreads];
        durations = new int[numberThreads];
        for (int i = 0; i < numberThreads; i++)
        {
            steps[i] = random.nextInt(1,100);
            durations[i] = random.nextInt(1000,5000);
        }
    }
}