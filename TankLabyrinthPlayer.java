import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class TankLabyrinthPlayer extends Player {
    @Override
    protected ImageHolder[] images() {
        GreenfootImage image = new GreenfootImage("tank_player.png");
        Point2D scaled = new Point2D(new Vector2D(image.getWidth(), image.getHeight()).multiply(.75f));
        image.scale(scaled.x(), scaled.y());

        return new ImageHolder[]{new ImageHolder(image)};
    }

    public void act() {
        super.act();

        if (Greenfoot.isKeyDown("e")) {
            Misc.exitMinigame();
        }
    }
}
