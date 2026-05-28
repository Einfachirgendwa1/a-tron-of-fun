import greenfoot.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * Ermöglicht es leicht Animationen abzuspielen mittels eines "State"-Systems.
 * Da {@link Animator} nicht von {@link greenfoot.Actor} erbt können die Animationen nicht automatisch mittels des {@link Actor#act()} Cycles aktualisiert werden.
 * Deshalb müssen die Objekte die {@code Animator} benutzen {@link #update()} manuell in ihrem {@link Actor#act()} aufrufen, um den {@code Animator} zu aktualisieren.
 * </p>
 *
 * <p>
 * Jeder Animator befindet sich immer in einem Zustand (state).
 * Zum Beispiel hat ein {@link GridBugsEnemy} drei verschiedene States:
 * <ol>
 * <li>{@link GridBugsEnemy#spawn(Animator)}</li>
 * <li>{@link GridBugsEnemy#run(Animator)}</li>
 * <li>{@link GridBugsEnemy#duplicate(Animator)}</li>
 * </ol>
 * Ein State ist nichts anderes als eine Funktion die einen {@link Animator} nimmt und {@code void} zurückgibt.
 * Diese Funktion wird ein einziges Mal ausgeführt, sobald zu diesem State gewechselt wird.
 * Innerhalb dieser Funktion können dann sogenannte {@link StateThread} mittels {@link #addThread()} erstellt werden.
 * </p>
 *
 * @author Faris
 * @see StateThread
 */
public class Animator {
    private final List<StateThread> threads = new ArrayList<>();

    public Animator(Consumer<Animator> activeState) {
        activeState.accept(this);
    }

    /**
     * Erstellt einen neuen {@link StateThread}.
     */
    public StateThread addThread() {
        StateThread thread = new StateThread();
        threads.add(thread);
        return thread;
    }

    /**
     * Spielt die aktuelle Animation ab.
     */
    public void update() {
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).update();
        }
    }

    /**
     * Wechselt zu einem neuen State.
     */
    public void switchState(Consumer<Animator> newState) {
        Misc.debugPrint("Switching to state " + newState);
        threads.clear();
        newState.accept(this);
    }
}
