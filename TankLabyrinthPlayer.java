import greenfoot.Greenfoot;

public class TankLabyrinthPlayer extends Player {
    public void act() {
        super.act();

        if (Greenfoot.isKeyDown("e")) {
            GameSelection.instance.exitMinigame();
        }
    }
}
