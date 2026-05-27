import greenfoot.Actor;
import greenfoot.Color;

import java.util.ArrayList;
import java.util.function.Consumer;

public class GridBugsWorld extends BaseWorld {
    private final GridBugsPlayer player;
    private final GridBugsTarget gridBugsTarget;
    private final ArrayList<GridBugsEnemy> enemies = new ArrayList<>();
    private final StateMachine stateMachine;
    private int timer = 700;

    public GridBugsWorld() {
        stateMachine = new StateMachine(this::gameplay);
        gridBugsTarget = Misc.addObject(new GridBugsTarget(), Vector2.MIDDLE);

        Vector2[] gridBugPositions = {
                new Vector2(200, 120), new Vector2(200, 100), new Vector2(220, 100), new Vector2(220, 120)
        };

        for (Vector2 gridBugPosition : gridBugPositions) {
            spawnEnemy(gridBugPosition);
        }

        player = Misc.addObject(new GridBugsPlayer(), 300, 100);
        renderTimer();
    }

    private void gameplay(StateMachine stateMachine) {
        stateMachine.addThread().execute(() -> {
            for (GridBugsEnemy gridBug : enemies) {
                gridBug.setTarget(player);

                if (player.intersects(gridBug)) {
                    lost();
                }
            }

            if (player.intersects(gridBugsTarget.getTarget())) {
                ScoreTracker.addScore(timer * 2);
                gameEnd(this::winAnimation);
            }

            if (timer == 0) lost();

            renderTimer();
            timer--;
        }).repeat();
    }

    private void renderTimer() {
        StringBuilder t = new StringBuilder(Integer.toString(timer));
        while (t.length() < 4) t.insert(0, "0");

        drawOnce(t.toString(), Misc.centeredAround(new Vector2(300, 251)), 18, Color.YELLOW);
    }

    private void winAnimation(StateMachine stateMachine) {
        showScore();
        gridBugsTarget.win();
        GridBugsWinAnimation anim = Misc.addObject(new GridBugsWinAnimation(), Vector2.MIDDLE);

        stateMachine.addThread().waitFor(anim::isAtEdge).execute(() -> {
            blank();

            drawForever("You won!", Misc.centeredAround(Vector2.MIDDLE), 50, Color.BLUE);
            stateMachine.addThread().wait(40).execute(Misc::exitMinigame);
        });
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

    public void spawnEnemy(IGetVector2 pos) {
        enemies.add(Misc.addObject(new GridBugsEnemy(this), pos));
    }

    @Override
    public void act() {
        super.act();
        stateMachine.update();
    }
}
