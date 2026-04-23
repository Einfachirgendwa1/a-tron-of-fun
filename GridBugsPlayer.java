import greenfoot.World;

public class GridBugsPlayer extends PlayerDefaultMovement {
    public GridBugsPlayer(World world) {
        super(world);
    }

    @Override
    protected ImageHolder[] defaultImages() {
        return new ImageHolder[]{
                new ImageHolder("man_stand_body.png", 0, 0),
        };
    }

    @Override
    public void act() {
        super.act();
    }
}
