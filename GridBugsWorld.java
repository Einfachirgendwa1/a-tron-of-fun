import java.util.ArrayList;

public class GridBugsWorld extends BaseWorld {
    private final GridBugsTarget target = new GridBugsTarget();
    private final GridBugsPlayer player = MultipleImages.createActor(GridBugsPlayer::new, 100, 100);
    private final ArrayList<GridBugsEnemy> enemies = new ArrayList<>();

    {
        addObject(target, 500, 300);
        spawnGridBugs(new Vector2[]{
                new Vector2(200, 120),
                new Vector2(200, 100),
                new Vector2(220, 100),
                new Vector2(220, 120)
        });
    }

    private void spawnGridBugs(Vector2[] locations) {
        for (Vector2 location : locations) {
            GridBugsEnemy gridBug = new GridBugsEnemy();
            addObject(gridBug, Math.round(location.x()), Math.round(location.y()));
            enemies.add(gridBug);
        }
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

        if (player.touches(target)) {
            Misc.exitMinigame();
        }
    }
}
