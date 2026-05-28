import greenfoot.Actor;
import greenfoot.Color;

import java.util.function.Consumer;

public class GridBugsWorld extends BaseWorld {
    private final GridBugsPlayer player;
    private final GridBugsTarget target;
    private final StateMachine stateMachine;
    private int timer = 700;

    public GridBugsWorld() {
        stateMachine = new StateMachine(this::gameplay);

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

    private void gameplay(StateMachine stateMachine) {
        stateMachine.addThread().execute(() -> {
            for (GridBugsEnemy gridBug : getObjects(GridBugsEnemy.class)) {
                if (player.intersects(gridBug)) {
                    lost();
                }
            }

            if (player.intersects(target.getTarget())) {
                ScoreTracker.addScore(timer * 2);
                gameEnd(this::winAnimation);
            }

            if (timer == 0) lost();

            renderTimer();
            timer--;
        }).repeat();
    }

    private void renderTimer() {
        StringBuilder stringBuilder = new StringBuilder(Integer.toString(timer));
        while (stringBuilder.length() < 4) stringBuilder.insert(0, "0");

        drawOnce(stringBuilder.toString(), Misc.centeredAround(new Vector2(300, 251)), 18, Color.YELLOW);
    }

    private void winAnimation(StateMachine stateMachine) {
        showScore();
        target.win();
        GridBugsWinAnimation anim = Misc.addObject(new GridBugsWinAnimation(), Vector2.MIDDLE);

        stateMachine.addThread().waitFor(anim::isAtEdge).execute(this::won);
    }

    private void gameEnd(Consumer<StateMachine> state) {
        for (Actor actor : getObjects(Actor.class)) {
            if (actor instanceof GridBugsPlayer || actor instanceof Bullet) {
                removeObject(actor);
            } else if (actor instanceof BaseActor) {
                ((BaseActor) actor).freeze();
            }
        }

        stateMachine.switchState(state);
    }

    @Override
    public void act() {
        super.act();
        stateMachine.update();
    }
}
