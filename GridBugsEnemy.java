import greenfoot.World;

/**
 * Write a description of class GridBugsEnemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GridBugsEnemy extends Enemy {
    public GridBugsEnemy(World world) {
        super(world);
    }

    @Override
    protected ImageHolder[] defaultImages() {
        return new ImageHolder[]{
                new ImageHolder("man_stand_body.png", 0, 0),
        };
    }

    /**
     * Act - do whatever the GridBugsEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        // Add your action code here.
    }
}
