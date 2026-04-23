import greenfoot.World;

/**
 * Write a description of class LightCyclesWorld here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LightCyclesWorld extends World {
    private static int gameCount = 0;
    /**
     * Constructor for objects of class LightCyclesWorld.
     *
     */
    public LightCyclesWorld() {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        LightCyclePlayer player = new LightCyclePlayer(this);
        addObject(player, 75, 350);
        LightCycleEnemy enemy = new LightCycleEnemy(this);
        addObject(enemy, 525, 50);
    }
}
