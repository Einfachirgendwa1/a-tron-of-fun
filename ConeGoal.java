import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * Write a description of class ConeGoal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ConeGoal extends Actor {

    public GreenfootImage goal = getImage();

    public ConeGoal(String image) { //Änderung des Bildes in Abhängigkeit von der Position im Ziel
        setImage(image);
        goal = getImage();
    }

    public void act() {
        super.act();
    }
}
