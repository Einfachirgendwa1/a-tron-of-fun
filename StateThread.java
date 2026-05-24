import greenfoot.Greenfoot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StateThread {
    private final List<Instruction> instructions = new ArrayList<>();
    private final StateMachine stateMachine;
    private int currentInstruction = 0;
    private boolean done;

    public StateThread(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public StateThread wait(int frames) {
        instructions.add(new Wait(frames));
        return this;
    }

    public StateThread waitFor(Supplier<Boolean> condition) {
        instructions.add(new WaitFor(condition));
        return this;
    }

    public StateThread waitRandom(int min, int max) {
        return wait(Greenfoot.getRandomNumber(max - min) + min);
    }

    public StateThread execute(Runnable code) {
        instructions.add(new Execute(code));
        return this;
    }

    public void switchState(Consumer<StateMachine> newState) {
        execute(() -> stateMachine.switchState(newState));
        done = true;
    }

    public void repeat() {
        instructions.add(new Repeat());
    }

    public void repeat(Runnable code) {
        execute(code);
        repeat();
    }

    public void reset() {
        for (Instruction instruction : instructions) {
            instruction.reset();
        }

        currentInstruction = 0;
        done = true;
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

    private class Repeat extends Instruction {
        @Override
        public void run() {
            StateThread.this.reset();
        }
    }

    private class WaitFor extends Instruction {
        private final Supplier<Boolean> condition;

        public WaitFor(Supplier<Boolean> condition) {
            this.condition = condition;
        }

        @Override
        public void run() {
            if (condition.get()) {
                currentInstruction++;
            } else {
                done = true;
            }
        }
    }

    private class Wait extends Instruction {
        private final int frames;
        private int remaining;

        private Wait(int frames) {
            this.frames = frames;
            this.remaining = frames;
        }

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
    }

    private class Execute extends Instruction {
        private final Runnable code;

        public Execute(Runnable code) {
            this.code = code;
        }

        @Override
        public void run() {
            code.run();
            currentInstruction++;
        }
    }
}
