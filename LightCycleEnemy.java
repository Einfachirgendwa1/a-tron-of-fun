import greenfoot.World;

/**
 * Write a description of class LightCycleEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LightCycleEnemy extends Enemy {
    private int direction = 4;
    public LightCycleEnemy(World world) {
        super(world);
        getImage().rotate(-90);
    }

    @Override
    protected ImageHolder[] defaultImages() {
        return new ImageHolder[]{
        };
    }

    /**
     * Act - do whatever the LightCycleEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        int result = direction % 4;

        if        (result == 3 || result == -1) {
            moveDown();
        } else if (result == 2 || result == -2) {
            moveRight();
        } else if (result == 1 || result == -3) {
            moveUp();
        } else if (result == 0) {
            moveLeft();
        }
    }
}
