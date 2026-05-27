import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class FrameSurface extends Actor {
    private GreenfootImage surface;

    public GreenfootImage getSurface() {
        return surface;
    }

    public void blank() {
        surface = new GreenfootImage(600, 400);
    }
}
