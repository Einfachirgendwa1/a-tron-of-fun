public class DelayedExecution {
    private final int max;
    private final Runnable callback;
    private int remaining = -1;

    public DelayedExecution(int timer, Runnable callback) {
        this.max = timer;
        this.callback = callback;
    }

    public void reset() {
        this.remaining = max;
    }

    public void update() {
        remaining--;

        if (remaining == 0) {
            callback.run();
        }
    }
}
