import greenfoot.Actor;

public class DelayedExecution extends Actor {
    private final int max;
    private final Runnable callback;
    private int remaining = -1;

    public DelayedExecution(int timer, Runnable callback) {
        this.max = timer;
        this.callback = callback;

        Misc.getCurrentWorld().addObject(this, 0, 0);
    }

    public void reset() {
        this.remaining = max;
    }

    @Override
    public void act() {
        remaining--;

        if (remaining == 0) {
            callback.run();
        }
    }
}
