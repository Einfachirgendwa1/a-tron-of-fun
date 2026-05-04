/**
 * Write a description of class LightCyclesWorld here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LightCyclesWorld extends BaseWorld {
    private static int gameCount = 0;
    public LightCycleEnemy enemy;
    public LightCyclePlayer player;

    /**
     * Constructor for objects of class LightCyclesWorld.
     *
     */
    public LightCyclesWorld() {
        super();

        player = MultipleImages.createActor(LightCyclePlayer::new, 75, 350);
        enemy  = MultipleImages.createActor(LightCycleEnemy::new,  525, 50);
        // Dunkle Magie, die anscheinend funktioniert 
    }

    public void gameOver() {
        Misc.exitMinigame();
    }
}
