import greenfoot.Actor;
import greenfoot.Color;

import java.util.function.Consumer;

public class GridBugsWorld extends BaseWorld {
    private final GridBugsPlayer player;
    private final GridBugsTarget target;
    private final Animator animator;
    private int timer = 1000;

    public GridBugsWorld() {
        animator = new Animator(this::gameplay);

        target = Misc.addObject(new GridBugsTarget(), Vector2.MIDDLE);
        player = Misc.addObject(new GridBugsPlayer(), 300, 100);

        gridBugsBlock(new Vector2(210, 110));
        gridBugsBlock(new Vector2(440, 210));
        gridBugsBlock(new Vector2(320, 330));
        gridBugsBlock(new Vector2(110, 310));
        gridBugsBlock(new Vector2(80, 240));

        Misc.addObject(new GridBugsBonus(), new Vector2(140, 260));
        renderTimer();
    }

    private void gridBugsBlock(Vector2 center) {
        Misc.addObject(new GridBugsEnemy(player), center.plus(Vector2.UP.scale(10)).plus(Vector2.LEFT.scale(10)));
        Misc.addObject(new GridBugsEnemy(player), center.plus(Vector2.UP.scale(10)).plus(Vector2.RIGHT.scale(10)));
        Misc.addObject(new GridBugsEnemy(player), center.plus(Vector2.DOWN.scale(10)).plus(Vector2.LEFT.scale(10)));
        Misc.addObject(new GridBugsEnemy(player), center.plus(Vector2.DOWN.scale(10)).plus(Vector2.RIGHT.scale(10)));
    }

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

    private void renderTimer() {
        StringBuilder stringBuilder = new StringBuilder(Integer.toString(timer));
        while (stringBuilder.length() < 4) stringBuilder.insert(0, "0");

        drawOnce(stringBuilder.toString(), Misc.centeredAround(new Vector2(300, 251)), 18, Color.YELLOW);
    }

    private void winAnimation(Animator animator) {
        showScore();
        target.win();
        GridBugsWinAnimation anim = Misc.addObject(new GridBugsWinAnimation(), Vector2.MIDDLE);

        animator.addThread().waitFor(anim::isAtEdge).execute(this::won);
    }

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
