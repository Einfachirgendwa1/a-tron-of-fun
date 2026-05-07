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

        //Erzeugung der Bahngrenzen an jeder 32. y-Koordinate, da die Bilder 32*32 groß sind

        for(int i = 160; i <= 400 ; i = i + 32){
            border = new ConeBorder();
            addObject(border, 200, i);
        }

        for(int i = 160; i <= 400 ; i = i + 32){
            border = new ConeBorder();
            addObject(border, 400, i);
        }
    }
}
