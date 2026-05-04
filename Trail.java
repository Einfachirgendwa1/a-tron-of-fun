import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.Color;

/**
 * Write a description of class Trail here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Trail extends Actor {
    
    //Erzeugung eines beliebig gefärbten Quadrats, der als Schweif der LightCycles dient
    public Trail(Color color) {
        GreenfootImage img = new GreenfootImage(3, 3);
        img.setColor(color);
        img.fill();
        setImage(img);
    }

    public int getAge() {
        return age;
    }

    private int age = 0;
    /**
     * Act - do whatever the Trail wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public void act() {
        age++;
    }
}
