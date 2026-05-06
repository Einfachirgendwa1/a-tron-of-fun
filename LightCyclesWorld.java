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
    public static int gridSize = 18;

    /** 
     * Verwendung eines Grids, welches die sechsfache breite der Wand hat, um die Bewegung der LightCycles zu erleichtern und die Kollisionsprüfung zu vereinfachen.
     * Außerdem können Schweife nicht direkt nebeneinanderliegen, sodass das Spiel optisch aufgelockert wird.
     */

    /**
     * Constructor for objects of class LightCyclesWorld.
     *
     */
    public LightCyclesWorld() {
        super();

        player = MultipleImages.createActor(LightCyclePlayer::new, 72, 360);
        enemy  = MultipleImages.createActor(LightCycleEnemy::new, 522, 54);
        // Dunkle Magie, die anscheinend funktioniert 
        //Koordinaten durch 18 teilbar, damit die Startposition dem Grid entspricht
    }

    public void gameOver() {
        Misc.exitMinigame();
    }
}
