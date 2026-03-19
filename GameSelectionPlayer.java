import greenfoot.Greenfoot;

public class GameSelectionPlayer extends Player {
    private final GameSelection world;
    private int currentMinigame = -1;

    public GameSelectionPlayer(GameSelection world) {
        this.world = world;
    }

    @Override
    public void act() {
        super.act();
        if (Greenfoot.isKeyDown("space") && currentMinigame != -1) {
            world.enterMinigame(currentMinigame);
        }
    }

    @Override
    protected void moveLeft() {
        setLocation(150, 200);
        currentMinigame = 0;
    }

    @Override
    protected void moveUp() {
        setLocation(300, 100);
        currentMinigame = 1;
    }

    @Override
    protected void moveRight() {
        setLocation(450, 200);
        currentMinigame = 2;
    }

    @Override
    protected void moveDown() {
        setLocation(300, 300);
        currentMinigame = 3;
    }
}
