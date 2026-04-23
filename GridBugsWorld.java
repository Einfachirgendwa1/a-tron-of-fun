import greenfoot.World;

public class GridBugsWorld extends World {
    private final GridBugsTarget target;
    private final GridBugsPlayer player;

    public GridBugsWorld() {
        super(600, 400, 1);

        target = new GridBugsTarget();
        addObject(target, 500, 300);

        player = new GridBugsPlayer(this);
        addObject(player, 100, 100);
    }

    @Override
    public void act() {
        super.act();

        if (player.touchesTarget(target)) {
            GameSelection.instance.exitMinigame();
        }
    }
}
