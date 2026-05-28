import greenfoot.Greenfoot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * <p>
 * Ein {@link StateThread} ist eine Liste an {@link Instruction}, die in Reihe ausgeführt werden.
 * Der {@code StateThread} merkt sich, bei welcher {@link Instruction} er sich beim letzten {@link #update()} befunden hat
 * und macht bei dieser beim nächsten {@link #update()} weiter.
 * Der Hauptvorteil des {@code StateThread} ist die einfache Verwendung der {@link #wait(int)},
 * {@link #waitRandom(int, int)} und {@link #waitFor(Supplier)} Methoden.
 * Diese ermöglichen es Code verzögert auszuführen, ohne manuell counter o.ä. verwenden zu müssen.
 * </p>
 *
 * <p>
 * Im Gegensatz zu echten Threads laufen {@code StateThread}s nicht wirklich parallel, sondern ihr
 * {@link #update()} muss manuell aufgerufen werden. Der Name kommt daher, dass es eine isolierte Liste an
 * Anweisungen ist, die sich immer merkt bei welcher Anweisung sie gerade ist.
 * </p>
 *
 * @author Faris
 * @see Animator
 */
public class StateThread {
    private final List<Instruction> instructions = new ArrayList<>();
    private int currentInstruction = 0;
    private boolean done;

    /**
     * Blockiert den {@link StateThread} für {@code frames} Frames.
     */
    public StateThread wait(int frames) {
        instructions.add(new Wait(frames));
        return this;
    }

    /**
     * Blockiert den {@link StateThread} bis {@code condition} true wird.
     */
    public StateThread waitFor(Supplier<Boolean> condition) {
        instructions.add(new WaitFor(condition));
        return this;
    }

    /**
     * Blockiert den {@link StateThread} für mindestens {@code min} und maximal {@code max} Frames.
     */
    public StateThread waitRandom(int min, int max) {
        return wait(Greenfoot.getRandomNumber(max - min) + min);
    }

    /**
     * Führt {@code code} aus.
     */
    public StateThread execute(Runnable code) {
        instructions.add(new Execute(code));
        return this;
    }

    /**
     * Springt zur ersten Anweisung zurück, sobald der Thread am Ende angekommen ist.
     */
    public void repeat() {
        instructions.add(new Repeat());
    }

    /**
     * Wiederholt {@code code} für immer.
     */
    public void repeat(Runnable code) {
        execute(code);
        repeat();
    }

    /**
     * Springt zur ersten Anweisung zurück.
     */
    public void reset() {
        for (Instruction instruction : instructions) {
            instruction.reset();
        }

        currentInstruction = 0;
        done = true;
    }

    /**
     * Führt die aktuelle Anweisung aus.
     */
    public void update() {
        if (instructions.isEmpty()) throw new RuntimeException("Thread without any instructions!");

        done = false;
        while (!done && currentInstruction < instructions.size()) {
            instructions.get(currentInstruction).run();
        }
    }

    /**
     * {@link #run()} wird ausgeführt, sobald der {@link StateThread} diese {@code Instruction} erreicht.
     * {@link #reset()} wird ausgeführt, wenn der {@link StateThread} zurück an den Anfang springt.
     */
    private abstract static class Instruction {
        public abstract void run();

        public void reset() {}
    }

    /**
     * @see StateThread#repeat()
     * @see StateThread#repeat(Runnable)
     */
    private class Repeat extends Instruction {
        @Override
        public void run() {
            StateThread.this.reset();
        }
    }

    /**
     * @see StateThread#waitFor(Supplier)
     */
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

    /**
     * @see StateThread#wait(int)
     * @see StateThread#waitRandom(int, int)
     */
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

    /**
     * @see StateThread#execute(Runnable)
     */
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
