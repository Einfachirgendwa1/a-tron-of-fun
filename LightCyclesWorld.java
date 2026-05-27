/**
 * Write a description of class LightCyclesWorld here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LightCyclesWorld extends BaseWorld {
    public static int gridSize = 18;
    public LightCycleEnemy enemy;
    public LightCyclePlayer player;

    /**
     * Verwendung eines Grids, welches die sechsfache breite der Wand hat, um die Bewegung der LightCycles zu 
     * erleichtern und die Kollisionsprüfung zu vereinfachen.
     * Außerdem können Schweife nicht direkt nebeneinanderliegen, sodass das Spiel optisch aufgelockert wird.
     */

    /**
     * Constructor for objects of class LightCyclesWorld.
     *
     */
    public LightCyclesWorld() {
        super();

        player = Misc.addObject(new LightCyclePlayer(), 72, 360);
        enemy = Misc.addObject(new LightCycleEnemy(), 522, 54);
        // Dunkle Magie, die anscheinend funktioniert
        //Koordinaten durch 18 teilbar, damit die Startposition dem Grid entspricht
    }
}