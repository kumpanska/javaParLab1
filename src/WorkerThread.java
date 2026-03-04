public class WorkerThread extends Thread {
    private final int id;
    private final int step;
    private final ControllerThread controller;

    public WorkerThread(int id, int step, ControllerThread controller) {
        this.id = id;
        this.step = step;
        this.controller = controller;
    }
    @Override
    public void run() {
        long sum = 0;
        long count = 0;
        long currentElement = 0;
        do {
            sum += currentElement;
            currentElement += step;
            count++;
        } while (!controller.isAllowed(id));
        System.out.println("Thread " + id + ": sum=" + sum + ", count=" + count);
    }
}