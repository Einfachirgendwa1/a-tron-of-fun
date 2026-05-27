import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class FrameSurface extends Actor {
    {
        blank();
    }

    public void blank() {
        GreenfootImage blank = new GreenfootImage(Misc.blank);
        blank.scale(600, 400);
        setImage(blank);
    }
}
