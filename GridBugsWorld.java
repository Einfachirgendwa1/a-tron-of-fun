import java.util.ArrayList;

public class GridBugsWorld extends BaseWorld {
    private final GridBugsPlayer player;
    private final ArrayList<GridBugsEnemy> enemies = new ArrayList<>();

    public GridBugsWorld() {
        Misc.addObject(new GridBugsTarget(), Misc.worldWidth / 2, Misc.worldHeight / 2);

        Vector2[] gridBugPositions = {
                new Vector2(200, 120),
                new Vector2(200, 100),
                new Vector2(220, 100),
                new Vector2(220, 120)
        };

        for (Vector2 location : gridBugPositions) {
            int x = Math.round(location.x());
            int y = Math.round(location.y());

            GridBugsEnemy gridBug = Misc.addObject(new GridBugsEnemy(), x, y);
            enemies.add(gridBug);
        }

        player = Misc.addObject(new GridBugsPlayer(), 300, 100);
    }

    @Override
    public void act() {
        super.act();

        for (GridBugsEnemy gridBug : enemies) {
            gridBug.setTarget(player);

            if (player.touches(gridBug)) {
                Misc.exitMinigame();
            }
        }
    }
}
