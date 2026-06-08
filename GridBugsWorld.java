import greenfoot.Actor;
import greenfoot.Color;

import java.util.function.Consumer;

/**
 * Die Grid-Bugs Welt.
 */
public class GridBugsWorld extends BaseWorld {
    /**
     * Der Spieler.
     */
    private final GridBugsPlayer player;

    /**
     * Das Ziel.
     */
    private final GridBugsTarget target;

    /**
     * Ein Animator für die Siegesanimation.
     */
    private final Animator animator;

    /**
     * Die verbleibende Zeit bevor der Spieler stirbt.
     */
    private int timer = 1000;

    public GridBugsWorld() {
        animator = new Animator(this::gameplay);

        target = Misc.addObject(new GridBugsTarget(), Vector2D.MIDDLE);
        player = Misc.addObject(new GridBugsPlayer(), 300, 100);

        gridBugsBlock(new Vector2D(210, 110));
        gridBugsBlock(new Vector2D(440, 210));
        gridBugsBlock(new Vector2D(320, 330));
        gridBugsBlock(new Vector2D(110, 310));
        gridBugsBlock(new Vector2D(80, 240));

        Misc.addObject(new GridBugsBonus(), new Vector2D(140, 260));
        renderTimer();
    }

    /**
     * Erstellt 4 Grid-Bugs in einem Block.
     *
     * @param center Der Mittelpunkt des Blocks.
     */
    private void gridBugsBlock(Vector2D center) {
        Misc.addObject(
            new GridBugsEnemy(player),
            center.plus(Vector2D.UP.multiply(10)).plus(Vector2D.LEFT.multiply(10))
        );
        Misc.addObject(
            new GridBugsEnemy(player),
            center.plus(Vector2D.UP.multiply(10)).plus(Vector2D.RIGHT.multiply(10))
        );
        Misc.addObject(
            new GridBugsEnemy(player),
            center.plus(Vector2D.DOWN.multiply(10)).plus(Vector2D.LEFT.multiply(10))
        );
        Misc.addObject(
            new GridBugsEnemy(player),
            center.plus(Vector2D.DOWN.multiply(10)).plus(Vector2D.RIGHT.multiply(10))
        );
    }

    /**
     * Das ganz normale Spiel.
     *
     * @param animator Der Animator.
     */
    private void gameplay(Animator animator) {
        animator.addThread().execute(() -> {
            if (player.intersects(target)) {
                ScoreTracker.addScore(timer * 2);
                gameEnd(this::winAnimation);
            }

            if (timer == 0) lost("THE TIMER RAN OUT");

            renderTimer();
            timer--;
        }).repeat();
    }

    /**
     * Zeichnet den Timer.
     */
    private void renderTimer() {
        StringBuilder stringBuilder = new StringBuilder(Integer.toString(timer));
        while (stringBuilder.length() < 4) stringBuilder.insert(0, "0");

        drawOnce(stringBuilder.toString(), Misc.centeredAround(new Vector2D(300, 251)), 18, Color.YELLOW);
    }

    /**
     * Spielt die Animation ab, wenn der Spieler gewinnt.
     * Der Score und der Lichtstrahl werden angezeigt. Der Spieler wechselt zur Siegesanimation und sobald er das obere
     * Ende des Spielfelds erreicht wird {@link #won()} ausgeführt.
     */
    private void winAnimation(Animator animator) {
        showScore();
        target.win();
        GridBugsWinAnimation anim = Misc.addObject(new GridBugsWinAnimation(), Vector2D.MIDDLE);

        animator.addThread().waitFor(anim::isAtEdge).execute(this::won);
    }

    /**
     * Das Spielende und der State der nach Ende des Gameplay abgespielt werden soll.
     *
     * @param state Der neue State.
     */
    private void gameEnd(Consumer<Animator> state) {
        for (Actor actor : getObjects(Actor.class)) {
            if (actor instanceof GridBugsPlayer || actor instanceof Bullet) {
                removeObject(actor);
            } else if (actor instanceof BaseActor) {
                ((BaseActor) actor).freeze();
            }
        }

        animator.switchState(state);
    }

    @Override
    public void act() {
        super.act();
        animator.update();
    }
}
