import greenfoot.Greenfoot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StateMachine {
    private final List<Thread> threads = new ArrayList<>();

    public StateMachine(Consumer<StateMachine> activeState) {
        activeState.accept(this);
    }

    public Thread addThread() {
        Thread thread = new Thread();
        threads.add(thread);
        return thread;
    }

    public void update() {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).update();
        }
    }

    private void switchState(Consumer<StateMachine> newState) {
        Misc.debugPrint("Switching to state " + newState);
        threads.clear();
        newState.accept(this);
    }


    public class Thread {
        private final List<Instruction> instructions = new ArrayList<>();
        private int currentInstruction = 0;
        private boolean done;


        public Thread wait(int frames) {
            instructions.add(new Instruction() {
                int remaining = frames;

                @Override
                public void run() {
                    if (remaining <= 0) {
                        currentInstruction++;
                        Misc.debugPrint("Done waiting " + frames + " frames");
                    } else {
                        done = true;
                        remaining--;
                    }
                }

                @Override
                public void reset() {
                    remaining = frames;
                }
            });

            return this;
        }


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
            instructions.add(new Instruction() {
                @Override
                public void run() {
                    Misc.debugPrint("Repeating");
                    for (Instruction instruction : instructions) {
                        instruction.reset();
                    }

                    currentInstruction = 0;
                    done = true;
                }
            });
        }

        public void repeat(Runnable code) {
            execute(code);
            repeat();
        }

        public void update() {
            if (instructions.isEmpty()) throw new RuntimeException("Thread without any instructions!");

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
