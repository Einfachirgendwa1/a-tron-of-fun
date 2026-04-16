import greenfoot.Greenfoot;
import greenfoot.World;

public class TankLabyrinthPlayer extends Player {
    public TankLabyrinthPlayer(World world) {
        super(world);
    }

    @Override
    protected ImageHolder[] defaultImageHolders() {
        return new ImageHolder[]{
                new ImageHolder("man_stand_body.png", 0, 0),
        };
    }

    public void act() {
        super.act();

        if (Greenfoot.isKeyDown("e")) {
            GameSelection.instance.exitMinigame();
        }
    }
}
