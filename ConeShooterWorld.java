import greenfoot.Greenfoot;
/**
 * Write a description of class LightConeWorld here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ConeShooterWorld extends BaseWorld {

    /**
     * Constructor for objects of class LightConeWorld.
     *
     */
    public ConeShooterWorld() {
        super();
        ConeBorder border = new ConeBorder();
        ConeGoal goal = new ConeGoal();


        //Erzeugung der Bahngrenzen an jeder 32. y-Koordinate, da die Bilder 32*32 groß sind

        for(int y = 160; y <= 400 ; y = y + 32){
            border = new ConeBorder();
            addObject(border, 192, y);
        }

        for(int y = 160; y <= 400 ; y = y + 32){
            border = new ConeBorder();
            addObject(border, 416, y);
            border.border.rotate(180);
        }

        goal = new ConeGoal();
        addObject(goal, 224, 128);
    }
}
