/**
 * Write a description of class LightCyclesWorld here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LightCyclesWorld extends BaseWorld {
    private static int gameCount = 0;

    /**
     * Constructor for objects of class LightCyclesWorld.
     *
     */
    public LightCyclesWorld() {
        super();

        LightCyclePlayer player = new LightCyclePlayer();
        addObject(player, 75, 350);
        LightCycleEnemy enemy = new LightCycleEnemy();
        addObject(enemy, 525, 50);
    }


    public void gameOver() {
        Misc.exitMinigame();
    }
}
