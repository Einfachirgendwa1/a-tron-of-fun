import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Animator {
    private final List<StateThread> threads = new ArrayList<>();

    public Animator(Consumer<Animator> activeState) {
        activeState.accept(this);
    }

    public StateThread addThread() {
        StateThread thread = new StateThread(this);
        threads.add(thread);
        return thread;
    }

    public void update() {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).update();
        }
    }

    public void switchState(Consumer<Animator> newState) {
        Misc.debugPrint("Switching to state " + newState);
        threads.clear();
        newState.accept(this);
    }
}
