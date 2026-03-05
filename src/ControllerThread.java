public class ControllerThread implements Runnable {

    private final int[] durations;
    private final boolean[] allowed;

    public ControllerThread(int[] durations) {
        this.durations = durations;
        this.allowed = new boolean[durations.length];
    }

    public synchronized boolean isAllowed(int threadId) {
        return allowed[threadId - 1];
    }
    private synchronized void allow(int index) {
        allowed[index] = true;
    }

    @Override
    public void run() {
        Thread[] timers = new Thread[durations.length];
        for (int i = 0; i < durations.length; i++) {
            final int index = i;
            final int waitTime = durations[i];
            timers[i] = new Thread(() -> {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                allow(index);
            });
            timers[i].start();
        }
    }
}