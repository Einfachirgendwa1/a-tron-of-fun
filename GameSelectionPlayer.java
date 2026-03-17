import greenfoot.Greenfoot;

public class GameSelectionPlayer extends Player {
    private final GameSelection world;
    private int currentMinigame = -1;

    public GameSelectionPlayer(GameSelection world) {
        this.world = world;
    }

    @Override
    public void act() {
        if (Greenfoot.isKeyDown("space") && currentMinigame != -1) {
            world.enterMinigame(currentMinigame);
        }
    }

    @Override
    protected void moveLeft() {
        super.moveLeft();
        currentMinigame = 0;
    }

    @Override
    protected void moveUp() {
        super.moveUp();
        currentMinigame = 1;
    }

    @Override
    protected void moveRight() {
        super.moveRight();
        currentMinigame = 2;
    }

    @Override
    protected void moveDown() {
        super.moveDown();
        currentMinigame = 3;
    }
}
