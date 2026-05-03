import greenfoot.Greenfoot;

public class TankLabyrinthPlayer extends Player {
    @Override
    protected ImageHolder[] images() {
        return new ImageHolder[]{
                new ImageHolder("tank_player.png", 0, 0),
        };
    }

    public void act() {
        super.act();

        if (Greenfoot.isKeyDown("e")) {
            Misc.exitMinigame();
        }
    }
}
