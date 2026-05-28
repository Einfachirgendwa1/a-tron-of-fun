import greenfoot.Actor;
import greenfoot.Greenfoot;

public class GameSelectionPlayer extends HumanoidPlayer {
    private final MiniGame[] miniGames;
    private MiniGame targetMinigame = null;

    public GameSelectionPlayer(MiniGame[] miniGames) {
        allowShooting = false;
        speed = 2f;
        this.miniGames = miniGames;
    }

    private Vector2 targetPosition() {
        return targetMinigame != null ? targetMinigame.position() : Vector2.MIDDLE;
    }

    @Override
    protected boolean isMoving() {
        return !position().equals(targetPosition());
    }

    @Override
    public void act() {
        super.act();

        Vector2 target = targetPosition();
        for (int i = 0; i < speed; i++) {
            move(towards(target));
        }

        if (Greenfoot.isKeyDown("space") && !isMoving()) {
            Actor intersectingMinigame = getOneIntersectingObject(MiniGame.class);
            if (intersectingMinigame != null) {
                ((MiniGame) intersectingMinigame).loadWorld();
            }
        }
    }

    public void reset() {
        teleport(Vector2.MIDDLE);
        targetMinigame = null;
    }

    @Override
    protected void moveUp() {
        targetMinigame = miniGames[1];
    }

    @Override
    protected void moveDown() {
        targetMinigame = miniGames[3];
    }

    @Override
    protected void moveLeft() {
        targetMinigame = miniGames[0];
    }

    @Override
    protected void moveRight() {
        targetMinigame = miniGames[2];
    }
}
