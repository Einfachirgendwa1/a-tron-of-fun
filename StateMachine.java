import greenfoot.Greenfoot;

import javax.annotation.CheckReturnValue;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StateMachine {
    private final List<Thread> threads = new ArrayList<>();
    private Consumer<StateMachine> activeState;
    private int frameCounter = 0;

    public StateMachine(Consumer<StateMachine> activeState) {
        this.activeState = activeState;
    }

    public void switchState(Consumer<StateMachine> activeState) {
        Misc.debugPrint("Switching to state " + activeState);

        this.activeState = activeState;
        frameCounter = 0;
        threads.clear();
    }

    public void onStart(Runnable onStart) {
        if (frameCounter == 0) {
            Misc.debugPrint("onStart: " + onStart);
            onStart.run();
        }
    }

    @CheckReturnValue
    public Thread addThread() {
        Thread thread = new Thread();
        onStart(() -> threads.add(thread));
        return thread;
    }

    public void update() {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).update();
        }

        activeState.accept(this);
        frameCounter++;
    }

    public class Thread {
        private final List<Instruction> instructions = new ArrayList<>();
        private int currentInstruction = 0;
        private boolean done;

        @CheckReturnValue
        public Thread wait(int frames) {
            instructions.add(new Instruction() {
                int remaining = frames;

                @Override
                public void run() {
                    Misc.debugPrint("Remaining frames: " + remaining);

                    if (remaining <= 0) {
                        currentInstruction++;
                        Misc.debugPrint("Done waiting " + frames + " frames");
                    } else {
                        done = true;
                        remaining--;
                        Misc.debugPrint("Wait " + (remaining + 1) + " -> " + remaining);
                    }
                }

                @Override
                public void reset() {
                    remaining = frames;
                }
            });

            return this;
        }

        @CheckReturnValue
        public Thread waitRandom(int min, int max) {
            return wait(Greenfoot.getRandomNumber(max - min) + min);
        }

        public Thread execute(Runnable code) {
            instructions.add(new Instruction() {
                @Override
                public void run() {
                    Misc.debugPrint("Executing " + code);
                    code.run();

                    currentInstruction++;
                }
            });

            return this;
        }

        public void switchState(Consumer<StateMachine> newState) {
            execute(() -> StateMachine.this.switchState(newState));
            done = true;
        }

        public void repeat() {
            execute(() -> {
                Misc.debugPrint("Repeating");
                for (Instruction instruction : instructions) {
                    instruction.reset();
                }

                currentInstruction = 0;
                done = true;
            });
        }

        public void update() {
            if (instructions.isEmpty()) return;

            done = false;
            while (!done && currentInstruction < instructions.size()) {
                instructions.get(currentInstruction).run();
            }

        }

        private abstract static class Instruction {
            public abstract void run();

            public void reset() {}
        }
    }
}
