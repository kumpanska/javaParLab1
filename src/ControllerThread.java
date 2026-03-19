import java.util.Arrays;

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
        Integer[] order = new Integer[durations.length];
        for (int i = 0; i < durations.length; i++)
            order[i] = i;
        Arrays.sort(order, (a, b) -> durations[a] - durations[b]);
        int elapsed = 0;
        for (int idx : order) {
            int waitTime = durations[idx] - elapsed;
            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                elapsed += waitTime;
            }
            allow(idx);;
        }
    }
}