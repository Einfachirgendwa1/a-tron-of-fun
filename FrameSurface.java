import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class FrameSurface extends Actor {
    {
        blank();
    }

    public void blank() {
        GreenfootImage blank = new GreenfootImage(Misc.blank);
        blank.scale(Point2D.CANVAS.x(), Point2D.CANVAS.y());
        setImage(blank);
    }
}
