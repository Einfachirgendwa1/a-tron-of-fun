import greenfoot.Actor;
import greenfoot.Color;

import java.util.ArrayList;
import java.util.function.Consumer;

public class GridBugsWorld extends BaseWorld {
    private final GridBugsPlayer player;
    private final GridBugsTarget gridBugsTarget;
    private final ArrayList<GridBugsEnemy> enemies = new ArrayList<>();
    private final StateMachine stateMachine;

    public GridBugsWorld() {
        stateMachine = new StateMachine(this::gameplay);
        gridBugsTarget = Misc.addObject(new GridBugsTarget(), Misc.worldWidth / 2, Misc.worldHeight / 2);

        Vector2[] gridBugPositions = {
            new Vector2(200, 120), new Vector2(200, 100), new Vector2(220, 100), new Vector2(220, 120)
        };

        for (Vector2 gridBugPosition : gridBugPositions) {
            spawnEnemy(gridBugPosition);
        }

        player = Misc.addObject(new GridBugsPlayer(), 300, 100);
    }

    private void gameplay(StateMachine stateMachine) {
        stateMachine.addThread().execute(() -> {
            for (GridBugsEnemy gridBug : enemies) {
                gridBug.setTarget(player);

                if (player.touches(gridBug)) {
                    gameEnd(endScreen("You lost!"));
                }
            }

            if (player.touches(gridBugsTarget.getTarget())) {
                gameEnd(this::winAnimation);
            }
        }).repeat();
    }

    private void winAnimation(StateMachine stateMachine) {
        gridBugsTarget.win();
        GridBugsWinAnimation anim = Misc.addObject(new GridBugsWinAnimation(), Vector2.MIDDLE);
        stateMachine.addThread().waitFor(anim::isAtEdge).switchState(endScreen("You won!"));
    }

    private Consumer<StateMachine> endScreen(String text) {
        return (stateMachine) -> {
            getObjects(Actor.class).forEach(this::removeObjectUnchecked);

            Misc.drawText(text, Misc.centeredAround(Vector2.MIDDLE), 50, Color.BLUE);
            stateMachine.addThread().wait(120).execute(Misc::exitMinigame);
        };
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
        stateMachine.update();
    }
}
