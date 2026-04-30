import greenfoot.Greenfoot;

public class TankLabyrinthPlayer extends PlayerDefaultMovement {
    @Override
    protected ImageHolder[] defaultImages() {
        return new ImageHolder[]{
                new ImageHolder("man_stand_body.png", 0, 0),
        };
    }

    public void act() {
        super.act();

        if (Greenfoot.isKeyDown("e")) {
            Misc.exitMinigame();
        }
    }
}
