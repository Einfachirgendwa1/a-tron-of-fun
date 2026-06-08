import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * Ein leeres Bild was als rendering surface dient.
 */
public class FrameSurface extends Actor {
    {
        blank();
    }

    /**
     * Entfernt alles, was auf die surface gerendert wurde.
     */
    public void blank() {
        GreenfootImage blank = new GreenfootImage(Misc.blank);
        blank.scale(Point2D.CANVAS.x(), Point2D.CANVAS.y());
        setImage(blank);
    }
}
